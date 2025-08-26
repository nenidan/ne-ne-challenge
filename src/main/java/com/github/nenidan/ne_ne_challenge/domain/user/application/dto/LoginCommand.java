package com.github.nenidan.ne_ne_challenge.domain.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginCommand {

    private String email;

    private String password;

}
