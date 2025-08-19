package com.github.nenidan.ne_ne_challenge.domain.notification.presentation;

import com.github.nenidan.ne_ne_challenge.global.aop.annotation.AuditIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request.FcmTokenRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm.FcmTokenService;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FcmTokenController {
	private final FcmTokenService fcmTokenService;

	@PostMapping("/fcm/token/{userId}")
	@AuditIgnore
	public ResponseEntity<ApiResponse<Void>> saveToken(@PathVariable Long userId, @RequestBody FcmTokenRequest request) {
		return ApiResponse.success(
			HttpStatus.OK,
			"FCM 토큰 저장완료",
			fcmTokenService.saveToken(userId, request)
		);
	}
}
