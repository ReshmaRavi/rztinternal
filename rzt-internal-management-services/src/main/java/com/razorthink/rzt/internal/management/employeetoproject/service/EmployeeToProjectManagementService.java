package com.razorthink.rzt.internal.management.employeetoproject.service;

import java.util.List;
import com.razorthink.rzt.internal.management.domain.Employee;
import com.razorthink.rzt.internal.management.domain.EmployeeToProject;
import com.razorthink.rzt.internal.management.domain.Project;

public interface EmployeeToProjectManagementService {

	public EmployeeToProject createOrUpdateLeave( EmployeeToProject employeeToProject );

	public Boolean removeEmployeeToProject( Integer id );

	public List<Employee> getAllEmployeesByProjectId( Integer projectId );

	public List<Project> getAllProjectByEmployeeId( Integer empId );

}
