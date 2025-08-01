package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.dto.ChallengeInfo;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public Challenge createChallenge(ChallengeInfo info) {
        Challenge newChallenge = Challenge.from(info);
        return challengeRepository.save(newChallenge);
    }

    public Challenge getChallenge(Long challengeId) {
        return challengeRepository.findById(challengeId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));
    }
}
