package com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.inner;

import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.ChallengeLog;
import com.github.nenidan.ne_ne_challenge.global.dto.InnerResponseBase;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InnerChallengeHistoryResponse extends InnerResponseBase {

    private Long id;

    private Long userId;

    private Long challengeId;

    private String content;

    private boolean isSuccess;

    private InnerChallengeHistoryResponse(LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        Long id,
        Long userId,
        Long challengeId,
        String content,
        boolean isSuccess
    ) {
        super(createdAt, updatedAt, deletedAt);
        this.id = id;
        this.userId = userId;
        this.challengeId = challengeId;
        this.content = content;
        this.isSuccess = isSuccess;
    }

    public static InnerChallengeHistoryResponse from(ChallengeLog challengeHistory) {
        return new InnerChallengeHistoryResponse(challengeHistory.getCreatedAt(),
            challengeHistory.getUpdatedAt(),
            challengeHistory.getDeletedAt(),
            challengeHistory.getId(),
            challengeHistory.getUser().getId(),
            challengeHistory.getChallenge().getId(),
            challengeHistory.getContent(),
            challengeHistory.isSuccess()
        );
    }
}
