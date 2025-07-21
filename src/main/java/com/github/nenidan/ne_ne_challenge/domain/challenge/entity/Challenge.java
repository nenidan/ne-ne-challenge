package com.github.nenidan.ne_ne_challenge.domain.challenge.entity;

import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Challenge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    @Enumerated(EnumType.STRING)
    private ChallengeCategory category;

    private int minParticipants;

    private int maxParticipants;

    private int participationFee;

    private int totalFee;

    private LocalDateTime startedAt;

    private LocalDate dueAt;

    public Challenge(String name,
        String description,
        ChallengeStatus status,
        ChallengeCategory category,
        int minParticipants,
        int maxParticipants,
        int participationFee,
        LocalDate dueAt
    ) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.category = category;
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
        this.participationFee = participationFee;
        this.dueAt = dueAt;
    }

    // Todo OneToMany로 participants를 받도록 리팩터링 → ChallengeUserService 불필요
    public void addParticipant(User user) {
        this.totalFee += this.participationFee;
    }
}