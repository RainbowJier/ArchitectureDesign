package com.example.frame.aop;

import com.example.frame.aop.annotation.RepeatSubmit;
import com.example.frame.constant.RedisKey;
import com.example.frame.enums.BizCodeEnum;
import com.example.frame.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class RepeatSubmitAop {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 切点
     */
    @Pointcut("@annotation(com.example.frame.aop.annotation.RepeatSubmit)")
    public void pt() {
    }


    @Around("pt()")
    public Object repeatSubmit(ProceedingJoinPoint pjp) throws Throwable {
        Object ret;

        // 获取当前请求的属性
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取注解对象
        RepeatSubmit repeatSubmit = getRepeatSubmit(pjp);

        handleBefore(request);

        ret = pjp.proceed();

        handleAfter(request);

        return ret;
    }


    /**
     * 请求处理前
     */
    public void handleBefore(HttpServletRequest request) {
        // 账号
        String accountNo = "13599829312";
        // token 令牌
        String requestToken = request.getHeader("request-token");

        // 防重标志
        boolean flag;

        // 校验令牌
        if (StringUtils.isBlank(requestToken)) {
            throw new BizException(BizCodeEnum.ORDER_CONFIRM_TOKEN_EQUAL_FAIL);
        }

        // 校验令牌是否存在
        String key = String.format(RedisKey.SUBMIT_ORDER_TOKEN_KEY, accountNo, requestToken);
        flag = redisTemplate.delete(key);

        // 删除失败，说明重复提交
        if (!flag) {
            throw new BizException(BizCodeEnum.ORDER_CONFIRM_REPEAT);
        }
    }

    /**
     * 请求处理后
     */
    public void handleAfter(HttpServletRequest request) {

    }

    /**
     * 获取注解对象
     */
    private RepeatSubmit getRepeatSubmit(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        return methodSignature.getMethod().getAnnotation(RepeatSubmit.class);
    }
}
