package com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC) // 매퍼 사용을 위해 잠깐 열겠습니다 (2025-18:26)
public class ChallengeHistoryResponse {

    private Long id;

    private Long userId;

    private Long challengeId;

    private String content;

    private boolean isSuccess;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
