package com.github.nenidan.ne_ne_challenge.notification.dto.request;

import com.github.nenidan.ne_ne_challenge.notification.infra.fcm.Platform;

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
