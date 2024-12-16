package com.codeforall.online.c3po.exceptions;

import com.codeforall.online.c3po.errors.ErrorMessage;

public class InvalidScoreException extends C3POException {

    public InvalidScoreException() {
        super(ErrorMessage.INVALID_NEW_SCORE);
    }
}
