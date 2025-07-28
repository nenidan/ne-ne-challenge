package com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request;

import java.time.LocalDate;

import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus;

import lombok.Getter;

@Getter
public class CreateChallengeRequest {

    // Todo 빈 검증 제약조건
    private String name;

    private String description;

    private int minParticipants;

    private int maxParticipants;

    private LocalDate dueAt;

    private ChallengeCategory category;

    private int participationFee;

    public Challenge toEntity() {
        return new Challenge(name,
            description,
            ChallengeStatus.WAITING,
            category,
            minParticipants,
            maxParticipants,
            participationFee,
            dueAt
        );
    }
}
