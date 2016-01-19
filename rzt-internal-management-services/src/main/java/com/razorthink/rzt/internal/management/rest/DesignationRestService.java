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
import com.razorthink.rzt.internal.management.designation.service.DesignationManagementService;
import com.razorthink.rzt.internal.management.domain.Designation;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.utils.Response;

@RestController
@Path( "/designation" )
@Component
public class DesignationRestService {

	private static final Logger log = Logger.getLogger(DesignationRestService.class);
	@Autowired
	private DesignationManagementService designationManagementService;

	@POST
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Path( "/createOrUpdate" )
	public ResponseEntity<Response> createOrUpdateDesignation( @NotNull Designation DesignationUI )
	{
		// check mandatory fields (validate())
		Response response = new Response();
		// if creating first time
		if( DesignationUI.getId() == null )
		{
			try
			{
				designationManagementService.findByName(DesignationUI.getName());
				response.setErrorMessage("Designation with \"" + DesignationUI.getName()
						+ "\" Designation name already exists in the system ");
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
			Designation Designation = designationManagementService.findById(DesignationUI.getId());
			if( !Designation.getName().equalsIgnoreCase(DesignationUI.getName()) )  /* Name Case Insensitive*/
			{
				try
				{
					designationManagementService.findByName(DesignationUI.getName());
					response.setErrorMessage(
							"Designation with \"" + DesignationUI.getName() + "\" Designation name already exists in the system ");
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
			Designation designation = designationManagementService.createOrUpdateDesignation(DesignationUI);
			response.setObject(designation);
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
	@GET
	@Path( "/removeDesignation" )
	public ResponseEntity<Response> removeDesignation(
			@NotNull( message = "Cannot be null" ) @QueryParam( "id" ) Integer id)
	{
		Response response = new Response();
		try
		{
			Boolean status = designationManagementService.removeDesignation(id);
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
				response.setErrorMessage("Could not delete Designation entity");
				log.error("Could not delete Designation entity");
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
