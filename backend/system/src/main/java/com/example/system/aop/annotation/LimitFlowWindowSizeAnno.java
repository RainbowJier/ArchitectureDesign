package com.example.system.aop.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LimitFlowWindowSizeAnno {
    String behavior() default "";

    /**
     * Time scope, the unit is minute.
     */
    long windowSize() default 1;


    /**
     * Limitation times.
     */
    long requestLimit() default 3;
}
