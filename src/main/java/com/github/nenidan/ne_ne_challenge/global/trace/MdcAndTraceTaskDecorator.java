package com.github.nenidan.ne_ne_challenge.global.trace;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class MdcAndTraceTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        TraceContext ctx = TraceContextHolder.get();
        return () -> {
            Map<String, String> previous = MDC.getCopyOfContextMap();
            try {
                if (contextMap != null) MDC.setContextMap(contextMap); else MDC.clear();
                if (ctx != null) TraceContextHolder.set(ctx);
                runnable.run();
            } finally {
                if (previous != null) MDC.setContextMap(previous); else MDC.clear();
                TraceContextHolder.clear();
            }
        };
    }
}
