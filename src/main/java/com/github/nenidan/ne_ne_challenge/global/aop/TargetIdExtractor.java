package com.github.nenidan.ne_ne_challenge.global.aop;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public final class TargetIdExtractor {
    private static final List<String> COMMON_ID_NAMES = List.of(
            "id", "targetId", "paymentId", "orderId", "challengeId", "pointId", "reviewId", "memberId", "imageId"
    );

    public static Long tryExtract(Object[] args, Object result) {
        // 1) 결과 객체 우선
        Long id = fromObject(result);
        if (id != null) return id;
        // 2) 파라미터에서
        for (Object a : args) {
            id = fromObject(a);
            if (id != null) return id;
        }
        return null;
    }

    private static Long fromObject(Object obj) {
        if (obj == null) return null;
        // BaseEntity 호환
        try {
            Method m = obj.getClass().getMethod("getId");
            Object v = m.invoke(obj);
            if (v instanceof Number n) return n.longValue();
        } catch (Exception ignored) {}

        for (String name : COMMON_ID_NAMES) {
            try {
                Field f = obj.getClass().getDeclaredField(name);
                f.setAccessible(true);
                Object v = f.get(obj);
                if (v instanceof Number n) return n.longValue();
            } catch (Exception ignored) {}
        }
        return null;
    }

    private TargetIdExtractor() {}
}

