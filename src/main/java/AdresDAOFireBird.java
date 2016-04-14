import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdresDAOFireBird extends Adreslijst implements AdresDAO {
	static final Logger LOG = LoggerFactory.getLogger(AdresDAOFireBird.class);

	
	public static Connection getConnection(){
		Connection connection = DatabaseConnection.getPooledConnection();
		return connection;
	}
   	
	public AdresDAOFireBird() {     
	}
	
	@Override
	public void createAdres(int klant_id, Adres adres){
		Connection connection = getConnection();
		logger.info("createAdres(int klant_id, Adres adres); gestart");
		String insertAdresString = "INSERT INTO Adres (straatnaam, huisnummer, toevoeging, postcode, woonplaats) VALUES (?,?,?,?,?) RETURNING adres_id ;";
		String insertKlantHasAdresString = "INSERT INTO klant_has_adres (klant_id, adres_id) values (?,?);";
		PreparedStatement insertAdres = null;
		PreparedStatement insertKlantHasAdres = null;
		int adres_id = 0;

		try {
			insertAdres = connection.prepareStatement(insertAdresString);
			insertKlantHasAdres = connection.prepareStatement(insertKlantHasAdresString);
			insertAdres.setString(1, adres.getStraatnaam());
			insertAdres.setInt(2, adres.getHuisnummer());
			insertAdres.setString(3, adres.getToevoeging());
			insertAdres.setString(4, adres.getPostcode());
			insertAdres.setString(5, adres.getWoonplaats());
			ResultSet resultSet = insertAdres.executeQuery();
			System.out.println("Adres created!");

			while (resultSet .next()) {
				adres_id = resultSet.getInt("adres_id");

				if (resultSet.isBeforeFirst()) {
					resultSet.next();
					insertKlantHasAdres.setInt(1, klant_id);
					insertKlantHasAdres.setInt(2, adres_id);
					insertKlantHasAdres.executeUpdate();
					logger.info("createAdres(int klant_id, Adres adres); uitgevoerd");

				}
			}
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
	} 	
	

	@Override
	public void updateAdres(int klant_id, Adres adres){		
		createAdres(klant_id, adres);		
		System.out.println("Adresgegevens gewijzigd");
	}

}

