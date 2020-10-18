package com.epolsoft.wtr.dao;

import com.epolsoft.wtr.model.Project;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.List;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value={"/create-project-before.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value={"/create-project-after.sql"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProjectDAOTest {
    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void createProjectTest()
    {
        Calendar start_date=new GregorianCalendar();
        start_date.set(Calendar.YEAR, 2017);
        start_date.set(Calendar.MONTH, 1);
        start_date.set(Calendar.DAY_OF_MONTH, 1);

        Date date1=start_date.getTime();

        Calendar end_date=new GregorianCalendar();
        end_date.set(Calendar.YEAR, 2018);
        end_date.set(Calendar.MONTH, 1);
        end_date.set(Calendar.DAY_OF_MONTH, 1);

        Date date2 = end_date.getTime();

        Project project = new Project(3,"the2", date1,date2, null,null);

        Project projectCreate = projectDAO.save(project);

        Assert.assertEquals(project.getProjectId(), projectCreate.getProjectId());
        Assert.assertEquals(project.getProjectName(), projectCreate.getProjectName());
        Assert.assertEquals(project.getStartDate().getDate(), projectCreate.getStartDate().getDate());
        Assert.assertEquals(project.getEndDate().getDate(), projectCreate.getEndDate().getDate());

        projectCreate= jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Project where projectId = ? ",
                new Object[] {project.getProjectId()},
                new BeanPropertyRowMapper<Project>(Project.class)
        );

        Assert.assertEquals(project.getProjectId(), projectCreate.getProjectId());
        Assert.assertEquals(project.getProjectName(), projectCreate.getProjectName());
    }

    @Test
    public void findAllProjectTest()
    {
        Iterable <Project> allproject = projectDAO.findAll();
        List<Project> projectList=new ArrayList();
        allproject.forEach(projectList::add);
        Assert.assertEquals(2,projectList.size());

        List<Project> projectList1=new ArrayList();
        projectList1= jdbcTemplate.query(
                "SELECT * FROM wtrtest.Project",
                new BeanPropertyRowMapper<Project>(Project.class)
        );

        for (int i=0;i<projectList.size();i++)
        {
            Assert.assertTrue(projectList.get(i).getProjectId().equals(projectList1.get(i).getProjectId()));
            Assert.assertTrue(projectList.get(i).getProjectName().equals(projectList1.get(i).getProjectName()));
            Assert.assertTrue(projectList.get(i).getStartDate().equals(projectList1.get(i).getStartDate()));
            Assert.assertTrue(projectList.get(i).getEndDate().equals(projectList1.get(i).getEndDate()));
        }
    }

    @Test
    public void findProjectByIdTest()
    {
        Calendar start_date=new GregorianCalendar();
        start_date.set(Calendar.YEAR, 2018);
        start_date.set(Calendar.MONTH, 04);
        start_date.set(Calendar.DAY_OF_MONTH, 20);

        Date date1=start_date.getTime();

        Calendar end_date=new GregorianCalendar();
        end_date.set(Calendar.YEAR, 2021);
        end_date.set(Calendar.MONTH, 12);
        end_date.set(Calendar.DAY_OF_MONTH, 31);

        Date date2 = end_date.getTime();

        Project project = new Project(2,"the best game in the world!", date1, date2,null,null);

        Project projectFind = projectDAO.findById(2).get();

        Assert.assertTrue(Optional.of(projectFind).isPresent());
        Assert.assertEquals(project.getProjectId(), projectFind.getProjectId());
        Assert.assertEquals(project.getProjectName(),projectFind.getProjectName());
        Assert.assertEquals(project.getStartDate().getDate(), projectFind.getStartDate().getDate());
        Assert.assertEquals(project.getEndDate().getDate(), projectFind.getEndDate().getDate());

        projectFind = jdbcTemplate.queryForObject(
                "SELECT * FROM wtrtest.Project where projectId = ? ",
                new Object[] {project.getProjectId()},
                new BeanPropertyRowMapper<Project>(Project.class)
        );

        Assert.assertTrue(Optional.of(projectFind).isPresent());
        Assert.assertEquals(project.getProjectId(), projectFind.getProjectId());
        Assert.assertEquals(project.getProjectName(),projectFind.getProjectName());
        Assert.assertEquals(project.getStartDate().getDate(), projectFind.getStartDate().getDate());
        Assert.assertEquals(project.getEndDate().getDate(), projectFind.getEndDate().getDate());

    }

    @Test
    public void deleteProjectTest(){

        Calendar start_date=new GregorianCalendar();
        start_date.set(Calendar.YEAR, 2018);
        start_date.set(Calendar.MONTH, 04);
        start_date.set(Calendar.DAY_OF_MONTH, 20);

        Date date1=start_date.getTime();

        Calendar end_date=new GregorianCalendar();
        end_date.set(Calendar.YEAR, 2021);
        end_date.set(Calendar.MONTH, 12);
        end_date.set(Calendar.DAY_OF_MONTH, 31);

        Date date2 = end_date.getTime();

        Project project = new Project(2,"the best game in the world!", date1, date2,null,null);
        projectDAO.delete(project);

        List <Project> projectList=new ArrayList();
        projectList = jdbcTemplate.query(
                "SELECT * FROM wtrtest.Project",
                new BeanPropertyRowMapper<Project>(Project.class)
        );

        Assert.assertFalse(projectList.contains(project));
        Assert.assertEquals(1, projectList.size());


    }

    @Test
    public void deleteAll()
    {
        Integer count=2;

        projectDAO.deleteAll();

        count= jdbcTemplate.queryForObject("SELECT COUNT(*) FROM wtrtest.Project",Integer.TYPE);

        Assert.assertEquals(0, projectDAO.count());
    }
}
