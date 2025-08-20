package com.github.nenidan.ne_ne_challenge.global.trace;

public final class TraceContextHolder {
    private static final InheritableThreadLocal<TraceContext> CTX = new InheritableThreadLocal<>();

    public static void set(TraceContext ctx) { CTX.set(ctx); }
    public static TraceContext get() { return CTX.get(); }
    public static void clear() { CTX.remove(); }
    private TraceContextHolder() {}
}
