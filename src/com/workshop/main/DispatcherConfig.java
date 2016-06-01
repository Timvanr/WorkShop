package com.workshop.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan(basePackages = "com.workshop.controller")
@EnableWebMvc
public class DispatcherConfig extends WebMvcConfigurerAdapter{
	
	@Bean (name="viewResolver")
	public InternalResourceViewResolver viewResolver(){
		InternalResourceViewResolver viewRes = new InternalResourceViewResolver();
		viewRes.setPrefix("/WEB-INF/jsp/");
		viewRes.setSuffix(".jsp");
		viewRes.setViewClass(JstlView.class);
		return viewRes;
	}
}