package com.github.nenidan.ne_ne_challenge.domain.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpHeaders;

@Getter
@AllArgsConstructor
public class UserWithTokenResult {

    private final UserResult userResult;
    private final HttpHeaders headers;

}
