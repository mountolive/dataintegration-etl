package com.etlapp.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.etlapp.core.Mapper;
import com.etlapp.core.Transform;
import com.etlapp.entities.Logistic;
import com.etlapp.entities.RawLogistic;
import com.etlapp.repositories.LogisticRepo;
import com.etlapp.repositories.RawLogisticRepo;

public class TransformLogisticService implements ITransformLogisticService {
    @Autowired
    private RawLogisticRepo rawLogisticRepo;
    
    @Autowired
    private LogisticRepo logisticRepo;
    
    @Autowired
    private Mapper<RawLogistic, Logistic> mapper;
    
    @PersistenceContext
    @Autowired
    private EntityManager em;
    
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Logistic> transform(Date fromDate) {
        List<RawLogistic> rawLogistics = rawLogisticRepo.listAllLogisticsByDate(fromDate);
        List<Logistic> newLogistics = new ArrayList<>();
        Transform<RawLogistic, Logistic> transformer = new Transform<RawLogistic, Logistic>() {

            @Override
            public Logistic transform(RawLogistic source) throws Exception {
                return new Logistic(source.getName().toUpperCase(),
                                    source.getDescription(),
                                    Math.pow(source.getAmount(), 2),
                                    source.getDate());
            }
            
        };
        for(RawLogistic raw : rawLogistics) {
            newLogistics.add(mapper.mapFromEntity(raw, transformer));
        }
        return logisticRepo.saveAll(newLogistics);
    }

}
