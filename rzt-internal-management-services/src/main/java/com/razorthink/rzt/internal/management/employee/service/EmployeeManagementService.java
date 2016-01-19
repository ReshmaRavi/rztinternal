package com.razorthink.rzt.internal.management.employee.service;

import com.razorthink.rzt.internal.management.domain.Employee;

public interface EmployeeManagementService {

	public Employee createOrUpdateEmployee( Employee employee );
	
	public Boolean removeEmployee(Integer employeeId);
	
	public Employee findByEmployeeNumber(String empNum);
	
	public Employee findByEmployeeId(Integer id);

}
