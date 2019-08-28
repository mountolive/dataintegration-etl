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
import com.etlapp.entities.RawLogistic;
import com.etlapp.extraction.input.entities.InputLogistic;
import com.etlapp.extraction.input.repositories.InputLogisticRepo;
import com.etlapp.repositories.RawLogisticRepo;
import com.etlapp.utilities.RawDataExtractor;

public class ExtractLogisticService implements IExtractLogisticService {
    
    @Autowired
    private RawLogisticRepo rawLogisticRepo;
    
    @Autowired
    private InputLogisticRepo inputLogisticRepo;
    
    @PersistenceContext
    @Autowired
    private EntityManager em;
    
    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public List<RawLogistic> extractRawLogisticFromJdbc(Date fromDate) {
        List<InputLogistic> inputLogistics = inputLogisticRepo.listAllLogisticsByDate(fromDate);
        RawDataExtractor<InputLogistic, RawLogistic> extractor = new RawDataExtractor<>();
        List<RawLogistic> rawLogistics = extractor.fromDataSource(inputLogistics);
        return rawLogisticRepo.saveAll(rawLogistics);
    }

    @Override
    public List<RawLogistic> extractRawLogisticFromWorksheet(Date fromDate, boolean isXlsx) {
        WorksheetRetriever retriever;
        if(isXlsx) retriever = new WorksheetRetriever(ExtensionType.XLSX);
        else retriever = new WorksheetRetriever(ExtensionType.CSV);
        File worksheet = retriever.getFileFromDate(fromDate, RetailType.LOGISTIC);
        RawDataExtractor<InputLogistic, RawLogistic> extractor = new RawDataExtractor<>();
        List<RawLogistic> rawLogistics = extractor.fromWorksheet(worksheet);
        return rawLogisticRepo.saveAll(rawLogistics);
    }
    
}
