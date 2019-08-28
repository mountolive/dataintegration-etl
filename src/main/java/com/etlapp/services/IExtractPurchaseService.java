package com.etlapp.services;

import java.util.Date;
import java.util.List;

import com.etlapp.entities.RawPurchase;
import com.etlapp.extraction.input.entities.InputPurchase;

public interface IExtractPurchaseService {

    /**
     * Retrieves all {@link InputPurchase} with date later than the passed fromDate and turns
     * them into a {@link List} of {@link RawPurchase}
     * @param fromDate
     * @return List of RawPurchase
     */
    List<RawPurchase> extractRawPurchaseFromJdbc(Date fromDate);
    
    /**
     * Retrieves all of the worksheet's registers with date later than the passed fromDate and turns
     * them into a {@link List} of {@link RawPurchase}
     * @param fromDate
     * @return List of RawPurchase
     */
    List<RawPurchase> extractRawPurchaseFromWorksheet(Date fromDate, boolean isXlsx);
    
}
