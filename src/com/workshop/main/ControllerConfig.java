package com.workshop.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan(basePackages = {"com.workshop.controller"})
public class ControllerConfig {

	@Bean (name="viewResolver")
	public InternalResourceViewResolver viewResolver(){
		InternalResourceViewResolver viewRes = new InternalResourceViewResolver();
		viewRes.setPrefix("/WEB-INF/jsp/");
		viewRes.setSuffix(".jsp");
		viewRes.setViewClass(JstlView.class);
		return viewRes;
	}
}
