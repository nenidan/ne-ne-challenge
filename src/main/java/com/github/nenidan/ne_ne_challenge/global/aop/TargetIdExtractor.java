package com.github.nenidan.ne_ne_challenge.global.aop;

import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public final class TargetIdExtractor {
    public static Long tryExtract(Object[] args, Object result) {
        // 1) 메서드 파라미터에서 우선
        for (Object a : args) {
            if (a instanceof Number n) return n.longValue();
            if (a instanceof String s && s.matches("\\d+")) return Long.parseLong(s);
            // DTO에 id/getId 존재 시
            var id = reflectId(a);
            if (id != null) return id;
        }
        // 2) ResponseEntity/body/DTO의 getId() 시도
        if (result instanceof ResponseEntity<?> re && re.getBody() != null) {
            var id = reflectId(re.getBody());
            if (id != null) return id;
        }
        var id = reflectId(result);
        return id;
    }
    private static Long reflectId(Object o) {
        if (o == null) return null;
        try {
            var m = o.getClass().getMethod("getId");
            var v = m.invoke(o);
            if (v instanceof Number n) return n.longValue();
            if (v instanceof String s && s.matches("\\d+")) return Long.parseLong(s);
        } catch (Exception ignored) {}
        return null;
    }

    private TargetIdExtractor() {}
}

