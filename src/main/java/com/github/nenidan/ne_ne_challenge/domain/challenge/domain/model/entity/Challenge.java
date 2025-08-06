package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.UpdateChallengeInfoCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.dto.ChallengeRequestInfo;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode.*;
import static com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus.WAITING;

@Entity
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "challenge_id")
    private Set<Participant> participants = new HashSet<>();
    private int minParticipants;
    private int maxParticipants;
    private int currentParticipantCount;

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

        if(minParticipants < 0 || maxParticipants < 0) {
            throw new ChallengeException(REQUEST_ERROR);
        }

        if(minParticipants > maxParticipants) {
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

        this.name = name;
        this.description = description;
        this.status = status;
        this.category = category;
        this.hostId = hostId;
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
        this.currentParticipantCount = 1;
        this.participationFee = participationFee;
        this.totalFee = participationFee; // 생성 시 방장이 포인트 지불
        this.startAt = startAt;
        this.dueAt = dueAt;

        participants.add(new Participant(hostId));
    }

    // null은 처리하지 않는다.
    public void updateInfo(Long loginUserId, UpdateChallengeInfoCommand command) {
        verifyHost(loginUserId);
        verifyWaiting();

        String newName = command.getName();
        if(newName != null && !newName.isEmpty()) {
            this.name = newName;
        }

        String newDescription = command.getDescription();
        if(newDescription != null && !newDescription.isEmpty()) {
            this.description = newDescription;
        }

        LocalDate newStartAt = command.getStartAt() == null ? this.startAt : command.getStartAt();
        LocalDate newDueAt = command.getDueAt() == null ? this.dueAt : command.getDueAt();

        if (newStartAt.isEqual(newDueAt) || newStartAt.isAfter(newDueAt)) {
            throw new ChallengeException(REQUEST_ERROR);
        }

        LocalDate today = LocalDate.now();
        if (newStartAt.isBefore(today)) {
            throw new ChallengeException(REQUEST_ERROR);
        }
        if(newDueAt.isBefore(today) || newDueAt.isEqual(today)) {
            throw new ChallengeException(REQUEST_ERROR);
        }

        this.startAt = newStartAt;
        this.dueAt = newDueAt;

        ChallengeCategory newCategory = command.getCategory();
        if(newCategory != null) {
            this.category = newCategory;
        }
    }

    private void verifyHost(Long loginUserId) {
        if(!Objects.equals(hostId, loginUserId)) {
            throw new ChallengeException(NOT_HOST);
        }
    }

    public List<Long> getParticipantIdList() {
        return participants.stream().map(Participant::getUserId).toList();
    }

    public void deleteChallenge(Long loginUserId) {
        verifyHost(loginUserId);
        verifyWaiting();

        delete();
        participants.forEach(Participant::delete);
    }

    public void join(Long loginUserId, int userPoint) {
        verifyWaiting();
        if(currentParticipantCount >= maxParticipants) {
            throw new ChallengeException(ChallengeErrorCode.CHALLENGE_FULL);
        }
        verifyEnoughPoint(userPoint);

        if(participants.stream().anyMatch(p -> Objects.equals(p.getUserId(), loginUserId))) {
            throw new ChallengeException(ALREADY_PARTICIPATED);
        }

        participants.add(new Participant(loginUserId));
        totalFee += participationFee;
        currentParticipantCount++;
    }

    private void verifyEnoughPoint(int userPoint) {
        if(userPoint < participationFee) {
            throw new ChallengeException(POINT_INSUFFICIENT);
        }
    }

    private void verifyWaiting() {
        if(status != WAITING) {
            throw new ChallengeException(NOT_WAITING);
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
            info.getName(), info.getDescription(), WAITING, info.getCategory(), requesterId,
            info.getMinParticipants(), info.getMaxParticipants(), info.getParticipationFee(), info.getStartAt(), info.getDueAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ChallengeStatus getStatus() {
        return status;
    }

    public ChallengeCategory getCategory() {
        return category;
    }

    public Long getHostId() {
        return hostId;
    }

    public int getMinParticipants() {
        return minParticipants;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public int getCurrentParticipantCount() {
        return currentParticipantCount;
    }

    public int getParticipationFee() {
        return participationFee;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public LocalDate getDueAt() {
        return dueAt;
    }
}