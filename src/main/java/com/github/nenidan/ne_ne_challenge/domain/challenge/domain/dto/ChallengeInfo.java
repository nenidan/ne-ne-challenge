package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.dto;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChallengeInfo {

    private String name;

    private String description;

    private int minParticipants;

    private int maxParticipants;

    private LocalDate startAt;

    private LocalDate dueAt;

    private ChallengeCategory category;

    private int participationFee;
}
