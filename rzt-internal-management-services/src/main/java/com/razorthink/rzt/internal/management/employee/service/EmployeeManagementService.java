package com.razorthink.rzt.internal.management.employee.service;

import java.util.List;

import com.razorthink.rzt.internal.management.domain.Employee;
import com.razorthink.rzt.internal.management.domain.TinyEmployee;

public interface EmployeeManagementService {

	public Employee createOrUpdateEmployee( Employee employee );
	
	public Boolean removeEmployee(Integer employeeId);
	
	public Employee findByEmployeeNumber(String empNum);
	
	public Employee findByEmployeeId(Integer id);
	
	public List<Employee> getAllEmployees();

	public List<TinyEmployee> getAllEmployeesMin();

}
