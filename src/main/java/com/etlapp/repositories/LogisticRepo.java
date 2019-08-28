package com.etlapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etlapp.entities.Logistic;

public interface LogisticRepo extends JpaRepository<Logistic, Long> {

}
