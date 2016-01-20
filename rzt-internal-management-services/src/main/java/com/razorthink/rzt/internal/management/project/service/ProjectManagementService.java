package com.razorthink.rzt.internal.management.project.service;

import java.util.List;

import com.razorthink.rzt.internal.management.domain.Project;

public interface ProjectManagementService {
	
	public Project createOrUpdateProject(Project project);
	
	public Boolean removeProject(Integer projectId);
	
	public Project findProjectByName(String projectName);
	
	public Project findProjectById(Integer id);
	
	public List<Project> getAllProjects();

}
