package com.github.nenidan.ne_ne_challenge.domain.user.application.dto;

import org.springframework.http.HttpHeaders;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserWithTokenResult {

    private final UserResult userResult;
    private final HttpHeaders headers;

}
