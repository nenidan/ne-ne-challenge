package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.auth;

import com.github.nenidan.ne_ne_challenge.domain.user.application.service.JwtTokenProvider;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.User;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Role;
import com.github.nenidan.ne_ne_challenge.global.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProviderImpl implements JwtTokenProvider {

    private final JwtUtil jwtUtil;

    @Override
    public HttpHeaders createAuthHeaders(User user) {
        Auth auth = new Auth(
                user.getId().getValue(),
                user.getProfile().getNickname(),
                Role.of(user.getAccount().getRole().name())
        );

        String token = jwtUtil.createToken(auth);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtUtil.getBearerToken(token));

        return headers;
    }
}
