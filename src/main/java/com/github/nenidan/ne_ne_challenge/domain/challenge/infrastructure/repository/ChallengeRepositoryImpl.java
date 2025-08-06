package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChallengeRepositoryImpl implements ChallengeRepository {

    private final JpaChallengeRepository jpaChallengeRepository;

    @Override
    public Challenge save(Challenge challenge) {
        return jpaChallengeRepository.save(challenge);
    }

    @Override
    public Optional<Challenge> findById(Long id) {
        return jpaChallengeRepository.findById(id);
    }

    @Override
    public List<Challenge> findAll() {
        return jpaChallengeRepository.findAll();
    }
}
