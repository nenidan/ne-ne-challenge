package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
