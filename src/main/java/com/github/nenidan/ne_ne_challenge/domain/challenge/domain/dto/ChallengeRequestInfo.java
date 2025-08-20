package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.dto;

import java.time.LocalDate;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChallengeRequestInfo {

    private Long Id;

    private String name;
    private String description;

    private ChallengeStatus status;
    private ChallengeCategory category;

    private int minParticipants;
    private int maxParticipants;

    private LocalDate startAt;
    private LocalDate dueAt;

    private int participationFee;
}
