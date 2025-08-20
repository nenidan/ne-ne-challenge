package com.github.nenidan.ne_ne_challenge.global.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.global.aop.annotation.AuditIgnore;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import com.github.nenidan.ne_ne_challenge.global.trace.TraceContext;
import com.github.nenidan.ne_ne_challenge.global.trace.TraceContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.core.Ordered;

import java.time.Instant;
import java.util.*;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@Order(Ordered.LOWEST_PRECEDENCE)
public class AdminLogAspect {

    private static final Logger AUDIT = LoggerFactory.getLogger("AUDIT");
    private final ObjectMapper loggingObjectMapper;
    private final MaskingUtils maskingUtils;

    // 필요한 도메인 묶음
    @Pointcut("execution(* com.github.nenidan.ne_ne_challenge.domain..application..*(..))"
            + "&& !within(com.github.nenidan.ne_ne_challenge.domain.notification.application.service.NotificationRetryService)"
    )
    private void domainServices() {}

    // 컨트롤러 레벨에서도 한 번 잡아 유저케이스의 시작과 끝을 명확히
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)"
            )
    private void anyController() {}


    @Around("(domainServices() || anyController())")
    public Object logAdminAction(ProceedingJoinPoint joinPoint) throws Throwable {

        //스킵 로깅 대상 패스
        if (AuditSkipContext.isSkip() || isAuditIgnored(joinPoint)) {
            return joinPoint.proceed();
        }

        // 요청 정보
        long start = System.currentTimeMillis();
        MethodSignature sig = (MethodSignature) joinPoint.getSignature();
        String className = sig.getDeclaringType().getSimpleName();
        String methodName = sig.getName();
        String fullMethod = className + "." + methodName + "()";
        String pkg = sig.getDeclaringType().getPackageName();
        DomainType domain = resolveDomain(pkg);

        Object[] args = joinPoint.getArgs();
        Object result = null; boolean success = false; String error = null;

        try {
            result = joinPoint.proceed();
            success = true;
            return result;
        } catch (Throwable t) {
            error = t.getClass().getSimpleName() + ": " + Optional.ofNullable(t.getMessage()).orElse("");
            throw t;
        } finally {
            long took = System.currentTimeMillis() - start;
            TraceContext ctx = TraceContextHolder.get();

            Long targetId = TargetIdExtractor.tryExtract(args, result);

            // userId 보강 로직 추가
            Long userId = (ctx != null ? ctx.getUserId() : null);
            if (userId == null ) {
                for (Object arg : args) {
                    if (arg instanceof Auth auth) {
                        userId = auth.getId();
                        break;
                    }
                }
            }

            // JSON 페이로드 (민감정보 마스킹)
            String paramsJson = maskingUtils.toMaskedJson(args);
            String resultJson = maskingUtils.toMaskedJson(result);

            Map<String, Object> event = new LinkedHashMap<>();
            event.put("ts", Instant.now().toEpochMilli());
            event.put("service", "ne-ne-challenge");
            event.put("env", System.getProperty("spring.profiles.active", "local"));
            if (ctx != null) {
                event.put("traceId", ctx.getTraceId());
                event.put("ip", ctx.getIp());
                event.put("uri", ctx.getUri());
                event.put("httpMethod", ctx.getHttpMethod());
            }
            event.put("userId", userId);
            event.put("domain", domain != null ? domain.name() : null);
            event.put("classMethod", fullMethod);
            event.put("targetId", targetId);
            event.put("success", success);
            event.put("durationMs", took);
            event.put("req", paramsJson);
            event.put("res", resultJson);
            event.put("error", error);

            try {
                AUDIT.info(loggingObjectMapper.writeValueAsString(event));
            } catch (Exception e) {
                log.warn("[AUDIT_WRITE_ERR] {}", e.toString());
            }
        }
    }

    private DomainType resolveDomain(String pkg) {
        try {
            String[] p = pkg.split("\\.");
            int i = Arrays.asList(p).indexOf("domain");
            if (i >= 0 && i + 1 < p.length) {
                return DomainType.valueOf(p[i + 1].toUpperCase());
            }
        } catch (Exception ignored) {}
        return null;
    }

    private boolean isAuditIgnored(ProceedingJoinPoint pjp) {
        MethodSignature sig = (MethodSignature) pjp.getSignature();
        Method method = sig.getMethod();

        // 1) 현재 메서드에 @AuditIgnore?
        if (method.isAnnotationPresent(AuditIgnore.class)) return true;

        // 2) 선언 클래스에 @AuditIgnore?
        Class<?> declaring = method.getDeclaringClass();
        if (declaring.isAnnotationPresent(AuditIgnore.class)) return true;

        // 3) 실제 타겟 클래스 메서드(프록시 우회)에도 달렸는지 확인
        try {
            Method targetMethod = pjp.getTarget()
                    .getClass()
                    .getMethod(method.getName(), method.getParameterTypes());
            if (targetMethod.isAnnotationPresent(AuditIgnore.class)) return true;
            if (targetMethod.getDeclaringClass().isAnnotationPresent(AuditIgnore.class)) return true;
        } catch (NoSuchMethodException ignored) {}

        return false;
    }

}
