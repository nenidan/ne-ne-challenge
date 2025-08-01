package com.github.nenidan.ne_ne_challenge.domain.challenge.application;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.request.CreateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.mapper.ChallengeMapper;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Participant;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.service.ChallengeFacade;
import com.github.nenidan.ne_ne_challenge.global.client.point.PointClient;
import com.github.nenidan.ne_ne_challenge.global.client.user.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeApplicationService {

    private final ChallengeFacade challengeFacade;
    private final UserClient userClient;
    private final PointClient pointClient;
    private final ChallengeMapper mapper = ChallengeMapper.INSTANCE;

    public ChallengeResponse createChallenge(CreateChallengeRequest request, Long loginUserId) {
        // Todo: 유저 존재 검증

        // Todo: 포인트 조회
        // int point = pointClient.getPoint();
        int point = 9999999;

        Challenge challenge = challengeFacade.createChallenge(mapper.toInfo(request), loginUserId, point);
        challengeFacade.createParticipant(challenge, loginUserId, true);

        return mapper.toResponse(challenge);
    }

    public ChallengeResponse getChallenge(Long challengeId) {
        return mapper.toResponse(challengeFacade.getChallenge(challengeId));
    }
}
