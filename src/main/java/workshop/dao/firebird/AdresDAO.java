import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdresDAOFireBird extends Adreslijst implements AdresDAO {
	static Logger logger = LoggerFactory.getLogger(AdresDAOFireBird.class);

	
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

			if (resultSet.isBeforeFirst()) {
				resultSet.next();
				insertKlantHasAdres.setInt(1, klant_id);
				insertKlantHasAdres.setInt(2, resultSet.getInt("adres_id"));
				insertKlantHasAdres.executeUpdate();
				logger.info("createAdres(int klant_id, Adres adres); uitgevoerd");

				}
			
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
			//System.out.println(ex.getErrorCode());
			if (ex.getErrorCode() == 335544665){
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
	public void updateAdres(int klant_id, Adres adres){		
		createAdres(klant_id, adres);		
		System.out.println("Adresgegevens gewijzigd");
	}

}

