package com.etlapp.services;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.etlapp.constants.ExtensionType;
import com.etlapp.constants.RetailType;
import com.etlapp.core.WorksheetRetriever;
import com.etlapp.entities.RawSale;
import com.etlapp.extraction.input.entities.InputSale;
import com.etlapp.repositories.RawSaleRepo;
import com.etlapp.utilities.RawDataExtractor;

public class ExtractSaleService implements IExtractSaleService {
    
    @Autowired
    private RawSaleRepo rawSaleRepo;
    
    @Autowired
    private com.etlapp.extraction.input.repositories.InputSaleRepo inputSaleRepo;
    
    @PersistenceContext
    @Autowired
    private EntityManager em;
    
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<RawSale> extractRawSaleFromJdbc(Date fromDate) {
        List<InputSale> inputSales = inputSaleRepo.listAllSalesBySaleDate(fromDate);
        RawDataExtractor<InputSale, RawSale> extractor = new RawDataExtractor<>();
        List<RawSale> rawSales = extractor.fromDataSource(inputSales);
        return rawSaleRepo.saveAll(rawSales);
    }

    @Override
    public List<RawSale> extractRawSaleFromWorksheet(Date fromDate, boolean isXlsx) {
        WorksheetRetriever retriever;
        if(isXlsx) retriever = new WorksheetRetriever(ExtensionType.XLSX);
        else retriever = new WorksheetRetriever(ExtensionType.CSV);
        File worksheet = retriever.getFileFromDate(fromDate, RetailType.SALE);
        RawDataExtractor<InputSale, RawSale> extractor = new RawDataExtractor<>();
        List<RawSale> rawSales = extractor.fromWorksheet(worksheet);
        return rawSaleRepo.saveAll(rawSales);
    }
}
