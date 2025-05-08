package com.example.system.aop.annotation;


import com.example.common.enums.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
    OperationType operateType();
}
