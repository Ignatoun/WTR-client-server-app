package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationDAO extends CrudRepository<Location, Integer> {

}
