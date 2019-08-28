package com.etlapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.etlapp.entities.RawLogistic;

@Repository
public interface RawLogisticRepo extends CrudRepository<RawLogistic, Long> {

}
