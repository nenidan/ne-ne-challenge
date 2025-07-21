package com.github.nenidan.ne_ne_challenge.global.security.auth;

import com.github.nenidan.ne_ne_challenge.domain.user.type.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Auth {

    private final Long id;

    private final String nickname;

    private final UserRole role;
}
