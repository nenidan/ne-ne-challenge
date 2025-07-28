package com.github.nenidan.ne_ne_challenge.domain.shop.vo;

import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;

import lombok.Getter;

@Getter
public class UserId {
    private final Long value;

    public UserId(Long value) {
        if (value == null || value <= 0) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }
        this.value = value;
    }
}
