package com.etlapp.extraction.input.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.etlapp.extraction.input.entities.InputSale;

@Repository
public interface InputSaleRepo extends CrudRepository<InputSale, Long> {

}
