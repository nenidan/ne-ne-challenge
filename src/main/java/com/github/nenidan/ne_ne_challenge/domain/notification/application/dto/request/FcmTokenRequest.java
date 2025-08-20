package com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm.Platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FcmTokenRequest {
	@Schema(description = "토큰", example = "fRpYpt6bTaQ69E5OUO8cWy:APA91bHPmp930uUmbp1NQ3BXY-00d6IXewfD_kmwGe0WbhYxpeus3bv-frQBLZtj_LAPNeKWgXWmyp65vHF8k2q3NE5XVLOmkIN-nNYuIp6jgkp8icCr1gQ")
	private String token;
	@Schema(description = "플랫폼", example = "WEB")
	private Platform platform;
}
