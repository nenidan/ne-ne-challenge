package com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.inner;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InnerHistoryResponse {

    private Long id;

    private Long userId;

    private Long challengeId;

    private String content;

    private boolean isSuccess;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
