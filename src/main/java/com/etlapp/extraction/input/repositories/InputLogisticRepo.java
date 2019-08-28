package com.etlapp.extraction.input.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.etlapp.extraction.input.entities.InputLogistic;

@Repository
public interface InputLogisticRepo extends CrudRepository<InputLogistic, Long> {

    /**
     * Retrieves all logistic that happened before the passed fromDate
     * @param fromDate date after which the consumer intents to retrieve the logistic data
     * @return {@link List} of {@link InputLogistic}
     */
    @Query(value = "SELECT i FROM InputLogistic WHERE i.date > :from_date")
    List<InputLogistic> listAllLogisticsByDate(@Param("from_date") Date fromDate);
    
}
