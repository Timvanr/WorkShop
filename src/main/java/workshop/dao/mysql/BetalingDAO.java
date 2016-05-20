package workshop.dao.mysql;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import workshop.model.Betaling;
import workshop.model.Betaling.Betaalwijze;
import workshop.model.Factuur;
import workshop.model.Klant;
@Repository
public class BetalingDAO implements workshop.dao.BetalingDAOInterface {

	private SessionFactory sessionFactory;
	 
	public SessionFactory getSessionFactory() {
	return sessionFactory;
	}
	 
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Betaling createBetaling(long factuur_id, BigDecimal bedrag, Betaalwijze bwijze) {
		
		Session session = sessionFactory.openSession();
		try{
			Betaling betaling = new Betaling();
			betaling.setBetaalDatum(new Date());
			betaling.setBetalingsGegevens("Er is een betaling gedaan, Joepie!");
			betaling.setFactuur((Factuur)session.load(Factuur.class, factuur_id));
			betaling.setBetaalWijze(bwijze);
			betaling.setBetaaldBedrag(bedrag);
		//	betaling.setKlant((Klant)session.load(Klant.class, ???)); //dit nog veranderen
			session.beginTransaction();
			session.save(betaling);
			session.getTransaction().commit();
			session.beginTransaction();
			FactuurDAO fdao = new FactuurDAO();
			fdao.updateFactuurBedrag(factuur_id);
			session.getTransaction().commit();
		}
		catch(HibernateException ex){
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			session.close();
		}
		return null;
	}
	
	@Override
	public void readAlleBetalingen(long factuur_id) {
		Session session = sessionFactory.openSession();
		try{
			session.beginTransaction();
			List betalingen= session.createQuery("From Betaling where factuur_id =" + factuur_id).list();
			System.out.println("De betalingen voor factuurnummer " + factuur_id + " zijn:");
			for (Iterator<Betaling> iterator = betalingen.iterator(); iterator.hasNext();){
				Betaling betaling = iterator.next();
				System.out.println("betaling id: " + betaling.getBetaling_id());
				System.out.println("betaling bedrag: " + betaling.getBetaaldBedrag());
				System.out.println("betaalwijze: " + betaling.getBetaalWijze());
				System.out.println("betaald op: " + betaling.getBetaalDatum());
			}
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
