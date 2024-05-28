package org.webppo.clubcommunity_backend.response.exception.board;

import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public class FileUploadFailureException extends BoardBusinessException {

    public FileUploadFailureException() {
        super(ExceptionType.FILE_UPLOAD_FAILURE_EXCEPTION);
    }

    public FileUploadFailureException(Throwable cause) {
        super(ExceptionType.FILE_UPLOAD_FAILURE_EXCEPTION, cause);
    }
}
