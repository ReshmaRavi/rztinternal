package com.razorthink.rzt.internal.management.user.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razorthink.rzt.internal.management.domain.AggregateUsers;
import com.razorthink.rzt.internal.management.domain.Users;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.user.service.UserManagementService;
import com.razorthink.rzt.internal.management.user.service.repo.UserManagementRepository;
import com.razorthink.rzt.internal.management.utils.AggregateUserRepo;
import com.razorthink.rzt.internal.management.utils.MD5Utils;
import com.razorthink.utils.spring.repo.GenericRepository;
import com.razorthink.utils.spring.repo.GenericRepositoryFactoryBean;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserManagementServiceImpl implements UserManagementService
{

    @Autowired
    private UserManagementRepository userManagementRepository;
    @Autowired
    private AggregateUserRepo userRepo;

    /*
     * (non-Javadoc)
     * 
     * @see com.razorthink.rzt.internal.management.user.service.impl.
     * UserManagementService#createOrUpdate(com.razorthink.rzt.internal.
     * management.domain.Users)
     */
    @Override
    public Users createOrUpdateUser(Users user)
    {
	System.out.println("\n user==" + user);
	user.setIsActive(true);
	System.out.println("\npassword==" + user.getPassword());
	try
	{
	    user.setPassword(MD5Utils.md5String(user.getPassword()));
	} catch (Exception e)
	{
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
    public Boolean removeUser(Integer employeeId)
    {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("employeeId", employeeId);
	try
	{
	    Users user = userManagementRepository.findOneByNamedQueryAndParams("Users.findByEmployeeId", params);
	    user.setIsActive(false);
	    Users newUser = userManagementRepository.save(user);
	    if (newUser == null)
		return false;
	    return true;
	} catch (Exception e)
	{
	    throw new DataException("data.error", "Could not find user entity");
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.razorthink.rzt.internal.management.user.service.impl.
     * UserManagementService#findByEmailId(java.lang.String)
     */
    @Override
    public Users findByEmailId(String email)
    {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("email", email);
	try
	{
	    Users newUser = userManagementRepository.findOneByNamedQueryAndParams("Users.findByEmailId", params);
	    return newUser;
	} catch (Exception e)
	{
	    throw new DataException("data.error", "Could not find user entity");

	}
    }

    @Override
    public Users findByEmployeeId(Integer employeeId)
    {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("employeeId", employeeId);
	try
	{
	    Users newUser = userManagementRepository.findOneByNamedQueryAndParams("Users.findByEmployeeId", params);
	    return newUser;
	} catch (Exception e)
	{
	    throw new DataException("data.error", "Could not find user entity");

	}

    }

    @Override
    public Users findByUserNameAndPassword(String username, String password)
    {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("username", username);
	params.put("password", password);
	try
	{
	    Users newUser = userManagementRepository.findOneByNamedQueryAndParams("Users.findByUserNameAndPassword", params);
	    return newUser;
	} catch (Exception e)
	{
	    throw new DataException("data.error", "Could not find user entity");

	}
    }

    @Override
    public List<AggregateUsers> findAllUsers()
    {
	List<AggregateUsers> users = new ArrayList<>();
	users = userRepo.findByNativeQuery(
		"select u.u_id,u.u_username,e.emp_eno,e.emp_first_name,e.emp_last_name,u.u_is_admin from im_users u,im_employees e where u.u_emp_id=e.emp_id and u.u_is_active=true");
	return users;
    }

}
