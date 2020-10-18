package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.model.DetailedTask;
import com.epolsoft.wtr.model.dto.DetailedTaskDTO;
import com.epolsoft.wtr.service.DetailedTaskService;

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
@Api(value = "DetailedTasks", description = "Everything about Detailed Tasks")
public class DetailedTaskController {

    @Autowired
    private DetailedTaskService detailedTaskService;

    private static final Logger LOGGER = LogManager.getLogger(DetailedTaskController.class);

    @ApiOperation(value="View all list of Detailed Tasks")
    @GetMapping("/user/detailedTasks")
    public ResponseEntity<List<DetailedTaskDTO>> getAllDetailedTasks() {
        LOGGER.info("Controller method called to view all list of DetailedTasks");
        return ResponseEntity.ok().body(detailedTaskService.getAllDetailedTasks());
    }

    @ApiOperation(value="View Detailed Task by Id")
    @GetMapping("/user/detailedTasks/{id}")
    public ResponseEntity <DetailedTaskDTO> getDetailedTaskById(@PathVariable Integer id) {
        LOGGER.info("Controller method called to view DetailedTask by id" + id);
        return ResponseEntity.ok().body(detailedTaskService.getDetailedTaskById(id));
    }

    @ApiOperation(value="Add new Detailed Task")
    @PostMapping("/manager/detailedTasks")
    public ResponseEntity <DetailedTask> createDetailedTask(@RequestBody DetailedTaskDTO detailedTaskDto) {
        LOGGER.info("Controller method called to create DetailedTask;" +
                " DetailedTask=" + detailedTaskDto.toString());
        return ResponseEntity.ok().body(this.detailedTaskService.createDetailedTask(detailedTaskDto));
    }

    @ApiOperation(value = "Update Detailed Task by Id")
    @PutMapping("/manager/detailedTasks/{id}")
    public ResponseEntity <DetailedTask> updateDetailedTask(@PathVariable Integer id, @RequestBody DetailedTaskDTO detailedTaskDto) {
        detailedTaskDto.setDetailedTaskId(id);
        LOGGER.info("Controller method called to update DetailedTask;" +
                " id="+id+", detailedTask="+detailedTaskDto.toString());
        return ResponseEntity.ok().body(this.detailedTaskService.updateDetailedTask(detailedTaskDto));
    }

    @ApiOperation(value="Delete DetailedTask by Id")
    @DeleteMapping("/manager/detailedTasks/{id}")
    public HttpStatus deleteDetailedTask(@PathVariable Integer id) {
        LOGGER.info("Controller method called to delete DetailedTask by id="+id);
        this.detailedTaskService.deleteDetailedTask(id);
        return HttpStatus.OK;
    }
}
