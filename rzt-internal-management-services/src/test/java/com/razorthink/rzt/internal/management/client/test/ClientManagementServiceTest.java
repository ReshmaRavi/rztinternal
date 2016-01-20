package com.razorthink.rzt.internal.management.client.test;

import org.glassfish.jersey.test.JerseyTest;

public class ClientManagementServiceTest  extends JerseyTest{

/*	@Autowired
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
	}*/
}
