package com.github.nenidan.ne_ne_challenge.domain.notification.presentation;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name="FCM")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FcmTokenController {
	private final FcmTokenService fcmTokenService;

	@Operation(summary = "생성", description = "FCM 토큰을 저장 합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "토큰 저장 성공"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
	})
	@PostMapping("/fcm/token/{userId}")
	public ResponseEntity<ApiResponse<Void>> saveToken(@PathVariable Long userId, @RequestBody FcmTokenRequest request) {
		return ApiResponse.success(
			HttpStatus.OK,
			"FCM 토큰 저장완료",
			fcmTokenService.saveToken(userId, request)
		);
	}
}
