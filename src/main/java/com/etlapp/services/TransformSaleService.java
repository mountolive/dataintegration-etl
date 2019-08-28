package com.etlapp.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.etlapp.core.Mapper;
import com.etlapp.core.Transform;
import com.etlapp.entities.RawSale;
import com.etlapp.entities.Sale;
import com.etlapp.repositories.RawSaleRepo;
import com.etlapp.repositories.SaleRepo;

public class TransformSaleService implements ITransformSaleService {
    @Autowired
    private RawSaleRepo rawSaleRepo;
    
    @Autowired
    private SaleRepo saleRepo;
    
    @Autowired
    private Mapper<RawSale, Sale> mapper;
    
    @PersistenceContext
    @Autowired
    private EntityManager em;
    
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Sale> transform(Date fromDate) {
        List<RawSale> rawSales = rawSaleRepo.listAllSalesByDate(fromDate);
        List<Sale> newSales = new ArrayList<>();
        Transform<RawSale, Sale> transformer = new Transform<RawSale, Sale>() {

            @Override
            public Sale transform(RawSale source) throws Exception {
                return new Sale(source.getItemName().toUpperCase(),
                                    Math.pow(source.getPrice(), 2),
                                    source.getTotalAmount(),
                                    source.getDate());
            }
            
        };
        for(RawSale raw : rawSales) {
            newSales.add(mapper.mapFromEntity(raw, transformer));
        }
        return saleRepo.saveAll(newSales);
    }
}
