package com.example.system.config;

import com.example.common.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Component
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/system/*/**")
                .excludePathPatterns(
                        "/system/register","/system/login",
                        "/system/captcha","/system/sendCode","/system/resetPwd"
                );
    }
}
