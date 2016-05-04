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
import workshop.dao.KlantDAOInterface;
import workshop.model.Klant;

public class KlantDAO implements KlantDAOInterface{
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	Scanner scInput = new Scanner(System.in);
	protected static Logger logger = LoggerFactory.getLogger(KlantDAO.class);


	public KlantDAO() {
	}

	StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure().build();
	Metadata metadata = new MetadataSources(standardRegistry)
			.getMetadataBuilder().build();
	SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();


	@Override
	public long createKlant(workshop.model.Klant klant) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		long klant_id = 0;
		try{
			tx = session.beginTransaction();
			session.persist(klant);
			klant_id = klant.getKlant_id();
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return klant_id;
	}

	@Override
	public Klant readKlantWithId(long klant_id){
		Session session = sessionFactory.openSession();
		Klant klant = null;
		try {	
			klant = (Klant) session.get(Klant.class, klant_id);
		}catch (HibernateException e){
			e.printStackTrace();
		}finally {
			session.close(); 
		}
		return klant;
	}

	@Override
	public List<Klant> readAll(){
		Session session = sessionFactory.openSession();
		List<Klant> klantList = null;
		try{
			klantList = (List<Klant>)session.createQuery("from Klant").list();
		}catch(HibernateException e){
			e.printStackTrace();
		}finally {
			session.close(); 
		}
		return klantList;
	}

	@Override
	public void UpdateKlant(long klant_id, Klant klant1){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Klant klant = (Klant)session.load(Klant.class, klant_id);
			klant.setVoornaam(klant1.getVoornaam());
			klant.setTussenvoegsel(klant1.getTussenvoegsel());
			klant.setAchternaam(klant1.getAchternaam());
			klant.setEmail(klant1.getEmail());
			session.update(klant); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}

	@Override
	public void UpdateKlantNaam(long klant_id, Klant klant1){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Klant klant = (Klant)session.load(Klant.class, klant_id);
			klant.setVoornaam(klant1.getVoornaam());
			klant.setTussenvoegsel(klant1.getTussenvoegsel());
			klant.setAchternaam(klant1.getAchternaam());
			session.update(klant); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}

	@Override
	public void UpdateKlantEmail(long klant_id, Klant klant1){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Klant klant = (Klant)session.load(Klant.class, klant_id);
			klant.setEmail(klant1.getEmail());
			session.update(klant); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}

	@Override
	public void deleteAllFromKlantId(long klant_id){
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{ 
			tx = session.beginTransaction();
			Klant klant = (Klant)session.get(Klant.class, klant_id);
			session.delete(klant);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
	}
}
