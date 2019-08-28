package com.etlapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.etlapp.entities.RawLogistic;

@Repository
public interface RawLogisticRepo extends JpaRepository<RawLogistic, Long> {

}
