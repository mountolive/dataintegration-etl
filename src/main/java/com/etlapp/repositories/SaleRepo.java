package com.etlapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.etlapp.entities.Sale;

public interface SaleRepo extends CrudRepository<Sale, Long> {

}
