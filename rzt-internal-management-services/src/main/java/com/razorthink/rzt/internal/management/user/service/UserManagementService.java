package com.razorthink.rzt.internal.management.user.service;

import java.util.List;

import com.razorthink.rzt.internal.management.domain.AggregateUsers;
import com.razorthink.rzt.internal.management.domain.Users;

public interface UserManagementService {

	Users createOrUpdateUser(Users user);

	Boolean removeUser(Integer userId);

	Users findByEmailId(String email);
	
	Users findByEmployeeId(Integer employeeId);
	
	Users findByUserNameAndPassword(String username,String password);

	List<AggregateUsers> findAllUsers();

}