package com.epolsoft.wtr.service;

import com.epolsoft.wtr.model.Scheduler;

import java.util.List;

public interface SchedulerService {
    Scheduler createParam(Scheduler scheduler);

    Scheduler updateParam(Scheduler scheduler);

    List<Scheduler> updateParams(List<Scheduler> schedulers);

    List< Scheduler > getAllParams();

    Scheduler getParamById(Integer paramId);

    void deleteParam(Integer paramId);
}
