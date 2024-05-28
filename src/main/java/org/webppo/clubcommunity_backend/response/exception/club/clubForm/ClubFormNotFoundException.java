package org.webppo.clubcommunity_backend.response.exception.club.clubForm;

import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;
import org.webppo.clubcommunity_backend.response.exception.member.MemberBusinessException;

public class ClubFormNotFoundException extends ClubFormBusinessException {

    public ClubFormNotFoundException() {
        super(ExceptionType.CLUB_FROM_NOT_FOUND);
    }
}
