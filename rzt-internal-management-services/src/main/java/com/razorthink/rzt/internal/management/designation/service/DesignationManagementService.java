package com.razorthink.rzt.internal.management.designation.service;

import java.util.List;
import com.razorthink.rzt.internal.management.domain.Designation;

public interface DesignationManagementService {

	public Designation createOrUpdateDesignation( Designation designation );

	public Boolean removeDesignation( Integer designationId );

	public List<Designation> getAllDesignations();
	
	public Designation findByName(String name);

	public Designation findById( Integer id );

}
