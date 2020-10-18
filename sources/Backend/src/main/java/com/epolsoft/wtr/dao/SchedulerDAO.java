package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulerDAO extends JpaRepository<Scheduler, Integer> {

}
