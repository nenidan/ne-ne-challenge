package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Participant;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
}
