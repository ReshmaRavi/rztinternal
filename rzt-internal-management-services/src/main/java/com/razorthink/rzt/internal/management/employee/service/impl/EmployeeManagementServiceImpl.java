package com.razorthink.rzt.internal.management.employee.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razorthink.rzt.internal.management.domain.Employee;
import com.razorthink.rzt.internal.management.domain.TinyEmployee;
import com.razorthink.rzt.internal.management.employee.service.EmployeeManagementService;
import com.razorthink.rzt.internal.management.employee.service.repo.EmployeeManagementRepository;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.utils.GenericRepo;

@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeManagementServiceImpl implements EmployeeManagementService {
	@Autowired
	private EmployeeManagementRepository empRepo;
	
	@Autowired
	private GenericRepo genericRepo;


	@Override
	public Employee createOrUpdateEmployee(Employee employee) {
		try {
			employee.setIsActive(true);
			Employee emp = empRepo.save(employee);
			return emp;
		} catch (Exception e) {
			throw new DataException("data.error", "Could not save employee entity");

		}
	}

	@Override
	public Boolean removeEmployee(Integer id) {
		Employee employee = empRepo.findOne(id);
		if (employee == null)
			throw new DataException("data.error", "Could not find employee entity for id :" + id);
		employee.setIsActive(false);
		Employee emp = empRepo.save(employee);
		if (emp == null)
			return false;
		return true;
	}

	@Override
	public Employee findByEmployeeNumber(String empNum) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("empNum", empNum);
		try {
			Employee employee = empRepo.findOneByNamedQueryAndParams("Employee.findByEmpNum", params);
			return employee;
		} catch (Exception e) {
			throw new DataException("data.error", "Entity not found for employee num " + empNum);
		}

	}

	@Override
	public Employee findByEmployeeId(Integer id) {
		Employee employee = empRepo.findOne(id);
		if (employee == null) {
			throw new DataException("data.error", "Entity not found for employee id " + id);
		}
		return employee;

	}

	@Override
	public List<Employee> getAllEmployees() {
		return empRepo.findByNamedQuery("Employee.findAllEmployees");
	}

	@Override
	public List<TinyEmployee> getAllEmployeesMin() {
		List<TinyEmployee> employees = new ArrayList<>();
		employees=genericRepo.findByNativeQuery("select emp_id,emp_eno,emp_first_name,emp_last_name from im_employees where emp_is_active=true");
		return employees;
	}

}
