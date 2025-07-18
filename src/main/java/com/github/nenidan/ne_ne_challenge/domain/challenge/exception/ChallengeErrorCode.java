package com.github.nenidan.ne_ne_challenge.domain.challenge.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ChallengeErrorCode implements ErrorCode {
    CHALLENGE_NOT_FOUND("챌린지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CHALLENGE_ALREADY_STARTED("이미 시작된 챌린지 입니다.", HttpStatus.CONFLICT),
    CHALLENGE_ALREADY_ENDED("이미 종료된 챌린지 입니다.", HttpStatus.CONFLICT),
    CHALLENGE_PARTICIPANT_LIMIT_EXCEEDED("최대 인원이 초과되었습니다.", HttpStatus.CONFLICT),
    CHALLENGE_MIN_PARTICIPANTS_NOT_MET("최소 인원", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus status;
}
