package com.etlapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.etlapp.entities.RawSale;

@Repository
public interface RawSaleRepo extends JpaRepository<RawSale, Long> {

}
