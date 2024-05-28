package org.webppo.clubcommunity_backend.response.exception.board;

import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public class UnsupportedFileFormatException extends BoardBusinessException {

    public UnsupportedFileFormatException() {
        super(ExceptionType.UNSUPPORTED_FILE_FORMAT_EXCEPTION);
    }

    public UnsupportedFileFormatException(Throwable cause) {
        super(ExceptionType.UNSUPPORTED_FILE_FORMAT_EXCEPTION, cause);
    }
}
