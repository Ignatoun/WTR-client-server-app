package com.epolsoft.wtr.service;

import com.epolsoft.wtr.dao.LocationDAO;
import com.epolsoft.wtr.dao.ReportDetailsDAO;
import com.epolsoft.wtr.model.Location;
import com.epolsoft.wtr.model.ReportDetails;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceTest {
    @Autowired
    private LocationService locationService;

    @MockBean
    private LocationDAO locationDAO;

    @MockBean
    private ReportDetailsDAO reportDetailsDAO;

    @Test
    public void createLocationTest()
    {
        Location location = new Location(9, "Florida/epol",null);
        locationService.createLocation(location);
        Mockito.verify(locationDAO,Mockito.times(1)).save(location);
    }

    @Test
    public void updateLocationTest()
    {
        Location location = new Location(2, "Minsk/epol",null);
        Mockito.doReturn(Optional.of(location))
                .when(locationDAO)
                .findById(2);
        locationService.updateLocation(location);
        Mockito.verify(locationDAO,Mockito.times(1)).findById(location.getLocationId());
        Mockito.verify(locationDAO,Mockito.times(1)).save(location);
    }

    @Test
    public void getAllLocationTest()
    {
        Location location = new Location(12, "Brest/epol",null);

        List<Location> locationList = new ArrayList();
        locationList.add(location);

        Mockito.doReturn(locationList)
                .when(locationDAO)
                .findAll();

        Assert.assertEquals(locationList, locationService.getAllLocations());
        Mockito.verify(locationDAO,Mockito.times(1)).findAll();
    }

    @Test
    public void getLocationByIdTest()
    {
        Location location = new Location(4, "Location4",null);

        Mockito.doReturn(Optional.of(location))
                .when(locationDAO)
                .findById(4);

        Assert.assertEquals(location,locationService.getLocationById(location.getLocationId()));
    }

    @Test
    public void deleteLocationTest()
    {
        Location location = new Location(8, "Location8",new HashSet<ReportDetails>());

        Mockito.doReturn(Optional.of(location))
                .when(locationDAO)
                .findById(8);

        Mockito.doReturn(Optional.of(location).get().getReportDetails())
                .when(reportDetailsDAO)
                .save(null);

        locationService.deleteLocation(location.getLocationId());

        Mockito.verify(locationDAO,Mockito.times(1)).delete(location);
    }
}