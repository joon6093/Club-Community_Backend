package org.webppo.clubcommunity_backend.response.exception.member;

import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public class UnsupportedOAuth2Exception extends MemberBusinessException {
    public UnsupportedOAuth2Exception() {
        super(ExceptionType.UNSUPPORTED_OAUTH2_EXCEPTION);
    }
}
