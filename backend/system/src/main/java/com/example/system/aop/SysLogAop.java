package com.example.system.aop;

import com.alibaba.fastjson.JSON;
import com.example.common.util.JsonUtil;
import com.example.system.aop.annotation.SysLogAnno;
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
import java.util.HashMap;

@Component
@Aspect
@Slf4j
@Order(1)
public class SysLogAop {
    @Pointcut("@annotation(com.example.system.aop.annotation.SysLogAnno)")
    public void pt() {

    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;
        try {
            // 获取当前请求的属性
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();

            // 获取注解对象
            SysLogAnno systemlog = getSystemlog(joinPoint);

            // 执行前
            handleBefore(request, joinPoint, systemlog);

            // 响应结果
            ret = joinPoint.proceed();

            // 执行后
            handleAfter(ret, joinPoint, systemlog, request);

        } finally {
            log.info("=======================End=======================");
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
    }


    private void handleAfter(Object ret, ProceedingJoinPoint joinPoint, SysLogAnno systemlog, HttpServletRequest request) {
        try {
            String requestArgs = JSON.toJSONString(ret);
            log.info("返回参数   : {}", requestArgs);

            // 新增操作日志
            insertLog(systemlog, joinPoint, request, ret);
        }catch (Exception e){
            log.info("返回参数   : null");
        }

    }

    private void insertLog(SysLogAnno sysLogAnno, ProceedingJoinPoint joinPoint, HttpServletRequest request, Object result) {
        String ip = request.getRemoteHost();
        String desc = sysLogAnno.description();    // 操作描述
        String operateType = sysLogAnno.operateType().toString();


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
