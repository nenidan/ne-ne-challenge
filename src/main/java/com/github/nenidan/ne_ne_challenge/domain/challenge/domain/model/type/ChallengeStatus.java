package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type;

/**
 * WAITING: 챌린지 시작 전
 * READY: 챌린지 시작 전 참가자 확정
 * ONGOING: 챌린지 진행중
 * FINISHED: 챌린지 종료
 */
public enum ChallengeStatus {
    WAITING,
    READY,
    ONGOING,
    FINISHED;
}
