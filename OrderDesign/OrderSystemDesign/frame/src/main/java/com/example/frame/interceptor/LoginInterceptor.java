package com.example.frame.interceptor;

import com.example.frame.enums.BizCodeEnum;
import com.example.frame.model.entity.LoginUser;
import com.example.frame.utils.CommonUtil;
import com.example.frame.utils.JWTUtil;
import com.example.frame.model.JsonData;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

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

        // 获取token
        String accessToken = request.getHeader("token");
        if (StringUtils.isBlank(accessToken)) {
            // 有些情况，请求头中token可能为空，就从参数中获取token
            accessToken = request.getParameter("token");
        }

        if (StringUtils.isNotBlank(accessToken)) {
            Claims claims = JWTUtil.checkJWT(accessToken);
            if (claims == null) {
                // 未登录
                CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
                return false;
            }

            // 用户已经登录
            Long accountNo = Long.valueOf(claims.get("account_no").toString());
            String headImg = claims.get("head_img").toString();
            String username = claims.get("username").toString();
            String mail = claims.get("mail").toString();
            String phone = claims.get("phone").toString();
            String auth = claims.get("auth").toString();

            LoginUser loginUser = LoginUser.builder()
                    .accountNo(accountNo)
                    .headImg(headImg)
                    .username(username)
                    .mail(mail)
                    .phone(phone)
                    .auth(auth)
                    .build();

            //request.setAttribute("loginUser", loginUser);

            // 将参数传入当前线程
            threadLocal.set(loginUser);

            return true;
        }
        CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
        return false;
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
