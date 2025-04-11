package com.example.frame.aop.annotation;

import java.lang.annotation.*;

/**
 * 自定义防重提交
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RepeatSubmit {

}
