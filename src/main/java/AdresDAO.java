import java.sql.SQLException;
import java.util.Set;


public interface AdresDAO {
	
	//static connection getConnection(String user, String password) throws SQLException; evt inloggen met DBnaam?
	void createAdres(int klant_id, Adres adres);
	Adres readAdres(int id);
	Set<Adres> readAll();//of set of arraylist?
	void updateAdres(int klant_id, Adres adres);
	void deleteAdres(int id);
	Adres searchById(int id);
	Adres searchByPostcodeAndHuisnummer(String postcode, int huisnummer, String toevoeging);//moet ook werken als toevoeging null is
	Set<Adres> searchByWoonplaats(String plaats);
	Set<Adres> searchByStraat(String straat, String plaats);
	Set<Adres> searchByStraatAndHuisnummer(String straat, int huisnummer, String toevoeging, String plaats);//kan bijvoorbeeld searchByStraat() callen
	Set<Klant> getKlant(int adres_id);
	Set<Adres> adressenPerKlant(int klant_id);
	void deleteKlantAdresPair(int klant_id, int adres_id);
}
/*
Gerbrich vind dat iedere methode moet beginnen met getConnection() en moet eindigen met connection.close().
Is wel zo safe en volgens mij ook nodig als meerdere mensen tegelijk de database in moeten kunnen

Volgens mij is het niet nodig om een aparte klasse te maken voor de connectie

Zullen we de Git editor gebruiken om deze file aan te passen?

*/
