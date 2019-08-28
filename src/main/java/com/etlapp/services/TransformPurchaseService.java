package com.etlapp.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.etlapp.core.Mapper;
import com.etlapp.core.Transform;
import com.etlapp.entities.Purchase;
import com.etlapp.entities.RawPurchase;
import com.etlapp.repositories.PurchaseRepo;
import com.etlapp.repositories.RawPurchaseRepo;

public class TransformPurchaseService implements ITransformPurchaseService {
    @Autowired
    private RawPurchaseRepo rawPurchaseRepo;
    
    @Autowired
    private PurchaseRepo purchaseRepo;
    
    @Autowired
    private Mapper<RawPurchase, Purchase> mapper;
    
    @PersistenceContext
    @Autowired
    private EntityManager em;
    
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Purchase> transform(Date fromDate) {
        List<RawPurchase> rawPurchases = rawPurchaseRepo.listAllPurchasesByDate(fromDate);
        List<Purchase> newPurchases = new ArrayList<>();
        Transform<RawPurchase, Purchase> transformer = new Transform<RawPurchase, Purchase>() {

            @Override
            public Purchase transform(RawPurchase source) throws Exception {
                return new Purchase(source.getItemName().toUpperCase(),
                                    Math.pow(source.getPrice(), 2),
                                    source.getTotalAmount(),
                                    source.getDate());
            }
            
        };
        for(RawPurchase raw : rawPurchases) {
            newPurchases.add(mapper.mapFromEntity(raw, transformer));
        }
        return purchaseRepo.saveAll(newPurchases);
    }
}
