package com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateChallengeRequest {

    private String name;

    private String description;

    private ChallengeStatus status;

    private int minParticipants;

    private int maxParticipants;

    private LocalDate dueAt;

    private ChallengeCategory category;
}
