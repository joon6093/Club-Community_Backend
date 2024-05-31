package org.webppo.clubcommunity_backend.response.exception.club.clubJoinForm;

import org.webppo.clubcommunity_backend.entity.club.clubJoinForm.ClubJoinForm;
import org.webppo.clubcommunity_backend.response.exception.club.clubForm.ClubFormBusinessException;
import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public class ClubJoinFormNotFoundException extends ClubJoinFormBusinessException {

    public ClubJoinFormNotFoundException() {
        super(ExceptionType.CLUB_JOIN_FORM_NOT_FOUND);
    }
}
