package com.github.nenidan.ne_ne_challenge.domain.user.application.service;

import org.springframework.http.HttpHeaders;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.User;

public interface JwtTokenProvider {

    HttpHeaders createAuthHeaders(User user);

    void addToBlacklist(String bearerToken);

    void addToWhitelist(String bearerToken);

    void checkWhitelisted(String bearerToken);
}
