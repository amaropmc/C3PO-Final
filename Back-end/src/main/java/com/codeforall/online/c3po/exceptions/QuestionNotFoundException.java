package com.codeforall.online.c3po.exceptions;

import com.codeforall.online.c3po.errors.ErrorMessage;

/**
 * Throws an exception if a Question was not found
 */
public class QuestionNotFoundException extends C3POException {

    public QuestionNotFoundException() {
        super(ErrorMessage.QUESTION_NOT_FOUND);
    }
}
