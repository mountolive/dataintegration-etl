package com.etlapp.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.etlapp.extraction.input.repository.InputSaleRepo;
import com.etlapp.repository.RawSaleRepo;

public class ExtractSaleService implements IExtractSaleService {
    
    @Autowired
    private RawSaleRepo rawSaleRepo;
    
    @Autowired
    private InputSaleRepo inputSaleRepo;
    
    @PersistenceContext
    @Autowired
    private EntityManager em;
    
    public void setEm(EntityManager em) {
        this.em = em;
    }

}
