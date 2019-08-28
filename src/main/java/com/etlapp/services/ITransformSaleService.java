package com.etlapp.services;

import java.util.Date;
import java.util.List;

import com.etlapp.entities.RawSale;
import com.etlapp.entities.Sale;

public interface ITransformSaleService {
    
    /**
     * Transform all {@link RawSale} with date greater or equal than fromDate into {@link Sale}
     * doing the corresponding operations
     * @param fromDate starting date to retrieve from DB
     * @return List of {@link Sale} created
     */
    List<Sale> transform(Date fromDate);

}
