package com.epolsoft.wtr.service;

import com.epolsoft.wtr.dao.DetailedTaskDAO;
import com.epolsoft.wtr.dao.ReportDetailsDAO;
import com.epolsoft.wtr.dao.TaskDAO;
import com.epolsoft.wtr.model.DetailedTask;
import com.epolsoft.wtr.model.ReportDetails;
import com.epolsoft.wtr.model.Tasks;
import com.epolsoft.wtr.model.dto.DetailedTaskDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DetailedTaskServiceTest {
    @Autowired
    private DetailedTaskService detailedTaskService;

    @MockBean
    private DetailedTaskDAO detailedTaskDAO;

    @MockBean
    private TaskDAO taskDAO;

    @MockBean
    private ReportDetailsDAO reportDetailsDAO;

    @Test
    public void createDetailedTaskTest(){
        DetailedTaskDTO detailedTaskDTO = new DetailedTaskDTO(21, "DetailedTask5", null);

        Mockito.doReturn(Optional.of(new Tasks()))
                .when(taskDAO)
                .findById(detailedTaskDTO.getTaskId());

        DetailedTask detailedTask=new DetailedTask(
                null,
                detailedTaskDTO.getDetailedTaskName(),
                new Tasks(),
                null);

        Mockito.doReturn(detailedTask)
                .when(detailedTaskDAO)
                .save(Mockito.any(DetailedTask.class));

        DetailedTask detailedTaskCreate = detailedTaskService.createDetailedTask(detailedTaskDTO);

        Assert.assertEquals(detailedTask.getDetailedTaskId(), detailedTaskCreate.getDetailedTaskId());
        Assert.assertEquals(detailedTask.getDetailedTaskName(), detailedTaskCreate.getDetailedTaskName());
        Assert.assertEquals(detailedTask.getTask(),detailedTaskCreate.getTask());

        Mockito.verify(detailedTaskDAO, Mockito.times(1)).save(Mockito.any(DetailedTask.class));
    }

    @Test
    public void updateTaskTest() {

        DetailedTaskDTO detailedTaskDTO = new DetailedTaskDTO(2, "DetailedTask1", null);

        Mockito.doReturn(Optional.of(new Tasks()))
                .when(taskDAO)
                .findById(detailedTaskDTO.getTaskId());

        DetailedTask detailedTask=new DetailedTask(
                null,
                detailedTaskDTO.getDetailedTaskName(),
                new Tasks(),
                null);

        Mockito.doReturn(Optional.of(detailedTask))
                .when(detailedTaskDAO)
                .findById(2);

        DetailedTask detailedTaskUpdate = detailedTaskService.updateDetailedTask(detailedTaskDTO);

        Assert.assertEquals(detailedTask.getDetailedTaskId(), detailedTaskUpdate.getDetailedTaskId());
        Assert.assertEquals(detailedTask.getDetailedTaskName(), detailedTaskUpdate.getDetailedTaskName());
        Assert.assertEquals(detailedTask.getTask(), detailedTaskUpdate.getTask());

        Mockito.verify(detailedTaskDAO, Mockito.times(1)).findById(detailedTask.getDetailedTaskId());
        Mockito.verify(detailedTaskDAO, Mockito.times(1)).save(Mockito.any(DetailedTask.class));
    }

    @Test
    public void getAllDetailedTaskTest()
    {
        DetailedTaskDTO detailedTask = new DetailedTaskDTO(2, "DetailedTask5", 1);

        List<DetailedTaskDTO> allDetailedTasks = new ArrayList();
        allDetailedTasks.add(detailedTask);

        Mockito.doReturn(new ArrayList<DetailedTask>(Arrays.asList(new DetailedTask(2,"DetailedTask5",new Tasks(1,null,null,null,null),null))))
                .when(detailedTaskDAO)
                .findAll();

        Assert.assertEquals(allDetailedTasks, detailedTaskService.getAllDetailedTasks());
        Mockito.verify(detailedTaskDAO, Mockito.times(1)).findAll();
    }

    @Test
    public void getDetailedTaskByIdTest(){
        DetailedTaskDTO detailedTask = new DetailedTaskDTO(2, "DetailedTask5", 1);

        Mockito.doReturn(Optional.of(new DetailedTask(2,"DetailedTask5",new Tasks(1,null,null,null,null),null)))
                .when(detailedTaskDAO)
                .findById(2);

        Assert.assertEquals(detailedTask, detailedTaskService.getDetailedTaskById(2));
        Mockito.verify(detailedTaskDAO, Mockito.times(1)).findById(detailedTask.getDetailedTaskId());
    }

    @Test
    public void deleteDetailedTaskTest(){
        DetailedTask detailedTask = new DetailedTask(2, "DetailedTask5", new Tasks(), new HashSet<ReportDetails>());

        Mockito.doReturn(Optional.of(detailedTask))
                .when(detailedTaskDAO)
                .findById(2);

        Mockito.doReturn(Optional.of(detailedTask).get().getReportDetails())
                .when(reportDetailsDAO)
                .save(null);

        detailedTaskService.deleteDetailedTask(detailedTask.getDetailedTaskId());
        Mockito.verify(detailedTaskDAO, Mockito.times(1)).findById(2);
    }
}
