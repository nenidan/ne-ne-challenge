package com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto;

import java.time.LocalDate;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateChallengeCommand {

    private String name;
    private String description;

    private int minParticipants;
    private int maxParticipants;

    private LocalDate startAt;
    private LocalDate dueAt;

    private ChallengeCategory category;

    private int participationFee;
}
