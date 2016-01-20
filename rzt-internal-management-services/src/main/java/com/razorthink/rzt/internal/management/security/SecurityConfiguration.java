/*package com.razorthink.rzt.internal.management.security;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.razorthink.rzt.internal.management.filter.GoogleOAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("arun").password("abc123").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("root123").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("dba").password("root123").roles("ADMIN","DBA");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  
	  http.authorizeRequests()
	  	.antMatchers("/**").permitAll()
	  	//.antMatchers("/admin/**").access("hasRole('ADMIN')")
	  	.antMatchers("/users/**").access("hasRole('User')")
	  	//.antMatchers("/home/**").access("hasRole('ADMIN')")
	  	.antMatchers("/client/**").access("hasRole('ADMIN')")
	  	.antMatchers("/designation/**").access("hasRole('ADMIN')")
	  	.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
	  	.and().formLogin().loginPage("/")
	  	.usernameParameter("userName").passwordParameter("userPassword")
	  	//.and().addFilterAfter(googleLoginFilter(), GoogleOAuthFilter.class)
	  	.and().csrf()
	  	.and().exceptionHandling().accessDeniedPage("/Access_Denied");
	}
	@Bean
	public FilterRegistrationBean googleLoginFilter()
	{
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new GoogleOAuthFilter());
		registration.setOrder(1);
		registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		registration.addUrlPatterns("/home");
		return registration;
	}
}
*/