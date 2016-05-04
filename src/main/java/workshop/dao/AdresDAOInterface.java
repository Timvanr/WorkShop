package workshop.dao;

import java.util.List;
import workshop.model.Adres;

public interface AdresDAOInterface {
	
	void createAdres(long klant_id, Adres adres);
	List<Adres> readAll();
	Adres readAdresmetId(long adres_id);
	void updateAdres(Adres adres);
	void deleteAdres(long adres_id);
	
}
