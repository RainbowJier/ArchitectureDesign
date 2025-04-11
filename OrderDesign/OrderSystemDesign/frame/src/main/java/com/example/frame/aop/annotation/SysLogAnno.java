package com.example.frame.aop.annotation;

import com.example.frame.enums.OperationEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:自定义注解
 * @Author:Rainbow
 * @CreateTime:2023/12/2017:51
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SysLogAnno {

    /**
     * 操作描述
     */
    String description();

    /**
     * 操作类型
     */
    OperationEnum operateType();
}
