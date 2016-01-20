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

import com.razorthink.rzt.internal.management.domain.Employee;
import com.razorthink.rzt.internal.management.domain.TinyEmployee;
import com.razorthink.rzt.internal.management.employee.service.EmployeeManagementService;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.utils.Response;

@RestController
@RequestMapping("/employee")
@Component
public class EmployeeRestService {

	private static final Logger log = Logger.getLogger(EmployeeRestService.class);
	@Autowired
	private EmployeeManagementService employeeManagementService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/createOrUpdate", method = RequestMethod.POST)
	public ResponseEntity<Response> createOrUpdateEmployee(@RequestBody Employee employeeUI) {
		// check mandatory fields (validate())
		Response response = new Response();
		// if creating first time
		if (employeeUI.getId() == null) {
			try {
				employeeManagementService.findByEmployeeNumber(employeeUI.getEmployeeNum());
				response.setErrorMessage("Employee with \"" + employeeUI.getEmployeeNum()
						+ "\" employee number already exists in the system ");
				response.setErrorCode("im.error");
				response.setObject(null);
				log.error(response.getErrorMessage());
				return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (DataException e) {
				// do nothing
			}
		} else {
			Employee employee = employeeManagementService.findByEmployeeId(employeeUI.getId());
			if (!employee.getEmployeeNum().equalsIgnoreCase(employeeUI.getEmployeeNum())) {
				try {
					employeeManagementService.findByEmployeeNumber(employeeUI.getEmployeeNum());
					response.setErrorMessage("Employee with \"" + employeeUI.getEmployeeNum()
							+ "\" employee number already exists in the system ");
					response.setErrorCode("im.error");
					response.setObject(null);
					log.error(response.getErrorMessage());
					return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
				} catch (DataException e) {
					// do nothing
				}
			}
		}

		try {
			Employee emp = employeeManagementService.createOrUpdateEmployee(employeeUI);
			response.setObject(emp);
			response.setErrorCode(null);
			response.setErrorMessage(null);
			return new ResponseEntity(response, HttpStatus.OK);

		} catch (DataException e) {
			response.setErrorMessage(e.getErrorMessage());
			response.setErrorCode(e.getErrorCode());
			response.setObject(null);
			log.error(e.getErrorMessage());
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GET
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Path("/removeEmployee")
	public ResponseEntity<Response> removeEmployee(@NotNull(message = "Cannot be null") @QueryParam("id") Integer id) {
		Response response = new Response();
		try {
			Boolean status = employeeManagementService.removeEmployee(id);
			if (status) {
				response.setObject(null);
				response.setErrorCode(null);
				response.setErrorMessage(null);
				return new ResponseEntity(response, HttpStatus.OK);
			} else {
				response.setObject(null);
				response.setErrorCode("data.error");
				response.setErrorMessage("Could not delete Employee entity");
				log.error("Could not delete Employee entity");
				return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (DataException e) {
			response.setErrorMessage(e.getErrorMessage());
			response.setErrorCode(e.getErrorCode());
			response.setObject(null);
			log.error(e.getErrorMessage());
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GET
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/findAllEmployees", method = RequestMethod.GET)
	public ResponseEntity<Response> findAllEmployeesMin() {
		Response response = new Response();
		List<Employee> employess = new ArrayList<>();
		try {
			employess = employeeManagementService.getAllEmployees();
			response.setObject(employess);
			response.setErrorCode(null);
			response.setErrorMessage(null);
			return new ResponseEntity(response, HttpStatus.OK);
		} catch (DataException e) {
			response.setErrorMessage(e.getErrorMessage());
			response.setErrorCode(e.getErrorCode());
			response.setObject(null);
			log.error(e.getErrorMessage());
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GET
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/findAllEmployeesMin", method = RequestMethod.GET)
	public List<TinyEmployee> findAllEmployees() {
		List<TinyEmployee> employees = new ArrayList<>();
		try {
			employees = employeeManagementService.getAllEmployeesMin();
			return employees;
		} catch (DataException e) {
			log.error(e.getErrorMessage());
			return null;
		} 
	}
}
