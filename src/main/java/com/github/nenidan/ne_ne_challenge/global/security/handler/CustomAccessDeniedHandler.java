package com.github.nenidan.ne_ne_challenge.global.security.handler;

import com.github.nenidan.ne_ne_challenge.global.security.exception.SecurityServletErrorCode;
import com.github.nenidan.ne_ne_challenge.global.security.util.SecurityResponseWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        SecurityResponseWriter.writeJsonErrorResponse(
                response,
                SecurityServletErrorCode.ACCESS_DENIED
        );
    }
}
