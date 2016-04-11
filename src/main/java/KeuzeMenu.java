
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeuzeMenu {
	Klantbestand klantbestand = new Klantbestand();
	Service service = new Service();
	Adreslijst adreslijst = new Adreslijst();
	Bestellijst bestellijst = new Bestellijst();
	ArtikelLijst artikellijst = new ArtikelLijst();
	AdresController adrescontrol = new AdresController(service, adreslijst);
	KlantController klantcontrol = new KlantController(service, klantbestand);
	BestellingController bestellingcontrol = new BestellingController(service, bestellijst);
		
	static Logger logger = LoggerFactory.getLogger(KeuzeMenu.class);

	static String URL;
	static String PW;
	static String USERNAME;
	static int connectieKeuze;

	private static Connection connection;
	Scanner scInput = new Scanner(System.in);
	
	static final KeuzeMenu keuzeMenu = new KeuzeMenu();

	public KeuzeMenu(){
		/*klant = new Klant();
		adres = new Adres();
		bestelling = new Bestelling();
		artikel = new Artikel();
	*/
	}

	public static void main(String[] args) throws IOException{
		keuzeMenu.startInlog();
	}

	public void startInlog() throws IOException{
		logger.info("test3 {}");
		System.out.println("Inlog scherm: ");
		System.out.println("");
		/*System.out.print("Gebruikersnaam: ");
		String userName = scInput.next();
		System.out.print("Wachtwoord: ");
		String passWord = scInput.next();
		*/
		try {
			System.out.println("Wie bent u? \n1. Tim \n2. Maurice \n3. Sander \n ");
			int user = scInput.nextInt();
			switch (user) {
			case 1:	URL = "jdbc:mysql://localhost:3306/workshop";
					PW = "tiger";
					USERNAME = "scott";
					break;
			case 2: URL = "jdbc:mysql://localhost:3306/adresboek";
					PW = "komt_ie";
					USERNAME = "root";
					break;
			case 3: URL = "jdbc:mysql://localhost:3306/workshop";
					PW = "FrIkandel";
					USERNAME = "sandermegens";
					break;
			}
			connectieKeuze = service.kiesConnectie();
			//getConnection(userName, passWord);
			startHoofd();
		} catch (SQLException e) {
			System.out.println("");
			System.out.println("Username or password fout");
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
		case 1:	startCrud();
			break;
		case 2:	startClassSelectie();
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
		case 4:	exit();
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
		System.out.println("1. Create menu");
		System.out.println("2. Read menu");
		System.out.println("3. Update menu");
		System.out.println("4. Delete menu");
		System.out.println("5: Hoofdmenu");
		System.out.println("");
		System.out.print("Maak een keuze:");
		int keuze = scInput.nextInt();
		switch (keuze){
		case 1:	createMenu();
			break;
		case 2:	readMenu();     
			break;
		case 3:	updateMenu();
			break;
		case 4:	deleteMenu();
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

		private void createMenu() throws SQLException, IOException {
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
			case 1:	klantcontrol.createKlant();	break;
			case 2:	klantcontrol.createKlantenAdres(); break;
			/*case 3: BESTELLING EN ARTIKELEN NOG OP ELKAAR AFStEMMEN, KLOPT VOLGENS MIJ NOG NIET.
				klant = new Klant(voornaamPrompt(), tussenvoegselPrompt(), achternaamPrompt(), emailPrompt());
				adres = new Adres(straatnaamPrompt(), huisnummerPrompt(), toevoegingPrompt(), postcodePrompt(), woonplaatsPrompt());
				bestelling = new Bestelling();
				System.out.println("Hoeveel artikelen wilt u toevoegen: ");
				int aantal = scInput.nextInt();
				/*for (int i = 0; i < aantal; i++){
					artikel = new Artikel(arikelIdPrompt(), artikelNaamPrompt(), artikelPrijsPrompt(), artikelAantalPrompt());
					bestelling.voegArtikelToeAanBestelling(artikel);
				}
				klantBestand.createKlantEnAdresEnBestelling(klant, adres, bestelling);
				break;
			case 4:
				klant = new Klant(voornaamPrompt(), tussenvoegselPrompt(), achternaamPrompt(), emailPrompt());
				bestelling = new Bestelling();
				System.out.println("Hoeveel artikelen wilt u toevoegen: ");
				int aantal2 = scInput.nextInt();
				/*for (int i = 0; i < aantal2; i++){
					artikel = new Artikel(arikelIdPrompt(), artikelNaamPrompt(), artikelPrijsPrompt(), artikelAantalPrompt());
					bestelling.voegArtikelToeAanBestelling(artikel);
				}
				klantBestand.createKlantEnBestelling(klant, bestelling);
				break;*/
			case 5:
				System.out.println("Terug naar het hoofdmenu...");
				startHoofd();
			default:
				System.out.println("Ongeldige keuze!");
				createMenu();
				break;
			}
		}
						
		private void readMenu() throws IOException, SQLException {
			System.out.println("");
			System.out.println("SELECTEER EEN METHODE: ");
			System.out.println("");
			System.out.println("1. Read Klant met klant id");
			System.out.println("2. Read Klant met voor- en achternaam");
			System.out.println("3. Read Klant(en) met voornaam");
			System.out.println("4. Read alle Klanten");
			System.out.println("5. Read Adres met adres id");
			System.out.println("6. Read Adres met klant id");
			System.out.println("7. Read Adres(sen) met straatnaam en woonplaats");
			System.out.println("8. Read Adres met postcode en huisnummer");
			System.out.println("9. Read alle Adressen");
			System.out.println("10. Read Bestelling met bestelling id");
			System.out.println("11. Read alle bestellingen");
			System.out.println("12. Hoofdmenu");
			System.out.println("");
			System.out.print("Maak een keuze: ");
			int keuze = scInput.nextInt();
			switch (keuze){
				case 1:	klantcontrol.printKlantmetId();	break;
				case 2:	klantcontrol.printKlantmetNaam(); break;
				case 3:	klantcontrol.printKlantmetVoornaam(); break;
				case 4:	klantcontrol.printAlleKlanten(); break;
				case 5:	adrescontrol.printAdresMetId();	break;
				case 6:	adrescontrol.printAdresMetKlantId(); break;
				case 7:	adrescontrol.printAdresMetStraatnaam();	break;
				case 8:	adrescontrol.printAdresMetPCEnHuisnummer();	break;
				case 9:	adrescontrol.printAlleAdressen(); break;
				case 10:bestellingcontrol.printBestellingmetId(); break;
				case 11:bestellingcontrol.printAlleBestellingen(); break;
				case 12:
					System.out.println("Terug naar het hoofdmenu...");
					startHoofd();
					default:
						System.out.println("Ongeldige keuze!");
						startHoofd();
						break;
			}
		}
	
	private void updateMenu() throws IOException, SQLException{
		System.out.println("SELECTEER EEN UPDATE METHODE");
		System.out.println("");
		System.out.println("1. Update Klantnaam");
		System.out.println("2. Update E-mail adres");
		System.out.println("2. Update Adres");
		System.out.println("3. Update Bestelling");
		System.out.println("4. Hoofdmenu");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = scInput.nextInt();
		switch (keuze){
		case 1:	klantcontrol.updateKlant(); break;
		case 2:	klantcontrol.updateKlantEmail(); break;
		case 3:	adrescontrol.updateAdres();	break;
		case 4: bestellingcontrol.updateBestelling();
		case 5:
			System.out.println("Terug naar het hoofdmenu...");
			startHoofd();
		default:
			System.out.println("Ongeldige keuze!");
			updateMenu();
			break;
		}
	}

	private void deleteMenu() throws IOException, SQLException {
		System.out.println("");
		System.out.println("SELECTEER EEN DELETE METHODE");
		System.out.println("");
		System.out.println("1. Delete Klant met ID-nummer");
		System.out.println("2. Delete Klant met naam");
		System.out.println("3. Delete Adres van Klant");
		System.out.println("4. Delete Bestelling van Klant");
		System.out.println("5. Delete Artikel uit Bestelling");
		System.out.println("6. Hoofdmenu");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = scInput.nextInt();
		switch (keuze){
		case 1:	klantcontrol.deleteKlantmetKlantId();	break;
		case 2:	klantcontrol.deleteKlantmetKlantNaam();	break;
		case 3: adrescontrol.deleteAdresvanKlant(); break;
		//case 4: bestellingcontrol.deletebestelling
		//case 5: bestellingcontrol.deleteartikelvanbestelling
		default:
			System.out.println("Ongeldige keuze!");
			deleteMenu();
			break;
		}
	}

	

	private void exit() {
		System.out.println("Uw bewerking is beeindigd.");	
		System.exit(1);
		}
}
