package com.github.nenidan.ne_ne_challenge.global.client.user;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.global.client.user.dto.UserResponse;

public interface UserClient {

    UserResponse getUserById(long userId);

    List<UserResponse> getUserAll();

    List<UserResponse> getUserListByidList(List<Long> userIdList);
}
