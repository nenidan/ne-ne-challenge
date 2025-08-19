package com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto;

import java.time.LocalDate;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UpdateChallengeInfoCommand {

    private String name;

    private String description;

    private LocalDate startAt;

    private LocalDate dueAt;

    private ChallengeCategory category;
}