package org.webppo.clubcommunity_backend.response.exception.member;

import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public class RoleNotFoundException extends MemberBusinessException {
    public RoleNotFoundException() {
        super(ExceptionType.ROLE_NOT_FOUND_EXCEPTION);
    }
}
