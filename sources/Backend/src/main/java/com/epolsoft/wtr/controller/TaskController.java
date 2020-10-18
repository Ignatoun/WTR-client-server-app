package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.model.Tasks;
import com.epolsoft.wtr.model.dto.TasksDTO;
import com.epolsoft.wtr.service.TaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
@Api(value = "Tasks", description = "Everything about Tasks")
public class TaskController {

    private static final Logger LOGGER = LogManager.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation(value="View all list of Tasks")
    @GetMapping("/user/tasks")
    public ResponseEntity<List<TasksDTO>> getAllTasks(){
        LOGGER.info("Controller method called to view all list of Tasks");
        List<TasksDTO> list = taskService.getAllTask();
        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value="View Task by Id")
    @GetMapping("/user/tasks/{id}")
    public ResponseEntity<TasksDTO> getTaskById(@PathVariable("id") Integer id){
        LOGGER.info("Controller method called to view Task by id="+id);
        TasksDTO task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, new HttpHeaders(), HttpStatus.OK);
    }

    @ApiOperation(value="Add new Task")
    @PostMapping("/manager/tasks")
    public ResponseEntity<Tasks> createTask(@RequestBody TasksDTO tasksDTO){
        LOGGER.info("Controller method called to create Task;" +
                " task="+tasksDTO.toString());
        Tasks updated = taskService.createTask(tasksDTO);
        return new ResponseEntity<>(updated,new HttpHeaders(),HttpStatus.OK);
    }

    @ApiOperation(value = "Update Task by Id")
    @PutMapping("/manager/tasks/{id}")
    public ResponseEntity<Tasks> updateTasks (@PathVariable("id") Integer id,@RequestBody TasksDTO tasksDTO){
        tasksDTO.setTaskId(id);
        LOGGER.info("Controller method called to update Task;" +
                " id="+ id+", task="+ tasksDTO.toString());
        Tasks update = taskService.updateTask(tasksDTO);
        return new  ResponseEntity<>(update,new HttpHeaders(),HttpStatus.OK);
    }

    @ApiOperation(value="Delete Task by Id")
    @DeleteMapping("/manager/tasks/{id}")
    public HttpStatus deleteTaskById(@PathVariable("id") Integer id){
        LOGGER.info("Controller method called to delete Task by id="+id);
        taskService.deleteTask(id);
        return HttpStatus.OK;
    }
}
