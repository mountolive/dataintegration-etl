package com.etlapp.services;

import java.util.Date;
import java.util.List;

import com.etlapp.entities.Purchase;
import com.etlapp.entities.RawPurchase;

public interface ITransformPurchaseService {
    
    /**
     * Transform all {@link RawPurchase} with date greater or equal than fromDate into {@link Purchase}
     * doing the corresponding operations
     * @param fromDate starting date to retrieve from DB
     * @return List of {@link Purchase} created
     */
    List<Purchase> transform(Date fromDate);

}
