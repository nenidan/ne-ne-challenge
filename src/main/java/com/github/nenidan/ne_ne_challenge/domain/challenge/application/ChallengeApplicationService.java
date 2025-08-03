package com.github.nenidan.ne_ne_challenge.domain.challenge.application;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.request.*;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.ChallengeHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.ChallengeSuccessRateResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.mapper.ChallengeMapper;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.History;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Participant;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.service.ChallengeFacade;
import com.github.nenidan.ne_ne_challenge.global.client.point.PointClient;
import com.github.nenidan.ne_ne_challenge.global.client.user.UserClient;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeApplicationService {

    private final ChallengeFacade challengeFacade;
    private final UserClient userClient;
    private final PointClient pointClient;
    private final ChallengeMapper mapper = ChallengeMapper.INSTANCE;


    public ChallengeResponse createChallenge(CreateChallengeRequest request, Long loginUserId) {

        userClient.getUserById(loginUserId); // Todo 예외

        int point = pointClient.getMyBalance(loginUserId).getBalance();

        Challenge challenge = challengeFacade.createChallenge(mapper.toInfo(request));
        challengeFacade.createParticipant(challenge, loginUserId, true, point);
        pointClient.decreasePoint(loginUserId, challenge.getParticipationFee(), "CHALLENGE_ENTRY");

        return mapper.toResponse(challenge);
    }

    public ChallengeResponse getChallenge(Long challengeId) {
        return mapper.toResponse(challengeFacade.getChallenge(challengeId));
    }

    public ChallengeResponse updateChallengeInfo(Long userId, Long challengeId, UpdateChallengeInfoRequest request) {
        // 유저 존재 검증
        Challenge challenge = challengeFacade.getChallenge(challengeId);
        Challenge updatedChallenge = challengeFacade.updateChallengeInfo(challenge, userId, mapper.toInfo(request));
        return mapper.toResponse(updatedChallenge);
    }

    public Void deleteChallenge(Long userId, Long challengeId) {

        userClient.getUserById(userId);
        challengeFacade.checkChallengeHost(userId, challengeId);

        List<Long> userIdList = challengeFacade.getParticipantList(challengeId).stream().map(Participant::getId).toList();
        int amount = challengeFacade.getChallengeParticipationFee(challengeId);
        pointClient.refundPoints(userIdList, amount);

        challengeFacade.deleteChallenge(challengeId);

        return null;
    }

    public ChallengeResponse joinChallenge(Long userId, Long challengeId) {
        userClient.getUserById(userId);

        Challenge challenge = challengeFacade.joinChallenge(userId, challengeId);
        pointClient.decreasePoint(userId, challenge.getParticipationFee(), "CHALLENGE_ENTRY");

        return mapper.toResponse(challenge);
    }

    public ChallengeResponse updateChallengeStatus(Long userId, Long challengeId, ChallengeStatus status) {
        userClient.getUserById(userId);
        challengeFacade.checkChallengeHost(userId, challengeId);

        Challenge challenge = challengeFacade.getChallenge(challengeId);

        Challenge updatedChallenge = challengeFacade.updateChallengeStatus(challenge, status);

        if(updatedChallenge.getStatus() == ChallengeStatus.FINISHED) {
            List<Participant> winners = challengeFacade.getWinners(challengeId);
            distributeTotalFee(winners, updatedChallenge.getTotalFee());
        }

        return mapper.toResponse(updatedChallenge);
    }

    private void distributeTotalFee(List<Participant> winners, int totalFee) {
        List<Long> idList = winners.stream().map(Participant::getUserId).toList();
        if(idList.isEmpty()) return;
        int reward = totalFee / idList.size();
        pointClient.refundPoints(idList, reward);
    }

    public ChallengeHistoryResponse createHistory(CreateHistoryRequest request, Long userId, Long challengeId) {
        userClient.getUserById(userId);

        History history = challengeFacade.createHistory(userId, challengeId, request.getContent());

        return mapper.toResponse(history);
    }

    public CursorResponse<ChallengeHistoryResponse, LocalDateTime> getHistoryList(Long challengeId, HistorySearchCond cond) {

        CursorResponse<History, LocalDateTime> result = challengeFacade.getHistoryList(
            challengeId,
            cond.getUserId(),
            cond.getCursor(),
            cond.getSize()
        );

        return CursorResponse.of(
            result.getContent().stream().map(mapper::toResponse).toList(),
            result.getNextCursor(),
            result.isHasNext()
        );
    }

    public ChallengeSuccessRateResponse getSuccessRate(Long userId, Long challengeId) {
        userClient.getUserById(userId);

        int successRate = challengeFacade.getSuccessRate(userId, challengeId);

        return new ChallengeSuccessRateResponse(successRate);
    }

    public CursorResponse<ChallengeResponse, LocalDateTime> getChallengeList(ChallengeSearchCond cond) {

        int size = cond.getSize();

        List<Challenge> challengeList = challengeFacade.getChallengeList(cond.getUserId(),
            cond.getName(),
            cond.getStatus(),
            cond.getDueAt(),
            cond.getCategory(),
            cond.getMaxParticipationFee(),
            cond.getCursor(),
            size + 1
        );

        List<ChallengeResponse> challengeResponseList = challengeList.stream()
            .map(mapper::toResponse).toList();

        return CursorResponse.of(challengeResponseList, ChallengeResponse::getCreatedAt, size);
    }
}
