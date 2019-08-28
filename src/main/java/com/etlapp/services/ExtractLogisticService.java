package com.etlapp.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.etlapp.extraction.input.repository.InputLogisticRepo;
import com.etlapp.repository.RawLogisticRepo;

public class ExtractLogisticService implements IExtractLogisticService {
    
    @Autowired
    private RawLogisticRepo rawLogisticRepo;
    
    @Autowired
    private InputLogisticRepo inputLogisticRepo;
    
    @PersistenceContext
    @Autowired
    private EntityManager em;
    
    public void setEm(EntityManager em) {
        this.em = em;
    }
    
}
