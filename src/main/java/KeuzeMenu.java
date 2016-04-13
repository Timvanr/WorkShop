
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeuzeMenu {
	
	Service service = new Service();
			
	static Logger logger = LoggerFactory.getLogger(KeuzeMenu.class);
	Connection connection = null;
	Scanner scInput = new Scanner(System.in);
	
	static final KeuzeMenu keuzeMenu = new KeuzeMenu();

	public KeuzeMenu(){
		
	}

	public static void main(String[] args) throws IOException{
		keuzeMenu.startInlog();
	}

	public void startInlog() throws IOException{
		logger.info("test3 {}");
		System.out.println("Inlog scherm: ");
		System.out.println("");
		try{
			DatabaseConnection.setDriverClass("com.mysql.jdbc.Driver");
			DatabaseConnection.setURL("jdbc:mysql://localhost:3306/workshop");
			System.out.print("Gebruikersnaam: ");
			DatabaseConnection.setUSERNAME(scInput.next());
			System.out.print("Wachtwoord: ");
			DatabaseConnection.setPW(scInput.next());
			service.kiesConnectie();
			connection = DatabaseConnection.getPooledConnection();
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
		System.out.println("3. Data opslag selectie");
		System.out.println("4. Uitloggen");
		System.out.println("5. Stoppen");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = scInput.nextInt();
		switch (keuze){
		case 1:	startCrud();
			break;
		case 2:	startClassSelectie();
			break;
		case 3: startDataOpslagSelectie();
			break;
		case 4:
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
		case 5:	exit();
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
			case 1:	service.getKlantcontrol().createKlant();	break;
			case 2:	service.getKlantcontrol().createKlantenAdres(); break;
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
				case 1:	service.getKlantcontrol().printKlantmetId();	break;
				case 2:	service.getKlantcontrol().printKlantmetNaam(); break;
				case 3:	service.getKlantcontrol().printKlantmetVoornaam(); break;
				case 4:	service.getKlantcontrol().printAlleKlanten(); break;
				case 5:	service.getAdrescontrol().printAdresMetId();	break;
				case 6:	service.getAdrescontrol().printAdresMetKlantId(); break;
				case 7:	service.getAdrescontrol().printAdresMetStraatnaam();	break;
				case 8:	service.getAdrescontrol().printAdresMetPCEnHuisnummer();	break;
				case 9:	service.getAdrescontrol().printAlleAdressen(); break;
				case 10:service.getBestellingcontrol().printBestellingmetId(); break;
				case 11:service.getBestellingcontrol().printAlleBestellingen(); break;
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
		case 1:	service.getKlantcontrol().updateKlant(); break;
		case 2:	service.getKlantcontrol().updateKlantEmail(); break;
		case 3:	service.getAdrescontrol().updateAdres();	break;
		case 4: service.getBestellingcontrol().updateBestelling();
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
		case 1:	service.getKlantcontrol().deleteKlantmetKlantId();	break;
		case 2:	service.getKlantcontrol().deleteKlantmetKlantNaam();	break;
		case 3: service.getAdrescontrol().deleteAdresvanKlant(); break;
		//case 4: bestellingcontrol.deletebestelling
		//case 5: bestellingcontrol.deleteartikelvanbestelling
		default:
			System.out.println("Ongeldige keuze!");
			deleteMenu();
			break;
		}
	}

	private void startDataOpslagSelectie() throws IOException, SQLException{
		System.out.println("\n SELECTEER EEN OPSLAGMETHODE \n");
		System.out.println("1. via MySql");
		System.out.println("2. via FireBird");
		System.out.println("3. via JSON");
		System.out.println("4. via XML");
		System.out.println("5. Hoofdmenu \n");
		int keuze = scInput.nextInt();
		
		if (keuze == 1 || keuze == 2 || keuze == 3 || keuze == 4){
			service.kiesDataOpslag(keuze);
			startHoofd();
		}
		else if (keuze == 5){
			startHoofd();
		}
		else {
			System.out.println("Geef een getal van 1 t/m 5 op aub");
			startDataOpslagSelectie();
		}
		
		
		
	}

	private void exit() {
		System.out.println("Uw bewerking is beeindigd.");	
		System.exit(1);
		}
}
