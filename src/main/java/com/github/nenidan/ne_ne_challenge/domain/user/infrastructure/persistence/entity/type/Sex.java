package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.type;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.exception.UserException;

import java.util.Arrays;

public enum Sex {
    MALE, FEMALE, UNKNOWN;

    public static Sex of(String sex) {
        return Arrays.stream(Sex.values())
                .filter(r -> r.name().equalsIgnoreCase(sex))
                .findFirst()
                .orElseThrow(() -> new UserException(UserErrorCode.INVALID_USER_SEX));
    }
}
