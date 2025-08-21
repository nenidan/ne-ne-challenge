package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.repository;

import java.util.List;
import java.util.Optional;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.request.ChallengeSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeDto;

public interface ChallengeQueryRepository {

    Optional<ChallengeDto> findChallengeById(Long challengeId);

    List<ChallengeDto> findChallenges(ChallengeSearchCond cond);
}
