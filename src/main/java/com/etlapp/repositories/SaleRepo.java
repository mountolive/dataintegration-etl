package com.etlapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etlapp.entities.Sale;

public interface SaleRepo extends JpaRepository<Sale, Long> {

}
