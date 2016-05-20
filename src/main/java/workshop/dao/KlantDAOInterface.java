package workshop.dao;

import workshop.model.Klant;

public interface KlantDAOInterface extends GenericDao<Klant, Long> {

	//Extra methodes. Methodes uit de genericDAO kun je gewoon aanspreken.
	
	Long deleteByVoornaamAndAchternaam(String voornaam, String achternaam);
	
	
	
}
