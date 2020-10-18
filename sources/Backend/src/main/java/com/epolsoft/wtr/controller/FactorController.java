package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.model.Factor;
import com.epolsoft.wtr.service.FactorService;
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

@RestController
@Api(value = "Factors", description = "Everything about Factors")
public class FactorController {

    private static final Logger LOGGER = LogManager.getLogger(FactorController.class);

    @Autowired
    private FactorService factorService;

    @ApiOperation(value="View all list of Factors")
    @GetMapping("/user/factors")
    public ResponseEntity <List<Factor>> getAllFactor() {
        LOGGER.info("Controller method called to view all list of Factors");
        return ResponseEntity.ok().body(factorService.getAllFactor());
    }

    @ApiOperation(value="View Factor by Id")
    @GetMapping("/user/factors/{id}")
    public ResponseEntity < Factor > getFactorById(@PathVariable Integer id) {
        LOGGER.info("Controller method called to view Factor by id="+id);
        return ResponseEntity.ok().body(factorService.getFactorById(id));
    }

    @ApiOperation(value="Add new Factor")
    @PostMapping("/manager/factors")
    public ResponseEntity < Factor > createFactor(@RequestBody Factor factor) {
        LOGGER.info("Controller method called to create Factor;" +
                " factor="+factor.toString());
        return ResponseEntity.ok().body(this.factorService.createFactor(factor));
    }

    @ApiOperation(value = "Update Factor by Id")
    @PutMapping("/manager/factors/{id}")
    public ResponseEntity < Factor > updateFactor(@PathVariable Integer id, @RequestBody Factor factor) {
        factor.setFactorId(id);
        LOGGER.info("Controller method called to update Factors;" +
                " id="+id+"factor="+factor.toString());
        return ResponseEntity.ok().body(this.factorService.updateFactor(factor));
    }

    @ApiOperation(value="Delete Factor by Id")
    @DeleteMapping("/manager/factors/{id}")
    public HttpStatus deleteFactor(@PathVariable Integer id) {
        LOGGER.info("Controller method called to delete Factor by id=" + id);
        this.factorService.deleteFactor(id);
        return HttpStatus.OK;
    }
}
