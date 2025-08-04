package com.github.nenidan.ne_ne_challenge.global.client.user;

import com.github.nenidan.ne_ne_challenge.global.client.user.dto.UserResponse;

public interface UserClient {

    UserResponse getUserById(long userId);

}
