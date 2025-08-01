package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.dto.ChallengeInfo;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

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

    // 챌린지 생성 시 포인트 추가
    public static Challenge from(ChallengeInfo info) {
        return new Challenge(info.getName(),
            info.getDescription(),
            ChallengeStatus.WAITING,
            info.getCategory(),
            info.getMinParticipants(),
            info.getMaxParticipants(),
            info.getParticipationFee(),
            info.getParticipationFee(),
            info.getStartAt(),
            info.getDueAt()
        );
    }
}