package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity;

import static com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode.*;
import static com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus.*;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

import org.hibernate.annotations.SQLRestriction;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.dto.ChallengeInfo;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private LocalDate startAt;

    private LocalDate dueAt;

    public Challenge(String name,
        String description,
        ChallengeStatus status,
        ChallengeCategory category,
        int minParticipants,
        int maxParticipants,
        int participationFee,
        int totalFee,
        LocalDate startAt,
        LocalDate dueAt
    ) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.category = category;
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
        this.participationFee = participationFee;
        this.totalFee = totalFee;
        this.startAt = startAt;
        this.dueAt = dueAt;
    }

    public static Challenge from(ChallengeInfo info) {
        return new Challenge(info.getName(),
            info.getDescription(),
            ChallengeStatus.WAITING,
            info.getCategory(),
            info.getMinParticipants(),
            info.getMaxParticipants(),
            info.getParticipationFee(),
            info.getParticipationFee(),
            // 챌린지 생성 시 포인트 추가
            info.getStartAt(),
            info.getDueAt()
        );
    }

    public void updateInfo(ChallengeInfo info) {
        updateGeneralInfo(info.getName(), info.getDescription(), info.getCategory());
        updateDate(info.getStartAt(), info.getDueAt());
    }

    private void updateGeneralInfo(String name, String description, ChallengeCategory category) {
        if (name != null && !name.isEmpty()) this.name = name;
        if (description != null && !description.isEmpty()) this.description = description;
        if (category != null) this.category = category;
    }

    private void updateDate(LocalDate startAt, LocalDate dueAt) {
        if (startAt == null) startAt = this.startAt;
        if (dueAt == null) dueAt = this.dueAt;

        verifyStartAndEndDate(startAt, dueAt);

        this.startAt = startAt;
        this.dueAt = dueAt;
    }

    private void verifyStartAndEndDate(LocalDate startAt, LocalDate dueAt) {
        LocalDate today = LocalDate.now();

        if (startAt.isBefore(today)) {
            throw new ChallengeException(ChallengeErrorCode.INVALID_DATE);
        }

        if (startAt.isEqual(dueAt) || startAt.isAfter(dueAt)) {
            throw new ChallengeException(ChallengeErrorCode.INVALID_DATE);
        }
    }

    public void updateStatusWithoutParticipantCount(ChallengeStatus newStatus) {
        if (status == READY && newStatus == ONGOING) {
            verifyStartAtIsBeforeToday();
            this.status = ONGOING;
            return;
        }

        if (status == ONGOING && newStatus == FINISHED) {
            verifyDueAtIsBeforeToday();
            this.status = FINISHED;
            return;
        }

        throw new ChallengeException(INVALID_STATUS_TRANSITION);
    }

    public void increaseTotalFee() {
        totalFee += participationFee;
    }

    private void verifyStartAtIsBeforeToday() {
        ChronoLocalDate today = ChronoLocalDate.from(LocalDate.now());
        if (startAt.isAfter(today)) {
            throw new ChallengeException(INVALID_STATUS_TRANSITION);
        }
    }

    private void verifyDueAtIsBeforeToday() {
        ChronoLocalDate today = ChronoLocalDate.from(LocalDate.now());
        if(dueAt.isAfter(today) || dueAt.isEqual(today)) {
            throw new ChallengeException(INVALID_STATUS_TRANSITION);
        }
    }

    public void ready(int participantCount) {
        if(status != WAITING) {
            throw new ChallengeException(INVALID_STATUS_TRANSITION);
        }

        if(participantCount < minParticipants) {
            throw new ChallengeException(NOT_ENOUGH_PARTICIPANTS);
        }

        this.status = READY;
    }

    public void start(int participantCount) {
        verifyStartAtIsBeforeToday();

        if(status != WAITING && status != READY) {
            throw new ChallengeException(INVALID_STATUS_TRANSITION);
        }

        if(participantCount < minParticipants) {
            throw new ChallengeException(NOT_ENOUGH_PARTICIPANTS);
        }

        this.status = ONGOING;
    }
}