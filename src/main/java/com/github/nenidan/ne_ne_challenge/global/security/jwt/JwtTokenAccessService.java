package com.github.nenidan.ne_ne_challenge.global.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtTokenAccessService {

    private static final String BLACKLIST_PREFIX = "BL:";
    private static final String WHITELIST_PREFIX = "WL:";

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
}

