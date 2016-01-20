/**
 * 
 */
package com.razorthink.rzt.internal.management.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorthink.rzt.internal.management.domain.Employee;
import com.razorthink.rzt.internal.management.domain.Users;
import com.razorthink.rzt.internal.management.employee.service.EmployeeManagementService;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.user.service.UserManagementService;
import com.razorthink.rzt.internal.management.utils.MD5Utils;
import com.razorthink.rzt.internal.management.utils.StringUtils;
import com.razorthink.utils.aes.AesEncryption;

public class LoginFilter implements Filter {

	private static final Logger log = Logger.getLogger(LoginFilter.class);
	private UserManagementService userManagementService;
	private ObjectMapper objectMapper;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private EmployeeManagementService employeeManagementService;

	@Override
	public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain ) throws IOException,
			ServletException
	{
		System.out.println("IN Login Filter");
		this.request = (HttpServletRequest) req;
		this.response = (HttpServletResponse) res;

		log.debug("Processing doFilter" + userManagementService + "password is "
				+ (null != request.getParameter("password") ? request.getParameter("password") : ""));

		// pull the username and password
		if( StringUtils.isNullOrEmpty(request.getParameter("username"))
				|| StringUtils.isNullOrEmpty(request.getParameter("password")) )
		{
			response.sendRedirect("/");
			log.debug("Finished processing doFilter");
			return;
		}

		// else check validate user exists in the system
		try
		{
			System.out.println("\nPassword== "+request.getParameter("password"));
			System.out.println("Encrypted password=="+MD5Utils.md5String(request.getParameter("password")));
			Users user = userManagementService.findByUserNameAndPassword(request.getParameter("username"),
					MD5Utils.md5String(request.getParameter("password")));
			System.out.println("user.getEmployeeId().toString()==="+user.getEmployeeId().toString());
			Employee emp=employeeManagementService.findByEmployeeId(user.getEmployeeId());
			System.out.println("\n Employee =="+emp);
			request.getSession(true).setAttribute("loginUserInfo",user.getUsername());
			request.getSession(true).setAttribute("loginUserEmail", emp.getContactDetails().getOfficeEmail());
			String name=user.getUsername();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			if(user.getIsAdmin()){
			response.getWriter().write("/admin?name="+name);
			return;
			}
			response.getWriter().write("/userHome");
			return;
		}
		catch( Exception e )
		{
			log.error(e.getMessage());
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("/errorLogin?Status=error");
			//response.sendRedirect("/errorLogin?Status=error");
			return;
		}
	}

	@Override
	public void init( FilterConfig filterConfig ) throws ServletException
	{
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(filterConfig
				.getServletContext());
		this.userManagementService = context.getBean(UserManagementService.class);
		this.employeeManagementService=context.getBean(EmployeeManagementService.class);
		this.objectMapper = context.getBean(ObjectMapper.class);
	}

	@Override
	public void destroy()
	{

	}

}
