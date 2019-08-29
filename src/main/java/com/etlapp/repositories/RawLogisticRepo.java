package com.etlapp.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.etlapp.entities.RawLogistic;

@Repository
public interface RawLogisticRepo extends JpaRepository<RawLogistic, Long> {
    
    /**
     * Retrieves all sales that happened before the passed fromDate
     * @param fromDate date after which the consumer intents to retrieve the logistics' data
     * @return {@link List} of {@link RawLogistic}
     */
    @Query(value = "SELECT r FROM RawLogistic WHERE r.date >= :from_date")
    List<RawLogistic> listAllLogisticsByDate(@Param("from_date") LocalDateTime fromDate);

}
