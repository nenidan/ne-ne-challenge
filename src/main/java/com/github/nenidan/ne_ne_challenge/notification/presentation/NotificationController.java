package com.github.nenidan.ne_ne_challenge.notification.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import com.github.nenidan.ne_ne_challenge.notification.application.NotificationService;
import com.github.nenidan.ne_ne_challenge.notification.domain.entity.NotificationType;
import com.github.nenidan.ne_ne_challenge.notification.dto.request.ReadNotificationRequest;
import com.github.nenidan.ne_ne_challenge.notification.dto.request.SendNotificationRequest;
import com.github.nenidan.ne_ne_challenge.notification.dto.response.NotificationResponse;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotificationController {
	private final NotificationService notificationService;

	/*
	알림 생성 테스트 용 URL
	 */
	@PostMapping("/notifications/send")
	public ResponseEntity<ApiResponse<Void>> send(@RequestBody SendNotificationRequest notificationRequest){
		return ApiResponse.success(
			HttpStatus.OK,
			"알림 생성/보내기 완료",
			notificationService.send(notificationRequest)
		);
	}

	@PatchMapping("/notifications/{id}")
	public ResponseEntity<ApiResponse<Void>> read(@RequestBody ReadNotificationRequest request,@PathVariable Long id){
		return ApiResponse.success(
			HttpStatus.OK,
			"알림 읽음 처리 완료",
			notificationService.read(request,id)
		);
	}
	/*
		기본값
		- userId = 필수 값
		- cursor = 알림 ID
		- 알림 타입 : 전부
		- 커서 기준 : 알림 ID
		- 사이즈 : 10
	 */
	@GetMapping("/notifications")
	public ResponseEntity<ApiResponse<CursorResponse<NotificationResponse, Long>>> searchNotifications(
		@RequestParam Long userId,
		@RequestParam(defaultValue = "all") String isRead,
		@RequestParam(defaultValue = "ALL") NotificationType type,
		@RequestParam(defaultValue = "0") Long cursor,
		@RequestParam(defaultValue = "10") @Min(1) int size
	){
		return ApiResponse.success(
			HttpStatus.OK,
			"알림 목록 조회 성공",
			notificationService.findNotifications(userId, isRead, type, cursor, size)
		);
	}
}
