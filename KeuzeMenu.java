import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class KeuzeMenu {
	Scanner input;
	Klant klant;
	KlantDAO klantDAO;
	Bestelling bestelling;
	Artikel artikel;
	BestellingDAO bestellingDAO;

	static final KeuzeMenu keuzeMenu = new KeuzeMenu();

	public KeuzeMenu(){
		input = new Scanner(System.in);
		klant = new Klant();
		klantDAO = new KlantDAO();
		bestelling = new Bestelling();
		artikel = new Artikel();
		bestellingDAO = new BestellingDAO();
	}

	public static void main(String[] args){
		keuzeMenu.startInlog();
	}

	public void startInlog(){
		System.out.println("Inlog scherm: ");
		System.out.println("");
		System.out.print("Gebruikersnaam: ");
		String user = input.next();
		System.out.print("Wachtwoord: ");
		String pass = input.next();

		startHoofd();
	}

	private void startHoofd() {
		System.out.println("");
		System.out.println("HOOFDMENU: ");
		System.out.println("");
		System.out.println("1. CRUD-handelingen");
		System.out.println("2. Klasse-selectie");
		System.out.println("3. Uitloggen");
		System.out.println("4. Stoppen");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = input.nextInt();
		switch (keuze){
		case 1:
			startCrud();
			break;
		case 2:
			startClassSelectie();
			break;
		case 3:
			// DatabaseConnection.setUsernameAndPassword(null, null);
			System.out.println("Succesvol uitgelogd!");
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

	private void startCrud() {
		System.out.println("");
		System.out.println("CRUD-HANDELINGEN");
		System.out.println("");
		System.out.println("1. Create");
		System.out.println("2. Read");
		System.out.println("3. Update");
		System.out.println("4. Delete");
		System.out.println("5: Hoofdmenu");
		System.out.println("");
		System.out.print("Maak een keuze:");
		int keuze = input.nextInt();
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

	private void startClassSelectie(){
		System.out.println("KLASSE SELECTIE: ");
		System.out.println("");
		System.out.println("1. Klant klasse");
		System.out.println("2. Adres klasse");
		System.out.println("3. Bestelling klasse");
		System.out.println("4. Artikel klasse");
		System.out.println("5. Hoofdmenu");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = input.nextInt();
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


	private void createKlantMenu() {
		System.out.println("SELECTEER EEN CREATE METHODE");
		System.out.println("");
		System.out.println("1. Create klant");
		System.out.println("2. Create klant & adres");
		System.out.println("3. Create klant, adres & bestelling");
		System.out.println("4. Create klant & bestelling");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = input.nextInt();
		switch (keuze){
		case 1:
			createKlant();
			break;
		case 2:
			createKlantAdres();
			break;
		case 3:
			createKlantAdresBestelling();
			break;
		case 4:
			createKlantBestelling();
			break;
		default:
			System.out.println("Ongeldige keuze!");
			createKlantMenu();
			break;
		}
	}

	private void createKlant() {
		//Klant gegevens
		System.out.println("Vul de gevraagde gegevens correct in: ");
		System.out.print("Voornaam: ");
		String voornaam = input.next();
		System.out.print("Tussenvoegsel: ");
		String tussenvoegsel = input.next();
		System.out.print("Achternaam: ");
		String achternaam = input.next();
		System.out.print("Emailadres: ");
		String email = input.next();

		klant.setVoornaam(voornaam);
		klant.setTussenvoegsel(tussenvoegsel);
		klant.setAchternaam(achternaam);
		klant.setEmail(email);

		klantDAO.createKlant(klant);
		System.out.println(klant.toString() + " is aangemaakt");
	}
	
	private void createKlantAdres() {
		//Klant gegevens:
		System.out.print("Voornaam: ");
		String voornaam = input.next();
		System.out.print("Tussenvoegsel: ");
		String tussenvoegsel = input.next();
		System.out.print("Achternaam: ");
		String achternaam = input.next();
		System.out.print("Emailadres: ");
		String email = input.next();
		//Adres gegevens: 
		System.out.print("Straatnaam: ");
		String straatnaam = input.next();
		System.out.print("Postcode: ");
		String postcode = input.next();
		System.out.print("Toevoeging: ");
		String toevoeging = input.next();
		System.out.print("Huisnummer: ");
		int huisnummer = input.nextInt();
		System.out.print("Woonplaats: ");
		String woonplaats = input.next();

		klant.setVoornaam(voornaam);
		klant.setTussenvoegsel(tussenvoegsel);
		klant.setAchternaam(achternaam);
		klant.setEmail(email);
		klant.adres.setStraatnaam(straatnaam);
		klant.adres.setPostcode(postcode);
		klant.adres.setToevoeging(toevoeging);
		klant.adres.setHuisnummer(huisnummer);
		klant.adres.setWoonplaats(woonplaats);


		klantDAO.createKlant(klant);
		System.out.println(klant.toString() + klant.adres.toString() 
		+ " is aangemaakt");
	}

	private void createKlantAdresBestelling() {
		//Klant gegevens:
		System.out.print("Voornaam: ");
		String voornaam = input.next();
		System.out.print("Tussenvoegsel: ");
		String tussenvoegsel = input.next();
		System.out.print("Achternaam: ");
		String achternaam = input.next();
		System.out.print("Emailadres: ");
		String email = input.next();
		//Adres gegevens: 
		System.out.print("Straatnaam: ");
		String straatnaam = input.next();
		System.out.print("Postcode: ");
		String postcode = input.next();
		System.out.print("Toevoeging: ");
		String toevoeging = input.next();
		System.out.print("Huisnummer: ");
		int huisnummer = input.nextInt();
		System.out.print("Woonplaats: ");
		String woonplaats = input.next();

		klant.setVoornaam(voornaam);
		klant.setTussenvoegsel(tussenvoegsel);
		klant.setAchternaam(achternaam);
		klant.setEmail(email);
		klant.adres.setStraatnaam(straatnaam);
		klant.adres.setPostcode(postcode);
		klant.adres.setToevoeging(toevoeging);
		klant.adres.setHuisnummer(huisnummer);
		klant.adres.setWoonplaats(woonplaats);

		//Bestelling gegevens
		int keuze = 1;
		while (keuze == 1)  {
			System.out.println("Welke artikel wilt u toevoegen: ");
			System.out.println("Artikel id:");
			int id = input.nextInt();
			System.out.print("Artikel naam: ");
			String artikelnaam = input.next();
			System.out.print("Hoeveel wilt u van dit artkel: ");
			int aantal = input.nextInt();
			System.out.print("Wat is de prijs van dit artikel: ");
			double prijs = input.nextDouble();

			Artikel artikelAdd = new Artikel(id, artikelnaam, prijs, aantal);
			klant.bestelling.voegArtikelToeAanBestelling(artikelAdd);
			System.out.print("Wilt u meer artikelen toevoegen (kies 1 voor ja en 2 voor nee)? ");
			keuze = input.nextInt();
		}
		klantDAO.createKlant(klant);
	}

	private void createKlantBestelling() {
		//Klant gegevens: 
		System.out.print("Voornaam: ");
		String voornaam = input.next();
		System.out.print("Tussenvoegsel: ");
		String tussenvoegsel = input.next();
		System.out.print("Achternaam: ");
		String achternaam = input.next();
		System.out.print("Emailadres: ");
		String email = input.next();

		klant.setVoornaam(voornaam);
		klant.setTussenvoegsel(tussenvoegsel);
		klant.setAchternaam(achternaam);
		klant.setEmail(email);

		//Bestelling gegevens
		int keuze = 1;
		while (keuze == 1)  {
			System.out.println("Welke artikel wilt u toevoegen: ");
			System.out.println("Artikel id:");
			int id = input.nextInt();
			System.out.print("Artikel naam: ");
			String artikelnaam = input.next();
			System.out.print("Hoeveel wilt u van dit artkel: ");
			int aantal = input.nextInt();
			System.out.print("Wat is de prijs van dit artikel: ");
			double prijs = input.nextDouble();

			Artikel artikelAdd = new Artikel(id, artikelnaam, prijs, aantal);
			klant.bestelling.voegArtikelToeAanBestelling(artikelAdd);
			System.out.print("Wilt u meer artikelen toevoegen (kies 1 voor ja en 2 voor nee)? ");
			keuze = input.nextInt();
		}
		klantDAO.createKlant(klant);
	}
	
	private void readKlantMenu() {
		System.out.println("");
		System.out.println("SELECTEER EEN READ METHODE");
		System.out.println("");
		System.out.println("1. Read klant met ID-nummer");
		System.out.println("2. Read klant met voornaam");
		System.out.println("3. Read klant met volledige naam");
		System.out.println("4. Read klant met adres");
		System.out.println("5. Read klant met straatnaam");
		System.out.println("6. Read klant met postcode en huisnummer");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = input.nextInt();
		switch (keuze){
		case 1:
			System.out.println("Geef uw klant ID:");
			int id = input.nextInt();
			try {
				klantDAO.readKlantWithId(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("Geef u voornaam: ");
			String voornaam = input.next();
			try {
				klantDAO.readKlantWithFirstName(voornaam);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("Geef u volledige naam: ");
			String volledigeNaam = input.nextLine();
			try {
				klantDAO.readKlantWithFirstName(volledigeNaam);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 4:
			System.out.print("Straatnaam: ");
			String straatnaam = input.next();
			System.out.print("Postcode: ");
			String postcode = input.next();
			System.out.print("Toevoeging: ");
			String toevoeging = input.next();
			System.out.print("Huisnummer: ");
			int huisnummer = input.nextInt();
			System.out.print("Woonplaats: ");
			String woonplaats = input.next();
			try {
				klantDAO.readKlantWithAddress(straatnaam, huisnummer, toevoeging, postcode, woonplaats);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 5:
			System.out.println("Geef uw straatnaam: ");
			String straatnaam1 = input.next();
			try {
				klantDAO.readKlantWithStraatnaam(straatnaam1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 6:
			System.out.print("Geef uw Postcode: ");
			String postcode1 = input.next();
			System.out.print("Huisnummer: ");
			int huisnummer1 = input.nextInt();
			try {
				klantDAO.readKlantWithPostCodeHuisnummer(postcode1, huisnummer1);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		default:
			System.out.println("Ongeldige keuze!");
			readKlantMenu();
			break;
		}
	}
	
	private void updateKlantMenu(){
		System.out.println("SELECTEER EEN UPDATE METHODE");
		System.out.println("");
		System.out.println("1. Update klantnaam");
		System.out.println("2. Update klantadres");
		System.out.println("3. Create klantnaam & adres");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = input.nextInt();
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
		default:
			System.out.println("Ongeldige keuze!");
			updateKlantMenu();
			break;
		}
	}

	private void updateKlantnaam(){
		System.out.print("Geef uw klant ID: ");
		int id = input.nextInt();
		try {
			klantDAO.UpdateKlantNaam(id);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {		
			e.printStackTrace();
		}
	}
	
	private void updateKlantadres(){
		System.out.print("Geef uw klant ID: ");
		int id = input.nextInt();
		try {
			klantDAO.UpdateKlantAddress(id);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void updateKlantnaamadres() {
		System.out.print("Geef uw klant ID: ");
		int id = input.nextInt();
		try {
			klantDAO.updateKlantNaamAddress(id);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void deleteKlantMenu() {
		System.out.println("");
		System.out.println("SELECTEER EEN DELETE METHODE");
		System.out.println("");
		System.out.println("1. Delete klant met ID-nummer");
		System.out.println("2. Delete klant met naam");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = input.nextInt();
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

	private void deleteKlantMetNaam() {
		System.out.println("Vul de gevraagde gegevens correct in (alle uw gegevens worden verwijderd): ");
		System.out.print("Voornaam: ");
		String voornaam = input.next();
		System.out.print("Tussenvoegsel: ");
		String tussenvoegsel = input.next();
		System.out.print("Achternaam: ");
		String achternaam = input.next();

		try {
			klantDAO.deleteAllFromKlantNaam(voornaam, achternaam, tussenvoegsel);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteKlantID() {
		System.out.print("Geef uw klant ID (alle uw gegevens worden verwijderd): ");
		int id = input.nextInt();
		try {
			klantDAO.deleteAllFromKlantId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void exit() {
		// flush.screen(); //moet de juist code nog vinden om beeldscherm leeg te krijgen
		System.out.println("Uw bewerking is beeinding.");

	}
}