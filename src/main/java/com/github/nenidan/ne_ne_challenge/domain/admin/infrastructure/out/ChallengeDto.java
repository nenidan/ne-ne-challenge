package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChallengeDto{
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
