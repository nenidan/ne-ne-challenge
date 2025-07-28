package com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.ChallengeHistory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChallengeHistoryResponse {

    private Long id;

    private Long userId;

    private Long challengeId;

    private String content;

    private boolean isSuccess;

    private LocalDateTime createdAt;

    public static ChallengeHistoryResponse from(ChallengeHistory history) {
        return new ChallengeHistoryResponse(history.getId(),
            history.getUser().getId(),
            history.getChallenge().getId(),
            history.getContent(),
            history.isSuccess(),
            history.getCreatedAt()
        );
    }
}
