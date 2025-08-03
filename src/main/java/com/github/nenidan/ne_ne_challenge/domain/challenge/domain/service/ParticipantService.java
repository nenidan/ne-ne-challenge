package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Participant;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.ParticipantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public Participant createParticipant(Challenge challenge, Long loginUserId, boolean isHost) {
        Participant participant = new Participant(loginUserId, challenge, isHost);
        return participantRepository.save(participant);
    }

    public Participant getParticipant(Long challengeId, Long userId) {
        return participantRepository.findByChallengeIdAndUserId(challengeId, userId).orElseThrow(
            () -> new ChallengeException(ChallengeErrorCode.NOT_PARTICIPATING)
        );
    }

    public List<Participant> getParticipantList(Long challengeId) {
        return participantRepository.findbyChallengeId(challengeId);
    }

    public void delete(Participant participant) {
        participant.delete();
        participantRepository.save(participant);
    }

    public int getParticipantCount(Long challengeId) {
        return participantRepository.getParticipantCount(challengeId);
    }
}
