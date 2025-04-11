package com.example.frame.config;

import com.example.frame.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Component
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 添加权限校验拦截器
        registry.addInterceptor(new LoginInterceptor())
                // 拦截规则
                .addPathPatterns("")
                // 不拦截规则
                .excludePathPatterns("");
    }
}
