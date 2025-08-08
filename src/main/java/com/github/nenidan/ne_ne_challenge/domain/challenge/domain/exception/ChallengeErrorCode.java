package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception;

import org.springframework.http.HttpStatus;

import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

public enum ChallengeErrorCode implements ErrorCode {
    REQUEST_ERROR("요청값이 올바르지 않습니다", HttpStatus.BAD_REQUEST),
    EMPTY_HISTORY_CONTENT("인증 기록은 비어있을 수 없습니다.",HttpStatus.BAD_REQUEST),

    CHALLENGE_NOT_FOUND("챌린지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    HISTORY_NOT_FOUND("인증기록을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    NOT_HOST("권한이 없습니다", HttpStatus.FORBIDDEN),
    NOT_PARTICIPATING("참여 중인 챌린지가 아닙니다.", HttpStatus.FORBIDDEN),

    POINT_INSUFFICIENT("포인트가 부족합니다.", HttpStatus.CONFLICT),
    NOT_MODIFIABLE("수정할 수 없는 상태입니다.", HttpStatus.CONFLICT),
    INVALID_DATE("잘못된 시작일/종료일입니다.", HttpStatus.CONFLICT),
    INVALID_STATUS_TRANSITION("올바르지 않은 상태 변경입니다.", HttpStatus.CONFLICT),
    ALREADY_VERIFIED("이미 오늘의 기록을 남겼습니다.", HttpStatus.CONFLICT),
    ALREADY_PARTICIPATED("같은 챌린지에 참여 이력이 있습니다.", HttpStatus.CONFLICT),
    NOT_ENOUGH_PARTICIPANTS("참가 인원이 부족합니다.", HttpStatus.CONFLICT),
    NOT_WAITING("챌린지가 대기 중이 아닙니다.", HttpStatus.CONFLICT),
    NOT_STARTED("아직 시작하지 않은 챌린지입니다.", HttpStatus.CONFLICT),
    NOT_ONGOING("진행 중인 챌린지가 아닙니다.", HttpStatus.CONFLICT),
    CHALLENGE_FULL("챌린지 참가자가 가득 찼습니다.", HttpStatus.CONFLICT),
    NOT_QUITABLE("챌린지를 나갈 수 없는 상태입니다.", HttpStatus.CONFLICT),
    HOST_CANNOT_QUIT("방장은 나갈 수 없습니다.", HttpStatus.CONFLICT),
    START_DATE_NOT_REACHED("아직 시작일 이전입니다.", HttpStatus.CONFLICT),
    NOT_WRITABLE("기록을 남길 수 없는 상태입니다.", HttpStatus.CONFLICT),
    STILL_ONGOING("아직 진행중인 챌린지입니다.", HttpStatus.CONFLICT),

    SERVER_ERROR("첼린지 처리 중 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus httpStatus; // Todo: 도메인에서 Http 의존성 제거 필요

    ChallengeErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getStatus() {
        return httpStatus;
    }
}
