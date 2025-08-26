package com.github.nenidan.ne_ne_challenge.global.security.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import com.github.nenidan.ne_ne_challenge.global.security.exception.CustomSecurityException;
import com.github.nenidan.ne_ne_challenge.global.security.exception.SecurityServletErrorCode;
import com.github.nenidan.ne_ne_challenge.global.security.util.SecurityResponseWriter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtTokenAccessService jwtTokenAccessService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {

        String bearerJwt = request.getHeader("Authorization");

        if (!JwtUtil.isValidBearerToken(bearerJwt)) {
            chain.doFilter(request, response);
            return;
        }

        String jwt = jwtUtil.extractToken(bearerJwt);

        try {

            Claims claims = jwtUtil.extractClaims(jwt);

            if (claims == null) {
                SecurityResponseWriter.writeJsonErrorResponse(response, SecurityServletErrorCode.INVALID_JWT);
                return;
            }

            if (jwtTokenAccessService.isBlacklisted(jwt)) {
                chain.doFilter(request, response);
                return;
            }

            Auth auth = jwtUtil.extractAuth(claims);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    auth, null, List.of(new SimpleGrantedAuthority(auth.getRole().getAuthority()))
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            chain.doFilter(request, response);

        } catch (Exception e) {
            SecurityServletErrorCode errorCode = mapJwtException(e);
            SecurityResponseWriter.writeJsonErrorResponse(response, errorCode);
        }
    }

    private SecurityServletErrorCode mapJwtException(Exception e) {
        if (e instanceof SecurityException || e instanceof MalformedJwtException || e instanceof CustomSecurityException) {
            return SecurityServletErrorCode.INVALID_JWT_SIGNATURE;
        } else if (e instanceof ExpiredJwtException) {
            return SecurityServletErrorCode.EXPIRED_JWT_TOKEN;
        } else if (e instanceof UnsupportedJwtException) {
            return SecurityServletErrorCode.UNSUPPORTED_JWT;
        } else {
            return SecurityServletErrorCode.INTERNAL_SERVER_ERROR;
        }
    }
}
