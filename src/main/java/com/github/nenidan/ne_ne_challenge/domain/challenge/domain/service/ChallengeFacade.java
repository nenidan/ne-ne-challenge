package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.request.CreateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.dto.ChallengeInfo;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeFacade {

    private final ChallengeService challengeService;

    private final ParticipantService participantService;

    public Challenge createChallenge(ChallengeInfo info, Long userId, int point) {
        if(point < info.getParticipationFee()) {
            throw new ChallengeException(ChallengeErrorCode.POINT_INSUFFICIENT);
        }

        return challengeService.createChallenge(info);
    }

    public Participant createParticipant(Challenge challenge, Long loginUserId, boolean isHost) {
        return participantService.createParticipant(challenge, loginUserId, isHost);
    }

    public Challenge getChallenge(Long challengeId) {
        return challengeService.getChallenge(challengeId);
    }
}
