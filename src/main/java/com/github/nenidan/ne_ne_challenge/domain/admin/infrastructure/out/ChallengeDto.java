package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.global.dto.InnerResponseBase;

import lombok.Getter;

@Getter
public class ChallengeDto extends InnerResponseBase{
    private Long id;

    private String name;

    private String description;

    //private ChallengeStatus status;

    private int minParticipants;

    private int maxParticipants;

    private LocalDate dueAt;

    private LocalDateTime startedAt;

    //private ChallengeCategory category;

    private int participationFee;

    private int totalFee;

    public ChallengeDto(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        super(createdAt, updatedAt, deletedAt);
    }
}
