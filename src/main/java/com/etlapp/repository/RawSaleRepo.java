package com.etlapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.etlapp.entities.RawSale;

@Repository
public interface RawSaleRepo extends CrudRepository<RawSale, Long> {

}
