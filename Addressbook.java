import java.sql.*;

public class Addressbook {
	private static Connection connection;
	
	public Addressbook() {
		// Ensure the connection is initialized
		Addressbook.getConnection();
	}
	//test connection
	public static boolean isNewConnectionNeeded() throws SQLException{
		boolean isNewConnectionNeeded = false;
		
		if (connection == null){
			isNewConnectionNeeded = true;
		}else if (connection.isClosed()){
			isNewConnectionNeeded = true;
		}
				
		return isNewConnectionNeeded;
	}
	
	public static Connection getConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
						
			if (isNewConnectionNeeded()){
				System.out.print("Driver loaded... ");
				connection = DriverManager.getConnection("jdbc:mysql://localhost/Adresboek", "root", "komt_ie");
				System.out.println("Database connected!");
			}
		}catch (SQLException e){
			e.printStackTrace();
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		return connection;
	}
	
	public void close() throws SQLException{
		connection.close();
		System.out.println("Connection closed!");
	}
		
	public void voegKlantToe(Klant klant) throws SQLException{
		getConnection();
		
		PreparedStatement voegKlantToe = connection.prepareStatement
				("INSERT INTO Klant (voornaam, tussenvoegsel, achternaam, straat, huisnummer, toevoeging, postcode, woonplaats, email)\n" +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		voegKlantToe.setString(1, klant.getVoornaam());
		voegKlantToe.setString(2, klant.getTussenvoegsel());
		voegKlantToe.setString(3, klant.getAchternaam());
		voegKlantToe.setString(4, klant.getStraatnaam());
		voegKlantToe.setInt(5, klant.getHuisnummer());
		voegKlantToe.setString(6, klant.getToevoeging());
		voegKlantToe.setString(7, klant.getPostcode());
		voegKlantToe.setString(8, klant.getWoonplaats());
		voegKlantToe.setString(9, klant.getEmail());
		voegKlantToe.executeUpdate();
				
		System.out.println(klant.toString());
		System.out.println("is toegevoegd");
		System.out.println();
		
	}
	
	public static Klant haalKlant(int klant_id) throws SQLException{
		getConnection();
		
		PreparedStatement haalKlant = connection.prepareStatement
				("SELECT voornaam, tussenvoegsel, achternaam, straat, huisnummer, toevoeging, postcode, woonplaats, email FROM Klant WHERE klant_id = ?");
		
		haalKlant.setInt(1, klant_id);
		ResultSet gegevens = haalKlant.executeQuery();
		
		if (gegevens.next()){
			Naam naam = new Naam(gegevens.getString(1), gegevens.getString(2), gegevens.getString(3));
		
			Adres adres = new Adres(gegevens.getString(4), gegevens.getInt(5), gegevens.getString(6), gegevens.getString(7), gegevens.getString(8));
		
			String email = gegevens.getString(9);
			
			Klant klant = new Klant(naam, adres, email);
			
			return klant;
		}else{
			System.out.println("Deze klant is niet bekend in de database!");
		}
		System.out.println();
		return null;
	}
	
	public void printGegevens(int klant_id) throws SQLException{
		Klant klant = haalKlant(klant_id);
		
		System.out.println("De volgende klantgegevens zijn bij ons bekend:");
		klant.toString();
	}
	
	public void updateNaam (int klant_id, Naam naam) throws SQLException{
		getConnection();
		
		PreparedStatement updateNaam = connection.prepareStatement
				("UPDATE Klant SET voornaam = ?, tussenvoegsel = ?, achternaam = ? WHERE klant_id = ?");
		
		updateNaam.setString(1, naam.getVoornaam());
		updateNaam.setString(2, naam.getTussenvoegsel());
		updateNaam.setString(3, naam.getAchternaam());
		updateNaam.setInt(4, klant_id);
		updateNaam.executeUpdate();
		
		System.out.println("Naam is veranderd!");
		System.out.println();
	}
	
	public void updateAdres (int klant_id, Adres adres) throws SQLException{
		getConnection();
		
		PreparedStatement updateAdres = connection.prepareStatement
				("UPDATE Klant SET straat = ?, huisnummer = ?, toevoeging = ?, postcode = ?, woonplaats = ? WHERE klant_id = ?");
		
		updateAdres.setString(1, adres.getStraatnaam());
		updateAdres.setInt(2, adres.getHuisnummer());
		updateAdres.setString(3, adres.getToevoeging());
		updateAdres.setString(4, adres.getPostcode());
		updateAdres.setString(5, adres.getWoonplaats());
		updateAdres.setInt(6, klant_id);
		updateAdres.executeUpdate();
		
		System.out.println("Adres is veranderd!");
		System.out.println();
	}
	
	public void updateEmail(int klant_id, String email) throws SQLException{
		getConnection();
		
		PreparedStatement updateEmail = connection.prepareStatement
				("UPDATE Klant SET email = ? WHERE klant_id = ?");
		
		updateEmail.setString(1, email);
		updateEmail.setInt(2, klant_id);
		updateEmail.executeUpdate();
		
		System.out.println("E-mailadres is veranderd!");
		System.out.println();
	}
	
	public void lijstVanKlanten() throws SQLException{
		getConnection();
		
		PreparedStatement listAll = connection.prepareStatement
				("SELECT * FROM Klant");
		ResultSet allCustomers = listAll.executeQuery();
		
		System.out.println("Dit zijn alle klanten:");
		while (allCustomers.next()){
			System.out.println(allCustomers.getInt(1) + " " + allCustomers.getString(2) + " " + allCustomers.getString(3) + " " 
					+ allCustomers.getString(4) + " " + allCustomers.getString(5) + " " + allCustomers.getInt(6) + " " 
					+ allCustomers.getString(7) + " " + allCustomers.getString(8) + " " + allCustomers.getString(9) + " " 
					+ allCustomers.getString(10));
		}
		System.out.println();
	}
	
	public void verwijderKlant(int klant_id) throws SQLException{
		getConnection();
		
		PreparedStatement verwijdering = connection.prepareStatement
				("DELETE FROM Klant WHERE klant_id = ?");
		
		verwijdering.setInt(1, klant_id);
		verwijdering.executeUpdate();
		
		System.out.println("Klant is verwijderd!");
		System.out.println();
	}
}
