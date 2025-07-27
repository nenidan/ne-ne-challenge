package com.github.nenidan.ne_ne_challenge.domain.payment.application.client;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.UserClientResult;

public interface UserClient {

    UserClientResult getUser(Long userId);
}
