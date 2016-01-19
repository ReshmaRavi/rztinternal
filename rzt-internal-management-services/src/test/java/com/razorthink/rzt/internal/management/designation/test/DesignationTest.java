package com.razorthink.rzt.internal.management.designation.test;

import java.util.Calendar;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import com.razorthink.rzt.internal.management.designation.service.DesignationManagementService;
import com.razorthink.rzt.internal.management.domain.Designation;
import com.razorthink.rzt.internal.management.test.TestApplication;

@SpringApplicationConfiguration( classes = TestApplication.class )
public class DesignationTest extends JerseyTest {

	@Autowired
	private DesignationManagementService designationManagementService;

	@Test
	public void createOrUpdateDesignationTest()
	{
		Designation desgn = new Designation();
		desgn.setCreatedAt(Calendar.getInstance());
		desgn.setName("Software engineer");
		Designation savedDesignation = designationManagementService.createOrUpdateDesignation(desgn);
		System.out.println(savedDesignation);
	}
}
