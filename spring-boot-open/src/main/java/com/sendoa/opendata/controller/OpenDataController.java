package com.sendoa.opendata.controller;

import com.sendoa.opendata.service.OpenDataService;
import com.sendoa.opendata.model.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.sendoa.opendata.model.Model;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/v1")
@Validated
public class OpenDataController {

    private static Logger logger = LoggerFactory.getLogger(OpenDataController.class);

    @Autowired
    private OpenDataService service;

    @GetMapping(value = "/data", produces = "application/json")
    public ResponseEntity<ResponseModel> getData(@RequestParam(defaultValue = "1") @Valid @Min(1) Integer pageKey,
                                     @RequestParam(defaultValue = "10") @Valid @Min(1) @Max(30) Integer pageSize,
                                     @RequestParam(defaultValue = "code") String sort,
                                     @RequestParam(defaultValue = "asc") String direction) {
        logger.debug("Get open data with: pageKey {}, pageSize {}, sort {} and direction {}", pageKey, pageSize, sort, direction);
        return new ResponseEntity<>(service.findFiltered(pageKey, pageSize, sort, direction), HttpStatus.PARTIAL_CONTENT);

    }

    @GetMapping(value = "/data/{id}", produces = "application/json")
    public ResponseEntity<Model> getOneData(@PathVariable String code) {
        logger.debug("Get open data with Id {}", code);
        Model dataModel = service.findOne(code);
        return new ResponseEntity<>(dataModel, dataModel == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);

    }

}
