package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.DetailedTask;
import com.epolsoft.wtr.model.Tasks;
import com.epolsoft.wtr.model.dto.DetailedTaskDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value={"/create-detailedTask-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-detailedTask-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class DetailedTaskDAOTest {

    @Autowired
    private DetailedTaskDAO detailedTaskDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void createDetailedTask() {
        DetailedTask detailedTask = new DetailedTask(45, "dT1",
                taskDAO.findById(1).get(), null);
        DetailedTask detailedTask1;
        DetailedTaskDTO detailedTask2;

        detailedTask1 = detailedTaskDAO.save(detailedTask);
        Assert.assertEquals(Integer.valueOf(6), detailedTask1.getDetailedTaskId());

        detailedTask2 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.DetailedTask WHERE detailedTaskId = ?",
                new Object[]{detailedTask1.getDetailedTaskId()},
                new BeanPropertyRowMapper<DetailedTaskDTO>(DetailedTaskDTO.class)
        );

        assert detailedTask2 != null;
        Assert.assertEquals(detailedTask1.getDetailedTaskId(), detailedTask2.getDetailedTaskId());
        Assert.assertEquals(detailedTask1.getDetailedTaskName(), detailedTask2.getDetailedTaskName());
        Assert.assertEquals(detailedTask1.getTask().getTaskId(), detailedTask2.getTaskId());
    }

    @Test
    void findAllDetailedTask() {
        Iterable <DetailedTask> allDetailedTasks = detailedTaskDAO.findAll();
        List<DetailedTask> detailedTaskList = new ArrayList();
        allDetailedTasks.forEach(detailedTaskList::add);

        Assert.assertEquals(5, detailedTaskList.size());

        List<DetailedTaskDTO> detailedTaskList1;
        detailedTaskList1= jdbcTemplate.query(
                "SELECT * FROM wtrtest.DetailedTask",
                new BeanPropertyRowMapper<DetailedTaskDTO>(DetailedTaskDTO.class)
        );

        for (int i = 0; i < detailedTaskList.size(); i++)
        {
            Assert.assertEquals(detailedTaskList.get(i).getDetailedTaskId(), detailedTaskList1.get(i).getDetailedTaskId());
            Assert.assertEquals(detailedTaskList.get(i).getDetailedTaskName(), detailedTaskList1.get(i).getDetailedTaskName());
            Assert.assertEquals(detailedTaskList.get(i).getTask().getTaskId(), detailedTaskList1.get(i).getTaskId());
        }
    }

    @Test
    void findByIdDetailedTasks() {
        DetailedTask detailedTask = new DetailedTask(45, "dT1",
                taskDAO.findById(1).get(), null);
        DetailedTask detailedTask1;
        DetailedTaskDTO detailedTask2;

        detailedTask1 = detailedTaskDAO.save(detailedTask);
        detailedTask2 = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.DetailedTask WHERE detailedTaskId = ?",
                new Object[]{detailedTask1.getDetailedTaskId()},
                new BeanPropertyRowMapper<DetailedTaskDTO>(DetailedTaskDTO.class)
        );

        Optional<DetailedTask> detailedTask3 = detailedTaskDAO.findById(detailedTask1.getDetailedTaskId());
        Assert.assertTrue(detailedTask3.isPresent());
        assert detailedTask2 != null;
        Assert.assertEquals(detailedTask2.getDetailedTaskId(), detailedTask3.get().getDetailedTaskId());
        Assert.assertEquals(detailedTask2.getDetailedTaskName(), detailedTask3.get().getDetailedTaskName());
        Assert.assertEquals(detailedTask2.getTaskId(), detailedTask3.get().getTask().getTaskId());
    }

    @Test
    void deleteDetailedTask() {
        DetailedTask detailedTask = new DetailedTask(45, "dT1",
                taskDAO.findById(1).get(), null);
        DetailedTask detailedTask1 = detailedTaskDAO.save(detailedTask);
        Assert.assertTrue(detailedTaskDAO.findById(detailedTask1.getDetailedTaskId()).isPresent());
        detailedTaskDAO.deleteById(detailedTask1.getDetailedTaskId());
        Assert.assertFalse(detailedTaskDAO.findById(detailedTask1.getDetailedTaskId()).isPresent());
    }

    @Test
    void deleteAllDetailedTasks() {
        Assert.assertEquals(5, detailedTaskDAO.count());
        detailedTaskDAO.deleteAll();
        Assert.assertEquals(0, detailedTaskDAO.count());
    }
}
