package org.webppo.clubcommunity_backend.response.exception.club;

import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public class NotClubMasterException extends ClubBusinessException {

    public NotClubMasterException() {
        super(ExceptionType.NOT_CLUB_MASTER);
    }
}
