package com.github.nenidan.ne_ne_challenge.global.aop;

import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.entity.AopLog;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.respository.LogTransactionRepository;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;
import com.github.nenidan.ne_ne_challenge.global.aop.event.AdminActionLoggedEvent;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Set;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AdminLogAspect {

    private final ApplicationEventPublisher applicationEventPublisher;

    private static final Set<String> CLEAR_TRIGGER_METHODS = Set.of(
            "PaymentFacade.confirmAndChargePoint()"
    );//체인의 종료메서드 명시

    @Pointcut("execution(* com..github.nenidan.ne_ne_challenge.domain.payment.application..*(..))")
    private void paymentMethods() {}

    @Pointcut("execution(* com..github.nenidan.ne_ne_challenge.domain.point.application..*(..))")
    private void pointMethods() {}

    @Pointcut("execution(* com..github.nenidan.ne_ne_challenge.domain.order.application..*(..))")
    private void orderMethods() {}

    /*@Pointcut("execution(* com..github.nenidan.ne_ne_challenge.domain.challenge..*(..))")
    private void challengeMethods() {}*/

    @Pointcut("paymentMethods() || pointMethods() || orderMethods()")
    private void adminDomainMethods() {}

    @Around("adminDomainMethods()")
    public Object logAdminAction(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        // 요청 정보
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String packageName = signature.getDeclaringType().getPackageName();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        String fullMethodName = className + "." + methodName + "()";

        //로그의 도메인 타입
        String[] parts = packageName.split("\\.");
        String domainName = parts[5];
        DomainType type = DomainType.valueOf(domainName.toUpperCase());

        // 파라미터 정보
        Object[] args = joinPoint.getArgs();
        String params = Arrays.toString(args);

        // 요청자 정보
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        String clientIp = request.getRemoteAddr();

        Object result = null;
        Long targetId = LoggingContext.getId();
        boolean success = true;

        try {
            //proceed = return에서 추출, args = method 인자에서 추출
            result = joinPoint.proceed();
            return result;
        } catch (Throwable ex) {
            success = false;
            fullMethodName = ex.getMessage() + "(" + fullMethodName + ")"; //에러메시지 커스텀
            targetId = LoggingContext.getId();

            LoggingContext.clear();
            throw ex;
        } finally {
//            for (Object arg : args) {
//                if (arg instanceof BaseEntity entity) {
//                    targetId = entity.getId();
//                    break;
//                }
//            }
            if(success){
                LoggingContext.updateId(targetId);
            }

            long elapsedTime = System.currentTimeMillis() - start;

            // 로그 출력
            log.info("[ADMIN_LOG] uri={}, method={}, clientIp={}, params={}, success={}, elapsed={}ms",
                    requestURI, fullMethodName, clientIp, params, success, elapsedTime);

            if(CLEAR_TRIGGER_METHODS.contains(fullMethodName)){
                System.out.println("끝");
                LoggingContext.clear();
            }

            applicationEventPublisher.publishEvent(
                    new AdminActionLoggedEvent(type, targetId, fullMethodName, params, result != null ? result.toString() : null, success)
            );


        }
    }
}
