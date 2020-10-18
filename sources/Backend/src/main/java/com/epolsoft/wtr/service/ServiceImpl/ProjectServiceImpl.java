package com.epolsoft.wtr.service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epolsoft.wtr.dao.FeatureDAO;
import com.epolsoft.wtr.dao.ReportDetailsDAO;
import com.epolsoft.wtr.model.Feature;
import com.epolsoft.wtr.model.ReportDetails;
import com.epolsoft.wtr.service.ProjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epolsoft.wtr.util.ResourceNotFoundException;
import com.epolsoft.wtr.model.Project;
import com.epolsoft.wtr.dao.ProjectDAO;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private static final Logger LOGGER = LogManager.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private FeatureDAO featureDAO;

    @Autowired
    private ReportDetailsDAO reportDetailsDAO;

    @Override
    public Project createProject(Project project)
    {
        LOGGER.info("Service method called to create Project: "+project.toString());
        return projectDAO.save(project);
    }

    @Override
    public Project updateProject(Project project)
    {
        LOGGER.info("Service method called to update Project;" +
                " project="+project.toString());
        Optional <Project> projectOptional = this.projectDAO.findById(project.getProjectId());

        if (projectOptional.isPresent()) {
            LOGGER.info("Project found");
            Project projectToUpdate = projectOptional.get();
            projectToUpdate.setProjectId(project.getProjectId());
            projectToUpdate.setStartDate(project.getStartDate());
            projectToUpdate.setEndDate(project.getEndDate());
            projectToUpdate.setProjectName(project.getProjectName());
            LOGGER.info("Project save;" +
                    " project="+projectToUpdate.toString());
            projectDAO.save(projectToUpdate);
            return projectToUpdate;
        } else {
            LOGGER.info("Project not found");
            LOGGER.error("Project not found");
            throw new ResourceNotFoundException("Record not found with id : " + project.getProjectId());
        }
    }

    @Override
    public List <Project> getAllProjects()
    {
        LOGGER.info("Service method called to view all list of Projects");
        Iterable <Project> projects = projectDAO.findAll();
        List <Project> projectList = new ArrayList();
        projects.forEach(projectList::add);
        LOGGER.info("Records found: "+projectList.size());
        return projectList;
    }

    @Override
    public Project getProjectById(Integer projectId)
    {
        LOGGER.info("Service method called to view Project by id="+projectId);
        Optional <Project> projectOptional = this.projectDAO.findById(projectId);

        if (projectOptional.isPresent()) {
            LOGGER.info("Project found: " +projectOptional.get().toString());
            return projectOptional.get();
        } else {
            LOGGER.info("Project not found");
            LOGGER.error("Project not found");
            throw new ResourceNotFoundException("Record not found with id : " + projectId);
        }
    }

    @Override
    public void deleteProject(Integer projectId)
    {
        LOGGER.info("Service method called to delete Project by id="+projectId);
        Optional <Project> projectOptional = this.projectDAO.findById(projectId);

        if (projectOptional.isPresent()) {
            LOGGER.info("Project found: " +projectOptional.get().toString());
            List<ReportDetails> reportDetails=projectOptional.get().getReportDetails().stream().collect(Collectors.toList());

            reportDetails.forEach(report ->
            {
                report.setProject(null);
                reportDetailsDAO.save(report);
            });

            List<Feature> features= projectOptional.get().getFeature().stream().collect(Collectors.toList());

            features.forEach(feature ->
            {
                feature.setProject(null);
                featureDAO.save(feature);
            });
            this.projectDAO.delete(projectOptional.get());
        } else {
            LOGGER.info("Project not found");
            LOGGER.error("Project not found");
            throw new ResourceNotFoundException("Record not found with id : " + projectId);
        }
    }
}
