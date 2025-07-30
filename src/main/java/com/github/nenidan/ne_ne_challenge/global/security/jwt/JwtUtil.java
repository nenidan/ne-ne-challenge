package com.github.nenidan.ne_ne_challenge.global.security.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.nenidan.ne_ne_challenge.domain.user.type.UserRole;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;
import com.github.nenidan.ne_ne_challenge.global.security.exception.CustomSecurityException;
import com.github.nenidan.ne_ne_challenge.global.security.exception.SecurityErrorCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final Pattern BEARER_PATTERN = Pattern.compile("^Bearer\\s+[A-Za-z0-9-_.]+$");

    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(Auth auth) {
        Date date = new Date();

        return Jwts.builder()
                .setSubject(String.valueOf(auth.getId()))
                .claim("nickname", auth.getNickname())
                .claim("role", auth.getRole().name())
                .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String getBearerToken(String token) {
        return BEARER_PREFIX + token;
    }

    public static boolean isValidBearerToken(String authorizationHeader) {
        return authorizationHeader != null && BEARER_PATTERN.matcher(authorizationHeader).matches();
    }

    public String extractToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(BEARER_PREFIX.length()); // "Bearer " 제거 후 반환
        }

        throw new CustomSecurityException(SecurityErrorCode.TOKEN_NOT_FOUND);
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Auth extractAuth(Claims claims) {
        return new Auth(
                Long.parseLong(claims.getSubject()),
                claims.get("nickname", String.class),
                UserRole.of(claims.get("role", String.class))
        );
    }

}
