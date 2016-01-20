package com.razorthink.rzt.internal.management.client.service;

import java.util.List;

import com.razorthink.rzt.internal.management.domain.Client;

public interface ClientManagementService {

	public Client createOrUpdateClient( Client Client );

	public Boolean removeClient( Integer ClientId );
	
	public Client findByName(String clientName);

	public Client findByClientId( Integer id );
	
	public List<Client> getAllClients();

}
