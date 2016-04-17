import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.sql.RowSet;

import com.sun.rowset.JdbcRowSetImpl;

public class KlantDAOFireBird extends Klantbestand implements KlantDAO {
	
	
	public static Connection getConnection(){
		Connection connection = DatabaseConnection.getPooledConnection();
		return connection;		
	}
   	
	public KlantDAOFireBird() {     
	}
	
	@Override
	public int createKlant(Klant klant){
		Connection connection = getConnection();
		logger.info("createKlant(Klant klant); gestart");
		String insertKlantNaamString = "INSERT INTO klant (voornaam, tussenvoegsel, achternaam, email) "
				+ "values (?,?,?,?) RETURNING klant_id ;";
		PreparedStatement insertKlantNaam = null;
		ResultSet resultSet = null;
		int klantId = 0;
		
		try {
			insertKlantNaam = connection.prepareStatement(insertKlantNaamString);
			insertKlantNaam.setString(1, klant.getVoornaam());
			insertKlantNaam.setString(2, klant.getTussenvoegsel());
			insertKlantNaam.setString(3, klant.getAchternaam());
			insertKlantNaam.setString(4, klant.getEmail());
			resultSet = insertKlantNaam.executeQuery();
			while (resultSet .next()) {
				klantId = resultSet.getInt("klant_id");
				System.out.println("Klant met klantnummer " + klantId + " is succesvol aangemaakt");
				logger.info("CreataKlant(Klant klant); uitgevoerd:"
						+ "klant met " + klantId + "toegevoegd");
			}						
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return klantId;
	}
	
	public void createKlantEnAdres(Klant klant, Adres adres) {
		AdresDAOFireBird al = new AdresDAOFireBird();
		int klant_id = createKlant(klant);
		al.createAdres(klant_id, adres);
	}

	@Override
	public void createKlantEnAdresEnBestelling(Klant klant, Adres adres, Bestelling bestelling) throws SQLException {
		AdresDAOFireBird al = new AdresDAOFireBird();
		BestellingDAOFireBird bl = new BestellingDAOFireBird();
		int klant_id = createKlant(klant);
		al.createAdres(klant_id, adres);
		bl.voegBestellingToe(klant_id, bestelling);
		
	}

	@Override
	public void createKlantEnBestelling(Klant klant, Bestelling bestelling) throws SQLException {
		BestellingDAOFireBird bl = new BestellingDAOFireBird();
		int klant_id = createKlant(klant);
		bl.voegBestellingToe(klant_id, bestelling);
				
		}
	
	@Override
	public Set<Klant> readAll(){
			Connection connection = getConnection();		
			LinkedHashSet<Klant> klantset = new LinkedHashSet<>();
			String query = "Select * from klant";
			Klant klant = null;
			RowSet rowSet = null;

			try {
				rowSet = new JdbcRowSetImpl(connection);
				rowSet.setCommand(query);
				rowSet.execute();

				if (!rowSet.isBeforeFirst()) {
					System.out.println("There are no records");
					logger.info("Geen gegevens gevonden");
					return klantset;
				}

				while (rowSet.next()) {
					klant = new Klant();
					klant.setId(rowSet.getInt("klant_id"));
					klant.setVoornaam(rowSet.getString("voornaam"));
					klant.setTussenvoegsel(rowSet.getString("tussenvoegsel"));
					klant.setAchternaam(rowSet.getString("achternaam"));
					klant.setEmail(rowSet.getString("email"));
					klantset.add(klant);
					logger.info("readKlantWithFirstName(String voornaam); uitgevoerd");
				}
				
			} catch (SQLException ex) {
				logger.error(ex.getMessage());
				ex.printStackTrace();
			} finally {
				try {
					rowSet.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println();
			}
			return klantset;

		}

}
