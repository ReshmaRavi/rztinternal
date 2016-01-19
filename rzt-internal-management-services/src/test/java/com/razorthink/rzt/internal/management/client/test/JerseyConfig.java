package com.razorthink.rzt.internal.management.client.test;

import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig{
	
	public JerseyConfig()
	{
		//register(RequestContextFilter.class);
		//property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
		packages("com.razorthink.rzt.internal.management.client.service");
		//register(LoggingFilter.class);
		//register(MultiPartFeature.class);
	}

}
