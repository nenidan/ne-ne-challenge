package com.github.nenidan.ne_ne_challenge.global.aop.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetId {
    String spel() default "";  //"#req.paymentId"
}
