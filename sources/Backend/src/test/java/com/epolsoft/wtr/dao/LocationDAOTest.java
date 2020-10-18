package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.Department;
import com.epolsoft.wtr.model.Location;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value={"/create-location-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-location-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LocationDAOTest {
    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createLocationTest()
    {
        Location location=new Location(9,"Location1",null);
        Location location1=new Location();
        Location location2=new Location();

        location1=locationDAO.save(location);

        Assert.assertTrue(location.equals(location1));

        location2 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Location where locationId = ? ",
                new Object[] { location.getLocationId() },
                new BeanPropertyRowMapper<Location>(Location.class)
        );

        Assert.assertTrue(location.equals(location2));
    }

    @Test
    public void findAllLocationTest()
    {
        Iterable <Location> locations = locationDAO.findAll();
        List <Location> locationList = new ArrayList();
        locations.forEach(locationList :: add);

        Assert.assertEquals(8, locationList.size());

        List<Location> locationList1=new ArrayList();
        locationList1= jdbcTemplate.query(
                "SELECT * FROM wtrtest.Location",
                new BeanPropertyRowMapper<Location>(Location.class)
        );

        for (int i=0;i<locationList.size();i++)
        {
            Assert.assertTrue(locationList.get(i).getLocationId().equals(locationList1.get(i).getLocationId()));
            Assert.assertTrue(locationList.get(i).getLocationName().equals(locationList1.get(i).getLocationName()));
        }
    }

    @Test
    public void findLocationByIdTest()
    {
        Location location=new Location(7,"Location7",null);
        Location location1=new Location();

        location1 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Location where locationId = ? ",
                new Object[] { location.getLocationId() },
                new BeanPropertyRowMapper<Location>(Location.class)
        );

        Optional<Location> location3 = locationDAO.findById(location.getLocationId());

        Assert.assertTrue(location3.isPresent());
        Assert.assertTrue(location1.getLocationId().equals(location3.get().getLocationId()));
        Assert.assertTrue(location1.getLocationName().equals(location3.get().getLocationName()));
        Assert.assertFalse(locationDAO.findById(99).isPresent());
    }

    @Test
    public void deleteLocationTest()
    {
        Location location= new Location(7,"Location7",null);
        List <Location> locationList=new ArrayList();
        AtomicBoolean flag= new AtomicBoolean(false);

        locationDAO.delete(location);

        locationList = jdbcTemplate.query(
                "SELECT * FROM wtrtest.Location",
                new BeanPropertyRowMapper<Location>(Location.class)
        );

        locationList.forEach(loc->
        {
            if(loc.equals(location))
            {
                flag.set(true);
            }
        });

        Assert.assertFalse(flag.get());
    }

    @Test
    public void deleteAll()
    {
        Integer count=8;

        locationDAO.deleteAll();

        count= jdbcTemplate.queryForObject("SELECT COUNT(*) FROM wtrtest.Location",Integer.TYPE);

        Assert.assertEquals(0,locationDAO.count());
    }
}
