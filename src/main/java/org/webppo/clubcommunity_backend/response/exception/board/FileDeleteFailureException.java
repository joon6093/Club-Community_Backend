package org.webppo.clubcommunity_backend.response.exception.board;

import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public class FileDeleteFailureException extends BoardBusinessException {

    public FileDeleteFailureException() {
        super(ExceptionType.FILE_DELETE_FAILURE_EXCEPTION);
    }
}
