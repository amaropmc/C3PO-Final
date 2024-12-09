package com.codeforall.online.c3po.exceptions;

import com.codeforall.online.c3po.errors.ErrorMessage;

/**
 * Throws an exception if a Player was not found
 */
public class PlayerNotFoundException extends C3POException {

    public PlayerNotFoundException() {
        super(ErrorMessage.PLAYER_NOT_FOUND);
    }
}
