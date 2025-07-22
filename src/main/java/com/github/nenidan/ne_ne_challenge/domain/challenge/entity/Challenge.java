package com.github.nenidan.ne_ne_challenge.domain.challenge.entity;

import com.github.nenidan.ne_ne_challenge.domain.challenge.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

import static com.github.nenidan.ne_ne_challenge.domain.challenge.exception.ChallengeErrorCode.*;
import static com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus.*;

@Entity
@Getter
@NoArgsConstructor
@SQLRestriction("deleted_at IS NULL")
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

    // 항상 변경 가능한 속성
    public void safeUpdate(String name, String description, ChallengeCategory category) {
        if (name != null) this.name = name;
        if (description != null) this.description = description;
        if (category != null) this.category = category;
    }

    public void start(int currentParticipants) {
        if (status != WAITING) throw new ChallengeException(INVALID_STATUS_TRANSITION);

        if (currentParticipants < minParticipants || currentParticipants > maxParticipants) {
            throw new ChallengeException(CHALLENGE_PARTICIPANT_LIMIT_EXCEEDED);
        }

        if (dueAt.isBefore(ChronoLocalDate.from(LocalDateTime.now()))) {
            this.status = FINISHED;
            throw new ChallengeException(CHALLENGE_ALREADY_FINISHED);
        }

        startedAt = LocalDateTime.now();
        status = ONGOING;
    }

    public void setDueDate(LocalDate dueAt) {
        if (status != WAITING) throw new ChallengeException(CHALLENGE_ALREADY_STARTED);
        if (dueAt.isBefore(ChronoLocalDate.from(LocalDateTime.now()))) throw new ChallengeException(INVALID_DUE_DATE);

        this.dueAt = dueAt;
    }

    public void updateParticipantsLimit(int currentParticipants, int minParticipants, int maxParticipants) {
        if (status != WAITING) throw new ChallengeException(CHALLENGE_ALREADY_STARTED);
        if (minParticipants > maxParticipants || currentParticipants > maxParticipants)
            throw new ChallengeException(INVALID_PARTICIPANT_LIMIT);

        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;

        if (this.minParticipants <= currentParticipants) {
            start(currentParticipants);
        }
    }
}