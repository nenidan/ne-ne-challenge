package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeResponse;

import java.util.Optional;

public interface ChallengeQueryRepository {

    Optional<ChallengeResponse> findChallengeById(Long challengeId);
}
