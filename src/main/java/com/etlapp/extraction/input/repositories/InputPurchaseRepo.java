package com.etlapp.extraction.input.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.etlapp.extraction.input.entities.InputPurchase;

@Repository
public interface InputPurchaseRepo extends CrudRepository<InputPurchase, Long> {

    /**
     * Retrieves all purchases that happened before the passed fromDate
     * @param fromDate date after which the consumer intents to retrieve the purchases
     * @return {@link List} of {@link InputPurchase}
     */
    @Query(value = "SELECT i FROM InputPurchase WHERE i.date >= :from_date")
    List<InputPurchase> listAllPurchasesByDate(@Param("from_date") Date fromDate);
    
}
