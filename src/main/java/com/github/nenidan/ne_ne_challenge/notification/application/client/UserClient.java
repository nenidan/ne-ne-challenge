package com.github.nenidan.ne_ne_challenge.notification.application.client;

import com.github.nenidan.ne_ne_challenge.dto.UserResponse;

public interface UserClient {
	UserResponse findUserById(Long id);
}
