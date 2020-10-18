package com.epolsoft.wtr.service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epolsoft.wtr.dao.ReportDetailsDAO;
import com.epolsoft.wtr.model.ReportDetails;
import com.epolsoft.wtr.service.LocationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epolsoft.wtr.util.ResourceNotFoundException;
import com.epolsoft.wtr.model.Location;
import com.epolsoft.wtr.dao.LocationDAO;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    private static final Logger LOGGER = LogManager.getLogger(LocationServiceImpl.class);

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private ReportDetailsDAO reportDetailsDAO;

    @Override
    public Location createLocation(Location location)
    {
        LOGGER.info("Service method called to create Location: "+location.toString());
        return locationDAO.save(location);
    }

    @Override
    public Location updateLocation(Location location)
    {
        LOGGER.info("Service method called to update Location;" +
            " location="+location.toString());
        Optional <Location> locationOptional = this.locationDAO.findById(location.getLocationId());

        if (locationOptional.isPresent()) {
            LOGGER.info("Location found");
            Location locationToUpdate = locationOptional.get();
            locationToUpdate.setLocationId(location.getLocationId());
            locationToUpdate.setLocationName(location.getLocationName());
            LOGGER.info("Location save;" +
                    " location="+locationToUpdate.toString());
            locationDAO.save(locationToUpdate);
            return locationToUpdate;
        } else {
            LOGGER.info("Location not found");
            LOGGER.error("Location not found");
            throw new ResourceNotFoundException("Record not found with id : " + location.getLocationId());
        }
    }

    @Override
    public List <Location> getAllLocations()
    {
        LOGGER.info("Service method called to view all list of Locations");
        Iterable <Location> locations = locationDAO.findAll();
        List <Location> locationList = new ArrayList();
        locations.forEach(locationList::add);
        LOGGER.info("Records found: "+locationList.size());
        return locationList;
    }

    @Override
    public Location getLocationById(Integer locationId)
    {
        LOGGER.info("Service method called to view Location by id="+locationId);
        Optional <Location> locationOptional = this.locationDAO.findById(locationId);

        if (locationOptional.isPresent()) {
            LOGGER.info("Location found: " +locationOptional.get().toString());
            return locationOptional.get();
        } else {
            LOGGER.info("Location not found");
            LOGGER.error("Location not found");
            throw new ResourceNotFoundException("Record not found with id : " + locationId);
        }
    }

    @Override
    public void deleteLocation(Integer locationId)
    {
        LOGGER.info("Service method called to delete Location by id="+locationId);
        Optional <Location> locationOptional = this.locationDAO.findById(locationId);

        if (locationOptional.isPresent()) {
            LOGGER.info("Location found: " +locationOptional.get().toString());
            List<ReportDetails> reportDetails=locationOptional.get().getReportDetails().stream().collect(Collectors.toList());

            reportDetails.forEach(reportDetail ->
            {
                reportDetail.setLocation(null);
                reportDetailsDAO.save(reportDetail);
            });

            this.locationDAO.delete(locationOptional.get());
        } else {
            LOGGER.info("Location not found");
            LOGGER.error("Location not found");
            throw new ResourceNotFoundException("Record not found with id : " + locationId);
        }
    }
}
