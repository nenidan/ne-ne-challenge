package com.github.nenidan.ne_ne_challenge.domain.challenge.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.ChallengeHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.ChallengeUser;
import com.github.nenidan.ne_ne_challenge.domain.challenge.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeUserRepository;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.response.UserResponse;
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
public class ChallengeUserService {

    private final UserRepository userRepository;

    private final ChallengeRepository challengeRepository;

    private final ChallengeUserRepository challengeUserRepository;

    @Transactional
    public void joinChallenge(long userId, long challengeId, boolean isHost) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(() -> new ChallengeException(
            ChallengeErrorCode.CHALLENGE_NOT_FOUND));

        // Todo 사용자의 포인트 검증
        challenge.addParticipant(user);
        ChallengeUser challengeUser = new ChallengeUser(user, challenge, isHost);

        challengeUserRepository.save(challengeUser);
    }

    public CursorResponse<UserResponse, Long> getChallengeParticipantList(Long id, Long cursor, int size) {
        Challenge challenge = challengeRepository.findById(id).orElseThrow(() -> new ChallengeException(
            ChallengeErrorCode.CHALLENGE_NOT_FOUND));

        List<UserResponse> participantList = challengeUserRepository.getParticipantList(id,
            cursor,
            size
        ).stream().map(UserResponse::from).toList();

        boolean hasNext = participantList.size() > size;
        List<UserResponse> content = hasNext ? participantList.subList(0, size) : participantList;
        Long nextCursor = hasNext ? participantList.get(participantList.size() - 1).getId() : null;

        return CursorResponse.of(content, nextCursor, hasNext);
    }
}
