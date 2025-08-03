package com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateChallengeRequest {

    private String name;

    private String description;

    private int minParticipants;

    private int maxParticipants;

    private LocalDate startAt;

    private LocalDate dueAt;

    private ChallengeCategory category;

    private int participationFee;
}
