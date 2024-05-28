package org.webppo.clubcommunity_backend.response.exception.club;

import org.webppo.clubcommunity_backend.response.exception.common.BusinessException;
import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public abstract class ClubBusinessException extends BusinessException {

    public ClubBusinessException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public ClubBusinessException(ExceptionType exceptionType, Throwable cause) {
        super(exceptionType, cause);
    }
}
