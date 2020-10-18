package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.model.Project;
import com.epolsoft.wtr.service.ProjectService;
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

@RestController
@Api(value = "Projects", description = "Everything about Projects")
public class ProjectController {

    private static final Logger LOGGER = LogManager.getLogger(ProjectController.class);

    @Autowired
    ProjectService projectService;

    @ApiOperation(value="View Project by Id")
    @GetMapping("/user/projects/{id}")
    public ResponseEntity<Object> getByIdProject(@PathVariable Integer id) {
        LOGGER.info("Controller method called to view Project by id="+id);
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @ApiOperation(value="View all list of Projects")
    @GetMapping("/user/projects")
    public ResponseEntity<Object> getAllProjects() {
        LOGGER.info("Controller method called to view all list of Projects");
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @ApiOperation(value = "Update Project by Id")
    @PutMapping("/manager/projects/{id}")
    public ResponseEntity<Object> updateProject(@PathVariable Integer id,@RequestBody Project project) {
        project.setProjectId(id);
        LOGGER.info("Controller method called to update Project;" +
                " id="+ id+ ", project="+ project.toString());
        projectService.updateProject(project);
        return new ResponseEntity<>("Project is updated successfully", HttpStatus.OK);
    }

    @ApiOperation(value="Delete Project by Id")
    @DeleteMapping("/manager/projects/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        LOGGER.info("Controller method called to delete Project by id="+id);
        projectService.deleteProject(id);
        return new ResponseEntity<>("Project is deleted successfully", HttpStatus.OK);
    }

    @ApiOperation(value="Add new Project")
    @PostMapping("/manager/projects")
    public ResponseEntity<Object> createProject(@RequestBody Project project) {
        LOGGER.info("Controller method called to create Project;" +
                " project="+project.toString());
        projectService.createProject(project);
        return new ResponseEntity<>("Project is created successfully", HttpStatus.CREATED);
    }
}