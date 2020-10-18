package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.Title;
import org.springframework.data.repository.CrudRepository;

public interface TitleDAO extends CrudRepository<Title, Integer> {
}