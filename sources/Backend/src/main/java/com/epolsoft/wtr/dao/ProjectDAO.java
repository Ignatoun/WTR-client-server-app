package com.epolsoft.wtr.dao;

import  org.springframework.data.repository.CrudRepository;

import  com.epolsoft.wtr.model.Project;

public interface ProjectDAO extends CrudRepository <Project, Integer> {

}