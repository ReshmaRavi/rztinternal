package com.razorthink.rzt.internal.management;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.razorthink.utils.spring.repo.GenericRepositoryFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableAutoConfiguration
@ComponentScan( "com.razorthink.rzt.internal.management.*" )
@EnableJpaRepositories(basePackages = {
"com.razorthink.rzt.internal.management.*" }, repositoryFactoryBeanClass = GenericRepositoryFactoryBean.class)
public class Application {

	@Autowired
	private Environment env;

	public static void main( String[] args ) throws Exception
	{
		new SpringApplicationBuilder(Application.class).showBanner(false).run(args);
	}
	
    @Bean
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor()
    {
        ThreadPoolTaskExecutor executorFactoryBean = new ThreadPoolTaskExecutor();
        executorFactoryBean.setCorePoolSize(20);
        return executorFactoryBean;
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
