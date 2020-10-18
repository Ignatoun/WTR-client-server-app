package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.model.Feature;
import com.epolsoft.wtr.model.dto.FeatureDTO;
import com.epolsoft.wtr.service.FeatureService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.util.List;

@Api(value = "Features", description = "Everything about Features")
@RestController
public class FeatureController {

    private static final Logger LOGGER = LogManager.getLogger(FeatureController.class);

    @Autowired
    private FeatureService featureService;

    @ApiOperation(value="View all list of Features")
    @GetMapping("/user/features")
    public ResponseEntity<List<FeatureDTO>> getAllFeatures() {
        LOGGER.info("Controller method called to view all list of Features");
        return ResponseEntity.ok().body(featureService.getAllFeatures());
    }

    @ApiOperation(value="View Feature by Id")
    @GetMapping("/user/features/{id}")
    public ResponseEntity < FeatureDTO > getFeatureById(@PathVariable int id) {
        LOGGER.info("Controller method called to view Feature by id=" + id);
        return ResponseEntity.ok().body(featureService.getFeatureById(id));
    }

    @ApiOperation(value="Add new Feature")
    @PostMapping("/manager/features")
    public ResponseEntity < Feature > createFeature(@RequestBody FeatureDTO featureDTO) {
        LOGGER.info("Controller method called to create Feature;" +
                " feature="+featureDTO.toString());
        return ResponseEntity.ok().body(this.featureService.createFeature(featureDTO));
    }

    @ApiOperation(value = "Update Feature by Id")
    @PutMapping("/manager/features/{id}")
    public ResponseEntity < Feature > updateFeature(@PathVariable int id, @RequestBody FeatureDTO featureDTO) {
        featureDTO.setFeatureId(id);
        LOGGER.info("Controller method called to update Feature;" +
                " id="+ id + ", feature="+featureDTO.toString());
        return ResponseEntity.ok().body(this.featureService.updateFeature(featureDTO));
    }

    @ApiOperation(value="Delete Feature by Id")
    @DeleteMapping("/manager/features/{id}")
    public HttpStatus deleteFeature(@PathVariable int id) {
        this.featureService.deleteFeature(id);
        LOGGER.info("Controller method called delete Feature by id="+id);
        return HttpStatus.OK;
    }
}
