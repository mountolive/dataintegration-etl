package com.etlapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.etlapp.entities.Purchase;

public interface PurchaseRepo extends CrudRepository<Purchase, Long> {

}
