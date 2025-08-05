package com.github.nenidan.ne_ne_challenge.global.security.jwt;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtTokenAccessService {

    private static final String BLACKLIST_PREFIX = "BL:";
    private static final String WHITELIST_PREFIX = "WL:";

    private static final String REFRESH_TOKEN_PREFIX = "RT:";

    private final RedisTemplate<String, String> jwtRedisTemplate;
    private final JwtUtil jwtUtil;

    public void addToBlacklist(String token) {
        long expirationMillis = jwtUtil.getRemainingExpiration(token);
        jwtRedisTemplate.opsForValue()
                .set(getBlacklistKey(token), "", expirationMillis, TimeUnit.MILLISECONDS);
    }

    public void addToWhitelist(String token) {
        long expirationMillis = jwtUtil.getRemainingExpiration(token);
        jwtRedisTemplate.opsForValue()
                .set(getWhitelistKey(token), "", expirationMillis, TimeUnit.MILLISECONDS);
    }

    public void addRefreshToken(Long id, String token) {
        long expirationMillis = jwtUtil.getRemainingExpiration(token);
        jwtRedisTemplate.opsForValue()
                .set(getRefreshTokenKey(id), token, expirationMillis, TimeUnit.MILLISECONDS);
    }

    public void removeRefreshToken(Long id) {
        jwtRedisTemplate.delete(getRefreshTokenKey(id));
    }

    public Long getUserIdFromRefreshToken(String refreshToken) {
        return jwtUtil.extractUserIdFromToken(refreshToken);
    }

    public String getRefreshToken(Long id) {
        String refreshValue = jwtRedisTemplate.opsForValue().get(getRefreshTokenKey(id));
        return refreshValue != null ? refreshValue.replace(REFRESH_TOKEN_PREFIX, "") : null;
    }

    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(jwtRedisTemplate.hasKey(getBlacklistKey(token)));
    }

    public boolean isWhitelisted(String token) {
        return Boolean.TRUE.equals(jwtRedisTemplate.hasKey(getWhitelistKey(token)));
    }

    private String getBlacklistKey(String token) {
        return BLACKLIST_PREFIX + token;
    }

    private String getWhitelistKey(String token) {
        return WHITELIST_PREFIX + token;
    }

    private String getRefreshTokenKey(Long id) {
        return REFRESH_TOKEN_PREFIX + id;
    }
}

