package com.github.nenidan.ne_ne_challenge.global.aop;

public class LoggingContext {
    private static final ThreadLocal<Long> extractTargetId = new ThreadLocal<>();

    public static Long getId() {
        return extractTargetId.get();
    }

    public static void updateId(Long id) {
        if (id != null) {
            extractTargetId.set(id);
        }
    }

    public static void clear() {
        extractTargetId.remove();
        System.out.println("끝");
    }

}
