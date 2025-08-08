package com.github.nenidan.ne_ne_challenge.global.client.user;

import com.github.nenidan.ne_ne_challenge.global.client.user.dto.UserResponse;

import java.util.List;

public interface UserClient {

    UserResponse getUserById(long userId);

    List<UserResponse> getUserAll();
}
