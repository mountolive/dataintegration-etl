package com.etlapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.etlapp.entities.RawPurchase;

@Repository
public interface RawPurchaseRepo extends JpaRepository<RawPurchase, Long> {

}
