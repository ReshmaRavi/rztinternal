package com.razorthink.rzt.internal.management.client.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razorthink.rzt.internal.management.client.service.ClientManagementService;
import com.razorthink.rzt.internal.management.client.service.repo.ClientManagementRepository;
import com.razorthink.rzt.internal.management.domain.Client;
import com.razorthink.rzt.internal.management.exception.DataException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ClientManagementServiceImpl implements ClientManagementService {

	@Autowired
	private ClientManagementRepository clientRepo;
	

	@Override
	public Client createOrUpdateClient(Client client) {
		client.setIsActive(true);
		Client clnt = clientRepo.save(client);
		if (clnt == null)
			throw new DataException("data.error", "Could not save Client entity");
		return clnt;
	}

	@Override
	public Boolean removeClient(Integer id) {
		Client client = clientRepo.findOne(id);
		if (client == null)
			throw new DataException("data.error", "Could not find Client entity for id :" + id);
		client.setIsActive(false);
		Client clnt = clientRepo.save(client);
		if (clnt == null)
			return false;
		return true;
	}

	@Override
	public Client findByClientId(Integer id) {
		try {
			Client client = clientRepo.findOne(id);
			return client;

		} catch (Exception e) {
			throw new DataException("data.error", "Entity not found for Client id " + id);
		}
	}

	@Override
	public Client findByName(String clientName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientName", clientName);
		try {
			Client client=clientRepo.findOneByNamedQueryAndParams("Client.findByClientName", params);
			return client;
		} catch (Exception e) {
			throw new DataException("data.error", "Entity not found for Client name : " + clientName);

		}
	}

	@Override
	public List<Client> getAllClients() {
		
		return clientRepo.findByNamedQuery("Client.findAllClients");
	}

}
