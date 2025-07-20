package com.github.nenidan.ne_ne_challenge.global.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nenidan.ne_ne_challenge.global.security.exception.SecurityServletErrorCode;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class SecurityResponseWriter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private SecurityResponseWriter() {}

    public static void writeJsonErrorResponse(
            HttpServletResponse response,
            SecurityServletErrorCode errorCode
    ) throws IOException {

        response.setStatus(errorCode.getHttpStatus());
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", errorCode.getMessage());
        body.put("timestamp", LocalDateTime.now().toString());

        String jsonBody = objectMapper.writeValueAsString(body);

        response.getWriter().write(jsonBody);
    }
}
