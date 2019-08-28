package com.etlapp.services;

import java.util.Date;
import java.util.List;

import com.etlapp.entities.RawLogistic;
import com.etlapp.extraction.input.entities.InputLogistic;

public interface IExtractLogisticService {

    /**
     * Retrieves all {@link InputLogistic} with date later than the passed fromDate and turns
     * them into a {@link List} of {@link RawLogistic}
     * @param fromDate
     * @return List of RawLogistic
     */
    List<RawLogistic> extractRawLogisticFromJdbc(Date fromDate);
    
    /**
     * Retrieves all of the worksheet's registers with date later than the passed fromDate and turns
     * them into a {@link List} of {@link RawLogistic}
     * @param fromDate
     * @return List of RawLogistic
     */
    List<RawLogistic> extractRawLogisticFromWorksheet(Date fromDate, boolean isXlsx);
    
}
