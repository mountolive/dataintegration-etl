package com.etlapp.services;

import java.time.LocalDateTime;
import java.util.List;

public interface ITransform<T> {
    
    /**
     * Transform all Raw entities with date greater or equal than fromDate into Transformed entities (Sale, Logistic, Purchase)
     * doing the corresponding operations
     * @param fromDate starting date to retrieve from DB
     * @return List of entities (Sale, Logistic, Purchase) created
     */
    List<T> transform(LocalDateTime fromDate);

    
}
