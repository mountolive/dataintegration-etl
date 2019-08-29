package com.etlapp.services;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.etlapp.constants.ExtensionType;
import com.etlapp.constants.RetailType;
import com.etlapp.core.WorksheetRetriever;
import com.etlapp.entities.RawPurchase;
import com.etlapp.extraction.input.entities.InputPurchase;
import com.etlapp.extraction.input.repositories.InputPurchaseRepo;
import com.etlapp.repositories.RawPurchaseRepo;
import com.etlapp.utilities.RawDataExtractor;

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
    
    @Override
    public List<RawPurchase> extractRawFromJdbc(LocalDateTime fromDate) {
        List<InputPurchase> inputPurchases = inputPurchaseRepo.listAllPurchasesByDate(fromDate);
        RawDataExtractor<InputPurchase, RawPurchase> extractor = new RawDataExtractor<>();
        List<RawPurchase> rawPurchases = extractor.fromDataSource(inputPurchases);
        return rawPurchaseRepo.saveAll(rawPurchases);
    }

    @Override
    public List<RawPurchase> extractRawFromWorksheet(LocalDateTime fromDate, boolean isXlsx) {
        WorksheetRetriever retriever;
        if(isXlsx) retriever = new WorksheetRetriever(ExtensionType.XLSX);
        else retriever = new WorksheetRetriever(ExtensionType.CSV);
        File worksheet = retriever.getFileFromDate(Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant()), RetailType.PURCHASE);
        RawDataExtractor<InputPurchase, RawPurchase> extractor = new RawDataExtractor<>();
        List<RawPurchase> rawPurchases = extractor.fromWorksheet(worksheet);
        return rawPurchaseRepo.saveAll(rawPurchases);
    }

}
