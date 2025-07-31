package com.github.nenidan.ne_ne_challenge.domain.user.domain.model;

import lombok.Getter;

@Getter
public class UserId {
    private final Long value;

    public UserId(Long value) {
        if (value == null) throw new IllegalArgumentException("UserId cannot be null");
        this.value = value;
    }
}
