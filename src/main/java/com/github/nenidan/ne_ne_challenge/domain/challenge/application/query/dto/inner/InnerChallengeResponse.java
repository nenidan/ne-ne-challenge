package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InnerChallengeResponse {
    private Long id;

    private String name;

    private String description;

    private ChallengeStatus status;

    private ChallengeCategory category;

    private Long hostId;

    private int minParticipants;

    private int maxParticipants;

    private int currentParticipantCount;

    private int participationFee;

    private int totalFee;

    private LocalDate startAt;

    private LocalDate dueAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
