package com.github.nenidan.ne_ne_challenge.global.security.auth;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.exception.UserException;

import java.util.Arrays;

public enum Role {
    ADMIN, USER;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }

    public static Role of(String role) {
        return Arrays.stream(Role.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new UserException(UserErrorCode.INVALID_USER_ROLE));
    }
}