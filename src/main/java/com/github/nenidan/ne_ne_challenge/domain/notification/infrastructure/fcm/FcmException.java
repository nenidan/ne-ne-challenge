package com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm;

import com.github.nenidan.ne_ne_challenge.global.exception.BusinessException;
import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

public class FcmException extends BusinessException {
	public FcmException(ErrorCode errorCode) {
		super(errorCode);
	}
}
