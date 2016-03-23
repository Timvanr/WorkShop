import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class KeuzeMenu {
	Klant klant;
	Adres adres;
	Klantbestand klantBestand;
	Bestelling bestelling;
	Artikel artikel;
	Bestellijst bestelLijst;
	Adreslijst adresLijst;

	private static Connection connection;
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	Scanner scInput = new Scanner(System.in);
	
	public static Connection getConnection(String user, String pass) throws SQLException {
		if (connection == null || connection.isClosed()){
			try {
				Class.forName("com.mysql.jdbc.Driver");			
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop?rewriteBatchedStatements=true&autoReconnect=true&useSSL=false", user, pass);				
			} catch (ClassNotFoundException e) {
			}
		}	
		return connection;
	}
	
	static final KeuzeMenu keuzeMenu = new KeuzeMenu();

	public KeuzeMenu(){
		klant = new Klant();
		adres = new Adres();
		bestelling = new Bestelling();
		artikel = new Artikel();
		klantBestand = new Klantbestand();
		bestelLijst = new Bestellijst();
		adresLijst = new Adreslijst();

	}

	public static void main(String[] args) throws IOException{
		keuzeMenu.startInlog();
	}

	public void startInlog() throws IOException{
		System.out.println("Inlog scherm: ");
		System.out.println("");
		System.out.print("Gebruikersnaam: ");
		String userName = scInput.next();
		System.out.print("Wachtwoord: ");
		String passWord = scInput.next();
		try {
			getConnection(userName, passWord);
			startHoofd();
		} catch (SQLException e) {
			System.out.println("");
			System.out.println("Username or password incorrect");
			System.out.println("");
			System.out.println("Try again!");
			startInlog();
		}		
	}

	private void startHoofd() throws IOException, SQLException {
		System.out.println("");
		System.out.println("HOOFDMENU: ");
		System.out.println("");
		System.out.println("1. CRUD-handelingen");
		System.out.println("2. Klasse-selectie");
		System.out.println("3. Uitloggen");
		System.out.println("4. Stoppen");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = scInput.nextInt();
		switch (keuze){
		case 1:
			startCrud();
			break;
		case 2:
			startClassSelectie();
			break;
		case 3:
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("");
			System.out.println("Succesvol uitgelogd!");
			System.out.println("");
			startInlog();
			break;
		case 4:
			exit();
			break;
		default:
			System.out.println("Ongeldige keuze!");
			startHoofd();
			break;
		}
	}

	private void startCrud() throws IOException, SQLException {
		System.out.println("");
		System.out.println("CRUD-HANDELINGEN");
		System.out.println("");
		System.out.println("1. Create klant");
		System.out.println("2. Read klant");
		System.out.println("3. Update klant");
		System.out.println("4. Delete klant");
		System.out.println("5: Hoofdmenu");
		System.out.println("");
		System.out.print("Maak een keuze:");
		int keuze = scInput.nextInt();
		switch (keuze){
		case 1:
			createKlantMenu();
			break;
		case 2:
			readKlantMenu();     
			break;
		case 3:
			updateKlantMenu();
			break;
		case 4:
			deleteKlantMenu();
			break;
		case 5:
			System.out.println("Terug naar het hoofdmenu...");
			startHoofd();
		default:
			System.out.println("Ongeldige keuze!");
			startCrud();
			break;
		}
	}

	private void startClassSelectie() throws IOException, SQLException{
		System.out.println("");
		System.out.println("KLASSE SELECTIE: ");
		System.out.println("");
		System.out.println("1. Klant klasse");
		System.out.println("2. Adres klasse");
		System.out.println("3. Bestelling klasse");
		System.out.println("4. Artikel klasse");
		System.out.println("5. Hoofdmenu");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = scInput.nextInt();
		switch (keuze){
		case 1:
			System.out.println("Verdere implementatie volgt");
			break;
		case 2:
			System.out.println("Verdere implementatie volgt");
			break;
		case 3:
			System.out.println("Verdere implementatie volgt");
			break;
		case 4:
			System.out.println("Verdere implementatie volgt");
			break;
		case 5:
			System.out.println("Terug naar het hoofdmenu...");
			startHoofd();
		default:
			System.out.println("Ongeldige keuze!");
			startClassSelectie();
			break;
		}
	}

		private void createKlantMenu() throws SQLException, IOException {
			System.out.println("SELECTEER EEN CREATE METHODE");
			System.out.println("");
			System.out.println("1. Create klant");
			System.out.println("2. Create klant & adres");
			System.out.println("3. Create klant, adres & bestelling");
			System.out.println("4. Create klant & bestelling");
			System.out.println("5. Hoofdmenu");
			System.out.println("");
			System.out.print("Maak een keuze: ");
			int keuze = scInput.nextInt();
			switch (keuze){
			case 1:
				klant = new Klant(voornaamPrompt(), tussenvoegselPrompt(), achternaamPrompt(), emailPrompt());
				klantBestand.createKlant(klant);
				break;
			case 2:
				klant = new Klant(voornaamPrompt(), tussenvoegselPrompt(), achternaamPrompt(), emailPrompt());
				adres = new Adres(straatnaamPrompt(), huisnummerPrompt(), toevoegingPrompt(), postcodePrompt(), woonplaatsPrompt());
				klantBestand.createKlantEnAdres(klant, adres);
				break;
			case 3:
				klant = new Klant(voornaamPrompt(), tussenvoegselPrompt(), achternaamPrompt(), emailPrompt());
				adres = new Adres(straatnaamPrompt(), huisnummerPrompt(), toevoegingPrompt(), postcodePrompt(), woonplaatsPrompt());
				bestelling = new Bestelling();
				System.out.println("Hoeveel artikelen wilt u toevoegen: ");
				int aantal = scInput.nextInt();
				for (int i = 0; i < aantal; i++){
					artikel = new Artikel(arikelIdPrompt(), artikelNaamPrompt(), artikelPrijsPrompt(), artikelAantalPrompt());
					bestelling.voegArtikelToeAanBestelling(artikel);
				}
				klantBestand.createKlantEnAdresEnBestelling(klant, adres, bestelling);
				break;
			case 4:
				// klantBestand.createKlantEnBestelling();
				break;
			case 5:
				System.out.println("Terug naar het hoofdmenu...");
				startHoofd();
			default:
				System.out.println("Ongeldige keuze!");
				createKlantMenu();
				break;
			}
		}
		
		private void readKlantMenu() throws IOException, SQLException {
			System.out.println("");
			System.out.println("READ KLANT MENU: ");
			System.out.println("");
			System.out.println("1. Read klant");
			System.out.println("2. Read klant-adres");
			System.out.println("3. Read klant-bestelling");
			System.out.println("4. Read klant-, adres en bestelling");
			System.out.println("5. Hoofdmenu");
			System.out.println("");
			System.out.print("Maak een keuze: ");
			int keuze = scInput.nextInt();
			switch (keuze){
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			default:
				System.out.println("Ongeldige keuze!");
				startHoofd();
				break;
			}
		}
	
	private void updateKlantMenu() throws IOException, SQLException{
		System.out.println("SELECTEER EEN UPDATE METHODE");
		System.out.println("");
		System.out.println("1. Update klantnaam");
		System.out.println("2. Update klantadres");
		System.out.println("3. Create klantnaam & adres");
		System.out.println("4. Hoofdmenu");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = scInput.nextInt();
		switch (keuze){
		case 1:
			updateKlantnaam();
			break;
		case 2:
			updateKlantadres();
			break;
		case 3:
			updateKlantnaamadres();
			break;
		case 4:
			System.out.println("Terug naar het hoofdmenu...");
			startHoofd();
		default:
			System.out.println("Ongeldige keuze!");
			updateKlantMenu();
			break;
		}
	}

	private void updateKlantnaam(){
		System.out.print("Geef uw klant ID: ");
		int id = scInput.nextInt();
		try {
			klantBestand.UpdateKlantNaam(id);
		} catch (SQLException e) {		
			e.printStackTrace();
		}
	}
	
	private void updateKlantadres(){
		System.out.print("Geef uw klant ID: ");
		int id = scInput.nextInt();
		System.out.println("");
	}
	
	private void updateKlantnaamadres() {
		System.out.print("Geef uw klant ID: ");
		int id = scInput.nextInt();
		try {
			klantBestand.UpdateKlantNaam(id);
			// adresLijst.UpdateAdres(); // of zoiets nog even kijken wat dit wordt...
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteKlantMenu() throws IOException {
		System.out.println("");
		System.out.println("SELECTEER EEN DELETE METHODE");
		System.out.println("");
		System.out.println("1. Delete klant met ID-nummer");
		System.out.println("2. Delete klant met naam");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = scInput.nextInt();
		switch (keuze){
		case 1:
			deleteKlantID();
			break;
		case 2:
			deleteKlantMetNaam();
			break;
		default:
			System.out.println("Ongeldige keuze!");
			deleteKlantMenu();
			break;
		}
	}

	private void deleteKlantMetNaam() throws IOException {
		System.out.println("Vul de gevraagde gegevens correct in (alle uw gegevens worden verwijderd): ");
		String voornaam = voornaamPrompt();
		String tussenvoegsel = tussenvoegselPrompt();
		String achternaam = achternaamPrompt();
		try {
			klantBestand.deleteAllFromKlantNaam(voornaam, achternaam, tussenvoegsel);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteKlantID() throws IOException {
		int id = id_Prompt();
		try {
			klantBestand.deleteAllFromKlantId(id);
			System.out.print("Alle uw gegevens zijn verwijderd");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void exit() {
		// flush.screen(); //moet de juist code nog vinden om beeldscherm leeg te krijgen
		System.out.println("Uw bewerking is beeindigd.");

	}
	
	private int id_Prompt() throws IOException {
		System.out.println("Geef uw klant ID: ");
		String IDstr = input.readLine();
		int id = Integer.parseInt(IDstr);
		return id;
	}
	private String voornaamPrompt() throws IOException {
		System.out.print("Voornaam: ");
		String voornaam = input.readLine();
		return voornaam;
		}
	private String tussenvoegselPrompt() throws IOException {
		System.out.print("Tussenvoegsel: ");
		String tussenvoegsel = input.readLine();
		return tussenvoegsel;
		}
	private String achternaamPrompt() throws IOException {
		System.out.print("Achternaam: ");
		String achternaam = input.readLine();
		return achternaam;
		}
	private String emailPrompt() throws IOException {
		System.out.print("Email: ");
		String email = input.readLine();
		return email;
		}
	private String straatnaamPrompt() throws IOException {
		System.out.print("Straatnaam: ");
		String straatnaam = input.readLine();
		return straatnaam;
		}
	private int huisnummerPrompt() throws IOException {
		System.out.print("Huisnummer: ");
		String huisnummerstr = input.readLine();
		int huisnummer = Integer.parseInt(huisnummerstr);
		return huisnummer;
	}
	private String toevoegingPrompt() throws IOException {
		System.out.print("Toevoeging: ");
		String toevoeging = input.readLine();
		return toevoeging;
		}
	private String postcodePrompt() throws IOException {
		System.out.print("Postcode: ");
		String postcode = input.readLine();
		return postcode;
		}
	private String woonplaatsPrompt() throws IOException {
		System.out.print("Woonplaats: ");
		String woonplaats = input.readLine();
		return woonplaats;
		}
	private int artikelAantalPrompt() throws IOException {
		System.out.print("Hoeveel wilt u van dit atikel: ");
		String artikelAantalstr = input.readLine();
		int artikelAantal = Integer.parseInt(artikelAantalstr);
		return artikelAantal;
	}
	private BigDecimal artikelPrijsPrompt() throws IOException {
		System.out.print("Wat is de prijs van dit artikel: ");
		String artikelPrijsstr = input.readLine();
		BigDecimal artikelPrijs = new BigDecimal(artikelPrijsstr);
		return artikelPrijs;
	}
	private String artikelNaamPrompt() throws IOException {
		System.out.print("Welk artikel wilt u toevoegen: ");
		String artikelNaam = input.readLine();
		return artikelNaam;
	}
	private int arikelIdPrompt() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
}
