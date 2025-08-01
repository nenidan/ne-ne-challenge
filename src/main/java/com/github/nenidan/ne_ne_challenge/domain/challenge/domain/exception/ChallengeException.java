package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception;

import com.github.nenidan.ne_ne_challenge.global.exception.BusinessException;
import com.github.nenidan.ne_ne_challenge.global.exception.ErrorCode;

public class ChallengeException extends BusinessException {

    public ChallengeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
