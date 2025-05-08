package com.example.common.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.example.common.entity.LoginUser;
import com.example.common.enums.BizCode;
import com.example.common.util.JWTUtil;
import com.example.common.util.CommonUtil;
import com.example.common.util.JsonData;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: RainbowJier
 * @Description: 👺🐉😎通用登录拦截器
 * @Date: 2024/10/5 20:34
 * @Version: 1.0
 */

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 处理请求前拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 前后端分离会有option刺探请求，查看网络是否正常
        if (HttpMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }

        String token = request.getHeader("Authorization");
        String loginId = (String) StpUtil.getLoginIdByToken(token);

        if(loginId == null){
            CommonUtil.sendJsonMessage(response, JsonData.buildError(BizCode.UNAUTHORIZED));
            return false;
        }

        threadLocal.set(Long.valueOf(loginId));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 当前线程完成后，移除
        threadLocal.remove();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
