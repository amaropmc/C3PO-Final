package com.codeforall.online.c3po.exceptions;

import com.codeforall.online.c3po.errors.ErrorMessage;

/**
 * Throws an exception if an Answer was not found
 */
public class AnswerNotFoundException extends C3POException {

    public AnswerNotFoundException() {
        super(ErrorMessage.ANSWER_NOT_FOUND);
    }
}
