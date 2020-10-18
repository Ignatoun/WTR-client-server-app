package com.epolsoft.wtr.service;


import com.epolsoft.wtr.model.Tasks;
import com.epolsoft.wtr.model.dto.TasksDTO;

import java.util.List;

public interface TaskService  {

    List<TasksDTO> getAllTask();

    TasksDTO getTaskById(Integer taskId);

    Tasks createTask(TasksDTO taskDto);

    Tasks updateTask(TasksDTO taskDto);

    void deleteTask(Integer taskId);
}
