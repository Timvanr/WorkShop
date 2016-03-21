import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class KeuzeMenu {
	Scanner input;
	Klant klant;
	Klantbestand klantBestand;
	Bestelling bestelling;
	Artikel artikel;
	Bestellijst bestelLijst;

	static final KeuzeMenu keuzeMenu = new KeuzeMenu();

	public KeuzeMenu(){
		input = new Scanner(System.in);
		klant = new Klant();
		klantBestand = new Klantbestand();
		bestelling = new Bestelling();
		artikel = new Artikel();
		bestelLijst = new Bestellijst();
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

		private void createKlantMenu() throws SQLException, IOException {
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
				klantBestand.createKlant();;
				break;
			case 2:
				klantBestand.createKlantEnAdres();
				break;
			case 3:
				klantBestand.createKlantEnAdresEnBestelling();
				break;
			case 4:
				klantBestand.createKlantEnBestelling();
				break;
			default:
				System.out.println("Ongeldige keuze!");
				createKlantMenu();
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
			klantBestand.UpdateKlantNaam(id);
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
			klantBestand.UpdateKlantAddress(id); // moet Adreslijst worden
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
			klantBestand.UpdateKlantNaam(id);
			adreslijst.UpdateAdres(); // of zoiets nog even kijken wat dit wordt...
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
			klantBestand.deleteAllFromKlantNaam(voornaam, achternaam, tussenvoegsel);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteKlantID() {
		System.out.print("Geef uw klant ID (alle uw gegevens worden verwijderd): ");
		int id = input.nextInt();
		try {
			klantBestand.deleteAllFromKlantId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void exit() {
		// flush.screen(); //moet de juist code nog vinden om beeldscherm leeg te krijgen
		System.out.println("Uw bewerking is beeindigd.");

	}
}