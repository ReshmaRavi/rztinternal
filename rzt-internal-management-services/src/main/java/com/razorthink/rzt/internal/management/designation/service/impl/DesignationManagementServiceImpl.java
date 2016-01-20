package com.razorthink.rzt.internal.management.designation.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razorthink.rzt.internal.management.designation.service.DesignationManagementService;
import com.razorthink.rzt.internal.management.designation.service.repository.DesignationManagementRepository;
import com.razorthink.rzt.internal.management.domain.Designation;
import com.razorthink.rzt.internal.management.exception.DataException;

@Service
@Transactional( rollbackFor = Exception.class )
public class DesignationManagementServiceImpl implements DesignationManagementService {
	@Autowired
	private DesignationManagementRepository designationManagementRepo;

	@Override
	public Designation createOrUpdateDesignation( Designation designation )
	{
		designation.setIsActive(true);
		Designation designationEntity = designationManagementRepo.save(designation);
		if( designationEntity == null )
			throw new DataException("data.error", "Could not save designation entity");
		return designationEntity;
	}

	@Override
	public Boolean removeDesignation( Integer designationId )
	{
		Designation designation = designationManagementRepo.findOne(designationId);
		if( designation == null )
		{
			throw new DataException("data.error", "Could not find designation entity with id: " + designationId);
		}
		designation.setIsActive(false);
		Designation designationEntity = designationManagementRepo.save(designation);
		if( designationEntity == null )
			return false;
		return true;
	}

	@Override
	public List<Designation> getAllDesignations()
	{
		return designationManagementRepo.findByNamedQuery("Designation.findAllDesignation");
	}

	@Override
	public Designation findByName( String name )
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		try
		{
		Designation designation = designationManagementRepo.findOneByNamedQueryAndParams("Designation.findByName",
				params);
		return designation;
		}catch(Exception e)
		{
			throw new DataException("data.error", "Could not find designation entity with name: " + name);
		}
	}

	@Override
	public Designation findById( Integer id )
	{
		Designation designation = designationManagementRepo.findOne(id);
		if( designation == null )
			throw new DataException("data.error", "Could not find designation entity with id: " + id);
		return designation;

	}

}
