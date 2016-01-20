package com.razorthink.rzt.internal.management.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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

import com.razorthink.rzt.internal.management.domain.Project;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.project.service.ProjectManagementService;
import com.razorthink.rzt.internal.management.utils.Response;
@RestController
@RequestMapping( "/project" )
@Component
public class ProjectRestService {

	private static final Logger log = Logger.getLogger(ProjectRestService.class);
	@Autowired
	private ProjectManagementService projectManagementService;

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
	@RequestMapping(value="/createOrUpdateProject" ,method=RequestMethod.POST)
	public ResponseEntity<Response> createOrUpdateProject( @RequestBody Project projectUI )
	{
		// check mandatory fields (validate())
		Response response = new Response();
		// if creating first time
		if( projectUI.getId() == null )
		{
			try
			{
				projectManagementService.findProjectByName(projectUI.getName());
				response.setErrorMessage(
						"Project with \"" + projectUI.getName() + "\" project name already exists in the system ");
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
			Project project = projectManagementService.findProjectById(projectUI.getId());
			if( !project.getName().equalsIgnoreCase(projectUI.getName()) )
			{
				try
				{
					projectManagementService.findProjectByName(projectUI.getName());
					response.setErrorMessage(
							"Project with \"" + projectUI.getName() + "\" project name already exists in the system ");
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
			Project projectEntity = projectManagementService.createOrUpdateProject(projectUI);
			response.setObject(projectEntity);
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
	@Path( "/removeProject" )
	public ResponseEntity<Response> removeProject(
			@NotNull( message = "Cannot be null" ) @QueryParam( "id" ) Integer id)
	{
		Response response = new Response();
		try
		{
			Boolean status = projectManagementService.removeProject(id);
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
				response.setErrorMessage("Could not delete Project entity");
				log.error("Could not delete Project entity");
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GET
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
	@RequestMapping(value="/findAllProjects" ,method=RequestMethod.GET)
	public ResponseEntity<Response> findAllProjects()
	{
		Response response = new Response();
		List<Project> projects= new ArrayList<>();
		try{
			projects=projectManagementService.getAllProjects();
			response.setObject(projects);
			response.setErrorCode(null);
			response.setErrorMessage(null);
			return new ResponseEntity(response, HttpStatus.OK);
		}
		catch(DataException e){
			response.setErrorMessage(e.getErrorMessage());
			response.setErrorCode(e.getErrorCode());
			response.setObject(null);
			log.error(e.getErrorMessage());
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
