package workshop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import workshop.dao.KlantDAOInterface;
import workshop.dao.mysql.KlantDAO;
import workshop.model.Klant;

public class springTest {

	public static void main(String[] args){
		
		
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		KlantDAO kdao = context.getBean(KlantDAO.class);
		System.out.println(kdao.findAll());
		
		//Klant k1 = kdaoi.readKlantWithId(3);
		
		//System.out.println(k1.getAccounts());
		//System.out.println(k1.toString());

	}
}
