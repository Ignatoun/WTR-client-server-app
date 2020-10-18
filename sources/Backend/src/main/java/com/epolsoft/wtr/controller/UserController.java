package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.model.User;
import com.epolsoft.wtr.model.dto.UserDTO;
import com.epolsoft.wtr.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "Users", description = "Everything about Users")
public class UserController {
    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @ApiOperation(value="View all list of Users")
    @GetMapping("/manager/users")
    public ResponseEntity<List<User>> getAllUsers() {
        LOGGER.info("Controller method called to view all list of Users");
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @ApiOperation(value="View all Users by Filter")
    @GetMapping("/manager/users/filter")
    public ResponseEntity<List<User>> getUsersByFilter(@RequestParam(value="firstName", required = false) String firstName,
                                                       @RequestParam(value="lastName", required = false) String lastName) {
        LOGGER.info("Controller method called to view all Users by Filter");
        return ResponseEntity.ok().body(userService.getUsersByFilter(firstName,lastName));
    }

    @ApiOperation(value="View User by Id")
    @GetMapping("/manager/users/{id}")
    public ResponseEntity <User> getUserById(@PathVariable Integer id) {
        LOGGER.info("Controller method called to view User by id="+id);
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @ApiOperation(value="Add new User")
    @PostMapping("/manager/users")
    public ResponseEntity <User> createUser(@RequestBody UserDTO userDto) {
        LOGGER.info("Controller method called to create User;" +
                " user="+ userDto.toString());

        userService.validateUsername(userDto.getUserName());
        return ResponseEntity.ok().body(this.userService.createUser(userDto));
    }

    @ApiOperation(value = "Update User by Id")
    @PutMapping("/manager/users/{id}")
    public ResponseEntity <User> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDto) {
        userDto.setUserId(id);
        LOGGER.info("Controller method called to update User;" +
                " id="+id+", user="+userDto.toString());
        return ResponseEntity.ok().body(this.userService.updateUser(userDto));
    }

    @ApiOperation(value="Delete User by Id")
    @DeleteMapping("/manager/users/{id}")
    public HttpStatus deleteUser(@PathVariable Integer id) {
        LOGGER.info("Controller method called to delete User by id="+id);
        this.userService.deleteUser(id);
        return HttpStatus.OK;
    }
}
