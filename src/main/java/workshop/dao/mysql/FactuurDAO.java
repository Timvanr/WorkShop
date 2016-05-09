package workshop.dao.mysql;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import workshop.dao.FactuurDAOInterface;
import workshop.model.Bestelling;
import workshop.model.Betaling;
import workshop.model.Factuur;
@Repository
public class FactuurDAO implements FactuurDAOInterface{


	@Autowired
	SessionFactory sessionFactory;
	//SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	
	public FactuurDAO(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	
	@Override
	@Transactional
	public Factuur createFactuur(int bestelling_id) {
		Session session = null;
		Factuur factuur = null;
		try{
			session = sessionFactory.openSession();
			factuur = new Factuur();
			factuur.setFactuurDatum(new Date());
			session.beginTransaction();
			factuur.setBestelling((Bestelling)session.load(Bestelling.class, (long)bestelling_id));
			factuur.setFactuurNummer("factuur + iets erachter");
			session.save(factuur);
			session.getTransaction().commit();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		finally{
			session.close();
		}
	
		return factuur;
	}

	@Override
	@Transactional
	public Factuur readFactuurMetFactuurId(long factuur_id) {
		Session session = sessionFactory.openSession();
		Factuur factuur = null;
		try{
		session.beginTransaction();
		factuur = (Factuur)session.get(Factuur.class, factuur_id);
		session.getTransaction().commit();
		
		}
		catch (HibernateException ex){
			ex.printStackTrace();
		}
		finally {
			session.close();
		}
		return factuur;
		
	}

	@Override
	@Transactional
	public Factuur readFactuurMetBestellingId(long bestelling_id) {
		Session session = sessionFactory.openSession();
		Factuur factuur = null;
		try{
		session.beginTransaction();
		factuur = (Factuur)session.get(Factuur.class, bestelling_id);
		session.getTransaction().commit();
		}
		catch (HibernateException ex){
			ex.printStackTrace();
		}
		finally {
			session.close();
		}
		return factuur;
		
	}

	@Override
	@Transactional
	public void updateFactuurBedrag(long factuur_id) {
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			Factuur factuur = (Factuur) session.get(Factuur.class, factuur_id);
			BigDecimal betaaldbedrag = new BigDecimal(0);
			for(Betaling betaling: factuur.getBetalingSet()){
				betaaldbedrag = betaaldbedrag.add(betaling.getBetaaldBedrag());
			}
			factuur.setOpenstaandBedrag(factuur.getBestelling().getTotaalBedrag().subtract(betaaldbedrag));
			session.getTransaction().commit();			
		}
		catch (HibernateException ex){
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			session.close();
		}
		
	}

	
}

	

	



	