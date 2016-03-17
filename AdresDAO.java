import java.sql.SQLException;


public interface AdresDAO {
	
	//static connection getConnection(String user, String password) throws SQLException; evt inloggen met DBnaam?
	void createAdres(Adres adres) throws SQLException;
	Adres readAdres(int id) throws SQLException;//optioneel met toString()
	void updateAdres(Adres adres) throws SQLException;
	void deleteAdres(int id) throws SQLException;
	Adres[] readAll() throws SQLException;//of set of arraylist?
	Adres searchById(int id) throws SQLException;
	Adres searchByPostcodeAndHuisnummer(String postcode, int huisnummer, String toevoeging) throws SQLException;//moet ook werken als toevoeging null is
	Adres[] searchByStraat(String straat) throws SQLException;
	Adres[] searchByStraatAndHuisnummer(String straat, int huisnummer, String toevoeging) throws SQLException;//kan bijvoorbeeld searchByStraat() callen
	Klant[] getKlant(int id) throws SQLException;
	void close() throws SQLException;	
}
/*
Gerbrich vind dat iedere methode moet beginnen met getConnection() en moet eindigen met connection.close().
Is wel zo safe en volgens mij ook nodig als meerdere mensen tegelijk de database in moeten kunnen

Volgens mij is het niet nodig om een aparte klasse te maken voor de connectie

Zullen we de Git editor gebruiken om deze file aan te passen?

*/