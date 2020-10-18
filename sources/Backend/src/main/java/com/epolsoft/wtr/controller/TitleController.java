package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.model.Title;
import com.epolsoft.wtr.service.TitleService;
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
@Api(value = "Titles", description = "Everything about Titles")
public class TitleController {

    private static final Logger LOGGER = LogManager.getLogger(TitleController.class);

    @Autowired
    private TitleService titleService;

    @ApiOperation(value="View all list of Titles")
    @GetMapping("/user/titles")
    public ResponseEntity<List<Title>> getAllTitles() {
        LOGGER.info("Controller method called to view all list of Titles");
        return ResponseEntity.ok().body(titleService.getAllTitles());
    }

    @ApiOperation(value="View Title by Id")
    @GetMapping("/user/titles/{id}")
    public ResponseEntity <Title> getTitleById(@PathVariable Integer id) {
        LOGGER.info("Controller method called to view Title by id="+id);
        return ResponseEntity.ok().body(titleService.getTitleById(id));
    }

    @ApiOperation(value="Add new Title")
    @PostMapping("/manager/titles")
    public ResponseEntity <Title> createTitle(@RequestBody Title title) {
        LOGGER.info("Controller method called to create Title;" +
                " title="+title.toString());
        return ResponseEntity.ok().body(this.titleService.createTitle(title));
    }

    @ApiOperation(value = "Update Title by Id")
    @PutMapping("/manager/titles/{id}")
    public ResponseEntity <Title> updateTitle(@PathVariable Integer id, @RequestBody Title title) {
        LOGGER.info("Controller method called to update Title;" +
                " id="+id+", title="+title.toString());
        title.setTitleId(id);
        return ResponseEntity.ok().body(this.titleService.updateTitle(title));
    }

    @ApiOperation(value="Delete Title by Id")
    @DeleteMapping("/manager/titles/{id}")
    public HttpStatus deleteTitle(@PathVariable Integer id) {
        LOGGER.info("Controller method called to deleteTitle by id="+id);
        this.titleService.deleteTitle(id);
        return HttpStatus.OK;
    }
}
