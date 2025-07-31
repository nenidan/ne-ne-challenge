package com.github.nenidan.ne_ne_challenge.domain.shop.vo;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.exception.UserErrorCode;
import lombok.Getter;

@Getter
public class UserId {
    private final Long value;

    public UserId(Long value) {
        if (value == null || value <= 0) {
            throw new ShopException(ShopErrorCode.USER_NOT_FOUND);
        }
        this.value = value;
    }
}
