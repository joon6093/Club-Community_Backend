package org.webppo.clubcommunity_backend.response.exception.club.clubJoinForm;

import org.webppo.clubcommunity_backend.response.exception.common.BusinessException;
import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public abstract class ClubJoinFormBusinessException extends BusinessException {

    public ClubJoinFormBusinessException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public ClubJoinFormBusinessException(ExceptionType exceptionType, Throwable cause) {
        super(exceptionType, cause);
    }
}
