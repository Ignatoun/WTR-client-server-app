package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.Factor;

import org.springframework.data.repository.CrudRepository;

public interface FactorDAO extends CrudRepository<Factor, Integer> {
}
