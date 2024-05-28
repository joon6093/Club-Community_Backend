package org.webppo.clubcommunity_backend.response;

import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public class ResponseUtil {

    public static ResponseBody<Void> createSuccessResponse() {
        return new SuccessResponseBody<>();
    }

    public static <T> ResponseBody<T> createSuccessResponse(T data) {
        return new SuccessResponseBody<>(data);
    }

    public static ResponseBody<Void> createFailureResponse(ExceptionType exceptionType) {
        return new FailedResponseBody(
                exceptionType.getCode(),
                exceptionType.getMessage()
        );
    }

    public static ResponseBody<Void> createFailureResponse(ExceptionType exceptionType, String customMessage) {
        return new FailedResponseBody(
                exceptionType.getCode(),
                customMessage
        );
    }
}
