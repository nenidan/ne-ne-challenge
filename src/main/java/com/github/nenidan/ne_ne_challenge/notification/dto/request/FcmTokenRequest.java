package com.github.nenidan.ne_ne_challenge.notification.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FcmTokenRequest {
	private String token;
}
