package com.razorthink.rzt.internal.management.test;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.razorthink.utils.spring.repo.GenericRepositoryFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@SpringBootApplication
@EnableJpaRepositories( repositoryFactoryBeanClass = GenericRepositoryFactoryBean.class )
//@Import( { HikariDataSourceConfig.class } )
public class TestApplication {

	@Autowired
	private Environment env;

	public static void main( String[] args ) throws Exception
	{
		SpringApplication.run(TestApplication.class);
	}

	@Bean
	public DataSource dataSource()
	{
		HikariConfig config = new HikariConfig();
		config.setMaximumPoolSize(100);
		config.setDataSourceClassName(env.getProperty("spring.datasource.driver-class-name"));
		config.addDataSourceProperty("url", env.getProperty("spring.datasource.url"));
		config.addDataSourceProperty("user", env.getProperty("spring.datasource.username"));
		config.addDataSourceProperty("password", env.getProperty("spring.datasource.password"));
		return new HikariDataSource(config);
	}
}
