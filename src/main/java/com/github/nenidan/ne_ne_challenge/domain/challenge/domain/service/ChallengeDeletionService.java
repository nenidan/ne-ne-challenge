package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.global.client.point.PointClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeDeletionService {

    private final PointClient pointClient;

    public void deleteChallenge(Long loginUserId, Challenge challenge) {
        List<Long> participantIdList = challenge.getParticipantIdList();

        challenge.deleteChallenge(loginUserId);

        pointClient.refundPoints(participantIdList, challenge.getParticipationFee());
    }
}
