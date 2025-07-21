package com.github.nenidan.ne_ne_challenge.domain.challenge.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.ChallengeSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.CreateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeRepository;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeUserService challengeUserService;

    private final ChallengeRepository challengeRepository;

    private final UserRepository userRepository;

    @Transactional
    public ChallengeResponse createChallenge(CreateChallengeRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Challenge newChallenge = request.toEntity();

        Challenge savedChallenge = challengeRepository.save(newChallenge);
        challengeUserService.joinChallenge(user.getId(), savedChallenge.getId(), true);

        return ChallengeResponse.from(savedChallenge);
    }

    public ChallengeResponse getChallenge(Long id) {
        Challenge foundChallenge = challengeRepository.findById(id).orElseThrow(() -> new ChallengeException(
            ChallengeErrorCode.CHALLENGE_NOT_FOUND));

        return ChallengeResponse.from(foundChallenge);
    }

    public CursorResponse<ChallengeResponse, LocalDateTime> getChallengeList(ChallengeSearchCond cond) {
        int size = cond.getSize();

        List<ChallengeResponse> challengeList = challengeRepository.getChallengeList(cond.getUserId(),
            cond.getName(),
            cond.getStatus(),
            cond.getDueAt(),
            cond.getCategory(),
            cond.getMaxParticipationFee(),
            cond.getCursor(),
            size + 1
        ).stream()
            .map(ChallengeResponse::from)
            .toList();

        boolean hasNext = challengeList.size() > size;
        List<ChallengeResponse> content = hasNext ? challengeList.subList(0, size) : challengeList;
        LocalDateTime nextCursor = hasNext ? challengeList.get(challengeList.size() - 1).getCreatedAt() : null;

        return CursorResponse.of(content, nextCursor, hasNext);
    }
}
