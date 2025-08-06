package com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.CreateChallengeCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.CreateHistoryCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.UpdateChallengeInfoCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.mapper.ChallengeMapper;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.ChallengeRepository;
import com.github.nenidan.ne_ne_challenge.global.client.point.PointClient;
import com.github.nenidan.ne_ne_challenge.global.client.user.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeCommandService {

    private final UserClient userClient;
    private final PointClient pointClient;

    private final ChallengeRepository challengeRepository;

    private static final ChallengeMapper challengeMapper = ChallengeMapper.INSTANCE;

    public Long createChallenge(Long loginUserId, CreateChallengeCommand command) {
        verifyUserExists(loginUserId);
        int userPoint = pointClient.getMyBalance(loginUserId).getBalance();

        Challenge newChallenge = Challenge.createChallenge(loginUserId, userPoint, challengeMapper.toInfo(command));

        Challenge savedChallenge = challengeRepository.save(newChallenge);
        pointClient.decreasePoint(loginUserId, savedChallenge.getParticipationFee(), "CHALLENGE_ENTRY");
        return savedChallenge.getId();
    }

    public void updateChallengeInfo(Long loginUserId, Long challengeId, UpdateChallengeInfoCommand command) {
        Challenge challenge = getChallengeOrThrow(challengeId);

        challenge.updateInfo(loginUserId, command);
    }

    public void deleteChallenge(Long loginUserId, Long challengeId) {
        verifyUserExists(loginUserId);
        Challenge challenge = getChallengeOrThrow(challengeId);

        List<Long> participantIdList = challenge.getParticipantIdList();
        challenge.deleteChallenge(loginUserId);
        pointClient.refundPoints(participantIdList, challenge.getParticipationFee());
    }

    public void joinChallenge(Long loginUserId, Long challengeId) {
        verifyUserExists(loginUserId);
        int userPoint = pointClient.getMyBalance(loginUserId).getBalance();
        Challenge challenge = getChallengeOrThrow(challengeId);

        challenge.join(loginUserId, userPoint);
        pointClient.decreasePoint(loginUserId, challenge.getParticipationFee(), "CHALLENGE_ENTRY");
    }

    public void quitChallenge(Long userId, Long challengeId) {
        // Todo
    }

    public void updateChallengeStatus(Long userId, Long challengeId, ChallengeStatus status) {
        // Todo
    }

    public Long createHistory(CreateHistoryCommand command, Long userId, Long challengeId) {
        return null; // Todo
    }

    private void verifyUserExists(Long loginUserId) {
        userClient.getUserById(loginUserId);
    }

    private Challenge getChallengeOrThrow(Long challengeId) {
        return challengeRepository.findById(challengeId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));
    }
}