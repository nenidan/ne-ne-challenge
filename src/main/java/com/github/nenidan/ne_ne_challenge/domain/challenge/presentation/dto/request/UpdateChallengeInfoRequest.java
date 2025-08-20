package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateChallengeInfoRequest {

    private String name;

    private String description;

    private LocalDate startAt;

    private LocalDate dueAt;

    private ChallengeCategory category;
}