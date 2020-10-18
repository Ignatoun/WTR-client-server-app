package com.epolsoft.wtr.service;

import java.util.List;

import com.epolsoft.wtr.model.DetailedTask;
import com.epolsoft.wtr.model.dto.DetailedTaskDTO;

public interface DetailedTaskService {
    DetailedTask createDetailedTask(DetailedTaskDTO detailedTaskDto);

    DetailedTask updateDetailedTask(DetailedTaskDTO detailedTaskDto);

    List<DetailedTaskDTO> getAllDetailedTasks();

    DetailedTaskDTO getDetailedTaskById(Integer detailedTaskId);

    void deleteDetailedTask(Integer detailedTaskId);
}
