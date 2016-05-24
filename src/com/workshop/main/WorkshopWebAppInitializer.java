package com.workshop.main;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WorkshopWebAppInitializer implements WebApplicationInitializer{

	 @Override
	    public void onStartup(ServletContext container) {
		 AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
	     rootContext.register(SpringConfig.class);
		
		 container.addListener(new ContextLoaderListener(rootContext));
		 
		 ServletRegistration.Dynamic registration = container.addServlet("dispatcher", new DispatcherServlet());
	        registration.setLoadOnStartup(1);
	        registration.addMapping("/welcome.jsp");
	        registration.addMapping("/welcome.html");
	        registration.addMapping("*.html");
	    }
}
