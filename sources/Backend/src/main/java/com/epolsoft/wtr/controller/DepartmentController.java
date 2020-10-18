package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.model.Department;
import com.epolsoft.wtr.service.DepartmentService;
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
@Api(value = "Departments", description = "Everything about Departments")
public class DepartmentController {

    private static final Logger LOGGER = LogManager.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value="View all list of Departments")
    @GetMapping("/user/departments")
    public ResponseEntity <List<Department>> getAllDepartments() {
        LOGGER.info("Controller method called to view all list of Departments");
        return ResponseEntity.ok().body(departmentService.getAllDepartment());
    }

    @ApiOperation(value="View Department by Id")
    @GetMapping("/user/departments/{id}")
    public ResponseEntity <Department> getDepartmentById(@PathVariable Integer id) {
        LOGGER.info("Controller method called to view Department by id=" + id);
        return ResponseEntity.ok().body(departmentService.getDepartmentById(id));
    }

    @ApiOperation(value="Add new Department")
    @PostMapping("/manager/departments")
    public ResponseEntity <Department> createDepartment(@RequestBody Department department) {
        LOGGER.info("Controller method called to create Department;" +
                " Department="+ department.toString());
        return ResponseEntity.ok().body(this.departmentService.createDepartment(department));
    }

    @ApiOperation(value = "Update Department by Id")
    @PutMapping("/manager/departments/{id}")
    public ResponseEntity <Department> updateDepartment(@PathVariable Integer id, @RequestBody Department department) {
        department.setDepartmentId(id);
        LOGGER.info("Controller method called to update Department;" +
                " Id=" + id + ", Department=" + department.toString());
        return ResponseEntity.ok().body(this.departmentService.updateDepartment(department));
    }

    @ApiOperation(value="Delete Department by Id")
    @DeleteMapping("/manager/departments/{id}")
    public HttpStatus deleteDepartment(@PathVariable Integer id) {
        LOGGER.info("Controller method called to delete Department by id=" + id);
        this.departmentService.deleteDepartment(id);
        return HttpStatus.OK;
    }
}
