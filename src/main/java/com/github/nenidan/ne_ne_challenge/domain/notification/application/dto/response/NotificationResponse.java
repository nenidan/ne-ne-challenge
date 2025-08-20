package com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.response;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.Notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
	@Schema(description = "알림 ID", example = "1")
	private Long id;
	@Schema(description = "제목", example = "리마인드 알림")
	private String title;
	@Schema(description = "내용", example = "오후 9시, 인증을 하지않아 알림 전송드립니다.")
	private String content;
	@Schema(description = "타입", example = "ADMIN_MESSAGE")
	private String type;
	@Schema(description = "읽음 처리", example = "1")
	private boolean isRead;
	@Schema(description = "날짜", example = "2025-07-02 11:00:00")
	private LocalDateTime createdAt;

	public static NotificationResponse from(Notification notification) {
		return new NotificationResponse(
			notification.getId(),
			notification.getTitle(),
			notification.getContent(),
			notification.getType().name(),
			notification.isRead(),
			notification.getCreatedAt()
		);
	}
}
