package com.razorthink.rzt.internal.management.test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan( value = "com.razorthink.*" )
@EnableJpaRepositories( basePackages = "com.razorthink.*" )
@PropertySource( { "classpath:application.properties" } )
public class TestConfig {

}
