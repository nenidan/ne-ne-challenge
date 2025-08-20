package com.github.nenidan.ne_ne_challenge.domain.notification.presentation;

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

import com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request.ReadNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request.SendNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.response.NotificationResponse;
import com.github.nenidan.ne_ne_challenge.domain.notification.application.service.NotificationService;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.NotificationType;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Tag(name="알림", description = "알림 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotificationController {
	private final NotificationService notificationService;

	@Operation(summary = "생성/보내기", description = "알림을 전송 합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "알림 생성/보내기 완료"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
	})
	@PostMapping("/notifications/send")
	public ResponseEntity<ApiResponse<Void>> send(@RequestBody SendNotificationRequest notificationRequest){
		return ApiResponse.success(
			HttpStatus.OK,
			"알림 생성/보내기 완료",
			notificationService.send(notificationRequest)
		);
	}

	@Operation(summary = "읽음", description = "알림을 읽음 처리 합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "알림 읽음 처리 완료"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
	})
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
	@Operation(summary = "목록", description = "알림 목록을 확인합니다.")
	@ApiResponses({
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "알림 목록 조회 성공"),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류")
	})
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
