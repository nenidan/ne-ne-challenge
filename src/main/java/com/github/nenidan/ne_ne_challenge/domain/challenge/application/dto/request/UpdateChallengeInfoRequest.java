package com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.request;

import java.time.LocalDate;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;

import lombok.Getter;

@Getter
public class UpdateChallengeInfoRequest {

    private String name;

    private String description;

    private LocalDate startAt;

    private LocalDate dueAt;

    private ChallengeCategory category;
}