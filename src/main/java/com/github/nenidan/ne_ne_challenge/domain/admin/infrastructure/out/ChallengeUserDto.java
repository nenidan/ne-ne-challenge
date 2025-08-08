package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChallengeUserDto {
    private Long id;

    private Long userId;

    private Long challengeId;

    private boolean isHost;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
