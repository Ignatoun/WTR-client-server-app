package com.epolsoft.wtr.dao;

import org.springframework.data.repository.CrudRepository;

import com.epolsoft.wtr.model.Tasks;

public interface TaskDAO extends CrudRepository<Tasks, Integer> {
}
