package com.epolsoft.wtr.service;

import com.epolsoft.wtr.dao.FeatureDAO;
import com.epolsoft.wtr.dao.TaskDAO;
import com.epolsoft.wtr.model.DetailedTask;
import com.epolsoft.wtr.model.Feature;
import com.epolsoft.wtr.model.ReportDetails;
import com.epolsoft.wtr.model.Tasks;
import com.epolsoft.wtr.model.dto.TasksDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskDAO taskDao;

    @MockBean
    private FeatureDAO featureDAO;

    @Test
    public void createTaskTest()
    {
        TasksDTO tasksDTO=new TasksDTO(2,"Task2",null);

        Tasks task=new Tasks(2,"Task2",new Feature(),null,null);

        Mockito.doReturn(task)
                .when(taskDao)
                .save(Mockito.any(Tasks.class));

        Mockito.doReturn(Optional.of(new Feature()))
                .when(featureDAO)
                .findById(null);

        Tasks task1 = taskService.createTask(tasksDTO);

        Assert.assertEquals(task.getTaskId(),task1.getTaskId());
        Assert.assertEquals(task.getTaskName(),task1.getTaskName());
        Assert.assertEquals(task.getFeature(),task1.getFeature());
        Mockito.verify(taskDao,Mockito.times(1)).save(Mockito.any(Tasks.class));
    }

    @Test
    public void updateTaskTest()
    {
        TasksDTO tasksDTO = new TasksDTO(2,"Task2",null);

        Tasks task = new Tasks(2,"Task2",new Feature(),null,null);

        Mockito.doReturn(Optional.of(task))
                .when(taskDao)
                .findById(2);

        Mockito.doReturn(task)
                .when(taskDao)
                .save(Mockito.any(Tasks.class));

        Mockito.doReturn(Optional.of(new Feature()))
                .when(featureDAO)
                .findById(null);

        Tasks task1 = taskService.updateTask(tasksDTO);

        Assert.assertEquals(task.getTaskId(),task1.getTaskId());
        Assert.assertEquals(task.getTaskName(),task1.getTaskName());
        Assert.assertEquals(task.getFeature(),task1.getFeature());
        Mockito.verify(taskDao,Mockito.times(1)).findById(task.getTaskId());
        Mockito.verify(taskDao,Mockito.times(1)).save(Mockito.any(Tasks.class));
    }

    @Test
    public void getAllTaskTest()
    {
        Tasks task = new Tasks(2,"Task2",new Feature(1,null,null,null,null),null,null);

        List<Tasks> list=new ArrayList();
        list.add(task);

        Mockito.doReturn(list)
                .when(taskDao)
                .findAll();

        List<TasksDTO> tasksList=taskService.getAllTask();

        for(int i=0;i<tasksList.size();i++) {
            Assert.assertEquals(tasksList.get(i).getTaskId(), list.get(i).getTaskId());
            Assert.assertEquals(tasksList.get(i).getTaskName(), list.get(i).getTaskName());
            Assert.assertEquals(tasksList.get(i).getFeatureId(), list.get(i).getFeature().getFeatureId());
        }
        Mockito.verify(taskDao,Mockito.times(1)).findAll();
    }

    @Test
    public void getTaskByIdTest()
    {
        Tasks task = new Tasks(2,"Task2",new Feature(1,null,null,null,null),null,null);

        Mockito.doReturn(Optional.of(task))
                .when(taskDao)
                .findById(2);

        TasksDTO task1 =taskService.getTaskById(task.getTaskId());

        Assert.assertEquals(task.getTaskId(),task1.getTaskId());
        Assert.assertEquals(task.getTaskName(),task1.getTaskName());
        Assert.assertEquals(task.getFeature().getFeatureId(),task1.getFeatureId());
    }

    @Test
    public void deleteTaskTest()
    {
        Tasks task = new Tasks(2,"Task2",new Feature(),new HashSet<ReportDetails>(),new HashSet<DetailedTask>());

        Mockito.doReturn(Optional.of(task))
                .when(taskDao)
                .findById(task.getTaskId());

        taskService.deleteTask(task.getTaskId());

        Mockito.verify(taskDao,Mockito.times(1)).deleteById(task.getTaskId());
    }
}