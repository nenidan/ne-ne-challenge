package com.github.nenidan.ne_ne_challenge.notification.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.notification.application.dto.request.FcmTokenRequest;
import com.github.nenidan.ne_ne_challenge.notification.infra.fcm.FcmTokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FcmTokenController {
	private final FcmTokenService fcmTokenService;

	@PostMapping("/fcm/token/{userId}")
	public ResponseEntity<ApiResponse<Void>> saveToken(@PathVariable Long userId, @RequestBody FcmTokenRequest request) {
		return ApiResponse.success(
			HttpStatus.OK,
			"FCM 토큰 저장완료",
			fcmTokenService.saveToken(userId, request)
		);
	}
}
