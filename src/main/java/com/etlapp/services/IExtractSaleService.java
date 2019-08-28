package com.etlapp.services;

import java.util.Date;
import java.util.List;

import com.etlapp.entities.RawSale;
import com.etlapp.extraction.input.entities.InputSale;

public interface IExtractSaleService {
    
    /**
     * Retrieves all {@link InputSale} with date later than the passed fromDate and turns
     * them into a {@link List} of {@link RawSale}
     * @param fromDate
     * @return List of RawSale
     */
    List<RawSale> extractRawSaleFromJdbc(Date fromDate);

    /**
     * Retrieves all of the worksheet's registers with date later than the passed fromDate and turns
     * them into a {@link List} of {@link RawSale}
     * @param fromDate
     * @return List of RawSale
     */
    List<RawSale> extractRawSaleFromWorksheet(Date fromDate, boolean isXlsx);
    
}
