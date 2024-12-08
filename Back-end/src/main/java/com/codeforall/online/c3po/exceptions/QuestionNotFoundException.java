package com.codeforall.online.c3po.exceptions;

import com.codeforall.online.c3po.errors.ErrorMessage;

public class QuestionNotFoundException extends C3POException {

    public QuestionNotFoundException() {
        super(ErrorMessage.QUESTION_NOT_FOUND);
    }
}
