package com.etlapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etlapp.entities.Purchase;

public interface PurchaseRepo extends JpaRepository<Purchase, Long> {

}
