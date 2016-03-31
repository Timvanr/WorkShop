import java.sql.*;


import java.util.Scanner;
public class DataRandomizer {

	public static void main(String[] args) {
		
		String[] voornamen = new String[] {"Liam", "Daan", "Sem", "Lucas", "Sophie", "Julia", "Emma", "Sara", "Finn", "Jesse"};
		String[] tussenvoegsel = new String[] {"van", "de", "van der", "", "van de"};
		String[] achternamen = new String[] {"Jansen", "Bakker", "Visser", "Boer", "Smit", "Jong", "Berg", "Groot", "Vos", "Stappen"};
		String[] straatnamen = new String[] {"Dorpsstraat", "Kerkstraat", "van Goghstraat", "Akkerstraat", "Fregatweg", "Alpenweg", "Beukenlaan", "Lindenstraat", "Versteweg", "Hoogstraat"};
		String[] woonplaatsen= new String[] {"Amsterdam", "Rotterdam", "Utrecht", "Eindhoven", "Zwolle", "Den Haag", "Arnhem", "Nijmegen", "Groningen", "Lelystad"};
		
		String query = "INSERT INTO klant (voornaam, tussenvoegsel, achternaam, email, straatnaam, postcode, toevoeging, huisnummer, woonplaats)" + 
		" values (?,?,?,?,?,?,?,?,?)";
		
		Connection connection = null;
		PreparedStatement insertRandoms = null;
		System.out.println("enter nr of randoms");
		
		try{
			Scanner input = new Scanner(System.in);
			int randoms = input.nextInt();
			connection = DatabaseConnection.getPooledConnection();
			insertRandoms = connection.prepareStatement(query);
						
			for (int i = 1; i <= randoms; i++){
				char c1 = (char)((int)'A'+Math.random()*((int)'Z'-(int)'A'+1));
				char c2 = (char)((int)'A'+Math.random()*((int)'Z'-(int)'A'+1));
				insertRandoms.setString(1, voornamen[(int)(Math.random() * 9)]);
				insertRandoms.setString(2, tussenvoegsel[(int)(Math.random() * 4)]);
				insertRandoms.setString(3, achternamen[(int)(Math.random() * 9)]);
				insertRandoms.setString(4, null);
				insertRandoms.setString(5, straatnamen[(int)(Math.random() * 9)]);
				insertRandoms.setString(6, (int)(Math.random()*7500 + 1000) + "" + c1 + c2);
				insertRandoms.setString(7, null);
				insertRandoms.setInt(8, (int)(Math.random() * 100));
				insertRandoms.setString(9, woonplaatsen[(int)(Math.random()*9)]);
				insertRandoms.addBatch();
				
				if (i%50 == 0){
					insertRandoms.executeBatch();
				}
				
			}
			insertRandoms.executeBatch();
			input.close();
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		finally{
			try{
				if (insertRandoms != null){
					insertRandoms.close();
				}
				connection.close();
			}
			catch (SQLException ex2){
				ex2.printStackTrace();
			}
		}
		

	}
	
}
