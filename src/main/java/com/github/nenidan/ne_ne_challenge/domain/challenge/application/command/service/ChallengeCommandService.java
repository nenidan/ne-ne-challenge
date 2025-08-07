package com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.CreateChallengeCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.CreateHistoryCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.UpdateChallengeInfoCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.mapper.ChallengeMapper;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.History;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.ChallengeRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.HistoryRepository;
import com.github.nenidan.ne_ne_challenge.global.client.point.PointClient;
import com.github.nenidan.ne_ne_challenge.global.client.user.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeCommandService {

    private final UserClient userClient;
    private final PointClient pointClient;

    private final ChallengeRepository challengeRepository;
    private final HistoryRepository historyRepository;

    private static final ChallengeMapper challengeMapper = ChallengeMapper.INSTANCE;

    private final SelectWinnerService selectWinnerService;

    public Long createChallenge(Long requesterId, CreateChallengeCommand command) {
        verifyUserExists(requesterId);
        int userPoint = pointClient.getMyBalance(requesterId).getBalance();

        Challenge newChallenge = Challenge.createChallenge(requesterId, userPoint, challengeMapper.toInfo(command));

        Challenge savedChallenge = challengeRepository.save(newChallenge);
        pointClient.decreasePoint(requesterId, savedChallenge.getParticipationFee(), "CHALLENGE_ENTRY");
        return savedChallenge.getId();
    }

    public void updateChallengeInfo(Long requesterId, Long challengeId, UpdateChallengeInfoCommand command) {
        Challenge challenge = getChallengeOrThrow(challengeId);

        challenge.updateInfo(requesterId, command);
    }

    public void deleteChallenge(Long requesterId, Long challengeId) {
        verifyUserExists(requesterId);
        Challenge challenge = getChallengeOrThrow(challengeId);

        List<Long> participantIdList = challenge.getParticipantIdList();
        challenge.deleteChallenge(requesterId);
        pointClient.refundPoints(participantIdList, challenge.getParticipationFee());
    }

    public void joinChallenge(Long requesterId, Long challengeId) {
        verifyUserExists(requesterId);
        int userPoint = pointClient.getMyBalance(requesterId).getBalance();
        Challenge challenge = getChallengeOrThrow(challengeId);

        challenge.join(requesterId, userPoint);
        pointClient.decreasePoint(requesterId, challenge.getParticipationFee(), "CHALLENGE_ENTRY");
    }

    public void quitChallenge(Long requesterId, Long challengeId) {
        Challenge challenge = getChallengeOrThrow(challengeId);

        challenge.quit(requesterId);
        if(challenge.getStatus() == ChallengeStatus.WAITING) {
            pointClient.refundPoints(List.of(requesterId), challenge.getParticipationFee());
        }
    }

    public void updateChallengeStatus(Long userId, Long challengeId, ChallengeStatus newStatus) {
        verifyUserExists(userId);
        Challenge challenge = getChallengeOrThrow(challengeId);

        if(newStatus == READY) {
            challenge.ready();
        }
        else if(newStatus == ONGOING) {
            challenge.start();
        } else if(newStatus == FINISHED) {
            challenge.finish();
            // Todo 비동기 처리로 변환 가능
            List<Long> winners = selectWinnerService.getWinners(challenge);
            if(!winners.isEmpty()) {
                pointClient.refundPoints(winners, challenge.getTotalFee() / winners.size());
            }
        }
    }

    public Long createHistory(Long requesterId, Long challengeId, CreateHistoryCommand command) {
        Challenge challenge = getChallengeOrThrow(challengeId);
        challenge.checkParticipation(requesterId);
        challenge.checkCanWrite();

        History history = History.createSuccesfulHistory(requesterId, challengeId, command.getContent());
        try {
            return historyRepository.save(history).getId();
        } catch (DataIntegrityViolationException e) {
            throw new ChallengeException(ChallengeErrorCode.ALREADY_VERIFIED);
        }
    }

    private void verifyUserExists(Long requesterId) {
        userClient.getUserById(requesterId);
    }

    private Challenge getChallengeOrThrow(Long challengeId) {
        return challengeRepository.findById(challengeId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));
    }
}