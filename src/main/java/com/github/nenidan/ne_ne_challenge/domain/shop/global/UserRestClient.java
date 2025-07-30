package com.github.nenidan.ne_ne_challenge.domain.shop.global;

import com.github.nenidan.ne_ne_challenge.domain.user.dto.response.UserResponse;

public interface UserRestClient {
    UserResponse getUser(Long userId);
}
