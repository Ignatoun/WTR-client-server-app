package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.model.Scheduler;
import com.epolsoft.wtr.service.SchedulerService;
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
public class SchedulerController {

    private static final Logger LOGGER = LogManager.getLogger(SchedulerController.class);

    @Autowired
    private SchedulerService schedulerService;

    @GetMapping("/admin/scheduler")
    public ResponseEntity<List<Scheduler>> getAllParameters() {
        LOGGER.info("Controller method called to view all list of Scheduler parameters");
        return ResponseEntity.ok().body(schedulerService.getAllParams());
    }

    @GetMapping("/admin/scheduler/{id}")
    public ResponseEntity <Scheduler> getParameterById(@PathVariable(value = "id") Integer paramId) {
        LOGGER.info("Controller method called to view Parameter by id=" + paramId);
        return ResponseEntity.ok().body(schedulerService.getParamById(paramId));
    }

    @PostMapping("/admin/scheduler")
    public ResponseEntity <Scheduler> createParameter(@RequestBody Scheduler scheduler) {
        LOGGER.info("Controller method called to create Scheduler parameter;" +
                " Scheduler="+ scheduler.toString());
        return ResponseEntity.ok().body(this.schedulerService.createParam(scheduler));
    }

    @PutMapping("/admin/scheduler/{id}")
    public ResponseEntity <Scheduler> updateParameter(@PathVariable Integer id, @RequestBody Scheduler scheduler) {
        scheduler.setParamId(id);
        LOGGER.info("Controller method called to update Scheduler parameter;" +
                " Id=" + id + ", Scheduler=" + scheduler.toString());
        return ResponseEntity.ok().body(this.schedulerService.updateParam(scheduler));
    }

    @PutMapping("/admin/scheduler/list")
    public ResponseEntity <List<Scheduler>> updateParameters( @RequestBody List<Scheduler> schedulers) {
        LOGGER.info("Controller method called to update Scheduler parameters");
        return ResponseEntity.ok().body(this.schedulerService.updateParams(schedulers));
    }

    @DeleteMapping("/admin/scheduler/{id}")
    public HttpStatus deleteParameter(@PathVariable Integer id) {
        LOGGER.info("Controller method called to delete Scheduler parameter by id=" + id);
        this.schedulerService.deleteParam(id);
        return HttpStatus.OK;
    }
}
