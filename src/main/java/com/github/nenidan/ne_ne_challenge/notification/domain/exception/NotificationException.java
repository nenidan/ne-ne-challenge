package com.github.nenidan.ne_ne_challenge.notification.domain.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.BusinessException;
import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

public class NotificationException extends BusinessException {
  public NotificationException(ErrorCode errorCode) {
    super(errorCode);
  }
}
