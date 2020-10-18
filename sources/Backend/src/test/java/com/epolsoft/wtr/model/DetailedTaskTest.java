package com.epolsoft.wtr.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class DetailedTaskTest {

    DetailedTask detailedTask = new DetailedTask();

    @Test
    void getDetailedTaskId() {
        detailedTask.setDetailedTaskId(2);
        Assert.assertEquals(Integer.valueOf(2), detailedTask.getDetailedTaskId());
    }

    @Test
    void getDetailedTaskName() {
        detailedTask.setDetailedTaskName("Hello");
        Assert.assertEquals("Hello", detailedTask.getDetailedTaskName());
    }

    @Test
    void getTask() {
        detailedTask.setTask(new Tasks(3,"abc",null,null,null));
        Assert.assertEquals(
                new Tasks(3,"abc",null,null,null),
                detailedTask.getTask());
    }
}