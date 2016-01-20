package com.razorthink.rzt.internal.management.controller;

public class AbstractController {

	/*@Autowired
	private HttpServletRequest httpServletRequest;
*/
	/*public LoginUserInfo getLoginUserInfo()
	{
		// this is mainly for testing
		if( httpServletRequest == null )
		{
			LoginUserInfo info = new LoginUserInfo();
			User u = new User();
			u.setId(1);
			info.setLoggedInUser(u);

			return info;
		}

		if( null != httpServletRequest.getSession().getAttribute("loginUserInfo") )
		{
			return (LoginUserInfo) httpServletRequest.getSession().getAttribute("loginUserInfo");
		}
		return null;
	}*/
}
