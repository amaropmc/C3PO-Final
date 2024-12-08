package com.codeforall.online.c3po.exceptions;

import com.codeforall.online.c3po.errors.ErrorMessage;

/**
 * Throws an exception if a Planet was not found
 */
public class PlanetNotFoundException extends C3POException {

    public PlanetNotFoundException() {
        super(ErrorMessage.PLANET_NOT_FOUND);
    }
}
