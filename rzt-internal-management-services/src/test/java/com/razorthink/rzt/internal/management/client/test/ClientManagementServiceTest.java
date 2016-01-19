package com.razorthink.rzt.internal.management.client.test;

import java.io.Serializable;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.razorthink.rzt.internal.management.client.service.ClientManagementService;
import com.razorthink.rzt.internal.management.domain.Address;
import com.razorthink.rzt.internal.management.domain.Client;
import com.razorthink.rzt.internal.management.test.TestConfig;
import com.razorthink.utils.spring.repo.GenericRepository;

public class ClientManagementServiceTest  extends JerseyTest{

	@Autowired
	private ClientManagementService clientManagementService;

	@Autowired
	private GenericRepository<Address, Serializable> repo;

	Address clientAddress = new Address();
	Client client = new Client();

	public void setValues()
	{
		clientAddress.setAddressLine1("House name");
		clientAddress.setAddressLine2("block");
		clientAddress.setCity("Bengaluru");
		clientAddress.setCountry("India");
		clientAddress.setPostalCode("");
		clientAddress.setPostalCode("560700");
		clientAddress.setState("Karnataka");
		Address savedAddress = repo.save(clientAddress);
		client.setClientAddress(savedAddress);
		client.setClientName("Arun");

	}

	@Override
	protected Application configure()
	{

		ApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
		return new JerseyConfig().property("contextConfig", context);
	}

	@Test
	public void createOrUpdateClientTest()
	{
		setValues();
		Client newClient = clientManagementService.createOrUpdateClient(client);
		System.out.println(newClient);
	}
}
