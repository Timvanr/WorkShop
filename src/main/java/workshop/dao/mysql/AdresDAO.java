package workshop.dao.mysql;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import workshop.dao.AdresDAOInterface;
import workshop.model.Adres;
@Repository
public class AdresDAO implements AdresDAOInterface {
	
	@Autowired
	SessionFactory sessionFactory;
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	Scanner scInput = new Scanner(System.in);
	protected static Logger logger = LoggerFactory.getLogger(AdresDAO.class);
	
	public AdresDAO(){			
	}
	
	public AdresDAO(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure().build();
	Metadata metadata = new MetadataSources(standardRegistry)
			.getMetadataBuilder().build();
	//SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
	
	@Override
	@Transactional
	public void createAdres(Adres adres){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			session.persist(adres);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}

	@Override
	@Transactional
	public List<Adres> readAll(){
		Session session = sessionFactory.openSession();
		List<Adres> adresList = null;
		try{
			adresList = (List<Adres>)session.createQuery("from Adres").list();
		}catch(HibernateException e){
			e.printStackTrace();
		}finally {
			session.close(); 
		}
		return adresList;
	}
	
	@Override
	@Transactional
	public Adres readAdresmetId(long adres_id) {
		Session session = sessionFactory.openSession();
		Adres adres = null;
		try {	
			adres = (Adres) session.get(Adres.class, adres_id);
		}catch (HibernateException e){
			e.printStackTrace();
		}finally {
			session.close();
		}
		return adres;
	}
	
	@Override
	@Transactional
	public void updateAdres(Adres adres){		
		
	}
	
	@Override
	@Transactional
	public void deleteAdres(long adres_id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{ 
			tx = session.beginTransaction();
			Adres adres = (Adres)session.get(Adres.class, adres_id);
			session.delete(adres);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}
}
