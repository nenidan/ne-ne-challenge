package com.github.nenidan.ne_ne_challenge.global.trace;

import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class RequestTraceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String traceId = Optional.ofNullable(request.getHeader("X-Request-ID")).orElse(UUID.randomUUID().toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null; String username = null;
        if (authentication != null && authentication.getPrincipal() instanceof Auth a) {
            userId = a.getId();
            username = a.getNickname();
        }

        TraceContext ctx = TraceContext.builder()
                .traceId(traceId)
                .userId(userId)
                .username(username)
                .ip(clientIp(request))
                .uri(request.getRequestURI())
                .httpMethod(request.getMethod())
                .userAgent(request.getHeader("User-Agent"))
                .build();

        // Propagate
        TraceContextHolder.set(ctx);
        if (traceId != null) MDC.put("traceId", traceId);
        if (userId != null) MDC.put("userId", String.valueOf(userId));
        if (ctx.getUri() != null) MDC.put("uri", ctx.getUri());

        try {
            chain.doFilter(request, response);
        } finally {
            TraceContextHolder.clear();
            MDC.remove("traceId");
            MDC.remove("userId");
            MDC.remove("uri");
        }
    }

    private static String clientIp(HttpServletRequest req) {
        String h = req.getHeader("X-Forwarded-For");
        if (h != null && !h.isBlank()) return h.split(",")[0].trim();
        h = req.getHeader("X-Real-IP");
        if (h != null && !h.isBlank()) return h;
        return req.getRemoteAddr();
    }
}
