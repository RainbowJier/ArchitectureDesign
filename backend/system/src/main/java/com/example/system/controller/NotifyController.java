package com.example.system.controller;


import com.example.common.constant.RedisKey;
import com.example.common.enums.BizCode;
import com.example.common.enums.OperationType;
import com.example.common.enums.SendCode;
import com.example.common.exception.BizException;
import com.example.common.util.CommonUtil;
import com.example.common.util.JsonData;
import com.example.system.aop.annotation.LimitFlowWindowSizeAnno;
import com.example.system.aop.annotation.SysLogAnno;
import com.example.system.controller.request.SendCodeRequest;
import com.example.system.service.NotifyService;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.network.Send;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Common verify module.
 */
@Slf4j
@RestController
@RequestMapping("/system")
public class NotifyController {

    @Resource
    private Producer captchaProducer;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private NotifyService notifyService;

    @Value("${captcha.expiredTime}")
    private Long CAPTCHA_EXPIRE_TIME;

    /**
     * Send Captcha code
     */
    @SysLogAnno(description = "Captcha code", operateType = OperationType.OTHER)
    @GetMapping("/captcha")
    public void getCaptcha(@RequestParam("captchaKeyType") String captchaKeyType, HttpServletRequest request, HttpServletResponse response) {
        String key = getCaptchaKey(request, captchaKeyType);
        String value = captchaProducer.createText();
        log.info("Captcha code: {}", value);

        // 保存到Redis
        redisTemplate.opsForValue()
                .set(key, value, CAPTCHA_EXPIRE_TIME, TimeUnit.SECONDS);

        BufferedImage bufferedImage = captchaProducer.createImage(value);
        try {
            response.setContentType("image/jpeg"); // 或 image/png 视你用的格式而定
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);

            outputStream.flush();
        } catch (IOException e) {
            log.error("Send captcha error: {}", e.getMessage());
            throw new BizException(BizCode.CODE_CAPTCHA_EXCEPTION);
        }
    }

    /**
     * Send code.
     */
    @SysLogAnno(description = "Send phone / mail code", operateType = OperationType.OTHER)
    @LimitFlowWindowSizeAnno(behavior = "emailCode", windowSize = 1, requestLimit = 3)
    @PostMapping("sendCode")
    public JsonData sendCode(SendCodeRequest sendCodeRequest, HttpServletRequest request) throws MessagingException {
        String keyType = sendCodeRequest.getCaptchaKeyType();

        // Obtain the captcha code.
        String key = getCaptchaKey(request, keyType);
        String codeCache = (String) redisTemplate.opsForValue().get(key);
        String code = sendCodeRequest.getCode();

        if (codeCache != null && code != null) {
            if (codeCache.equalsIgnoreCase(code)) {

                // delete captcha code.
                redisTemplate.delete(key);

                notifyService.sendCode(getType(keyType), sendCodeRequest.getTo());

                return JsonData.buildSuccess();
            }
        }

        return JsonData.buildError("图形验证码错误");
    }


    private String getCaptchaKey(HttpServletRequest request, String captchaKeyType) {
        String ip = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");

        String name = getType(captchaKeyType);

        return String.format(RedisKey.CHECK_CODE_CAPTCHA_KEY, name, CommonUtil.MD5(ip + userAgent));
    }

    private String getType(String str){
        return switch (str) {
            case "register" -> SendCode.USER_REGISTER.name();
            case "login" -> SendCode.USER_LOGIN.name();
            case "resetPassword" -> SendCode.USER_RESET_PASSWORD.name();
            default -> "";
        };
    }


}
