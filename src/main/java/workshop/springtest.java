package workshop;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import workshop.dao.KlantDAOInterface;
import workshop.model.Klant;

public class springtest {

	public static void main(String[] args) {
		
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		
		Klant k0 = context.getBean(Klant.class);
		k0.setVoornaam("jan");
		k0.setTussenvoegsel("de");
		k0.setAchternaam("busman");
		k0.setEmail("jandebusman@busje.nl");
		KlantDAOInterface kdaoi = context.getBean(KlantDAOInterface.class);
		kdaoi.createKlant(k0);
		Klant k1 = kdaoi.readKlantWithId(3);
		
		//System.out.println(k1.getAccounts());
		//System.out.println(k1.toString());

	}

}
