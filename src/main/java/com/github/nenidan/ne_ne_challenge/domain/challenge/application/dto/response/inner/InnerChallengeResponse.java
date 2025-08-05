package com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.inner;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class InnerChallengeResponse {
    private Long id;

    private String name;

    private String description;

    private ChallengeStatus status;

    private ChallengeCategory category;

    private int minParticipants;

    private int maxParticipants;

    private int participationFee;

    private int totalFee;

    private LocalDate startAt;

    private LocalDate dueAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
