package com.workshop.main;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"com.workshop.service", "com.workshop.dao", "com.workhop.main", "com.workshop.model"})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.workshop.dao")
public class SpringConfig {

	@Bean (name = "dataSource")
	public DataSource dataSource(){
		
		HikariConfig config = new HikariConfig();
		config.setMinimumIdle(1);
		config.setMaximumPoolSize(14);
		config.setInitializationFailFast(true);
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql://localhost:3306/test");
		config.setUsername("Sysdba");
		config.setPassword("MasterKey");
		config.addDataSourceProperty("useSSL", "false");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");				
		HikariDataSource hikariDS = new HikariDataSource(config);
		return hikariDS;
	}
	
	@Bean (name = "entityManagerFactory")
	public EntityManagerFactory entityManagerFactory(){
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.workshop.model");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();		
		return factory.getObject();
	}
	
	@Bean (name = "transactionManager")
	public PlatformTransactionManager transactionManager(){
		JpaTransactionManager txm = new JpaTransactionManager();
		txm.setEntityManagerFactory(entityManagerFactory());
		return txm;
	}
	
	/*
	@Bean (name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory){
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}
	*/
	
	@Bean (name = "sessionFactory")
	public LocalSessionFactoryBean sessionFactory(){
		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
		sfb.setDataSource(dataSource());
		sfb.setPackagesToScan("com.workshop");
		Properties props = new Properties();
		props.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
		sfb.setHibernateProperties(props);
		return sfb;	
	}
}
