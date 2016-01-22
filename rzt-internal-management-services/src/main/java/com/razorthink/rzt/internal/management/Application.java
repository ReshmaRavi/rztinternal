package com.razorthink.rzt.internal.management;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.razorthink.rzt.internal.management.filter.AccessPermissionFilter;
import com.razorthink.rzt.internal.management.filter.GoogleOAuthFilter;
import com.razorthink.rzt.internal.management.filter.LogOutFilter;
import com.razorthink.rzt.internal.management.filter.LoginFilter;
import com.razorthink.utils.spring.config.HikariDataSourceConfig;
import com.razorthink.utils.spring.repo.GenericRepositoryFactoryBean;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = GenericRepositoryFactoryBean.class )
@Import({ HikariDataSourceConfig.class })
public class Application{

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(Application.class).showBanner(false).run(args);
	}

	@Bean
	public FilterRegistrationBean logoutFilter()
	{
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new LogOutFilter());
		registration.setOrder(4);
		registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		registration.addUrlPatterns("/logout");
		return registration;
	}
	
	@Bean
	public FilterRegistrationBean googleLoginFilter()
	{
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new GoogleOAuthFilter());
		registration.setOrder(3);
		registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		registration.addUrlPatterns("/home");
		return registration;
	}

    @Bean
    public FilterRegistrationBean loginFilter()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginFilter());
        registration.setOrder(2);
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        registration.addUrlPatterns("/login");
        return registration;
    }
    
	@Bean
	public FilterRegistrationBean accessPermissionFilter()
	{
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new AccessPermissionFilter());
		registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		registration.addUrlPatterns("/*");
		registration.setOrder(1);
		registration.addInitParameter("excludeUrls", "/(css|js|lib|img|fonts|GoogleOAuth|home|login)/*");
		return registration;
	}

}
