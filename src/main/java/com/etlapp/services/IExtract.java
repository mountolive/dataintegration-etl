package com.etlapp.services;

import java.time.LocalDateTime;
import java.util.List;

public interface IExtract<T> {
    
    /**
     * Retrieves all Input with date later than the passed fromDate and turns
     * them into a {@link List} of Raw data
     * @param fromDate
     * @return List of Raw (Sale, Purchase, Logistic)
     */
    List<T> extractRawFromJdbc(LocalDateTime fromDate);
    
    /**
     * Retrieves all of the worksheet's registers with date later than the passed fromDate and turns
     * them into a {@link List} of Raw data
     * @param fromDate
     * @return List of Raw (Sale, Purchase, Logistic)
     */
    List<T> extractRawFromWorksheet(LocalDateTime fromDate, boolean isXlsx);
}
