package com.epolsoft.wtr.service.ServiceImpl;

import com.epolsoft.wtr.controller.DepartmentController;
import com.epolsoft.wtr.dao.DetailedTaskDAO;
import com.epolsoft.wtr.dao.ReportDetailsDAO;
import com.epolsoft.wtr.dao.TaskDAO;
import com.epolsoft.wtr.model.DetailedTask;
import com.epolsoft.wtr.model.ReportDetails;
import com.epolsoft.wtr.model.dto.DetailedTaskDTO;
import com.epolsoft.wtr.service.DetailedTaskService;
import com.epolsoft.wtr.util.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DetailedTaskServiceImpl implements DetailedTaskService{

    private static final Logger LOGGER = LogManager.getLogger(DetailedTaskServiceImpl.class);

    @Autowired
    private DetailedTaskDAO detailedTaskDao;

    @Autowired
    private TaskDAO taskDao;

    @Autowired
    private ReportDetailsDAO reportDetailsDAO;

    @Override
    public DetailedTask createDetailedTask(DetailedTaskDTO detailedTaskDto) {

        LOGGER.info("Service method called to create DetailedTask: "+detailedTaskDto.toString());

        DetailedTask detailedTask=new DetailedTask(
                null,
                detailedTaskDto.getDetailedTaskName(),
                taskDao.findById(detailedTaskDto.getTaskId()).get(),
                null);

        return detailedTaskDao.save(detailedTask);
    }

    @Override
    public DetailedTask updateDetailedTask(DetailedTaskDTO detailedTaskDto) {

        LOGGER.info("Service method called to update DetailedTask;" +
                " detailedTask="+detailedTaskDto.toString());
        Optional<DetailedTask> detailedTaskDb = this.detailedTaskDao.findById(detailedTaskDto.getDetailedTaskId());

        if(detailedTaskDb.isPresent()){
            LOGGER.info("DetailedTask found");
            DetailedTask detailedTaskUpdate = detailedTaskDb.get();
            detailedTaskUpdate.setDetailedTaskId(detailedTaskDto.getDetailedTaskId());
            detailedTaskUpdate.setDetailedTaskName(detailedTaskDto.getDetailedTaskName());
            detailedTaskUpdate.setTask(taskDao.findById(detailedTaskDto.getTaskId()).get());
            LOGGER.info("DetailedTask save;" +
                    " detailedTask="+detailedTaskUpdate.toString());
            detailedTaskDao.save(detailedTaskUpdate);
            return detailedTaskUpdate;
        }
        else{
            LOGGER.info("DetailedTask not found");
            LOGGER.error("DetailedTask not found");
            throw new ResourceNotFoundException("Record not found with id : " + detailedTaskDto.getDetailedTaskId());
        }
    }

    @Override
    public List<DetailedTaskDTO> getAllDetailedTasks() {
        LOGGER.info("Service method called to view all list of DetailedTasks");
        Iterable <DetailedTask> allDetailedTasks = detailedTaskDao.findAll();
        List <DetailedTaskDTO> detailedTaskDTOList = new ArrayList<>();

        allDetailedTasks.forEach(detailedTask -> {
            detailedTaskDTOList.add(new DetailedTaskDTO(
                    detailedTask.getDetailedTaskId(),
                    detailedTask.getDetailedTaskName(),
                    detailedTask.getTask().getTaskId()));
        });
        LOGGER.info("Records found: "+detailedTaskDTOList.size());
        return detailedTaskDTOList;
    }

    @Override
    public DetailedTaskDTO getDetailedTaskById(Integer detailedTaskId) {
        LOGGER.info("Service method called to view DetailedTask by id="+detailedTaskId);
        Optional <DetailedTask> detailedTaskDb = this.detailedTaskDao.findById(detailedTaskId);

        if(detailedTaskDb.isPresent()){
            LOGGER.info("DetailedTask found: " +detailedTaskDb.get().toString());
            return new DetailedTaskDTO(
                    detailedTaskDb.get().getDetailedTaskId(),
                    detailedTaskDb.get().getDetailedTaskName(),
                    detailedTaskDb.get().getTask().getTaskId());
        }
        else{
            LOGGER.info("DetailedTask not found");
            LOGGER.error("DetailedTask not found");
            throw new ResourceNotFoundException("Record not found with id: " + detailedTaskId);
        }
    }

    @Override
    public void deleteDetailedTask(Integer detailedTaskId) {
        LOGGER.info("Service method called to delete DetailedTask by id="+detailedTaskId);
        Optional <DetailedTask> detailedTaskDb = this.detailedTaskDao.findById(detailedTaskId);

        if(detailedTaskDb.isPresent()){
            LOGGER.info("DetailedTask found: " +detailedTaskDb.get().toString());

            List<ReportDetails> reportDetails=detailedTaskDb.get().getReportDetails().stream().collect(Collectors.toList());

            reportDetails.forEach(report ->
            {
                report.setDetailedTask(null);
                reportDetailsDAO.save(report);
            });

            this.detailedTaskDao.delete(detailedTaskDb.get());
        }
        else{
            LOGGER.info("DetailedTask not found");
            LOGGER.error("DetailedTask not found");
            throw new ResourceNotFoundException("Record not found with id: " + detailedTaskId);
        }
    }
}
