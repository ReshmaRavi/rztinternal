package com.razorthink.rzt.internal.management.employeetoproject.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.razorthink.rzt.internal.management.domain.Employee;
import com.razorthink.rzt.internal.management.domain.EmployeeToProject;
import com.razorthink.rzt.internal.management.domain.Project;
import com.razorthink.rzt.internal.management.employeetoproject.service.EmployeeToProjectManagementService;
import com.razorthink.rzt.internal.management.employeetoproject.service.repo.EmployeeToProjectManagementRepository;
import com.razorthink.rzt.internal.management.exception.DataException;

@Service
@Transactional( rollbackFor = Exception.class )
public class EmployeeToProjectManagementServiceImpl implements EmployeeToProjectManagementService {

	EmployeeToProjectManagementRepository empToProjectManagementRepository;

	@Autowired
	public EmployeeToProjectManagementRepository getEmpToProjectManagementRepository()
	{
		return empToProjectManagementRepository;
	}

	public void setEmpToProjectManagementRepository(
			EmployeeToProjectManagementRepository empToProjectManagementRepository )
	{
		this.empToProjectManagementRepository = empToProjectManagementRepository;
	}

	@Override
	public EmployeeToProject createOrUpdateLeave( EmployeeToProject employeeToProject )
	{
		employeeToProject.setIsActive(true);
		EmployeeToProject employeeToProjectEntity = empToProjectManagementRepository.save(employeeToProject);
		if( employeeToProjectEntity == null )
			throw new DataException("data.error", "Could not save EmployeeToProject entity");
		return employeeToProjectEntity;

	}

	@Override
	public Boolean removeEmployeeToProject( Integer id )
	{
		EmployeeToProject empToProject = empToProjectManagementRepository.findOne(id);
		if( empToProject == null )
			throw new DataException("data.error", "Could not find EmployeeToProject entity with id: " + id);
		empToProject.setIsActive(false);
		EmployeeToProject employeeToProjectEntity = empToProjectManagementRepository.save(empToProject);
		if( employeeToProjectEntity == null )
			return false;
		return true;
	}

	@Override
	public List<Employee> getAllEmployeesByProjectId( Integer projectId )
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", projectId);
//		 List<Employee>
//		 employee=empToProjectManagementRepository.findByNamedQueryAndParams("EmpToProject.findEmpByProject",
//		 params);

		return null;
	}

	@Override
	public List<Project> getAllProjectByEmployeeId( Integer empId )
	{
		// TODO Auto-generated method stub
		return null;
	}

}
