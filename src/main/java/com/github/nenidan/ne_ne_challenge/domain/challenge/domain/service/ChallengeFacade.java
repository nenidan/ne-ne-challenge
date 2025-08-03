package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.service;

import static com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.dto.ChallengeInfo;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.History;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Participant;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChallengeFacade {

    private final ChallengeService challengeService;

    private final ParticipantService participantService;

    private final HistoryService historyService;

    public Challenge createChallenge(ChallengeInfo info) {
        return challengeService.createChallenge(info);
    }

    public Participant createParticipant(Challenge challenge, Long loginUserId, boolean isHost, int point) {
        if (point < challenge.getParticipationFee()) {
            throw new ChallengeException(ChallengeErrorCode.POINT_INSUFFICIENT);
        }

        return participantService.createParticipant(challenge, loginUserId, isHost);
    }

    public Challenge getChallenge(Long challengeId) {
        return challengeService.getChallenge(challengeId);
    }

    public Challenge updateChallengeInfo(Challenge challenge, Long userId, ChallengeInfo info) {
        Participant participant = participantService.getParticipant(challenge.getId(), userId);
        if (!participant.isHost()) {
            throw new ChallengeException(ChallengeErrorCode.NOT_HOST);
        }

        challenge.updateInfo(info);
        return challenge;
    }

    public List<Participant> getParticipantList(Long challengeId) {
        return participantService.getParticipantList(challengeId);
    }

    public void checkChallengeHost(Long userId, Long challengeId) {
        Participant participant = participantService.getParticipant(challengeId, userId);
        if (!participant.isHost()) {
            throw new ChallengeException(ChallengeErrorCode.NOT_HOST);
        }
    }

    public int getChallengeParticipationFee(Long challengeId) {
        return challengeService.getChallenge(challengeId).getParticipationFee();
    }

    public void deleteChallenge(Long challengeId) {
        Challenge challenge = challengeService.getChallenge(challengeId);

        if(challenge.getStatus() != ChallengeStatus.WAITING) {
            throw new ChallengeException(ChallengeErrorCode.NOT_WAITING);
        }

        historyService.getHistoryListByChallengeId(challengeId).forEach(historyService::delete);
        participantService.getParticipantList(challengeId).forEach(participantService::delete);
        challengeService.delete(challenge);
    }

    public Challenge joinChallenge(Long userId, Long challengeId) {
        Challenge challenge = challengeService.getChallenge(challengeId);

        try {
            // 사람이 있으면 반환, 없으면 예외 (중복 참가 예외)
            participantService.getParticipant(challengeId, userId);
            throw new ChallengeException(ChallengeErrorCode.ALREADY_PARTICIPATED);
        } catch (ChallengeException e) {
            // 그냥 넘김
            if (e.getErrorCode() == ChallengeErrorCode.ALREADY_PARTICIPATED) {
                throw e;
            }
        }

        participantService.createParticipant(challenge, userId, false);
        challengeService.increaseTotalFee(challenge);
        return challenge;
    }

    public Challenge updateChallengeStatus(Challenge challenge, ChallengeStatus newStatus) {
        // WAITING -> READY/ONGOING 시에는 현재 인원수 필요
        if (challenge.getStatus() == ChallengeStatus.WAITING) {
            if (newStatus != ChallengeStatus.ONGOING && newStatus != READY) {
                throw new ChallengeException(ChallengeErrorCode.INVALID_STATUS_TRANSITION);
            }

            int participantCount = participantService.getParticipantCount(challenge.getId());

            // 이러면 Challenge에 totalParticipant를 유지하는 게?

            if (newStatus == READY) {
                challenge.ready(participantCount);
            }

            if (newStatus == ChallengeStatus.ONGOING) {
                challenge.start(participantCount);
            }

            return challenge;
        }

        challenge.updateStatusWithoutParticipantCount(newStatus);
        return challenge;
    }

    public History createHistory(Long userId, Long challengeId, String content) {
        Challenge challenge = challengeService.getChallenge(challengeId);

        if (participantService.getParticipant(challengeId, userId) == null) {
            throw new ChallengeException(ChallengeErrorCode.NOT_PARTICIPATING);
        }

        if(challenge.getStatus() != ChallengeStatus.ONGOING) {
            throw new ChallengeException(ChallengeErrorCode.NOT_ONGOING);
        }

        LocalDateTime startOfDay = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        Long count = historyService.countTodayHistory(userId, challengeId, startOfDay, endOfDay);
        if (count > 0) {
            throw new ChallengeException(ChallengeErrorCode.ALREADY_VERIFIED);
        }

        History newHistory = new History(userId, challenge, content, true);

        return historyService.save(newHistory);
    }

    public List<Participant> getWinners(Long challengeId) {
        Challenge challenge = challengeService.getChallenge(challengeId);
        List<Participant> participants = participantService.getParticipantList(challenge.getId());
        List<Participant> winners = new ArrayList<>();

        long totalDays = ChronoUnit.DAYS.between(challenge.getStartAt(), challenge.getDueAt()) + 1;
        for (Participant participant : participants) {
            int successfulDays = historyService.countOfSuccess(challenge.getId(), participant.getUserId());
            int successRate = (int) ((successfulDays * 100) / totalDays);
            if (successRate > 70) {
                winners.add(participant);
            }
        }

        return winners;
    }

    public CursorResponse<History, LocalDateTime> getHistoryList(Long challengeId,
        Long userId,
        LocalDateTime cursor,
        int size
    ) {

        List<History> historyList = historyService.getHistoryList(challengeId, userId, cursor, size);

        return CursorResponse.of(historyList, History::getCreatedAt, size);
    }

    public int getSuccessRate(Long userId, Long challengeId) {
        Challenge challenge = challengeService.getChallenge(challengeId);

        if (challenge.getStartAt() == null) {
            throw new ChallengeException(ChallengeErrorCode.NOT_STARTED);
        }

        participantService.getParticipant(challengeId, userId);

        LocalDate today = LocalDate.now();
        LocalDate dueDate = challenge.getDueAt();
        LocalDate endDate = today.isAfter(dueDate) ? dueDate : today;
        long daysFromStart = ChronoUnit.DAYS.between(challenge.getStartAt(), endDate);
        long totalPeriod = daysFromStart + 1; // 오늘 포함

        int successfulDays = historyService.countOfSuccess(challengeId, userId);

        return (int) ((successfulDays * 100) / totalPeriod);
    }

    public List<Challenge> getChallengeList(Long userId,
        String name,
        ChallengeStatus status,
        LocalDate dueAt,
        ChallengeCategory category,
        Integer maxParticipationFee,
        LocalDateTime cursor,
        int size
    ) {
        return challengeService.getChallengeList(userId, name, status, dueAt, category, maxParticipationFee, cursor, size);
    }
}
