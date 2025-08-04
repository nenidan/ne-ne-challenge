package com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm.Platform;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FcmTokenRequest {
	private String token;
	private Platform platform;
}
