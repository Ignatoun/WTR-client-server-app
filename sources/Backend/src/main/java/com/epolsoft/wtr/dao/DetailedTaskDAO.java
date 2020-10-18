package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.DetailedTask;
import org.springframework.data.repository.CrudRepository;

public interface DetailedTaskDAO extends  CrudRepository<DetailedTask, Integer>{
}
