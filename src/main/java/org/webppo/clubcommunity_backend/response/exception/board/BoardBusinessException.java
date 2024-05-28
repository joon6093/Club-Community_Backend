package org.webppo.clubcommunity_backend.response.exception.board;

import org.webppo.clubcommunity_backend.response.exception.common.BusinessException;
import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public abstract class BoardBusinessException extends BusinessException {

    public BoardBusinessException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public BoardBusinessException(ExceptionType exceptionType, Throwable cause) {
        super(exceptionType, cause);
    }
}
