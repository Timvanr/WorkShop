package workshop;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import workshop.dao.AccountDAOInterface;
import workshop.dao.AdresDAOInterface;
import workshop.dao.ArtikelDAOInterface;
import workshop.dao.BestellingDAOInterface;
import workshop.dao.BetalingDAOInterface;
import workshop.dao.FactuurDAOInterface;
import workshop.dao.KlantDAOInterface;

@Configuration
@ComponentScan ({"workshop.dao.mysql", "workshop.model"})
@EnableTransactionManagement
public class SpringConfig {
	
	@Autowired
	@Bean (name = "sessionFactory")
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource){
		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
		sfb.setDataSource(dataSource);
		sfb.setPackagesToScan("workshop");
		Properties props = new Properties();
		props.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
		sfb.setHibernateProperties(props);
		return sfb;		
	}
	
	@Autowired
	@Bean (name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory){
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		
		return transactionManager;
	}
	
	
	@Bean (name = "dataSource")
	public DataSource getDataSource(){
		
		HikariConfig config = new HikariConfig();
		config.setMinimumIdle(1);
		config.setMaximumPoolSize(14);
		config.setInitializationFailFast(true);
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql://localhost:3306/workshop2");
		config.setUsername("sandermegens");
		config.setPassword("FrIkandel");
		config.addDataSourceProperty("useSSL", "false");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
				
		HikariDataSource hikariDS = new HikariDataSource(config);
		return hikariDS;
		
	}
	@Bean
	public KlantDAOInterface klantDAO(SessionFactory sessionFactory){
		return new workshop.dao.mysql.KlantDAO(sessionFactory);
	}
	@Bean
	public FactuurDAOInterface factuurDAO(SessionFactory sessionFactory){
		return new workshop.dao.mysql.FactuurDAO(sessionFactory);
	}
	
	@Bean
	public BetalingDAOInterface betalingDAO(SessionFactory sessionFactory){
		return new workshop.dao.mysql.BetalingDAO(sessionFactory);
	}
	
	@Bean
	public BestellingDAOInterface bestellingDAO(SessionFactory sessionFactory){
		return new workshop.dao.mysql.BestellingDAO(sessionFactory);
	}
	
	@Bean
	public ArtikelDAOInterface artikelDAO(SessionFactory sessionFactory){
		return new workshop.dao.mysql.ArtikelDAO(sessionFactory);
	}
	
	@Bean
	public AdresDAOInterface adresDAO(SessionFactory sessionFactory){
		return new workshop.dao.mysql.AdresDAO(sessionFactory);
	}
	
	@Bean
	public AccountDAOInterface accountDAO(SessionFactory sessionFactory){
		return new workshop.dao.mysql.AccountDAO(sessionFactory);
	}
	
	
}
