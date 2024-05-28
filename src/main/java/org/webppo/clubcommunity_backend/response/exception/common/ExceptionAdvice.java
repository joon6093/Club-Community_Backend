package org.webppo.clubcommunity_backend.response.exception.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webppo.clubcommunity_backend.response.ResponseBody;

import static org.webppo.clubcommunity_backend.response.ResponseUtil.createFailureResponse;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseBody<Void>> businessException(BusinessException e) {
        ExceptionType exceptionType = e.getExceptionType();
        return ResponseEntity.status(exceptionType.getStatus())
                .body(createFailureResponse(exceptionType));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBody<Void>> exception(Exception e) {
        log.error("Exception Message: {} ", e.getMessage());
        return ResponseEntity
                .status(ExceptionType.EXCEPTION.getStatus())
                .body(createFailureResponse(ExceptionType.EXCEPTION));
    }

    @ExceptionHandler(AccessDeniedException.class) // @PreAuthorize으로 부터 발생하는 오류
    public ResponseEntity<ResponseBody<Void>> accessDeniedException(AccessDeniedException e) {
        return ResponseEntity
                .status(ExceptionType.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(createFailureResponse(ExceptionType.ACCESS_DENIED_EXCEPTION));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseBody<Void>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String customMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity
                .status(ExceptionType.BIND_EXCEPTION.getStatus())
                .body(createFailureResponse(ExceptionType.BIND_EXCEPTION, customMessage));
    }
}
