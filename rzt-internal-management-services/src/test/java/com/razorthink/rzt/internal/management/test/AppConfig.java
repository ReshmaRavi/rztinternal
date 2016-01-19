package com.razorthink.rzt.internal.management.test;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.razorthink.rzt.internal.management.client.service.ClientManagementService;
import com.razorthink.rzt.internal.management.client.service.impl.ClientManagementServiceImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class AppConfig {
/*
	@Bean
	public ClientManagementService getClientManagementService()
	{
		return new ClientManagementServiceImpl();
	}
	*/
/*	@Bean
	 public DataSource getDataSource() {
	     BasicDataSource dataSource = new BasicDataSource();
	     dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	     dataSource.setUrl("jdbc:mysql://localhost:3306/concretepage");
	     dataSource.setUsername("root");
	     dataSource.setPassword("");
	  
	     return dataSource;
	 }*/
}
