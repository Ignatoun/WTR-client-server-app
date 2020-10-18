package com.epolsoft.wtr.service.ServiceImpl;

import com.epolsoft.wtr.dao.DetailedTaskDAO;
import com.epolsoft.wtr.dao.FeatureDAO;
import com.epolsoft.wtr.dao.ReportDetailsDAO;
import com.epolsoft.wtr.dao.TaskDAO;
import com.epolsoft.wtr.model.DetailedTask;
import com.epolsoft.wtr.model.ReportDetails;
import com.epolsoft.wtr.model.Tasks;
import com.epolsoft.wtr.model.dto.TasksDTO;
import com.epolsoft.wtr.service.TaskService;
import com.epolsoft.wtr.util.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LogManager.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private FeatureDAO featureDAO;

    @Autowired
    private DetailedTaskDAO detailedTaskDAO;

    @Autowired
    private ReportDetailsDAO reportDetailsDAO;

    @Override
    public List<TasksDTO> getAllTask() {
        LOGGER.info("Service method called to view all list of Tasks");
        Iterable<Tasks> taskIterable = taskDAO.findAll();
        List<TasksDTO> tasks = new ArrayList<>();

        taskIterable.forEach(tasks1 -> {
            tasks.add(new TasksDTO(
               tasks1.getTaskId(),
               tasks1.getTaskName(),
               tasks1.getFeature().getFeatureId()));
        });
        LOGGER.info("Records found: "+tasks.size());
        return tasks;
    }

    @Override
    public TasksDTO getTaskById(Integer taskId) {
        LOGGER.info("Service method called to view Task by id="+taskId);
        Optional<Tasks> taskOptional = taskDAO.findById(taskId);
        if(taskOptional.isPresent()) {
            LOGGER.info("Task found: " +taskOptional.get().toString());
            return new TasksDTO(
                    taskOptional.get().getTaskId(),
                    taskOptional.get().getTaskName(),
                    taskOptional.get().getFeature().getFeatureId());
        } else {
            LOGGER.info("Task not found");
            LOGGER.error("Task not found");
            throw new ResourceNotFoundException("No task record exist for given id");
        }
    }


    @Override
    public Tasks updateTask(TasksDTO taskDto) {
        LOGGER.info("Service method called to update Task;" +
                " task="+taskDto.toString());
        Optional<Tasks> tasksDb = this.taskDAO.findById(taskDto.getTaskId());

        if(tasksDb.isPresent()){
            LOGGER.info("Task found");
            Tasks taskUpdate = tasksDb.get();
            taskUpdate.setTaskId(taskDto.getTaskId());
            taskUpdate.setTaskName(taskDto.getTaskName());
            taskUpdate.setFeature(featureDAO.findById(taskDto.getFeatureId()).get());
            LOGGER.info("Task save;" +
                    " task="+taskUpdate.toString());
            taskDAO.save(taskUpdate);
            return taskUpdate;
        }
        else{
            LOGGER.info("Task not found");
            LOGGER.error("Task not found");
            throw new ResourceNotFoundException("Record not found with id : " + taskDto.getTaskId());
        }
    }

    @Override
    public Tasks createTask(TasksDTO taskDto) {
        LOGGER.info("Service method called to create Task: "+taskDto.toString());
        Tasks tasks=new Tasks(
                null,
                taskDto.getTaskName(),
                featureDAO.findById(taskDto.getFeatureId()).get(),
                null,
                null);

        return taskDAO.save(tasks);
    }

    @Override
    public void deleteTask(Integer taskId) {
        LOGGER.info("Service method called to delete Task by id="+taskId);
        Optional<Tasks> task = taskDAO.findById(taskId);

        if(task.isPresent()){
            LOGGER.info("Project found: " +task.get().toString());
            List<ReportDetails> reportDetails=task.get().getReportDetails().stream().collect(Collectors.toList());

            reportDetails.forEach(report ->
            {
                report.setTask(null);
                reportDetailsDAO.save(report);
            });

            List<DetailedTask> detailedTasks = task.get().getDetailedTasks().stream().collect(Collectors.toList());

            detailedTasks.forEach(detailedTask->
            {
                detailedTask.setTask(null);
                detailedTaskDAO.save(detailedTask);
            });
            taskDAO.deleteById(taskId);
        } else {
            LOGGER.info("Task not found");
            LOGGER.error("Task not found");
            throw new ResourceNotFoundException("No task record exist for given id");
        }
    }
}
