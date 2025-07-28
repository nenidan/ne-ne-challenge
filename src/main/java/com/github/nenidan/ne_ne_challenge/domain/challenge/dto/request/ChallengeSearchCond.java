package com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus;

import lombok.Getter;
import lombok.Setter;

// Todo 빈 검증 제약 조건
@Getter
@Setter
public class ChallengeSearchCond {

    private Long userId;

    private String name;

    private ChallengeStatus status;

    private LocalDate dueAt;

    private ChallengeCategory category;

    private Integer maxParticipationFee;

    private LocalDateTime cursor = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

    private int size = 10;
}