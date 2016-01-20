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
import javax.servlet.http.HttpSession;

public class LogOutFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		System.out.println(
				"session " + (session != null && request.getSession().getAttribute("loginUserInfo") != null));
		System.out.println("attr " + request.getSession().getAttribute("loginUserInfo"));
		if (session != null && request.getSession(false).getAttribute("loginUserInfo") != null) {
			request.getSession().removeAttribute("loginUserInfo");
			System.out.println("attr " + request.getSession().getAttribute("loginUserInfo"));
			request.getSession(false).invalidate();
		}
		System.out.println("\nCookies== "+request.getCookies());
		System.out.println("\n attribute=="+request.getSession().getAttribute("loginUserInfo"));
		request.getSession().removeAttribute("loginUserInfo");
		System.out.println("\n attribute=="+request.getSession().getAttribute("loginUserInfo"));
		request.getSession(false).invalidate();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("logout");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
