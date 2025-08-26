package com.github.nenidan.ne_ne_challenge.global.security.handler;

import com.github.nenidan.ne_ne_challenge.global.security.exception.SecurityServletErrorCode;
import com.github.nenidan.ne_ne_challenge.global.security.util.SecurityResponseWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        SecurityResponseWriter.writeJsonErrorResponse(
                response,
                SecurityServletErrorCode.UNAUTHORIZED
        );
    }
}
