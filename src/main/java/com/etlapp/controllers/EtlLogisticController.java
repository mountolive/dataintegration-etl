package com.etlapp.controllers;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.etlapp.entities.Logistic;
import com.etlapp.entities.RawLogistic;
import com.etlapp.services.IExtractLogisticService;
import com.etlapp.services.ITransformLogisticService;
import com.etlapp.utilities.ControllerHelper;

/**
 * Basic controller to handle ETL actions from specified datetime for logistic's data
 * @author Leo Guercio
 *
 */
@RestController
@RequestMapping("/logistics")
public class EtlLogisticController {
    private static final Logger LOG = LogManager.getLogger();
    
    @Autowired
    private IExtractLogisticService extractService;
    
    @Autowired
    private ITransformLogisticService transformService;
    
    @Autowired
    private ControllerHelper<RawLogistic, Logistic> helper;
    
    @PostMapping(value = "/etl-jdbc", params = {"date"})
    @ResponseStatus(HttpStatus.CREATED)
    public void executeEtlLogisticJdbc(@RequestParam("date")
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                               final LocalDateTime fromDate) {
        helper.checkDateIsCorrect(fromDate);
        helper.extractFromJdbc(extractService, fromDate, LOG);
        helper.transform(transformService, fromDate, LOG);
    }
    
    @PostMapping(value = "/etl-xlsx", params = {"date"})
    @ResponseStatus(HttpStatus.CREATED)
    public void executeEtlLogisticXlsx(@RequestParam("date")
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                        final LocalDateTime fromDate) {
        helper.checkDateIsCorrect(fromDate);
        helper.extractFromWorksheet(extractService, fromDate, true, LOG);
        helper.transform(transformService, fromDate, LOG);
    }
    
    @PostMapping(value = "/etl-csv", params = {"date"})
    @ResponseStatus(HttpStatus.CREATED)
    public void executeEtlLogisticCsv(@RequestParam("date")
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                        final LocalDateTime fromDate) {
        helper.checkDateIsCorrect(fromDate);
        helper.extractFromWorksheet(extractService, fromDate, false, LOG);
        helper.transform(transformService, fromDate, LOG);
    }

    @PostMapping(value = "/extract-from-bd", params = {"date"})
    @ResponseStatus(HttpStatus.CREATED)
    public void executeExtractionJdbc(@RequestParam("date")
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                      final LocalDateTime fromDate) {
        helper.checkDateIsCorrect(fromDate);
        helper.extractFromJdbc(extractService, fromDate, LOG);
    }
    
    @PostMapping(value = "/extract-from-csv", params = {"date"})
    @ResponseStatus(HttpStatus.CREATED)
    public void executeExtractionCsv(@RequestParam("date")
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                     final LocalDateTime fromDate) {
        helper.checkDateIsCorrect(fromDate);
        helper.extractFromWorksheet(extractService, fromDate, false, LOG);
    }
    
    @PostMapping(value = "/extract-from-xlsx", params = {"date"})
    @ResponseStatus(HttpStatus.CREATED)
    public void executeExtractionXlsx(@RequestParam("date")
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                      final LocalDateTime fromDate) {
        helper.checkDateIsCorrect(fromDate);
        helper.extractFromWorksheet(extractService, fromDate, true, LOG);
    }
    
    @PostMapping(value = "/transform-load", params = {"date"})
    @ResponseStatus(HttpStatus.CREATED)
    public void executeTransformAndLoad(@RequestParam("date")
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                        final LocalDateTime fromDate) {
        helper.checkDateIsCorrect(fromDate);
        helper.transform(transformService, fromDate, LOG);
    }
}
