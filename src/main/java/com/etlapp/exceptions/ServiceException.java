package com.etlapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to be handled by controllers when a service fails
 * @author Leo Guercio
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ServiceException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -11002356819869377L;
    
    public ServiceException(String message) {
        super(String.format("An error occurred while executing call: \n %s", message));
    }

}
