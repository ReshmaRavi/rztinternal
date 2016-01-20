package com.razorthink.rzt.internal.management.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.razorthink.rzt.internal.management.domain.Employee;
import com.razorthink.rzt.internal.management.domain.Users;
import com.razorthink.rzt.internal.management.employee.service.EmployeeManagementService;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.user.service.UserManagementService;
import com.razorthink.rzt.internal.management.utils.Response;

@RestController
@RequestMapping("/users")
@Component
public class UserRestService {
	
	private static final Logger log = Logger.getLogger(UserRestService.class);
	@Autowired
	UserManagementService userManagementService;
	
	@Autowired
	EmployeeManagementService employeeManagementService;
	
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
	@RequestMapping(value="/createOrUpdate" ,method=RequestMethod.POST)
	public ResponseEntity<Response> createOrUpdateUsers( @RequestBody Users usersUI )
	{
		// check mandatory fields (validate())
		Response response = new Response();
		// if creating first time
		if( usersUI.getId() == null )
		{
			try
			{
				Employee emp=employeeManagementService.findByEmployeeNumber(usersUI.getEmployeeId().toString());
				userManagementService.findByEmployeeId(emp.getId());
				response.setErrorMessage(
						"User with Employee Id \"" +usersUI.getEmployeeId() + "\"  already exists in the system ");
				response.setErrorCode("im.error");
				response.setObject(null);
				log.error(response.getErrorMessage());
				return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			catch( DataException e )
			{
				// do nothing
			}
		}

		try
		{
			Employee emp=employeeManagementService.findByEmployeeNumber(usersUI.getEmployeeId().toString());
			usersUI.setEmployeeId(emp.getId());
			Users user = userManagementService.createOrUpdateUser(usersUI);
			response.setObject(user);
			response.setErrorCode(null);
			response.setErrorMessage(null);
			return new ResponseEntity(response, HttpStatus.OK);

		}
		catch( DataException e )
		{
			response.setErrorMessage(e.getErrorMessage());
			response.setErrorCode(e.getErrorCode());
			response.setObject(null);
			log.error(e.getErrorMessage());
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
