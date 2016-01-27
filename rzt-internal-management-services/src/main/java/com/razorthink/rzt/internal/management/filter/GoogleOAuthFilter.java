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
import com.google.api.client.auth.oauth2.Credential;
import com.razorthink.rzt.internal.management.domain.LoggedUser;
import com.razorthink.rzt.internal.management.domain.Users;
import com.razorthink.rzt.internal.management.user.service.UserManagementService;
import com.razorthink.utils.goauth.GoogleAuthException;
import com.razorthink.utils.goauth.GoogleOAuthService;
import com.razorthink.utils.goauth.GoogleUserInfo;

public class GoogleOAuthFilter implements Filter {
	private static final Logger log = Logger.getLogger(GoogleOAuthFilter.class);
	public static ObjectMapper objectMapper;
	
	private UserManagementService userManagementService;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		System.out.println("\n Inside google ");
		Credential credential;
		if (null != request.getParameter("code")) {
			try {
				log.debug("Creating user session and Calling authorizeService ");
				credential = GoogleOAuthService.authorizeService(request.getParameter("code"));
				GoogleOAuthService.setObjectMapper(GoogleOAuthFilter.objectMapper);
				GoogleUserInfo googleUserInfo = GoogleOAuthService.getUserInfo(credential);
				try {
					Users user=userManagementService.findByEmailId(googleUserInfo.getEmail());
					request.getSession(true).setAttribute("loginUserInfo", googleUserInfo.getName());
					request.getSession(true).setAttribute("loginUserEmail", googleUserInfo.getEmail());
					String name=googleUserInfo.getName();
					LoggedUser.setUsername(name);
					if(user.getIsAdmin()){
						response.sendRedirect("/admin");
						return;
					}
					response.sendRedirect("/userHome");
					return;
				} catch (Exception e) {
					response.sendRedirect("/errorLogin?Status=error");
				}

			} catch (GoogleAuthException e) {
				log.error("Exception occurred due to" + e.getMessage());
			}
		} else {
			System.out.println("in google other");
			response.sendRedirect("/");
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		this.objectMapper = context.getBean(ObjectMapper.class);
		this.userManagementService=context.getBean(UserManagementService.class);
	}

}
