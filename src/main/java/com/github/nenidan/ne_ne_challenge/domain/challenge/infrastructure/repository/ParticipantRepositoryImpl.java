package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Participant;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.ParticipantRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ParticipantRepositoryImpl implements ParticipantRepository {

    private final JpaParticipantRepository jpaParticipantRepository;

    @Override
    public Participant save(Participant participant) {
        return jpaParticipantRepository.save(participant);
    }

    @Override
    public Optional<Participant> findById(Long id) {
        return jpaParticipantRepository.findById(id);
    }

    @Override
    public Optional<Participant> findByChallengeIdAndUserId(Long challengeId, Long userId) {
        return jpaParticipantRepository.findByChallenge_IdAndUserId(challengeId, userId);
    }

    @Override
    public List<Participant> findbyChallengeId(Long challengeId) {
        return jpaParticipantRepository.findByChallenge_Id(challengeId);
    }

    @Override
    public int getParticipantCount(Long challengeId) {
        return jpaParticipantRepository.countByChallenge_Id(challengeId);
    }

    @Override
    public List<Participant> findAll() {
        return jpaParticipantRepository.findAll();
    }
}
