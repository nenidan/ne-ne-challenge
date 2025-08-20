package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class InnerParticipantResponse {
    private Long id;

    private Long userId;

    private Long challengeId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
