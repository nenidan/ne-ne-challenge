package com.github.nenidan.ne_ne_challenge.global.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuditIgnoreMarkerAspect {

    // @AuditIgnore가 붙은 컨트롤러 메서드에 진입하면 스킵 ON
    @Around("@annotation(com.github.nenidan.ne_ne_challenge.global.aop.annotation.AuditIgnore)")
    public Object markSkip(ProceedingJoinPoint pjp) throws Throwable {
        AuditSkipContext.enable();
        try {
            return pjp.proceed();
        } finally {
            AuditSkipContext.clear();
        }
    }
}
