package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.Feature;
import org.springframework.data.repository.CrudRepository;

public interface FeatureDAO  extends CrudRepository<Feature, Integer> {
}
