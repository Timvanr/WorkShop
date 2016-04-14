import java.sql.*;

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

	public void createKlantEnAdresEnBestelling(Klant klant, Adres adres, Bestelling bestelling) {
		AdresDAOFireBird al = new AdresDAOFireBird();
		BestellingDAOFireBird bl = new BestellingDAOFireBird();
		int klant_id = createKlant(klant);
		al.createAdres(klant_id, adres);
		bl.voegBestellingToe(bestelling);

	}

	public void createKlantEnBestelling(Klant klant, Bestelling bestelling) {
		BestellingDAOFireBird bl = new BestellingDAOFireBird();
		createKlant(klant);
		bl.voegBestellingToe(bestelling);

	}

}
