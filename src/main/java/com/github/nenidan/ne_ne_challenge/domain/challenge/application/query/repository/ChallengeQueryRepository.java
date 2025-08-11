package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.repository;

import java.util.List;
import java.util.Optional;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.request.ChallengeSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeResponse;

public interface ChallengeQueryRepository {

    Optional<ChallengeResponse> findChallengeById(Long challengeId);

    List<ChallengeResponse> findChallenges(ChallengeSearchCond cond);
}
