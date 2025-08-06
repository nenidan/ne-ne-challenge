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

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeCommandService {

    private final UserClient userClient;
    private final PointClient pointClient;

    private final ChallengeRepository challengeRepository;

    private static final ChallengeMapper challengeMapper = ChallengeMapper.INSTANCE;

    public Long createChallenge(Long loginUserId, CreateChallengeCommand command) {
        userClient.getUserById(loginUserId);
        int userPoint = pointClient.getMyBalance(loginUserId).getBalance();

        Challenge newChallenge = Challenge.createChallenge(loginUserId, userPoint, challengeMapper.toInfo(command));

        Challenge savedChallenge = challengeRepository.save(newChallenge);
        pointClient.decreasePoint(loginUserId, command.getParticipationFee(), "CHALLENGE_ENTRY");
        return savedChallenge.getId();
    }

    public void updateChallengeInfo(Long loginUserId, Long challengeId, UpdateChallengeInfoCommand command) {
        Challenge challenge = challengeRepository.findById(challengeId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));

        challenge.updateInfo(loginUserId, command);
    }

    public void deleteChallenge(Long userId, Long challengeId) {
        // Todo
    }

    public void joinChallenge(Long userId, Long challengeId) {
        // Todo
    }

    public void updateChallengeStatus(Long userId, Long challengeId, ChallengeStatus status) {
        // Todo
    }

    public Long createHistory(CreateHistoryCommand command, Long userId, Long challengeId) {
        return null; // Todo
    }
}