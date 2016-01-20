/**
 * 
 */
package com.razorthink.rzt.internal.management.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.razorthink.rzt.internal.management.domain.Users;
import com.razorthink.rzt.internal.management.user.service.UserManagementService;

public class AccessPermissionFilter implements Filter {

	// private static final Logger log = Logger.getLogger(LoginFilter.class);
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String excludedUrlsRegex;
	private UserManagementService userManagementService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		this.excludedUrlsRegex = filterConfig.getInitParameter("excludeUrls");
		this.userManagementService = context.getBean(UserManagementService.class);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		this.request = (HttpServletRequest) request;
		this.response = (HttpServletResponse) response;
		HttpSession session = this.request.getSession(false);
		// String sessionId = CookiesUtil.getCookie(this.request,"SESSION_ID");
		boolean excludedUrl = (this.excludedUrlsRegex != null && !this.excludedUrlsRegex.isEmpty() && Pattern.compile(this.excludedUrlsRegex).matcher(this.request.getRequestURL().toString()).find());
		System.out.println("uri==" + this.request.getRequestURI());
		System.out.println("excluded url ==" + (!excludedUrl));
		System.out.println("uri contains== " + this.request.getRequestURI().equals("/"));
		System.out.println("if condition== " + (!excludedUrl && this.request.getRequestURI().equals("/")));
		String uri = this.request.getRequestURI();
		String attribute = (String) this.request.getSession().getAttribute("loginUserInfo");
		System.out.println("attribute=" + attribute);
		System.out.println("\nuri=" + uri);
		if (!excludedUrl && (!uri.equals("/"))) {

			this.response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP
																								// 1.1.
			this.response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			this.response.setDateHeader("Expires", 0); // Proxies.
			System.out.println("inside if condition" + session);
			if (this.request.getSession(false) != null && (attribute == null)) {
				this.response.sendRedirect("/");
				return;
			}
		} else if (!excludedUrl && this.request.getRequestURI().equals("/") && attribute != null) {
			this.response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP
																								// 1.1.
			this.response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			this.response.setDateHeader("Expires", 0); // Proxies.
			Users user = userManagementService.findByEmailId((String) this.request.getSession().getAttribute("loginUserEmail"));
			if (user.getIsAdmin()) {
				this.response.sendRedirect("/admin?name=" + attribute);
				return;
			}
			this.response.sendRedirect("/userHome");
			return;
		}
		System.out.println("do filter");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}