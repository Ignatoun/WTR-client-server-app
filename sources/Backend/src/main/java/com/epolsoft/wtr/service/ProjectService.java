package com.epolsoft.wtr.service;

import java.util.List;

import com.epolsoft.wtr.model.Project;

public interface ProjectService {
    Project createProject(Project project);
    Project updateProject(Project project);
    List<Project> getAllProjects();
    void deleteProject(Integer projectId);
    Project getProjectById(Integer projectId);
}
