package com.etlapp.utilities;

import java.time.LocalDateTime;

import org.apache.logging.log4j.Logger;

import com.etlapp.exceptions.BadParametersException;
import com.etlapp.exceptions.ServiceException;
import com.etlapp.services.IExtract;
import com.etlapp.services.ITransform;

/**
 * Helper class that handles each case of extraction and transformation.
 * Used by the controllers. It's basically a try-catch wrapper around
 * the services
 * @author Leo Guercio
 *
 * @param <E> Raw class (RawSale, RawLogistic, RawPurchase)
 * @param <T> Transformed classe (Sale, Logistic, Purchase)
 */
public class ControllerHelper<E, T> {
    public void checkDateIsCorrect(final LocalDateTime param) {
        if(param == null) throw new BadParametersException("You must pass a datetime to the endpoint");
    }
    
    public void extractFromJdbc(final IExtract<E> extractService, final LocalDateTime fromDate, final Logger log) {
        try {
            extractService.extractRawFromJdbc(fromDate);
        } catch(Exception e) {
            String message = String.format("An error ocurred while extracting for given date % s", e.getMessage());
            log.error(message);
            throw new ServiceException(message);
        }
    }
    
    public void extractFromWorksheet(final IExtract<E> extractService, final LocalDateTime fromDate, final boolean isXlsx, final Logger log) {
        try {
            extractService.extractRawFromWorksheet(fromDate, isXlsx);
        } catch(Exception e) {
            String message = String.format("An error ocurred while extracting for given date % s", e.getMessage());
            log.error(message);
            throw new ServiceException(message);
        }
    }
    
    public void transform(final ITransform<T> transformService, final LocalDateTime fromDate, final Logger log) {
        try {
            transformService.transform(fromDate);
        } catch(Exception e) {
            String message = String.format("An error ocurred while transforming for given date % s", e.getMessage());
            log.error(message);
            throw new ServiceException(message);
        }
    }
    
}
