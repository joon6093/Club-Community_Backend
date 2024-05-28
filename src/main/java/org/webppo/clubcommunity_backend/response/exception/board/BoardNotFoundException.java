package org.webppo.clubcommunity_backend.response.exception.board;

import org.webppo.clubcommunity_backend.response.exception.common.ExceptionType;

public class BoardNotFoundException extends BoardBusinessException {

    public BoardNotFoundException() {
        super(ExceptionType.BOARD_NOT_FOUND_EXCEPTION);
    }
}
