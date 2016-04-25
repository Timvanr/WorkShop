package workshop;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import workshop.dao.*;
import workshop.controller.*;
import workshop.model.*;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeuzeMenu {
	
	Service service;
			
	static Logger logger = LoggerFactory.getLogger(KeuzeMenu.class);
	private Connection connection;
	private Scanner input;
	private static KeuzeMenu keuzeMenu;
	private int klant_id;
	private int adres_id;
	private Bestelling bestelling;
	
	
	public KeuzeMenu(){
		this.input = new Scanner(System.in);
		this.service = new Service();
	}

	public static void main(String[] args){
		KeuzeMenu keuzeMenu = new KeuzeMenu();
		keuzeMenu.bronDoelSelectie();
	}

	public void startInlog(){
		logger.info("test3 {}");
		System.out.println("Inlog scherm: ");
		System.out.println("");
		System.out.print("Gebruikersnaam: ");
		DatabaseConnection.setUSERNAME(input.nextLine());
		System.out.print("Wachtwoord: ");
		DatabaseConnection.setPW(input.nextLine());
		service.kiesConnectie();
		connection = DatabaseConnection.getPooledConnection();
		startHoofd();
		System.out.println("");
		System.out.println("Username or password fout");
		System.out.println("");
		System.out.println("Try again!");
		startInlog();
			
	}

	private void startHoofd() {
		System.out.println("     *************");
		System.out.println("  --|- HOOFDMENU -|--");
		System.out.println("1. Nieuwe klant");
		System.out.println("2. Bestaande klant");
		System.out.println("3. Voer een wijziging door");
		System.out.println("4. Bron en doel selectie voor de informatie");
		System.out.println("5. Uitloggen");
		System.out.println("6. Stoppen");
		System.out.println();
		int keuze = keuzePrompt();
		switch (keuze){
			case 1:	
				nieuweKlant();
				break;
			case 2:	
				zoekKlant();
				break;
			case 3: 
				updateMenu();
				break;
			case 4: 
				bronDoelSelectie();
				break;
			case 5:
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
			case 6:	
				exit();
				break;
			default: 
				defaultSwitch();
				startHoofd();
				break;
		}
	}

	private void updateMenu() {
		System.out.println("Kan nog niet!");
		startHoofd();
	}

	private void nieuweKlant(){
		System.out.println("________________________________________________");
		System.out.println("Informatie van de nieuwe klant:");
				
		Klant klant = klantPrompt();
		System.out.println(klant);
		
		System.out.println("Deze klant opslaan?\n ja(j) of nee(n)");
		if (input.nextLine().equalsIgnoreCase("J")){
			KlantDAOInterface kdi = DAOFactory.getKlantDAO();
			this.klant_id = kdi.createKlant(klant);
			voegToeMenu();
		} else {
			System.out.println("1. Opnieuw");
			System.out.println("2. Annuleren (naar Hoofdmenu)");
			int keuze = keuzePrompt();
			
			switch (keuze){
				case 1:
					nieuweKlant();
					break;
				case 2:
					startHoofd();
					break;
				default:
					defaultSwitch();
					startHoofd();
			}
		}
	}

	private void voegToeMenu() {
		KlantDAOInterface kdi = DAOFactory.getKlantDAO();
		System.out.println("________________________________________________");
		System.out.println(kdi.readKlantWithId(this.klant_id));
		System.out.println("Voeg een Adres of Bestelling toe aan deze Klant");
		System.out.println("1. Voeg Adres toe aan Klant");
		System.out.println("2. Voeg Bestelling toe aan Klant");
		System.out.println("3. Voer wijziging door");
		System.out.println("4. Terug naar Hoofdmenu");
		
		int keuze = keuzePrompt();
		switch (keuze){
			case 1:	
				createAdres();
				break;
			case 2:
				this.bestelling = new Bestelling(this.klant_id);
				addArtikel();
				break;
			case 3:
				updateMenu();
				break;
			case 4:	
				startHoofd();
				break;	
			default:
				defaultSwitch();
				voegToeMenu();
				break;
		}
	}

	private void zoekKlant(){
		System.out.println("__________________________________________");
		System.out.println("        SELECTEER EEN METHODE:");
		System.out.println(" 1. Zoek Klant met klantnummer");
		System.out.println(" 2. Zoek Klant(en) met voor- en achternaam");
		System.out.println(" 3. Zoek Klant(en) met achternaam");
		System.out.println(" 4. Zoek Klant(en) met voornaam");
		System.out.println(" 5. Zoek klant(en) met adresgegevens");
		System.out.println(" 6. Zoek klant met bestellingnummer");
		System.out.println(" 7. Lijst van alle Klanten");
		int keuze = keuzePrompt();
		KlantDAOInterface kdi = DAOFactory.getKlantDAO();
		switch (keuze) {
			case 1: 
				this.klant_id = klant_idPrompt();
				voegToeMenu();
				break;
			case 2: 
				this.klant_id = kdi.readKlantWithFirstLastName(voornaamPrompt(), tussenvoegselPrompt(), achternaamPrompt()).getId();
				voegToeMenu();
				break;
			case 3: 
				service.printKlanten(kdi.readKlantByAchternaam(achternaamPrompt()));
				this.klant_id = klant_idPrompt();
				voegToeMenu();
				break;
			case 4: 
				service.printKlanten(kdi.readKlantWithFirstName(voornaamPrompt()));
				this.klant_id = klant_idPrompt();
				voegToeMenu();
				break;
			case 5: 
				zoekAdres();
				break;
			case 6: 
				BestellingDAOInterface bdi = DAOFactory.getBestellingDAO();
				this.klant_id = kdi.readKlantWithId(bdi.haalKlant_id(bestellingIdPrompt())).getId();
				voegToeMenu();
			case 7: 
				service.printKlanten(kdi.readAll());
				this.klant_id = klant_idPrompt();
				voegToeMenu();
			default: defaultSwitch();
		}
	}
	
	private void bronDoelSelectie(){
		System.out.println("____________________________");
		System.out.println("SELECTEER EEN OPSLAGMETHODE");
		System.out.println("1. via MySql");
		System.out.println("2. via FireBird");
		System.out.println("3. via JSON");
		System.out.println("4. via XML");
		System.out.println("5. exit");
		int keuze = keuzePrompt();
		
		if (keuze == 1 || keuze == 2 || keuze == 3 || keuze == 4){
			service.kiesDataOpslag(keuze);
			startInlog();
		} else if (keuze == 5){
			System.exit(5);
		} else {
			System.out.println("Geef een getal van 1 t/m 4 op aub");
			bronDoelSelectie();
		}	
	}
	
	private void zoekAdres() {
		System.out.println("_____________________________________________________________");
		System.out.println("            Welke gegevens zijn bekend?");
		System.out.println("1. Postcode en huisnummer (en evt. toevoeging)");
		System.out.println("2. Woonplaats, straatnaam en huisnummer (en evt. toevoeging)");
		System.out.println("3. Woonplaats en straatnaam");
		System.out.println("4. Woonplaats");
		System.out.println("5. Terug");
		int keuze = keuzePrompt();
		AdresDAOInterface adi = DAOFactory.getAdresDAO();
		
		switch (keuze){
			case 1: 
				this.adres_id = adi.readAdresMetPostcodeEnHuisnummer(postcodePrompt(), huisnummerPrompt(), toevoegingPrompt()).getId();
				break;
			case 2: 
				service.printAdressen(adi.readAdresMetStraatEnHuisnummer(straatnaamPrompt(), huisnummerPrompt(), toevoegingPrompt(), woonplaatsPrompt()));
				break;
		}
	}

	private void addArtikel() {
		System.out.println("_____________________________________________");
		System.out.println(this.bestelling);
		System.out.println("1. Voer een artikelnummer in om toe te voegen");
		System.out.println("2. Zoek een artikel");
		System.out.println("3. Bestelling is afgerond");
		int keuze = keuzePrompt();
		ArtikelDAOInterface artikelDAO = DAOFactory.getArtikelDAO();
		BestellingDAOInterface bestelDAO = DAOFactory.getBestellingDAO();
		switch (keuze){
			case 1: 
				this.bestelling.voegArtikelToe(artikelDAO.getArtikelWithArtikelId(artikelIdPrompt()), artikelAantalPrompt());
				addArtikel();
				break;
			case 2: 
				zoekArtikel();
				break;
			case 3: 
				bestelDAO.voegBestellingToe(this.bestelling);
				voegToeMenu();
				break;
			default: 
				defaultSwitch();
				addArtikel();
		}
	}
	
	private void zoekArtikel() {
		System.out.println("_________________________________________");
		System.out.println("1. Voer de naam van het artikel in: ");
		System.out.println("2. Lijst van alle artikelen");
		System.out.println("3. Terug naar het hoofdmenu");

		int keuze = keuzePrompt();
		switch (keuze){
			case 1: 
				ArtikelDAOInterface artikellijst = DAOFactory.getArtikelDAO();
				System.out.println(artikellijst.getArtikelWithNaam(artikelNaamPrompt()));
				addArtikel();
			case 2: 
				service.printArtikelen();
				addArtikel();
				break;
			case 3: 
				startHoofd();
				break;
			default: 
				defaultSwitch();
				zoekArtikel();
		}
	}
	
	private void createAdres() {
		Adres adres = adresPrompt();
		AdresDAOInterface adreslijst = DAOFactory.getAdresDAO();
		adreslijst.createAdres(this.klant_id, adres);
		voegToeMenu();
	}

	private void defaultSwitch() {
		System.out.println("Onverwerkbare invoer; kies een getal uit de lijst");
	}

	private String artikelNaamPrompt(){
		System.out.print("Geef de naam van het artikel dat u wilt toevoegen: ");
		String artikelNaam = input.nextLine();
		return artikelNaam;
	}
	int artikelIdPrompt(){
		System.out.println("Geef het artikelnummer van het artikel: ");
		String artikelIdString = input.nextLine();
		return Integer.parseInt(artikelIdString);
	}
	int artikelAantalPrompt() {
		System.out.print("Hoeveel van deze atikelen wilt u: ");
		String artikelAantalstr = input.nextLine();
		return Integer.parseInt(artikelAantalstr);
	}
	int bestellingIdPrompt(){
		System.out.println("Bestelling ID:");
		String bestellingidString = input.nextLine();
		int bestelling_id = Integer.parseInt(bestellingidString);
		return bestelling_id;
	}	
	private int keuzePrompt() {
		System.out.println("Wat wilt u doen: ");
		int keuze = input.nextInt();
		String space = input.nextLine();//hack de buffer
		return keuze;
	}

	Adres adresPrompt(){
		return new Adres(straatnaamPrompt(), huisnummerPrompt(), toevoegingPrompt(), postcodePrompt(), woonplaatsPrompt());
	}
	int adresIdPrompt(){
		System.out.print("Adres ID: ");
		String adres_idString = input.nextLine();
		int adres_id = Integer.parseInt(adres_idString);
		return adres_id;
	}
	String straatnaamPrompt(){
		System.out.print("Straatnaam: ");
		String straatnaam = input.nextLine();
		return straatnaam;
	}
	int huisnummerPrompt(){
		System.out.print("Huisnummer: ");
		String huisnummerstr = input.nextLine();
		int huisnummer = Integer.parseInt(huisnummerstr);
		return huisnummer;
	}
	String toevoegingPrompt(){
		System.out.print("Toevoeging: ");
		String toevoeging = input.nextLine();
		return toevoeging;
	}
	String postcodePrompt(){
		System.out.print("Postcode: ");
		String postcode = input.nextLine();
		return postcode;
	}
	String woonplaatsPrompt(){
		System.out.print("Woonplaats: ");
		String woonplaats = input.nextLine();
		return woonplaats;
	}
	/*
	private void startClassSelectie() throws IOException, SQLException {
		System.out.println("");
		System.out.println("KLASSE SELECTIE: ");
		System.out.println("");
		System.out.println("1. Klant klasse");
		System.out.println("2. Adres klasse");
		System.out.println("3. Bestelling klasse");
		System.out.println("4. Hoofdmenu");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = scInput.nextInt();
		switch (keuze) {
		case 1:
			startKlantMenu();
			break;
		case 2:
			startAdresMenu();
			break;
		case 3:
			startBestellingMenu();
			break;
		case 4:
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
			System.out.println("5. Create Bestelling voor bestaande klant");
			System.out.println("6. Hoofdmenu");
			System.out.println("");
			System.out.print("Maak een keuze: ");
			int keuze = input.nextInt();
			switch (keuze){
			case 1:	service.getKlantcontrol().createKlant();
			startHoofd();
			break;
			case 2:	service.getKlantcontrol().createKlantenAdres(); 
			startHoofd();
			break;
			case 3:
			service.getKlantcontrol().createKlantenAdresEnBestelling();
			break;
		case 4:
			service.getKlantcontrol().createKlantenBestelling();
			break;
		case 5:
			service.getBestellingcontrol().createBestellingVoorBestaandeKlant();
			break;
		case 6:
			System.out.println("Terug naar het hoofdmenu...");
			startHoofd();
		default:
			System.out.println("Ongeldige keuze!");
			createMenu();
			break;
			}
			createMenu();
		}
						
		private void readMenu() throws IOException, SQLException {
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
			int keuze = input.nextInt();
			switch (keuze){
				case 1:	service.getKlantcontrol().printKlantmetId();	
				readMenu();
				break;
				case 2:	service.getKlantcontrol().printKlantmetNaam(); 
				readMenu();
				break;
				case 3:	service.getKlantcontrol().printKlantmetVoornaam(); 
				readMenu();
				break;
				case 4:	service.getKlantcontrol().printAlleKlanten(); 
				readMenu();
				break;
				case 5:	service.getAdrescontrol().printAdresMetId();	
				readMenu();
				break;
				case 6:	service.getAdrescontrol().printAdresMetKlantId(); 
				readMenu();
				break;
				case 7:	service.getAdrescontrol().printAdresMetStraatnaam();	
				readMenu();
				break;
				case 8:	service.getAdrescontrol().printAdresMetPCEnHuisnummer();	
				readMenu();
				break;
				case 9:	service.getAdrescontrol().printAlleAdressen(); 
				readMenu();
				break;
				case 10:service.getBestellingcontrol().printBestellingmetId(); 
				readMenu();
				break;
				case 11:service.getBestellingcontrol().printAlleBestellingen(); 
				readMenu();
				break;
				case 12:
				System.out.println("Terug naar het hoofdmenu...");
				startHoofd();
				default:
				System.out.println("Ongeldige keuze!");
				readMenu();
				break;
			}
			
		}
	
	private void updateMenu() throws IOException, SQLException{
		System.out.println("SELECTEER EEN UPDATE METHODE");
		System.out.println("");
		System.out.println("1. Update Klantnaam");
		System.out.println("2. Update E-mail adres");
		System.out.println("3. Update Adres");
		System.out.println("4. Update Bestelling");
		System.out.println("5. Hoofdmenu");
		System.out.println("");
		System.out.print("Maak een keuze: ");
		int keuze = input.nextInt();
		switch (keuze){
		case 1:	service.getKlantcontrol().updateKlant(); 
		updateMenu();
		break;
		case 2:	service.getKlantcontrol().updateKlantEmail(); 
		updateMenu();
		break;
		case 3:	service.getAdrescontrol().updateAdres();	
		updateMenu();
		break;
		//case 4: service.getBestellingcontrol().updateBestelling(); updateMenu(); break;
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
		int keuze = input.nextInt();
		switch (keuze){
		case 1:	service.getKlantcontrol().deleteKlantmetKlantId();	
		deleteMenu();
		break;
		case 2:	service.getKlantcontrol().deleteKlantmetKlantNaam();	
		deleteMenu();
		break;
		case 3: service.getAdrescontrol().deleteAdresvanKlant(); 
		deleteMenu();
		break;
		case 4:
			service.getBestellingcontrol().deleteBestelling(); deleteMenu();
			break;
		case 5:
			service.getBestellingcontrol().deleteArtikelVanBestelling(); deleteMenu();
			break;
		case 6: System.out.println("Terug naar het hoofdmenu...");
		startHoofd();
		default:
			System.out.println("Ongeldige keuze!");
			deleteMenu();
			break;
		}
	}
	private void startKlantMenu() throws IOException, SQLException {
		System.out.println("\nSELECTEER EEN KLANT METHODE");
		System.out.println("1. Create Klant");
		System.out.println("2. Create Klant & adres");
		System.out.println("3. Create Klant, adres & bestelling");
		System.out.println("4. Create Klant & bestelling");
		System.out.println("5. Read Klant met klant id");
		System.out.println("6. Read Klant met voor- en achternaam");
		System.out.println("7. Read Klant(en) met voornaam");
		System.out.println("8. Read alle Klanten");
		System.out.println("9. Update Klantnaam");
		System.out.println("10. Update E-mail adres");
		System.out.println("11. Delete Klant met ID-nummer");
		System.out.println("12. Delete Klant met naam");
		System.out.println("13. Terug naar hoofdmenu");
		int keuze = input.nextInt();
		switch (keuze) {
		case 1:
			service.getKlantcontrol().createKlant();
			break;
		case 2:
			service.getKlantcontrol().createKlantenAdres();
			break;
		case 3:
			service.getKlantcontrol().createKlantenAdresEnBestelling();
			break;
		case 4:
			service.getKlantcontrol().createKlantenBestelling();
			break;
		case 5:
			service.getKlantcontrol().printKlantmetId();
			break;
		case 6:
			service.getKlantcontrol().printKlantmetNaam();
			break;
		case 7:
			service.getKlantcontrol().printKlantmetVoornaam();
			break;
		case 8:
			service.getKlantcontrol().printAlleKlanten();
			break;
		case 9:
			service.getKlantcontrol().updateKlant();
			break;
		case 10:
			service.getKlantcontrol().updateKlantEmail();
			break;
		case 11:
			service.getKlantcontrol().deleteKlantmetKlantId();
			break;
		case 12:
			service.getKlantcontrol().deleteKlantmetKlantNaam();
			break;
		case 13:
			startHoofd();
			break;
		default:
			System.out.println("Ongeldige keuze!");
			startKlantMenu();
			break;
		}
		startKlantMenu();
	}

	private void startAdresMenu() throws IOException, SQLException {
		System.out.println("\nSELECTEER EEN ADRES METHODE");
		System.out.println("1. Read Adres met adres id");
		System.out.println("2. Read Adres met klant id");
		System.out.println("3. Read Adres(sen) met straatnaam en woonplaats");
		System.out.println("4. Read Adres met postcode en huisnummer");
		System.out.println("5. Read alle Adressen");
		System.out.println("6. Update Adres");
		System.out.println("7. Delete Adres van Klant");
		System.out.println("8. Terug naar hoofdmenu");
		int keuze = input.nextInt();
		switch (keuze) {
		case 1:
			service.getAdrescontrol().printAdresMetId();
			break;
		case 2:
			service.getAdrescontrol().printAdresMetKlantId();
			break;
		case 3:
			service.getAdrescontrol().printAdresMetStraatnaam();
			break;
		case 4:
			service.getAdrescontrol().printAdresMetPCEnHuisnummer();
			break;
		case 5:
			service.getAdrescontrol().printAlleAdressen();
			break;
		case 6:
			service.getAdrescontrol().updateAdres();
			break;
		case 7:
			service.getAdrescontrol().deleteAdresvanKlant();
			break;
		case 8:
			startHoofd();
			break;
		default:
			System.out.println("Ongeldige keuze!");
			startAdresMenu();
			break;
		}
		startAdresMenu();
	}

	private void startBestellingMenu() throws IOException, SQLException {
		System.out.println("\nSELECTEER EEN BESTELLING METHODE");
		System.out.println("1. Create Bestelling voor bestaande klant");
		System.out.println("2. Read Bestelling met bestelling id");
		System.out.println("3. Read alle bestellingen");
		System.out.println("4. Update Bestelling");
		System.out.println("5. Delete Bestelling van Klant");
		System.out.println("6. Delete Artikel uit Bestelling");
		System.out.println("7. Terug naar hoofdmenu");
		int keuze = input.nextInt();
		switch(keuze){
			case 1: 
				service.getBestellingcontrol().createBestellingVoorBestaandeKlant(); break;
			case 2: 
				service.getBestellingcontrol().printBestellingmetId(); break;
			case 3: 
				service.getBestellingcontrol().printAlleBestellingen(); break;
			//case 4: 
				//service.getBestellingcontrol().updateBestelling(); break;
			case 5: 
				service.getBestellingcontrol().deleteBestelling(); break;
			case 6: 
				service.getBestellingcontrol().deleteArtikelVanBestelling(); break;
			case 7: 
				startHoofd(); break;
			default:
				System.out.println("Ongeldige keuze!");
				startBestellingMenu();
				break;
		}
		startBestellingMenu();
	}
*/
		
	Klant klantPrompt(){
		Klant k = new Klant(voornaamPrompt(), tussenvoegselPrompt(), achternaamPrompt(), emailPrompt());
		return k;
	}
	
	int klant_idPrompt(){
		System.out.println("Geef uw klant ID: ");
		String IDstr = input.nextLine();
		int id = Integer.parseInt(IDstr);
		return id;
	}
	
	String voornaamPrompt(){
		System.out.print("Voornaam: ");
		String voornaam = input.nextLine();
		return voornaam;
	}
	String tussenvoegselPrompt(){
		System.out.print("Tussenvoegsel: ");
		String tussenvoegsel = input.nextLine();
		return tussenvoegsel;
	}
	String achternaamPrompt(){
		System.out.print("Achternaam: ");
		String achternaam = input.nextLine();
		return achternaam;
	}
	String emailPrompt(){
		EmailValidator emailVal = EmailValidator.getInstance();
		boolean validInput = false;
		String email = null;
		while (!validInput){
			System.out.print("Email: ");
			email = input.nextLine();
			if (emailVal.isValid(email)){
				validInput = true;
			} else {
				System.out.println("foutief E-mail adres. Probeer opnieuw a.u.b.");
			}
		}
		return email;
	}
	
	private void exit() {
		System.out.println("Uw bewerking is beeindigd.");	
		System.exit(1);
	}
	
}
