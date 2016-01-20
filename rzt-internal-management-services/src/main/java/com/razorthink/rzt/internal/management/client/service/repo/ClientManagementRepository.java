package com.razorthink.rzt.internal.management.client.service.repo;

import java.io.Serializable;

import com.razorthink.rzt.internal.management.domain.Client;
import com.razorthink.utils.spring.repo.GenericRepository;

public interface ClientManagementRepository extends GenericRepository<Client, Serializable> {
	
}
