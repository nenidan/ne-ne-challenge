package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out;

import com.github.nenidan.ne_ne_challenge.global.dto.InnerResponseBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

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
