package org.webppo.clubcommunity_backend.response.exception.club;

import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public class ClubNotFoundException extends ClubBusinessException {

    public ClubNotFoundException() {
        super(ExceptionType.CLUB_NOT_FOUND);
    }
}
