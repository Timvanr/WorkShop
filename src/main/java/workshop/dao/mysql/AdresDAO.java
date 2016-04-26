package workshop.dao.mysql;

import java.sql.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.RowSet;
import com.sun.rowset.JdbcRowSetImpl;
import workshop.model.Adres;
import workshop.DatabaseConnection;

public class AdresDAO implements workshop.dao.AdresDAOInterface {
	private static Connection connection;
	static Logger logger = LoggerFactory.getLogger(AdresDAO.class);
	
	public AdresDAO(){			
	}
	
	public static Connection getConnection(){
		connection = DatabaseConnection.getPooledConnection();
						
		return connection;
	}
	
	public void createTable(){
		Connection connection = getConnection();
		
		try {
			Statement createTable = connection.createStatement();
			createTable.execute("CREATE TABLE Adres (" +
					"adres_id INT AUTO_INCREMENT PRIMARY KEY, " +
					"straatnaam VARCHAR(45), " +
					"huisnummer INT, " +
					"toevoeging VARCHAR(6), " +
					"postcode VARCHAR(6), " +
					"woonplaats VARCHAR(45), " +
					"CONSTRAINT uniek UNIQUE (postcode, huisnummer, toevoeging)" +
					")");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Table Adres created!");
	}
	
	@Override
	public void createAdres(int klant_id, Adres adres){
		Connection connection = getConnection();
		logger.info("createAdres(int klant_id, Adres adres); gestart");
		String insertAdresString = "INSERT INTO Adres (straatnaam, huisnummer, toevoeging, postcode, woonplaats) VALUES (?,?,?,?,?);";
		String insertKlantHasAdresString = "INSERT INTO klant_has_adres (klant_id, adres_id) values (?,?);";
		PreparedStatement insertAdres = null;
		PreparedStatement insertKlantHasAdres = null;
		ResultSet rs = null;
		
		try {
			insertAdres = connection.prepareStatement(insertAdresString, Statement.RETURN_GENERATED_KEYS);
			insertKlantHasAdres = connection.prepareStatement(insertKlantHasAdresString);
			insertAdres.setString(1, adres.getStraatnaam());
			insertAdres.setInt(2, adres.getHuisnummer());
			insertAdres.setString(3, adres.getToevoeging());
			insertAdres.setString(4, adres.getPostcode());
			insertAdres.setString(5, adres.getWoonplaats());
			insertAdres.executeUpdate();
		
			
			rs = insertAdres.getGeneratedKeys();
			if (rs.isBeforeFirst()) {
				rs.next();
				insertKlantHasAdres.setInt(1, klant_id);
				insertKlantHasAdres.setInt(2, rs.getInt(1));
				insertKlantHasAdres.executeUpdate();
				System.out.println("Adres met ID: " + rs.getInt(1) + " aangemaakt");
			}
			logger.info("createAdres(int klant_id, Adres adres); uitgevoerd");

		
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			//System.out.println(ex.getErrorCode());
			if (ex.getErrorCode() == 1062){
				try {
					int existingAdres_id = readAdresMetPostcodeEnHuisnummer
							(adres.getPostcode(), adres.getHuisnummer(), adres.getToevoeging()).getId();
					
					Connection connection1 = getConnection();//connectie openen omdat die gesloten is door searchByPostcodeAndHuisnummer!
					PreparedStatement assignExistingAdres = connection1.prepareStatement(insertKlantHasAdresString);
					assignExistingAdres.setInt(1, klant_id);
					assignExistingAdres.setInt(2, existingAdres_id);
					assignExistingAdres.executeUpdate();
					System.out.println("Adres already exists; existing Adres was assigned to Klant!");
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				ex.printStackTrace();
			}
		} finally {
			try {
				insertAdres.close();
				insertKlantHasAdres.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

	@Override
	public Set<Adres> readAll() {
		Connection connection = getConnection();
		logger.info("Set<Adres> readAll(); gestart");
		Set<Adres> adresLijst = new LinkedHashSet<>();
		
		try {
			RowSet rowSet = new JdbcRowSetImpl(connection);
			String query = "Select * from Adres ORDER BY woonplaats ASC, straatnaam ASC";
			rowSet.setCommand(query);
			rowSet.execute();
			
			while(rowSet.next()){
				Adres adres = new Adres();
				adres.setId(rowSet.getInt(1));
				adres.setStraatnaam(rowSet.getString(2));
				adres.setHuisnummer(rowSet.getInt(3));
				adres.setToevoeging(rowSet.getString(4));
				adres.setPostcode(rowSet.getString(5));
				adres.setWoonplaats(rowSet.getString(6));				
				adresLijst.add(adres);			
				logger.info("Set<Adres> readAll(); uitgevoerd");
			}
			rowSet.close();
			
		} catch (SQLException ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return adresLijst;
	}

	@Override
	public Adres readAdresmetAdresId(int adres_id) {
		Connection connection = getConnection();
		logger.info("readAdresmetAdresId(int adres_id); gestart");
		String query = "SELECT * FROM Adres WHERE adres_id =?";
		Adres adres = new Adres();
		
		try {
			RowSet rowSet = new JdbcRowSetImpl(connection);
	
			rowSet.setCommand(query);
			rowSet.setInt(1, adres_id);
			rowSet.execute();
	
			while (rowSet.next()) {

				adres.setId(rowSet.getInt("adres_id"));
				adres.setStraatnaam(rowSet.getString("straatnaam"));
				adres.setHuisnummer(rowSet.getInt("huisnummer"));
				adres.setToevoeging(rowSet.getString("toevoeging"));
				adres.setPostcode(rowSet.getString("postcode"));
				adres.setWoonplaats(rowSet.getString("woonplaats"));
				logger.info("readAdresmetAdresId(int adres_id); uitgevoerd");
			}		
			rowSet.close();
		} catch (SQLException ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return adres;
	}

	@Override
	public void updateAdres(int klant_id, Adres adres){		
		createAdres(klant_id, adres);		
		System.out.println("Adresgegevens gewijzigd");
	}

	@Override
	public Set<Adres> readAdressenPerKlant(int klant_id){
		connection = getConnection();		
		logger.info("readAdressenPerKlant(int klant_id); gestart");
		Set<Adres> adressen = new LinkedHashSet();
		try {
			RowSet adressenPerKlant = new JdbcRowSetImpl(connection);
			adressenPerKlant.setCommand(
					"SELECT * FROM Adres " +
					"INNER JOIN klant_has_adres " +
					"ON klant_has_adres.adres_id=Adres.adres_id " +
					"WHERE klant_has_adres.klant_id = ? " +
					"ORDER BY woonplaats ASC, straatnaam ASC");
			adressenPerKlant.setInt(1, klant_id);
			adressenPerKlant.execute();
			
			while (adressenPerKlant.next()){
				adressen.add(new Adres(adressenPerKlant.getInt(1), adressenPerKlant.getString(2), adressenPerKlant.getInt(3), 
						adressenPerKlant.getString(4), adressenPerKlant.getString(5), adressenPerKlant.getString(6)));
				logger.info("readAdressenPerKlant(int klant_id); uitgevoerd");
			}
			adressenPerKlant.close();
			
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		
		} finally {
			try {
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		
		}		
		return adressen;
	}

	@Override
	public Adres readAdresMetPostcodeEnHuisnummer(String postcode, int huisnummer, String toevoeging) {
		Connection connection = getConnection();
		logger.info("readAdresMetPostcodeEnHuisnummer(String postcode, int huisnummer, String toevoeging); gestart");
		String query = "SELECT * FROM Adres WHERE postcode = ? AND huisnummer = ? and toevoeging = ? ";
		Adres adres = new Adres();
		
		try {
			RowSet rowSet = new JdbcRowSetImpl(connection);	
			rowSet.setCommand(query);
			rowSet.setString(1, postcode);
			rowSet.setInt(2, huisnummer);
			rowSet.setString(3, toevoeging);
			rowSet.execute();
	
			while (rowSet.next()) {

				adres.setId(rowSet.getInt("adres_id"));
				adres.setStraatnaam(rowSet.getString("straatnaam"));
				adres.setHuisnummer(rowSet.getInt("huisnummer"));
				adres.setToevoeging(rowSet.getString("toevoeging"));
				adres.setPostcode(rowSet.getString("postcode"));
				adres.setWoonplaats(rowSet.getString("woonplaats"));
				logger.info("readAdresMetPostcodeEnHuisnummer(String postcode, int huisnummer, String toevoeging); uitgevoerd");
			}		
			rowSet.close();
		} catch (SQLException ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return adres;
	}

	//overloaded zonder toevoeging
	public Adres readAdresMetPostcodeEnHuisnummer(String postcode, int huisnummer){
		return readAdresMetPostcodeEnHuisnummer(postcode, huisnummer, null);
	}
	
	@Override
	public Set<Adres> readAdresMetWoonplaats(String plaats){
		Connection connection = getConnection();		
		logger.info("readAdresMetWoonplaats(String plaats); gestart");
		Set<Adres> adressen = new LinkedHashSet<Adres>();
		
		try {
			RowSet adresData = new JdbcRowSetImpl(connection);
			adresData.setCommand("SELECT * FROM Adres WHERE woonplaats = ? ORDER BY straatnaam ASC");
			adresData.setString(1, plaats);
			adresData.execute();
			
			while (adresData.next()){
				adressen.add(new Adres
						(adresData.getInt(1), adresData.getString(2), adresData.getInt(3), adresData.getString(4), adresData.getString(5), adresData.getString(6)));
				logger.info("readAdresMetWoonplaats(String plaats); uitgevoerd");
			}
			adresData.close();
		
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return adressen;
	}

	@Override
	public Set<Adres> readAdresMetStraat(String straat, String plaats) {
		Connection connection = getConnection();
		logger.info("readAdresMetStraat(String straat, String plaats); gestart");
		Set<Adres> adressen = new LinkedHashSet<Adres>();
		try {
			RowSet adresData = new JdbcRowSetImpl(connection);
			adresData.setCommand("SELECT * FROM Adres WHERE straatnaam = ? AND woonplaats = ? ORDER BY huisnummer ASC");
			adresData.setString(1, straat);
			adresData.setString(2, plaats);
			adresData.execute();
			
			while (adresData.next()){
				adressen.add(new Adres(adresData.getInt(1), adresData.getString(2), adresData.getInt(3), adresData.getString(4), adresData.getString(5), adresData.getString(6)));
				logger.info("readAdresMetStraat(String straat, String plaats); uitgevoerd");
			}
			adresData.close();
		
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return adressen;
	}

	@Override
	public Set<Adres> readAdresMetStraatEnHuisnummer(String straat, int huisnummer, String toevoeging, String plaats) {
		Set<Adres> adressen = new LinkedHashSet<Adres>();
		for (Adres a: readAdresMetStraat(straat, plaats)){
			if (a.getHuisnummer() == huisnummer){
				if (a.getToevoeging() == toevoeging || a.getToevoeging() == null)
					adressen.add(a);
			}
		}		
		return adressen;
	}
	
	//overloaded zonder toevoeging
	public Set<Adres> readAdresMetStraatEnHuisnummer(String straat, int huisnummer, String plaats) {
		return readAdresMetStraatEnHuisnummer(straat, huisnummer, null, plaats);
	}
	
	@Override
	public Set<workshop.model.Klant> readKlantenMetAdresId(int adres_id) {
		Connection connection = getConnection();
		logger.info("readKlantenMetAdresId(int adres_id); gestart");
		Set<workshop.model.Klant> klantSet = new LinkedHashSet();
		try {
			RowSet rowSet = new JdbcRowSetImpl(connection);
			String query = "SELECT * FROM `Klant` " +
					"INNER JOIN klant_has_adres " +
					"ON Klant.klant_id = klant_has_adres.klant_id " +
					"WHERE klant_has_adres.adres_id = ? " +
					"ORDER BY achternaam ASC";
			rowSet.setCommand(query);
			rowSet.setInt(1, adres_id);
			rowSet.execute();

			while (rowSet.next()) {
				workshop.model.Klant klant = new workshop.model.Klant();
				klant.setId(rowSet.getInt(1));
				klant.setVoornaam(rowSet.getString(2));
				klant.setAchternaam(rowSet.getString(3));
				klant.setTussenvoegsel(rowSet.getString(4));
				klant.setEmail(rowSet.getString(5));
				
				klantSet.add(klant);
				logger.info("readKlantenMetAdresId(int adres_id); uitgevoerd");
			}
			rowSet.close();
			
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return klantSet;
	}

	@Override
	public void deleteAdres(int id) {
		Connection connection = getConnection();
		logger.info("deleteAdres(int id); gestart");
		
		try {		
			PreparedStatement deleteAdres = connection.prepareStatement
					("DELETE * FROM Adres WHERE adres_id = ?");
			deleteAdres.setInt(1, id);
			deleteAdres.executeUpdate();
			logger.info("deleteAdres(int id); uitgevoerd");
		
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Adres is verwijderd!");
	}
	
	@Override
	public void deleteKlantAdresPair(int klant_id, int adres_id){
		Connection connection = getConnection();	
		logger.info("deleteKlantAdresPair(int klant_id, int adres_id); gestart");
		try {
			PreparedStatement deleteKlantAdresPair = connection.prepareStatement
					("DELETE FROM klant_has_adres WHERE klant_id = ? AND adres_id = ?");
			deleteKlantAdresPair.setInt(1, klant_id);
			deleteKlantAdresPair.setInt(2, adres_id);
			deleteKlantAdresPair.executeUpdate();
			logger.info("deleteKlantAdresPair(int klant_id, int adres_id); uitgevoerd");

			
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Klant-Adres-koppeling is verwijderd!");
	}

}
