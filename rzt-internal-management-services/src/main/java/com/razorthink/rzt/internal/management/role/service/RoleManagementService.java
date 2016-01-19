package com.razorthink.rzt.internal.management.role.service;

import java.util.List;
import com.razorthink.rzt.internal.management.domain.EmployeeRole;

public interface RoleManagementService {

	public EmployeeRole createOrUpdateRole( EmployeeRole role );

	public Boolean removeRole( Integer roleId );

	public List<EmployeeRole> getAllRoles();
	
	public EmployeeRole findByName(String name);
	
	public EmployeeRole findById(Integer id);
}
