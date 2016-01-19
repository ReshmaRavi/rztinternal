package com.razorthink.rzt.internal.management.project.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.razorthink.rzt.internal.management.domain.Project;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.project.service.ProjectManagementService;
import com.razorthink.rzt.internal.management.project.service.repository.ProjectManagementRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectManagementServiceImpl implements ProjectManagementService {

	@Autowired
	private ProjectManagementRepository projectManagementRepo;

	@Override
	public Project createOrUpdateProject(Project project) {
		project.setIsActive(true);
		Project projectEntity = projectManagementRepo.save(project);
		if (projectEntity == null)
			throw new DataException("data.error", "Could not save project entity");
		return projectEntity;
	}

	@Override
	public Boolean removeProject(Integer projectId) {
		Project project = projectManagementRepo.findOne(projectId);
		if (project == null)
			throw new DataException("data.error", "Could not find project entity with id: " + projectId);
		project.setIsActive(false);
		Project projectEntity = projectManagementRepo.save(project);
		if (projectEntity == null)
			return false;
		return true;
	}

	@Override
	public Project findProjectByName(String projectName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectName", projectName);
		Project project = projectManagementRepo.findOneByNamedQueryAndParams("Project.findByName", params);
		if (project == null)
			throw new DataException("data.error", "Could not find project entity with name : " + projectName);
		return project;
	}

	@Override
	public Project findProjectById(Integer id) {
		Project project = projectManagementRepo.findOne(id);
		if (project == null)
			throw new DataException("data.error", "Could not find project entity with id :" + id);
		return project;
	}
}
