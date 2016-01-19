package com.razorthink.rzt.internal.management.role.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.razorthink.rzt.internal.management.domain.EmployeeRole;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.role.service.RoleManagementService;
import com.razorthink.rzt.internal.management.role.service.repo.RoleManagementRepository;

@Service
@Transactional( rollbackFor = Exception.class )
public class RoleManagementServiceImpl implements RoleManagementService {
	
	@Autowired
	private RoleManagementRepository roleManagementRepo;

	@Override
	public EmployeeRole createOrUpdateRole( EmployeeRole role )
	{
		EmployeeRole roleEntity = roleManagementRepo.save(role);
		if( roleEntity == null )
			throw new DataException("data.error", "Could not save role entity");
		return roleEntity;
	}

	@Override
	public Boolean removeRole( Integer roleId )
	{
		EmployeeRole role = roleManagementRepo.findOne(roleId);
		if( role == null )
		{
			throw new DataException("data.error", "Could not find role entity with id: " + roleId);
		}
		role.setIsActive(false);
		EmployeeRole roleEntity = roleManagementRepo.save(role);
		if( roleEntity == null )
			return false;
		return true;
	}

	@Override
	public List<EmployeeRole> getAllRoles()
	{
		return roleManagementRepo.findByNamedQuery("EmployeeRole.findAllRole");
	}

	@Override
	public EmployeeRole findByName( String name )
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		EmployeeRole role = roleManagementRepo.findOneByNamedQueryAndParams("EmployeeRole.findByName", params);
		if( role == null )
			throw new DataException("data.error", "Could not find role entity with name: " + name);
		return role;
	}

	@Override
	public EmployeeRole findById( Integer id )
	{
		EmployeeRole role = roleManagementRepo.findOne(id);
		if( role == null )
			throw new DataException("data.error", "Could not find role entity with id: " + id);
		return role;
	}

}
