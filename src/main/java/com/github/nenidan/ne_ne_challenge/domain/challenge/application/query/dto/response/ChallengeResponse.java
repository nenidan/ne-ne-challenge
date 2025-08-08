package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChallengeResponse {

    private Long id;

    private String name;
    private String description;
    private ChallengeStatus status;
    private ChallengeCategory category;

    private int minParticipants;
    private int maxParticipants;

    private int participationFee;
    private int totalFee;

    private LocalDate dueAt;
    private LocalDate startAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
