package com.example.frame.aop;

import com.alibaba.fastjson.JSON;
import com.example.frame.aop.annotation.SysLogAnno;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
@Aspect
@Slf4j
@Order(2)
public class SysLogAop {
    /**
     * 设置切入点d的注解
     */
    @Pointcut("@annotation(com.example.frame.aop.annotation.SysLogAnno)")
    public void pt() {

    }

    /**
     * 环绕通知
     *
     * @param joinPoint ： 可以拿到被增强方法的信息
     * @return
     * @throws Throwable
     */
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;
        try {
            // 获取当前请求的属性
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();

            // 获取注解对象
            SysLogAnno systemlog = getSystemlog(joinPoint);

            handleBefore(request, joinPoint, systemlog);

            ret = joinPoint.proceed(); // 执行目标方法

            handleAfter(ret, joinPoint, systemlog, request);

        } finally {
            log.info("=======================End=======================" + System.lineSeparator());
        }
        return ret;
    }


    /**
     * 处理请求前，打印日志信息
     */
    private void handleBefore(HttpServletRequest request, ProceedingJoinPoint joinPoint, SysLogAnno systemlog) {
        log.info("======================Start======================");
        log.info("访问IP    : {}", request.getRemoteHost());
        log.info("请求URL   : {}", request.getRequestURL());
        log.info("请求方式   : {}", request.getMethod());
        log.info("接口描述   : {}", systemlog.description());
        log.info("请求类名   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("传入参数   : {}", JSON.toJSONString(joinPoint.getArgs()));
        log.info("时间     : {}", LocalDateTime.now());

    }


    private void handleAfter(Object ret, ProceedingJoinPoint joinPoint, SysLogAnno systemlog, HttpServletRequest request) {
        log.info("返回参数   : {}", JSON.toJSONString(ret));
        insertLog(systemlog, joinPoint, request, ret);
    }

    private void insertLog(SysLogAnno sysLogAnno, ProceedingJoinPoint joinPoint, HttpServletRequest request, Object result) {
        String ip = request.getRemoteHost();
        String desc = sysLogAnno.description();    // 操作描述
        String operateType = sysLogAnno.operateType().toString();
        String params = JSON.toJSONString(joinPoint.getArgs());
        // TODO: 实际插入数据库、消息队列或其他持久化操作



    }

    /**
     * 获取注解对象
     */
    private SysLogAnno getSystemlog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(SysLogAnno.class);
    }

}
