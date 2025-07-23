package com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request;

import lombok.Getter;

// todo: 빈 검증
@Getter
public class CreateHistoryRequest {

    private Long userId;

    private String content;
}
