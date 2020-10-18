package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.model.Location;
import com.epolsoft.wtr.service.LocationService;
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
@Api(value = "Locations", description = "Everything about Locations")
public class LocationController {

    private static final Logger LOGGER = LogManager.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    @ApiOperation(value="View all list of Locations")
    @GetMapping("/user/locations")
    public ResponseEntity <List<Location>> getAllLocations() {
        LOGGER.info("Controller method called to view all list of Locations");
        return ResponseEntity.ok().body(locationService.getAllLocations());
    }

    @ApiOperation(value="View Location by Id")
    @GetMapping("/user/locations/{id}")
    public ResponseEntity <Location> getLocationById(@PathVariable Integer id) {
        LOGGER.info("Controller method called to view Location by id="+id);
        return ResponseEntity.ok().body(locationService.getLocationById(id));
    }

    @ApiOperation(value="Add new Location")
    @PostMapping("/manager/locations")
    public ResponseEntity <Location> createLocation(@RequestBody Location location) {
        LOGGER.info("Controller method called to create Location;" +
                " location="+location.toString());
        return ResponseEntity.ok().body(this.locationService.createLocation(location));
    }

    @ApiOperation(value = "Update Location by Id")
    @PutMapping("/manager/locations/{id}")
    public ResponseEntity <Location> updateDepartment(@PathVariable Integer id, @RequestBody Location location) {
        location.setLocationId(id);
        LOGGER.info("Controller method called to update Location;" +
                " id="+id+", location="+location.toString());
        return ResponseEntity.ok().body(this.locationService.updateLocation(location));
    }

    @ApiOperation(value="Delete Location by Id")
    @DeleteMapping("/manager/locations/{id}")
    public HttpStatus deleteLocation(@PathVariable Integer id) {
        this.locationService.deleteLocation(id);
        LOGGER.info("Controller method called to delete Location by id="+id);
        return HttpStatus.OK;
    }
}
