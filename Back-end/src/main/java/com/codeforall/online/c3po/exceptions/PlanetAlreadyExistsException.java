package com.codeforall.online.c3po.exceptions;

import com.codeforall.online.c3po.errors.ErrorMessage;

/**
 * Throws an exception if a Planet already exists
 */
public class PlanetAlreadyExistsException extends C3POException {

    public PlanetAlreadyExistsException() {
        super(ErrorMessage.PLANET_ALREADY_EXISTS);
    }
}
