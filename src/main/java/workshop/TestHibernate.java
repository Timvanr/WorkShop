package workshop;

import java.math.BigDecimal;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import workshop.model.*;

public class TestHibernate {

	public static void main(String[] args) {
		Klant klant = new Klant("Jan", "met de", "Pet", "jan@petteflat.nl");
		
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure().build();
		Metadata metadata = new MetadataSources(standardRegistry)
				.addAnnotatedClass(Klant.class)
				.addAnnotatedClass(Bestelling.class)
				.addAnnotatedClass(Artikel.class)
				.addAnnotatedClass(Adres.class)
				.addAnnotatedClass(Factuur.class)
				.addAnnotatedClass(Betaling.class)
				.getMetadataBuilder()
				.build();
		//System.out.println(metadata.getNamedEntityGraphs());
		SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
		try {
			Session session = sessionFactory.openSession();
			Adres adres = new Adres("Kreupelstraat", 1, "9711JA", "Groningen");
			Klant k2 = new Klant("Sjaak", "", "Smaak", "sjaak@somewhere.com");
			
			session.beginTransaction();
			session.persist(klant);
			session.persist(k2);
			adres.getBewoners().add(klant);
			adres.getBewoners().add(k2);
			session.persist(adres);
			session.getTransaction().commit();
			session.close();
			/*
			session = sessionFactory.openSession();
			session.persist(k2);
			session.persist(adres);
			session.getTransaction().commit();
			session.close();
			*/
			
			Bestelling bestelling = new Bestelling(klant);
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			session.persist(new Artikel("Kruimel", new BigDecimal(1.99)));
			session.persist(new Artikel("Keutel", new BigDecimal(0.49)));
			session.getTransaction().commit();
			session.close();
			
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			bestelling.voegArtikelToe((Artikel)session.get(Artikel.class, 1), 4);
			bestelling.voegArtikelToe((Artikel)session.get(Artikel.class, 2), 11);
			session.persist(bestelling);
			System.out.println(" - " + bestelling.getTotaalPrijs() + " - ");
			Factuur factuur = new Factuur(bestelling);
			session.persist(factuur);
			bestelling.getFactuurSet().add(factuur);
			session.persist(bestelling);
			session.getTransaction().commit();
			session.close();
			
			session = sessionFactory.openSession();
			session.beginTransaction();
			Factuur opgeslagenFactuur = (Factuur)session.get(Factuur.class, 1);
			session.getTransaction().commit();
			session.close();
			
			Betaling betaling = new Betaling(opgeslagenFactuur, new BigDecimal(4.50), "Eerste inkomsten");
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.persist(betaling);
			opgeslagenFactuur = (Factuur)session.get(Factuur.class, 1);
			opgeslagenFactuur.getBetalingSet().add(betaling);
			session.persist(opgeslagenFactuur);
			session.getTransaction().commit();
			session.close();
			
			Betaling betaling2 = new Betaling(opgeslagenFactuur, new BigDecimal(4.50), "Tweede inkomsten");
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.persist(betaling2);
			opgeslagenFactuur = (Factuur)session.get(Factuur.class, 1);
			opgeslagenFactuur.getBetalingSet().add(betaling2);
			
			session.persist(opgeslagenFactuur);
			System.out.println(opgeslagenFactuur.getOpenstaandBedrag());
			session.getTransaction().commit();
			session.close();
			
		sessionFactory.close();
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

}
