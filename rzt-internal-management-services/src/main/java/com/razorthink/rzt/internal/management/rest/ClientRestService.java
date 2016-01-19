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
import com.razorthink.rzt.internal.management.client.service.ClientManagementService;
import com.razorthink.rzt.internal.management.domain.Client;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.utils.Response;

@RestController
@Path( "/client" )
@Component
public class ClientRestService {

	private static final Logger log = Logger.getLogger(ClientRestService.class);
	@Autowired
	private ClientManagementService clientManagementService;

	@POST
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Path( "/createOrUpdate" )
	public ResponseEntity<Response> createOrUpdateClient( @NotNull Client ClientUI )
	{
		// check mandatory fields (validate())
		Response response = new Response();
		// if creating first time
		if( ClientUI.getId() == null )
		{
			try
			{
				clientManagementService.findByName(ClientUI.getClientName());
				response.setErrorMessage(
						"Client with \"" + ClientUI.getClientName() + "\" Client name already exists in the system ");
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
			Client Client = clientManagementService.findByClientId(ClientUI.getId());
			if( !Client.getClientName().equalsIgnoreCase(ClientUI.getClientName()) ) // Name
																						// Case
																						// Insensitive
			{
				try
				{
					clientManagementService.findByName(ClientUI.getClientName());
					response.setErrorMessage("Client with \"" + ClientUI.getClientName()
							+ "\" Client name already exists in the system ");
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
			Client client = clientManagementService.createOrUpdateClient(ClientUI);
			response.setObject(client);
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
	@Path( "/removeClient" )
	public ResponseEntity<Response> removeClient( @NotNull( message = "Cannot be null" ) @QueryParam( "id" ) Integer id)
	{
		Response response = new Response();
		try
		{
			Boolean status = clientManagementService.removeClient(id);
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
				response.setErrorMessage("Could not delete Client entity");
				log.error("Could not delete Client entity");
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
