package com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.NotificationType;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm.Platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SendNotificationRequest {
	@Schema(description = "제목", example = "리마인드 알림")
	private String title;
	@Schema(description = "내용", example = "오후 9시, 인증을 하지않아 알림 전송드립니다.")
	private String content;
	@Schema(description = "타입", example = "ADMIN_MESSAGE")
	private NotificationType notificationType;
	@Schema(description = "받는 유저 ID", example = "1")
	private Long receiverId;
	@Schema(description = "보내는 유저 ID", example = "null")
	private Long senderId;
	@Schema(description = "타입", example = "WEB")
	private Platform platform;
}
