package com.etlapp.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.etlapp.entities.RawPurchase;

@Repository
public interface RawPurchaseRepo extends JpaRepository<RawPurchase, Long> {

    /**
     * Retrieves all purchases that happened before the passed fromDate
     * @param fromDate date after which the consumer intents to retrieve the purchases
     * @return {@link List} of {@link RawPurchase}
     */
    @Query(value = "SELECT r FROM RawPurchase r WHERE r.date >= :from_date")
    List<RawPurchase> listAllPurchasesByDate(@Param("from_date") LocalDateTime fromDate);

}
