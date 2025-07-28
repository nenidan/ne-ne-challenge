package com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChallengeResponse {

    private Long id;

    private String name;

    private String description;

    private ChallengeStatus status;

    private int minParticipants;

    private int maxParticipants;

    private LocalDateTime createdAt;

    private LocalDate dueAt;

    private LocalDateTime startedAt;

    private ChallengeCategory category;

    private int participationFee;

    private int totalFee;

    public static ChallengeResponse from(Challenge challenge) {
        return new ChallengeResponse(challenge.getId(),
            challenge.getName(),
            challenge.getDescription(),
            challenge.getStatus(),
            challenge.getMinParticipants(),
            challenge.getMaxParticipants(),
            challenge.getCreatedAt(),
            challenge.getDueAt(),
            challenge.getStartedAt(),
            challenge.getCategory(),
            challenge.getParticipationFee(),
            challenge.getTotalFee()
        );
    }
}
