package org.webppo.clubcommunity_backend.response.exception.club.clubForm;

import org.webppo.clubcommunity_backend.response.exception.common.BusinessException;
import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public abstract class ClubFormBusinessException extends BusinessException {

    public ClubFormBusinessException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public ClubFormBusinessException(ExceptionType exceptionType, Throwable cause) {
        super(exceptionType, cause);
    }
}
