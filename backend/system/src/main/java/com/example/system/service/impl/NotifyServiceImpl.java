package com.example.system.service.impl;

import com.example.common.constant.RedisKey;
import com.example.common.enums.BizCode;
import com.example.common.enums.SendCode;
import com.example.common.util.CheckUtil;
import com.example.common.util.CommonUtil;
import com.example.common.util.JsonData;
import com.example.system.component.EmailComponent;
import com.example.system.component.SmsComponent;
import com.example.system.config.SmsConfig;
import com.example.system.service.NotifyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author RainbowJier
 * @since 2024-08-17
 */
@Service
@Slf4j
public class NotifyServiceImpl implements NotifyService {

    @Resource
    private EmailComponent emailComponent;

    @Resource
    private SmsComponent smsComponent;

    @Resource
    private SmsConfig smsConfig;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    private static final int CODE_EXPIRE = 60 * 1000 * 10;

    /**
     * Send Email code.
     */
    @Override
    public JsonData sendCode(String keyType, String to) {
        // Check whether the code has been sent within 60 seconds.
        String key = String.format(RedisKey.CHECK_CODE_KEY, keyType, to);
        String codeValue = (String) redisTemplate.opsForValue().get(key);

        if (StringUtils.isNotBlank(codeValue)) {
            Long ttl = Long.parseLong(codeValue.split("_")[1]);
            Long currentTimeStamp = CommonUtil.getCurrentTimestamp();
            long leftTime = currentTimeStamp - ttl;

            if (leftTime < (1000 * 60)) {
                log.info("The code has been sent to the phone or email within 60 seconds, " +
                        "the remaining time is {} seconds", leftTime);
                return JsonData.buildError(BizCode.CODE_LIMITED);
            }
        }

        // Store the code in Redis and set the expiration time.
        String randomCode = CommonUtil.getRandomCode(6);
        String value = randomCode + "_" + CommonUtil.getCurrentTimestamp();
        log.info("email code: {}", value);
        redisTemplate.opsForValue().set(key, value, CODE_EXPIRE, TimeUnit.MILLISECONDS);

        // Send code.
        if (CheckUtil.isEmail(to)) {
            emailComponent.send(to, randomCode);
        }

        // Phone
        if (CheckUtil.isPhone(to)) {
            // Send SMS
            smsComponent.send(to, smsConfig.getTemplateId(), randomCode);
        }
        return JsonData.buildSuccess();
    }


    @Override
    public boolean checkCode(String keyType, String to, String code) {
        // Get the code from Redis.
        String cacheKey = String.format(RedisKey.CHECK_CODE_KEY, keyType, to);
        String cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);

        if (StringUtils.isNotBlank(cacheValue)) {
            String cacheCode = cacheValue.split("_")[0];
            if (cacheCode.equalsIgnoreCase(code)) {
                redisTemplate.delete(cacheKey);
                return true;
            }
        }

        return false;
    }
}
