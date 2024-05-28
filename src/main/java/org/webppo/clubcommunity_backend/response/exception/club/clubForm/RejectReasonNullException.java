package org.webppo.clubcommunity_backend.response.exception.club.clubForm;

import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public class RejectReasonNullException extends ClubFormBusinessException {

    public RejectReasonNullException() {
        super(ExceptionType.REJECT_REASON_IS_NULL);
    }
}
