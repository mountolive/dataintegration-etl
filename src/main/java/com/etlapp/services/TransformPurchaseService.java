package com.etlapp.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.etlapp.repositories.PurchaseRepo;
import com.etlapp.repositories.RawPurchaseRepo;

public class TransformPurchaseService implements ITransformPurchaseService {
    @Autowired
    private RawPurchaseRepo rawPurchaseRepo;
    
    @Autowired
    private PurchaseRepo purchaseRepo;
    
    @PersistenceContext
    @Autowired
    private EntityManager em;
    
    public void setEm(EntityManager em) {
        this.em = em;
    }

}
