package com.etlapp.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.etlapp.repositories.RawSaleRepo;
import com.etlapp.repositories.SaleRepo;

public class TransformSaleService implements ITransformSaleService {
    @Autowired
    private RawSaleRepo rawSaleRepo;
    
    @Autowired
    private SaleRepo saleRepo;
    
    @PersistenceContext
    @Autowired
    private EntityManager em;
    
    public void setEm(EntityManager em) {
        this.em = em;
    }

}
