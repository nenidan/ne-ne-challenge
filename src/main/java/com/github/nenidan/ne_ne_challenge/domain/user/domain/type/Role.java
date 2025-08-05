package com.github.nenidan.ne_ne_challenge.domain.user.domain.type;

import java.util.Arrays;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.exception.UserException;

public enum Role {
    ADMIN, USER;

    public static Role of(String role) {
        return Arrays.stream(Role.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new UserException(UserErrorCode.INVALID_USER_ROLE));
    }
}
