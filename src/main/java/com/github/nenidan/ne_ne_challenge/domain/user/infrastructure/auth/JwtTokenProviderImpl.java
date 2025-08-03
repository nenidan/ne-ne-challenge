package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.user.application.service.JwtTokenProvider;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.User;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.auth.exception.JwtTokenProviderErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.auth.exception.JwtTokenProviderException;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Role;
import com.github.nenidan.ne_ne_challenge.global.security.jwt.JwtTokenAccessService;
import com.github.nenidan.ne_ne_challenge.global.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProviderImpl implements JwtTokenProvider {

    private final JwtUtil jwtUtil;
    private final JwtTokenAccessService jwtTokenAccessService;

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

    @Override
    public void addToBlacklist(String bearerToken) {
        String token = jwtUtil.extractToken(bearerToken);
        jwtTokenAccessService.addToBlacklist(token);
    }

    @Override
    public void addToWhitelist(String bearerToken) {
        String token = jwtUtil.extractToken(bearerToken);
        jwtTokenAccessService.addToWhitelist(token);
    }

    @Override
    public void checkWhitelisted(String bearerToken) {
        String token = jwtUtil.extractToken(bearerToken);

        if(!jwtTokenAccessService.isWhitelisted(token)) {
            throw new JwtTokenProviderException(JwtTokenProviderErrorCode.NOT_WHITELIST);
        }
    }


}
