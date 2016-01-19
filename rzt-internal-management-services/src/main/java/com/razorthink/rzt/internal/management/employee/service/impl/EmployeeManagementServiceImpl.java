package com.razorthink.rzt.internal.management.employee.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razorthink.rzt.internal.management.domain.Employee;
import com.razorthink.rzt.internal.management.employee.service.EmployeeManagementService;
import com.razorthink.rzt.internal.management.employee.service.repo.EmployeeManagementRepository;
import com.razorthink.rzt.internal.management.exception.DataException;

@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeManagementServiceImpl implements EmployeeManagementService {
	@Autowired
	private EmployeeManagementRepository empRepo;

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
		Employee employee = empRepo.findOneByNamedQueryAndParams("Employee.findByEmpNum", params);
		if (employee == null) {
			throw new DataException("data.error", "Entity not found for employee num " + empNum);
		}
		return employee;
	}

	@Override
	public Employee findByEmployeeId(Integer id) {
		Employee employee = empRepo.findOne(id);
		if (employee == null) {
			throw new DataException("data.error", "Entity not found for employee id " + id);
		}
		return employee;

	}

}
