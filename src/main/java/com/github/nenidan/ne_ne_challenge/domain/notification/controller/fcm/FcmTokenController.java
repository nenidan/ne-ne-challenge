package com.github.nenidan.ne_ne_challenge.domain.notification.controller.fcm;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.notification.dto.fcm.request.FcmTokenRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.service.fcm.FcmTokenService;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FcmTokenController {
	private final FcmTokenService fcmTokenService;

	@PostMapping("/fcm/token")
	public ResponseEntity<ApiResponse<Void>> saveToken(@AuthenticationPrincipal Auth auth, @RequestBody FcmTokenRequest request) {
		return ApiResponse.success(
			HttpStatus.OK,
			"FCM 토큰 저장완료",
			fcmTokenService.saveToken(auth.getId(), request.getToken())
		);
	}
}
