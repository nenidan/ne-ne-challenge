package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository;

import java.util.List;
import java.util.Optional;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;

public interface ChallengeRepository {

    Challenge save(Challenge challenge);

    Optional<Challenge> findById(Long id);

    List<Challenge> findAll(); // fixme: 통계 쿼리까지 분리 시 제거 예정
}
