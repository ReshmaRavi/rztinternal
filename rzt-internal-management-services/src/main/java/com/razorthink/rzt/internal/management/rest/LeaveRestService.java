package com.razorthink.rzt.internal.management.rest;

import java.util.List;
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

import com.razorthink.rzt.internal.management.domain.LeaveRecords;
import com.razorthink.rzt.internal.management.employee.service.EmployeeManagementService;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.leave.service.LeaveManagementService;
import com.razorthink.rzt.internal.management.utils.Response;
@RestController
@Path( "/leave" )
@Component
public class LeaveRestService {

	private static final Logger log = Logger.getLogger(LeaveRestService.class);
	@Autowired
	private LeaveManagementService leaveManagementService;

	@Autowired
	private EmployeeManagementService employeeManagementService;

	@POST
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Path( "/createOrUpdateLeaveRecords" )
	public ResponseEntity<Response> createOrUpdateLeaveRecords( @NotNull LeaveRecords leaveUI )
	{
		// check mandatory fields (validate())
		Response response = new Response();
		// if creating first time
		try
		{
			LeaveRecords leaveEntity = leaveManagementService.createOrUpdateLeave(leaveUI);
			response.setObject(leaveEntity);
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
	@Path( "/removeLeaveRecords" )
	public ResponseEntity<Response> removeLeaveRecords(
			@NotNull( message = "Cannot be null" ) @QueryParam( "id" ) Integer id)
	{
		Response response = new Response();
		try
		{
			Boolean status = leaveManagementService.removeLeave(id);
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
				response.setErrorMessage("Could not delete LeaveRecords entity");
				log.error("Could not delete LeaveRecords entity");
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

	@GET
	@Path( "/findLeavesByEmpId" )
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public ResponseEntity<Response> findAllLeavesForAnEmployee(
			@NotNull( message = "can not be null" ) @QueryParam( "id" ) Integer id)
	{
		Response response = new Response();
		try
		{
			employeeManagementService.findByEmployeeId(id);
		}
		catch( DataException e )
		{
			response.setObject(null);
			response.setErrorCode(e.getErrorCode());
			response.setErrorMessage(e.getErrorMessage());
			log.error(e.getErrorMessage());
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<LeaveRecords> leaves = leaveManagementService.getAllLeaveByEmpId(id);
		response.setObject(leaves);
		response.setErrorCode(null);
		response.setErrorMessage(null);
		return new ResponseEntity(response, HttpStatus.OK);
	}

	@GET
	@Path( "/findLeavesByEmpIdAndLeaveType" )
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public ResponseEntity<Response> findAllLeavesByEmpIdAndLeaveType(
			@NotNull( message = "can not be null" ) @QueryParam( "id" ) Integer id,
			@NotNull( message = "can not be null" ) @QueryParam( "leaveType" ) String leaveType)
	{
		Response response = new Response();
		try
		{
			employeeManagementService.findByEmployeeId(id);
		}
		catch( DataException e )
		{
			response.setObject(null);
			response.setErrorCode(e.getErrorCode());
			response.setErrorMessage(e.getErrorMessage());
			log.error(e.getErrorMessage());
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<LeaveRecords> leaves = leaveManagementService.getAllLeaveByEmpIdAndLeaveType(id, leaveType);
		response.setObject(leaves);
		response.setErrorCode(null);
		response.setErrorMessage(null);
		return new ResponseEntity(response, HttpStatus.OK);
	}
}
