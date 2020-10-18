package com.epolsoft.wtr.service;

import java.util.List;

import com.epolsoft.wtr.model.Location;

public interface LocationService {
    Location createLocation(Location location);

    Location updateLocation(Location location);

    List <Location> getAllLocations();

    Location getLocationById(Integer locationId);

    void deleteLocation(Integer locationId);
}
