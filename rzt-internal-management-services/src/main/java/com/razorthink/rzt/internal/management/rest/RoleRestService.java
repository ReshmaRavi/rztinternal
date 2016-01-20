package com.razorthink.rzt.internal.management.rest;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

import com.razorthink.rzt.internal.management.domain.EmployeeRole;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.role.service.RoleManagementService;
import com.razorthink.rzt.internal.management.utils.Response;

@RestController
@RequestMapping("/role")
@Component
public class RoleRestService {

	private static final Logger log = Logger.getLogger(RoleRestService.class);
	@Autowired
	private RoleManagementService roleManagementService;

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
	@RequestMapping(value="/createOrUpdate" ,method=RequestMethod.POST)
	public ResponseEntity<Response> createOrUpdateRole(@RequestBody EmployeeRole RoleUI )
	{
		// check mandatory fields (validate())
		Response response = new Response();
		// if creating first time
		if( RoleUI.getId() == null )
		{
			try
			{
				roleManagementService.findByName(RoleUI.getName());
				response.setErrorMessage(
						"EmployeeRole with \"" + RoleUI.getName() + "\" EmployeeRole name already exists in the system ");
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
		else
		{
			EmployeeRole role = roleManagementService.findById(RoleUI.getId());
			if( !role.getName().equalsIgnoreCase(RoleUI.getName()) )  /* Name Case Insensitive*/
			{
				try
				{
					roleManagementService.findByName(RoleUI.getName());
					response.setErrorMessage(
							"EmployeeRole with \"" + RoleUI.getName() + "\" EmployeeRole name already exists in the system ");
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
		}
		try
		{
			EmployeeRole role = roleManagementService.createOrUpdateRole(RoleUI);
			response.setObject(role);
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

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
	@RequestMapping(value="/removeRole",method=RequestMethod.GET)
	public ResponseEntity<Response> removeRole( @NotNull( message = "Cannot be null" ) @QueryParam( "id" ) Integer id)
	{
		Response response = new Response();
		try
		{
			Boolean status = roleManagementService.removeRole(id);
			if( status )
			{
				response.setObject(null);
				response.setErrorCode(null);
				response.setErrorMessage(null);
				return new ResponseEntity(response, HttpStatus.OK);
			}
			else
			{
				response.setObject(null);
				response.setErrorCode("data.error");
				response.setErrorMessage("Could not delete EmployeeRole entity");
				log.error("Could not delete EmployeeRole entity");
				return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
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
