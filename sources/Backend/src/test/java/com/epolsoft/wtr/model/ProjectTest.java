package com.epolsoft.wtr.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectTest {

    Project project = new Project();

    @Test
    public void getProjectId() {
        project.setProjectId(6);
        Assert.assertTrue(project.getProjectId()==6);
    }

    @Test
    public void getProjectName() {
        project.setProjectName("web application");
        Assert.assertTrue(project.getProjectName()=="web application");
    }

    @Test
    public void getStartDate() {
        Date date = new Date();
        project.setStartDate(date);
        Assert.assertTrue(project.getStartDate()==date);
    }

    @Test
    public void getEndDate() {
        Date date = new Date();
        long date1 = date.getTime();
        date.setTime(date1+10000);
        project.setEndDate(date);
        Assert.assertTrue(project.getEndDate()==date);
    }

}
