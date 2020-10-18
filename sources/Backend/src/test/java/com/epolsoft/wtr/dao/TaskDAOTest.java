package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.Tasks;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value={"/create-task-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-task-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TaskDAOTest {

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createTaskTest()
    {
        Tasks task=new Tasks(6,"Task6",null,null,null);
        Tasks task1=new Tasks();
        Tasks task2=new Tasks();

        task1=taskDAO.save(task);

        Assert.assertTrue(task.equals(task1));

        task2 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Task where taskId = ? ",
                new Object[] { task.getTaskId() },
                new BeanPropertyRowMapper<Tasks>(Tasks.class)
        );

        Assert.assertTrue(task.equals(task2));
}

    @Test
    public void findAllTaskTest()
    {
        Iterable <Tasks> alltasks = taskDAO.findAll();
        List <Tasks> tasksList=new ArrayList();
        alltasks.forEach(tasksList::add);

        Assert.assertEquals(5,tasksList.size());

        List<Tasks> tasksList1=new ArrayList();
        tasksList1= jdbcTemplate.query(
                "SELECT * FROM wtrtest.Task",
                new BeanPropertyRowMapper<Tasks>(Tasks.class)
        );

        for (int i=0;i<tasksList.size();i++)
        {
            Assert.assertEquals(tasksList.get(i).getTaskId(),tasksList1.get(i).getTaskId());
            Assert.assertEquals(tasksList.get(i).getTaskName(),tasksList1.get(i).getTaskName());
            Assert.assertEquals(tasksList.get(i).getFeature(),tasksList1.get(i).getFeature());
        }
    }

    @Test
    public void findByIdTest()
    {
        Tasks task=new Tasks(1,"Task1",null,null,null);
        Tasks task1=new Tasks();

        task1 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Task where taskId = ? ",
                new Object[] { task.getTaskId() },
                new BeanPropertyRowMapper<Tasks>(Tasks.class)
        );

        Optional<Tasks> task2 = taskDAO.findById(task.getTaskId());

        Assert.assertTrue(task2.isPresent());
        Assert.assertEquals(task1.getTaskId(),task2.get().getTaskId());
        Assert.assertEquals(task1.getTaskName(),task2.get().getTaskName());
        Assert.assertEquals(task1.getFeature(),task2.get().getFeature());
        Assert.assertFalse(taskDAO.findById(99).isPresent());
    }

    @Test
    public void deleteTest()
    {
        Tasks task= new Tasks(2,"Task2",null,null,null);
        List <Tasks> taskList=new ArrayList();
        AtomicBoolean flag= new AtomicBoolean(false);

        taskDAO.delete(task);

        taskList = jdbcTemplate.query(
                "SELECT * FROM wtrtest.Task",
                new BeanPropertyRowMapper<Tasks>(Tasks.class)
        );

        taskList.forEach(t->
        {
            if(t.getTaskId()==task.getTaskId()&&
               t.getTaskName()==task.getTaskName()&&
               t.getFeature()==task.getFeature())
            {
                flag.set(true);
            }
        });

        Assert.assertFalse(flag.get());
    }

    @Test
    public void deleteAll()
    {
        Integer count=5;

        taskDAO.deleteAll();

        count= jdbcTemplate.queryForObject("SELECT COUNT(*) FROM wtrtest.Task",Integer.TYPE);

        Assert.assertEquals(0,taskDAO.count());
    }

}