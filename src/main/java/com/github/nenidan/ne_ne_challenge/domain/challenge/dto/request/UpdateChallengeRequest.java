package com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request;

import java.time.LocalDate;

import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus;

import lombok.Getter;

@Getter
public class UpdateChallengeRequest {

    private String name;

    private String description;

    private ChallengeStatus status;

    private Integer minParticipants;

    private Integer maxParticipants;

    private LocalDate dueAt;

    private ChallengeCategory category;
}
