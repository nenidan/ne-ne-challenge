package com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.CreateChallengeCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.CreateHistoryCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.UpdateChallengeInfoCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
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

    public Long createChallenge(Long loginUserId, CreateChallengeCommand command) {
        // Todo
        return null;
    }

    public void updateChallengeInfo(Long loginUserId, Long challengeId, UpdateChallengeInfoCommand command) {
        // Todo
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