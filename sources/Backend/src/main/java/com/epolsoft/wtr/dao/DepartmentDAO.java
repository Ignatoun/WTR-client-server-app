package com.epolsoft.wtr.dao;

import org.springframework.data.repository.CrudRepository;

import com.epolsoft.wtr.model.Department;

public interface DepartmentDAO extends CrudRepository<Department, Integer> {

}
