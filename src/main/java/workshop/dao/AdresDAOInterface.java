import java.util.Set;

public interface AdresDAO {
	
	//static connection getConnection(String user, String password) throws SQLException; evt inloggen met DBnaam?
	void createAdres(int klant_id, Adres adres);
	Set<Adres> readAll();
	void updateAdres(int klant_id, Adres adres);
	void deleteAdres(int id);
	void deleteKlantAdresPair(int klant_id, int adres_id);
	Adres readAdresmetAdresId(int adres_id);
	Set<Adres> readAdressenPerKlant(int klant_id);
	Adres readAdresMetPostcodeEnHuisnummer(String postcode, int huisnummer, String toevoeging);
	Set<Adres> readAdresMetWoonplaats(String plaats);
	Set<Adres> readAdresMetStraat(String straat, String plaats);
	Set<Adres> readAdresMetStraatEnHuisnummer(String straat, int huisnummer, String toevoeging, String plaats);
	Set<Klant> readKlantenMetAdresId(int adres_id);
}	
/*
Gerbrich vind dat iedere methode moet beginnen met getConnection() en moet eindigen met connection.close().
Is wel zo safe en volgens mij ook nodig als meerdere mensen tegelijk de database in moeten kunnen

Volgens mij is het niet nodig om een aparte klasse te maken voor de connectie

Zullen we de Git editor gebruiken om deze file aan te passen?

*/
