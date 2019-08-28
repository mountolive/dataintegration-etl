package com.etlapp.services;

import java.util.Date;
import java.util.List;

import com.etlapp.entities.Logistic;
import com.etlapp.entities.RawLogistic;

public interface ITransformLogisticService {
    
    /**
     * Transform all {@link RawLogistic} with date greater or equal than fromDate into {@link Logistic}
     * doing the corresponding operations
     * @param fromDate starting date to retrieve from DB
     * @return List of {@link Logistic} created
     */
    List<Logistic> transform(Date fromDate);

}
