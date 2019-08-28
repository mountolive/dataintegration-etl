package com.etlapp.extraction.input.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.etlapp.extraction.input.entities.InputLogistic;

@Repository
public interface InputLogisticRepo extends CrudRepository<InputLogistic, Long> {

}
