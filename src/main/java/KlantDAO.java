import java.util.Set;

public interface KlantDAO {

	int createKlant(Klant klant);
	void createKlantEnAdres(Klant klant, Adres adres);
	void createKlantEnAdresEnBestelling(Klant klant, Adres adres, Bestelling bestelling);
	void createKlantEnBestelling(Klant klant, Bestelling bestelling);
	Klant readKlantWithId(int klant_id);
	Set<Klant> readKlantWithFirstName(String voornaam);
	Klant readKlantWithFirstLastName(String voornaam, String tussenvoegsel, String achternaam);
	void UpdateKlantNaam(int klant_id);
	void updateEmail(int klant_id);
	Set<Klant> readAll();
	void deleteAllFromKlantId(int klant_id);
	void deleteAllFromKlantNaam(String voornaam, String achternaam, String tussenvoegsel);
	
}
