package com.github.nenidan.ne_ne_challenge.global.aop;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.nenidan.ne_ne_challenge.global.aop.annotation.Masked;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public final class MaskingUtils {
    private final ObjectMapper loggingObjectMapper;

    private static final Set<String> SENSITIVE_KEYS = Set.of(
            "password", "pass", "pwd", "email", "phone", "ssn", "residentRegNo"
    );

    private static final Pattern EMAIL = Pattern.compile("^([^@]{2})[^@]*(@.*)$");
    private static final Pattern PHONE = Pattern.compile("^(01\\d)(\\d{2,4})(\\d{4})$");

    public String toMaskedJson(Object obj) {
        if (obj == null) return null;
        try {
            JsonNode tree = loggingObjectMapper.valueToTree(obj);
            JsonNode masked = maskNode(tree);
            return loggingObjectMapper.writeValueAsString(masked);
        } catch (JsonProcessingException e) {
            return String.valueOf(obj);
        }
    }

    private JsonNode maskNode(JsonNode node) {
        if (node == null) return null;
        if (node.isObject()) {
            ObjectNode obj = (ObjectNode) node;
            Iterator<String> it = obj.fieldNames();
            List<String> names = new ArrayList<>();
            it.forEachRemaining(names::add);
            for (String name : names) {
                JsonNode child = obj.get(name);
                if (SENSITIVE_KEYS.contains(name)) {
                    obj.put(name, "***");
                } else {
                    obj.set(name, maskNode(child));
                }
            }
            return obj;
        } else if (node.isArray()) {
            for (int i = 0; i < node.size(); i++) {
                ((ArrayNode) node).set(i, maskNode(node.get(i)));
            }
            return node;
        } else if (node.isTextual()) {
            String v = node.asText();
            if (EMAIL.matcher(v).matches()) {
                return new TextNode(v.replaceAll("^(.{2}).*(@.*)$", "$1***$2"));
            }
            if (PHONE.matcher(v).matches()) {
                return new TextNode(v.replaceAll("^(01\\d)\\d{2,4}(\\d{4})$", "$1****$2"));
            }
            return node;
        }
        return node;
    }

    public void maskAnnotatedFields(Object obj) {
        if (obj == null) return;
        for (Field f : obj.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(Masked.class)) {
                f.setAccessible(true);
                try { f.set(obj, "***"); } catch (IllegalAccessException ignored) {}
            }
        }
    }
}
