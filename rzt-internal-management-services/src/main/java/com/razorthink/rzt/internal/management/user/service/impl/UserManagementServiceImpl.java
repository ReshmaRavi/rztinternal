package com.razorthink.rzt.internal.management.user.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razorthink.rzt.internal.management.domain.Users;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.user.service.UserManagementService;
import com.razorthink.rzt.internal.management.user.service.repo.UserManagementRepository;
import com.razorthink.rzt.internal.management.utils.MD5Utils;
import com.razorthink.utils.aes.AesEncryption;
import com.razorthink.utils.aes.AesEncryptionException;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserManagementServiceImpl implements UserManagementService {

	@Autowired
	private UserManagementRepository userManagementRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.razorthink.rzt.internal.management.user.service.impl.
	 * UserManagementService#createOrUpdate(com.razorthink.rzt.internal.
	 * management.domain.Users)
	 */
	@Override
	public Users createOrUpdateUser(Users user) {
		System.out.println("\n user=="+user);
		user.setIsActive(true);
		System.out.println("\npassword=="+user.getPassword());
		try {
			user.setPassword(MD5Utils.md5String(user.getPassword()));
		} catch (Exception e) {
			throw new DataException("data.error", "Could not save user entity");
		}
		Users newUser = userManagementRepository.save(user);
		if (newUser == null)
			throw new DataException("data.error", "Could not save user entity");
		return newUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.razorthink.rzt.internal.management.user.service.impl.
	 * UserManagementService#removeUser(java.lang.Integer)
	 */
	@Override
	public Boolean removeUser(Integer userId) {
		Users user = userManagementRepository.findOne(userId);
		if (user == null)
			throw new DataException("data.error", "Could not find user entity");
		user.setIsActive(false);
		Users newUser = userManagementRepository.save(user);
		if (newUser == null)
			return false;
		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.razorthink.rzt.internal.management.user.service.impl.
	 * UserManagementService#findByEmailId(java.lang.String)
	 */
	@Override
	public Users findByEmailId(String email) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		try {
			Users newUser = userManagementRepository.findOneByNamedQueryAndParams("Users.findByEmailId", params);
			return newUser;
		} catch (Exception e) {
			throw new DataException("data.error", "Could not find user entity");

		}
	}

	@Override
	public Users findByEmployeeId(Integer employeeId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("employeeId", employeeId);
		try {
			Users newUser = userManagementRepository.findOneByNamedQueryAndParams("Users.findByEmployeeId", params);
			return newUser;
		} catch (Exception e) {
			throw new DataException("data.error", "Could not find user entity");

		}

	}

	@Override
	public Users findByUserNameAndPassword(String username, String password) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("password", password);
		try {
			Users newUser = userManagementRepository.findOneByNamedQueryAndParams("Users.findByUserNameAndPassword", params);
			return newUser;
		} catch (Exception e) {
			throw new DataException("data.error", "Could not find user entity");

		}
	}

}
