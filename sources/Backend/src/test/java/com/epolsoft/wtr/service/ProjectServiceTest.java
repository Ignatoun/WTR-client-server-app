package com.epolsoft.wtr.service;

import com.epolsoft.wtr.dao.ProjectDAO;
import com.epolsoft.wtr.dao.ReportDetailsDAO;
import com.epolsoft.wtr.model.Feature;
import com.epolsoft.wtr.model.Project;
import com.epolsoft.wtr.model.ReportDetails;
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
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {
    @Autowired
    private ProjectService projectService;

    @MockBean
    private ProjectDAO projectDAO;

    @MockBean
    private ReportDetailsDAO reportDetailsDAO;

    @Test
    public void createProjectTest()
    {
        Project project=new Project(2,"the2", new Date(213253454), new Date(2132534654),null,null);

        Mockito.doReturn(project)
                .when(projectDAO)
                .save(project);

        projectService.createProject(project);

        Mockito.verify(projectDAO,Mockito.times(1)).save(project);
    }

    @Test
    public void updateProjectTest()
    {
        Project project= new Project(2,"the best game in the world!", new Date(), new Date(),null,null);

        Mockito.doReturn(Optional.of(project))
                .when(projectDAO)
                .findById(2);
        projectService.updateProject(project);

        Mockito.verify(projectDAO,Mockito.times(1)).findById(project.getProjectId());
        Mockito.verify(projectDAO,Mockito.times(1)).save(project);
    }

    @Test
    public void getAllProjectTest()
    {
        Project project=new Project(2,"the best game in the world!", new Date(), new Date(), null,null);

        List<Project> list=new ArrayList();
        list.add(project);

        Mockito.doReturn(list)
                .when(projectDAO)
                .findAll();

        Assert.assertEquals(list,projectService.getAllProjects());
        Mockito.verify(projectDAO,Mockito.times(1)).findAll();
    }

    @Test
    public void getProjectByIdTest()
    {
        Project project= new Project(2,"the best game in the world!", new Date(), new Date(), null, null);

        Mockito.doReturn(Optional.of(project))
                .when(projectDAO)
                .findById(2);

        Assert.assertEquals(project,projectService.getProjectById(project.getProjectId()));
    }

    @Test
    public void deleteProjectTest()
    {
        Project project= new Project(2,"the best game in the world!", new Date(), new Date(), new HashSet<ReportDetails>(), new HashSet<Feature>());

        Mockito.doReturn(Optional.of(project))
                .when(projectDAO)
                .findById(2);

        Mockito.doReturn(Optional.of(project).get().getReportDetails())
                .when(reportDetailsDAO)
                .save(null);

        projectService.deleteProject(project.getProjectId());

        Mockito.verify(projectDAO,Mockito.times(1)).delete(project);
    }
}