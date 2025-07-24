package com.github.nenidan.ne_ne_challenge.notification.domain.entity;

public enum NotificationType {
	ALL,

	// 챌린지 관련
	CHALLENGE_STARTED,       // 챌린지가 시작되었을 때
	CHALLENGE_ENDED,         // 챌린지가 종료되었을 때
	CHALLENGE_REMINDER,      // 인증 마감 리마인드

	// 보상 관련
	REWARD_GRANTED,          // 포인트 또는 보상 지급됨

	// 인증 관련
	VERIFICATION_SUCCESS,    // 인증 성공
	VERIFICATION_MISSED,     // 인증 누락

	// 시스템 관련
	SYSTEM_ALERT,            // 서버 장애, 점검 등 시스템 경고
	ADMIN_MESSAGE,           // 관리자 공지사항 등 수동 메시지
}