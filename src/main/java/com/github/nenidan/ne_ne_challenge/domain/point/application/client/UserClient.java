package com.github.nenidan.ne_ne_challenge.domain.point.application.client;

import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.UserIdResult;

public interface UserClient {

    UserIdResult getUser(Long userId);
}
