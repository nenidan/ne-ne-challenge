package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChallengeHistoryResponse {

    private Long id;

    private Long userId;
    private Long challengeId;

    private String content;
    private boolean isSuccess;

    private LocalDate date;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
