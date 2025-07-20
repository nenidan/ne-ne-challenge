package com.github.nenidan.ne_ne_challenge.domain.user.dto.request;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String email;

    private String password;

}
