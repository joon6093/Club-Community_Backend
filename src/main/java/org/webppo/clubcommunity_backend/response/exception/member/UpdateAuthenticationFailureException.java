package org.webppo.clubcommunity_backend.response.exception.member;

import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public class UpdateAuthenticationFailureException extends MemberBusinessException {
    public UpdateAuthenticationFailureException() {
        super(ExceptionType.UPDATE_AUTHENTICATION_FAILURE_EXCEPTION);
    }
}
