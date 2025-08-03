package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out;

import com.github.nenidan.ne_ne_challenge.global.dto.InnerResponseBase;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChallengeUserDto extends InnerResponseBase {
    private Long id;

    private Long challengeId;

    private Long userId;

    private boolean isHost;

    public ChallengeUserDto(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        super(createdAt, updatedAt, deletedAt);
    }
}
