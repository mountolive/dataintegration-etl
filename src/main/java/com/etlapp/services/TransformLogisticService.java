package com.etlapp.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.etlapp.repositories.LogisticRepo;
import com.etlapp.repositories.RawLogisticRepo;

public class TransformLogisticService implements ITransformLogisticService {
    @Autowired
    private RawLogisticRepo rawLogisticRepo;
    
    @Autowired
    private LogisticRepo logisticRepo;
    
    @PersistenceContext
    @Autowired
    private EntityManager em;
    
    public void setEm(EntityManager em) {
        this.em = em;
    }

}
