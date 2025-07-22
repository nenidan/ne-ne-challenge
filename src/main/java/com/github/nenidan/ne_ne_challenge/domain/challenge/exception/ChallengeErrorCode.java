package com.github.nenidan.ne_ne_challenge.domain.challenge.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ChallengeErrorCode implements ErrorCode {
    CHALLENGE_NOT_FOUND("챌린지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CHALLENGE_ALREADY_STARTED("이미 시작된 챌린지 입니다.", HttpStatus.BAD_REQUEST),
    CHALLENGE_ALREADY_FINISHED("이미 종료된 챌린지 입니다.", HttpStatus.BAD_REQUEST),
    CHALLENGE_PARTICIPANT_LIMIT_EXCEEDED("인원수가 부족하거나 초과합니다.", HttpStatus.BAD_REQUEST),
    INVALID_PARTICIPANT_LIMIT("올바르지 않은 인원수 제한입니다.", HttpStatus.BAD_REQUEST),
    NOT_PARTICIPATING("참여 중인 챌린지가 아닙니다.", HttpStatus.FORBIDDEN),
    NOT_HOST("챌린지 수정 권한이 없습니다.", HttpStatus.FORBIDDEN),
    INVALID_STATUS_TRANSITION("해당 상태로 변경할 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_DUE_DATE("올바르지 않은 마감일입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_VERIFIED("이미 오늘의 기록을 남겼습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
