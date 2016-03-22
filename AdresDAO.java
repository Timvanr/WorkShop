import java.sql.SQLException;
import java.util.Set;


public interface AdresDAO {
	
	//static connection getConnection(String user, String password) throws SQLException; evt inloggen met DBnaam?
	void createAdres(int klant_id, Adres adres) throws SQLException;//Sander
	Adres readAdres(int id) throws SQLException;//Tim//optioneel met toString()
	void deleteAdres(int id) throws SQLException;//Tim
	Set<Adres> readAll() throws SQLException;//Tim//of set of arraylist?
	Adres searchByPostcodeAndHuisnummer(String postcode, int huisnummer, String toevoeging) throws SQLException;//Sander//moet ook werken als toevoeging null is
	Set<Adres> searchByStraat(String straat) throws SQLException;//Maurice
	Set<Adres> searchByStraatAndHuisnummer(String straat, int huisnummer, String toevoeging) throws SQLException;//Maurice//kan bijvoorbeeld searchByStraat() callen
	Set<Klant> getKlant(int id) throws SQLException;//Sander
	void close() throws SQLException;//Maurice
}
/*
Gerbrich vind dat iedere methode moet beginnen met getConnection() en moet eindigen met connection.close().
Is wel zo safe en volgens mij ook nodig als meerdere mensen tegelijk de database in moeten kunnen

DataIntegrityConstraintException catchen!!

Zullen we de Git editor gebruiken om deze file aan te passen?

*/
