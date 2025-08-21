package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChallengeSearchCond {

    private String name;

    private ChallengeStatus status;

    private LocalDate startAt;

    private LocalDate dueAt;

    private ChallengeCategory category;

    private Integer maxParticipationFee;

    private LocalDateTime cursor = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

    private int size = 10;
}
