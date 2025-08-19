package com.github.nenidan.ne_ne_challenge.global.aop;

public final class AuditSkipContext {
    private static final ThreadLocal<Boolean> SKIP = ThreadLocal.withInitial(() -> false);
    public static boolean isSkip() { return SKIP.get(); }
    public static void enable() { SKIP.set(true); }
    public static void clear() { SKIP.remove(); }
    private AuditSkipContext() {}
}
