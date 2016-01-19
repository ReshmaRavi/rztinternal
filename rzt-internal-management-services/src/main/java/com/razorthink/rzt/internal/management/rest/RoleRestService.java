package com.razorthink.rzt.internal.management.rest;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import com.razorthink.rzt.internal.management.domain.EmployeeRole;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.role.service.RoleManagementService;
import com.razorthink.rzt.internal.management.utils.Response;

@RestController
@Path( "/role" )
@Component
public class RoleRestService {

	private static final Logger log = Logger.getLogger(RoleRestService.class);
	@Autowired
	private RoleManagementService roleManagementService;

	@POST
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Path( "/createOrUpdate" )
	public ResponseEntity<Response> createOrUpdateRole( @NotNull EmployeeRole RoleUI )
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

	@GET
	@SuppressWarnings( { "rawtypes", "unchecked" } )
	@Path( "/removeRole" )
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
