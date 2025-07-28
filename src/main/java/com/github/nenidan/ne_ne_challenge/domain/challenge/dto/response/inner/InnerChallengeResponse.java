package com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.inner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.global.dto.InnerResponseBase;

import lombok.Getter;

@Getter
public class InnerChallengeResponse extends InnerResponseBase {

    private Long id;

    private String name;

    private String description;

    private ChallengeStatus status;

    private int minParticipants;

    private int maxParticipants;

    private LocalDate dueAt;

    private LocalDateTime startedAt;

    private ChallengeCategory category;

    private int participationFee;

    private int totalFee;

    public InnerChallengeResponse(Long id,
        String name,
        String description,
        ChallengeStatus status,
        int minParticipants,
        int maxParticipants,
        LocalDate dueAt,
        LocalDateTime startedAt,
        ChallengeCategory category,
        int participationFee,
        int totalFee,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
    ) {
        super(createdAt, updatedAt, deletedAt);
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
        this.dueAt = dueAt;
        this.startedAt = startedAt;
        this.category = category;
        this.participationFee = participationFee;
        this.totalFee = totalFee;
    }

    public static InnerChallengeResponse from(Challenge challenge) {
        return new InnerChallengeResponse(challenge.getId(),
            challenge.getName(),
            challenge.getDescription(),
            challenge.getStatus(),
            challenge.getMinParticipants(),
            challenge.getMaxParticipants(),
            challenge.getDueAt(),
            challenge.getStartedAt(),
            challenge.getCategory(),
            challenge.getParticipationFee(),
            challenge.getTotalFee(),
            challenge.getCreatedAt(),
            challenge.getUpdatedAt(),
            challenge.getDeletedAt()
        );
    }
}
