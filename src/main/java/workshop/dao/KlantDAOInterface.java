package workshop.dao;

import java.util.List;
import workshop.model.Klant;

public interface KlantDAOInterface {

	long createKlant(workshop.model.Klant klant);
	Klant readKlantWithId(long klant_id);
	List<Klant> readAll();
	void UpdateKlant(long klant_id, Klant klant);
	void UpdateKlantNaam(long klant_id, Klant klant);
	void UpdateKlantEmail(long klant_id, Klant klant);
	void deleteAllFromKlantId(long klant_id);
}
