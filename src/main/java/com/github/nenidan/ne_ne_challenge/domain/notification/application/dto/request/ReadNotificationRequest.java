package com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReadNotificationRequest {
	@Schema(description = "읽음 처리", example = "true")
	boolean read;
}
