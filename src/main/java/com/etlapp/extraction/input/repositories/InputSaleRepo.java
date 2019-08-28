package com.etlapp.extraction.input.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.etlapp.extraction.input.entities.InputSale;

@Repository
public interface InputSaleRepo extends CrudRepository<InputSale, Long> {
    
    /**
     * Retrieves all sales that happened before the passed fromDate
     * @param fromDate date after which the consumer intents to retrieve the sales
     * @return {@link List} of {@link InputSale}
     */
    @Query(value = "SELECT i FROM InputSale WHERE i.date >= :from_date")
    List<InputSale> listAllSalesBySaleDate(@Param("from_date") Date fromDate);

}
