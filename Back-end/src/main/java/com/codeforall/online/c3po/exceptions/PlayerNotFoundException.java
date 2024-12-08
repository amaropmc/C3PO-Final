package com.codeforall.online.c3po.exceptions;

import com.codeforall.online.c3po.errors.ErrorMessage;

public class PlayerNotFoundException extends C3POException {

    public PlayerNotFoundException() {
        super(ErrorMessage.PLAYER_NOT_FOUND);
    }
}
