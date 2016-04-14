

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public interface KlantDAO {

	public int createKlant(Klant klant);
	public void createKlantEnAdres(Klant klant, Adres adres);
	public void createKlantEnAdresEnBestelling(Klant klant, Adres adres, Bestelling bestelling);
	public void createKlantEnBestelling(Klant klant, Bestelling bestelling);
	public Klant readKlantWithId(int klant_id);
	public Set<Klant> readKlantWithFirstName(String voornaam);
	public Klant readKlantWithFirstLastName(String voornaam, String tussenvoegsel, String achternaam);
	public void UpdateKlantNaam(int klant_id);
	public void updateEmail(int klant_id);
	public Set<Klant> readAll();
	public void deleteAllFromKlantId(int klant_id);
	public void deleteAllFromKlantNaam(String voornaam, String achternaam, String tussenvoegsel);
	
}
