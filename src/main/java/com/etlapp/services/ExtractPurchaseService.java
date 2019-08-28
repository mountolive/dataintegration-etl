package com.etlapp.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.etlapp.extraction.input.repository.InputPurchaseRepo;
import com.etlapp.repository.RawPurchaseRepo;

public class ExtractPurchaseService implements IExtractPurchaseService {
    
    @Autowired
    private RawPurchaseRepo rawPurchaseRepo;
    
    @Autowired
    private InputPurchaseRepo inputPurchaseRepo;
    
    @PersistenceContext
    @Autowired
    private EntityManager em;
    
    public void setEm(EntityManager em) {
        this.em = em;
    }

}
