package com.codeforall.online.c3po.exceptions;

import com.codeforall.online.c3po.errors.ErrorMessage;

public class PlayerAlreadyExistsException extends RuntimeException {

    public PlayerAlreadyExistsException () {
        super(ErrorMessage.PLAYER_ALREADY_EXISTS);
    }
}