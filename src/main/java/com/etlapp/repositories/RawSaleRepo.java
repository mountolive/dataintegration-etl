package com.etlapp.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.etlapp.entities.RawSale;

@Repository
public interface RawSaleRepo extends JpaRepository<RawSale, Long> {

    /**
     * Retrieves all sales that happened before the passed fromDate
     * @param fromDate date after which the consumer intents to retrieve the sales
     * @return {@link List} of {@link RawSale}
     */
    @Query(value = "SELECT r FROM RawSale WHERE r.date >= :from_date")
    List<RawSale> listAllSalesByDate(@Param("from_date") LocalDateTime fromDate);
    
}
