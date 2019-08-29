package com.etlapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadParametersException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -334395857173477375L;
    
    public BadParametersException(String message) {
        super(String.format("There was an error regarding the parameters of the request: \n %s", message));
    }

}
