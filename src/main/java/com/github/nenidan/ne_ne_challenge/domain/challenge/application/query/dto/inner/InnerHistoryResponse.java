package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

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
