package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.dto.ChallengeRequestInfo;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode.POINT_INSUFFICIENT;
import static com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode.REQUEST_ERROR;

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

    private Long hostId;

    @OneToMany
    @JoinColumn(name = "challenge_id")
    private Set<Participant> participants = new HashSet<>();
    private int minParticipants;
    private int maxParticipants;
    private int currentParticipants;

    private int participationFee;
    private int totalFee;

    private LocalDate startAt;
    private LocalDate dueAt;

    public Challenge(String name,
        String description,
        ChallengeStatus status,
        ChallengeCategory category,
        Long hostId,
        int minParticipants,
        int maxParticipants,
        int participationFee,
        LocalDate startAt,
        LocalDate dueAt
    ) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.category = category;
        this.hostId = hostId;
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
        this.participationFee = participationFee;
        this.totalFee = participationFee; // 생성 시 방장이 포인트 지불
        this.startAt = startAt;
        this.dueAt = dueAt;

        if (name == null || name.isEmpty()) {
            throw new ChallengeException(REQUEST_ERROR);
        }

        if (description == null || description.isEmpty()) {
            throw new ChallengeException(REQUEST_ERROR);
        }

        if(status == null) {
            throw new ChallengeException(REQUEST_ERROR);
        }

        if(category == null) {
            throw new ChallengeException(REQUEST_ERROR);
        }

        if(startAt == null || dueAt == null) {
            throw new ChallengeException(REQUEST_ERROR);
        }

        if (startAt.isEqual(dueAt) || startAt.isAfter(dueAt)) {
            throw new ChallengeException(REQUEST_ERROR);
        }

        LocalDate today = LocalDate.now();
        if (startAt.isBefore(today)) {
            throw new ChallengeException(REQUEST_ERROR);
        }
        if(dueAt.isBefore(today) || dueAt.isEqual(today)) {
            throw new ChallengeException(REQUEST_ERROR);
        }
    }

    /**
     * 방장이 챌린지를 새로 생성하는 경우
     * @param requesterId 요청자 id
     * @param userPoint 요청자의 현재 포인트
     * @param info 챌린지 정보
     * @return 방장이 추가된 챌린지
     */
    public static Challenge createChallenge(Long requesterId, int userPoint, ChallengeRequestInfo info) {
        if(userPoint < info.getParticipationFee()) {
            throw new ChallengeException(POINT_INSUFFICIENT);
        }

        return new Challenge(
            info.getName(), info.getDescription(), ChallengeStatus.WAITING, info.getCategory(), requesterId,
            info.getMinParticipants(), info.getMaxParticipants(), info.getParticipationFee(), info.getStartAt(), info.getDueAt()
        );
    }
}