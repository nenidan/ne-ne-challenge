package com.github.nenidan.ne_ne_challenge.global.aop;

import java.util.Set;

public class LoggingContext {
    private static final ThreadLocal<Long> extractTargetId = new ThreadLocal<>();

    public static Long getId() {
        return extractTargetId.get();
    }

    public static void updateId(Long id) {
        extractTargetId.set(id);
    }

    public static void clear() {
        extractTargetId.remove();
        System.out.println("끝");
    }

}
