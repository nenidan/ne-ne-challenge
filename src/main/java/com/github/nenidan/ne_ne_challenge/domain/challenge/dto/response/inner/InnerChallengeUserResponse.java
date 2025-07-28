package com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.inner;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.ChallengeUser;
import com.github.nenidan.ne_ne_challenge.global.dto.InnerResponseBase;

import lombok.Getter;

@Getter
public class InnerChallengeUserResponse extends InnerResponseBase {

    private Long id;

    private Long challengeId;

    private Long userId;

    private boolean isHost;

    public InnerChallengeUserResponse(LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        Long id,
        Long challengeId,
        Long userId,
        boolean isHost
    ) {
        super(createdAt, updatedAt, deletedAt);
        this.id = id;
        this.challengeId = challengeId;
        this.userId = userId;
        this.isHost = isHost;
    }

    public static InnerChallengeUserResponse from(ChallengeUser challengeUser) {
        return new InnerChallengeUserResponse(challengeUser.getCreatedAt(),
            challengeUser.getUpdatedAt(),
            challengeUser.getDeletedAt(),
            challengeUser.getId(),
            challengeUser.getChallenge().getId(),
            challengeUser.getUser().getId(),
            challengeUser.isHost()
        );
    }
}
