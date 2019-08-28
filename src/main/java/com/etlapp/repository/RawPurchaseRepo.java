package com.etlapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.etlapp.entities.RawPurchase;

@Repository
public interface RawPurchaseRepo extends CrudRepository<RawPurchase, Long> {

}
