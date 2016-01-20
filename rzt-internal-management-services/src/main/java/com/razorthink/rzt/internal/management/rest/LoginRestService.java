package com.razorthink.rzt.internal.management.rest;

import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.razorthink.utils.goauth.GoogleOAuthService;

@RestController
@Component
@RequestMapping("/GoogleOAuth")
public class LoginRestService {
	private static final Logger log = Logger.getLogger(ProjectRestService.class);
	
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
	@RequestMapping(value="/getOAuthUrl" ,method=RequestMethod.GET)
	public String getOAuthUrl() {
		String url=null;
		try
		{
			url=GoogleOAuthService.getAuthUrl(Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email;https://mail.google.com/".split(";")), "client.json");
			System.out.println("google url== "+url);
		} catch( Exception e )
		{
			log.error("Failed to load OAuth url");
			
		}
		return url;
	}
}
