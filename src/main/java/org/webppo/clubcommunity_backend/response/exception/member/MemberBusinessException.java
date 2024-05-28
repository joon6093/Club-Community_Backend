package org.webppo.clubcommunity_backend.response.exception.member;

import org.webppo.clubcommunity_backend.response.exception.common.BusinessException;
import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public abstract class MemberBusinessException extends BusinessException {

    public MemberBusinessException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public MemberBusinessException(ExceptionType exceptionType, Throwable cause) {
        super(exceptionType, cause);
    }
}
