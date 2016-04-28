package workshop;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import workshop.dao.*;
import workshop.controller.*;
import workshop.model.*;
import org.apache.commons.validator.routines.EmailValidator;

public class Service {

	DAOFactory daoFactory;
	BufferedReader input;
	Scanner scInput;
	AdresController adrescontrol;
	KlantController klantcontrol;
	BestellingController bestellingcontrol;
	ArtikelController artikelcontrol;
	
	public Service(){
		this.daoFactory = new DAOFactory();
		this.input = new BufferedReader(new InputStreamReader(System.in));
		this.scInput = new Scanner(System.in);
		this.adrescontrol = new AdresController(this, daoFactory.getAdresDAO());
		this.klantcontrol = new KlantController(this, daoFactory.getKlantDAO());
		this.bestellingcontrol = new BestellingController(this, daoFactory.getBestellingDAO(), daoFactory.getArtikelDAO());
		this.artikelcontrol = new ArtikelController(this, daoFactory.getArtikelDAO());
		
	}
	
	
	
	BigDecimal artikelPrijsPrompt() throws IOException {
		System.out.print("Wat is de prijs van dit artikel: ");
		String artikelPrijsstr = input.readLine();
		BigDecimal artikelPrijs = new BigDecimal(artikelPrijsstr);
		return artikelPrijs;
	}
	
	
	// Hier de printmethodes (had ook een aparte klasse kunnen zijn).
	void printKlant(Klant klant){
		System.out.printf("%-20s %-20s %-20s %-20s\n", "Voornaam", "Tussenvoegsel", "Achternaam", "E-mail");
		System.out.printf("%-20s %-20s %-20s %-20s", klant.getVoornaam(), klant.getTussenvoegsel(), klant.getAchternaam(), klant.getEmail());
	}
	
	void printKlanten(Set<Klant> klantset){
		System.out.printf("%-10s %-20s %-20s %-20s %-20s\n", "Klant id", "Voornaam", "Tussenvoegsel", "Achternaam", "E-mail");
		for(Klant klant: klantset){
			System.out.printf("%-10s %-20s %-20s %-20s %-20s\n", klant.getId(), klant.getVoornaam(), klant.getTussenvoegsel(), klant.getAchternaam(), klant.getEmail());
		}
	}

	void printAdres(Adres adres){
		System.out.println("Het adres van deze klant:");
		System.out.printf("%-10s %-20s %-11s %-11s %-20s %-20s\n", "Adres id", "Straatnaam", "Huisnummer", "Toevoeging", "Postcode", "Woonplaats");
		System.out.printf("%-10d %-20s %-11d %-11s %-20s %-20s\n", adres.getId(), adres.getStraatnaam(), adres.getHuisnummer(), adres.getToevoeging(), adres.getPostcode(), adres.getWoonplaats());
	}
	
	void printAdressen(Set<Adres> adresset){
		System.out.println("Adres(sen) van deze klant(en)");
		System.out.printf("%-10s %-20s %-11s %-11s %-20s %-20s\n", "Adres id", "Straatnaam", "Huisnummer", "Toevoeging", "Postcode", "Woonplaats");
		for (Adres adres: adresset){
			System.out.printf("%-10d %-20s %-11d %-11s %-20s %-20s\n", adres.getId(), adres.getStraatnaam(), adres.getHuisnummer(), adres.getToevoeging(), adres.getPostcode(), adres.getWoonplaats());	
		}
	}
	
	void printBestelling(Bestelling bestelling){
		System.out.println("De inhoud van de bestelling:");
		System.out.println(bestelling);
		}
	
	void printBestellingen(Set<Bestelling> bestellijst){
		System.out.println("Dit zijn alle bekende bestellingen");
		for (Bestelling bestelling: bestellijst){
			System.out.println(bestelling);
		}
	}
	
	void printArtikelen(){
		ArtikelDAOInterface alijst = DAOFactory.getArtikelDAO();
		System.out.println("Alle Artikelen");
		System.out.printf("%-13s %-20s %-11s\n", "Artikelnummer", "Naam", "Prijs");
		for (Artikel a: alijst.getArtikellijst()){
			System.out.printf("%-13d %-20s %-11f\n", a.getId(), a.getNaam(), a.getPrijs().doubleValue());	
		}
	}
	
	// Hier de getters/setters die voor het keuzemenu gebruikt worden
		public AdresController getAdrescontrol() {
			return adrescontrol;
		}
		public void setAdrescontrol(AdresController adrescontrol) {
			this.adrescontrol = adrescontrol;
		}
		public KlantController getKlantcontrol() {
			return klantcontrol;
		}
		public void setKlantcontrol(KlantController klantcontrol) {
			this.klantcontrol = klantcontrol;
		}
		public BestellingController getBestellingcontrol() {
			return bestellingcontrol;
		}
		public void setBestellingcontrol(BestellingController bestellingcontrol) {
			this.bestellingcontrol = bestellingcontrol;
		}
		
	
	void kiesDataOpslag(int keuze){
		
		setKlantcontrol(new KlantController(this, daoFactory.getKlantDAO()));
		setAdrescontrol(new AdresController(this, daoFactory.getAdresDAO()));
		setBestellingcontrol(new BestellingController(this, daoFactory.getBestellingDAO(), daoFactory.getArtikelDAO()));
			switch(keuze){
				case 1: 
					DatabaseConnection.setDriverClass("com.mysql.jdbc.Driver"); 
					DatabaseConnection.setURL("jdbc:mysql://localhost:3306/Adresboek");
					DAOFactory.setDataOpslagType(1);
					break;
				case 2: 
					DatabaseConnection.setDriverClass("org.firebirdsql.jdbc.FBDriver");
					DatabaseConnection.setURL("jdbc:firebirdsql://localhost:3050/C:/Users/sande_000/Desktop/Java RSVIER/Firebird database workshop/Workshop.GDB");
					DAOFactory.setDataOpslagType(2);
					break;
			//case 3: DatabaseConnection.setDriverClass("Hier moet JSON komen dan");
			//case 4: DatabaseConnection.setDriverClass("Hier XML");
			}
		}

	void kiesConnectie(){
		boolean invalidInput = true;
		boolean invalidInput2 = true;
		while (invalidInput){
		System.out.println("Wilt u een connectietype kiezen? \n 1. Ja \n 2. Nee");
		int userInput = scInput.nextInt();
		
		if (userInput == 1){
			invalidInput = false;
			while (invalidInput2){
				System.out.println("Wilt u connectie via HikariCP of via c3p0? \n 1. HikariCP \n 2. c3p0 \n");
				userInput = scInput.nextInt();
				if (userInput == 1){
					invalidInput2 = false;
					DatabaseConnection.setConnectieKeuze(1);
				}
				else if (userInput == 2){
					invalidInput2 = false;
					DatabaseConnection.setConnectieKeuze(2);
				}
				else 
					System.out.println("Verkeerde opgave, probeer opnieuw");
				}
			}
			else if (userInput == 2){
				invalidInput = false;	
				DatabaseConnection.setConnectieKeuze(2);
			}
			else
				System.out.println("Verkeerde opgave probeer opnieuw");
		}
	}
	/*
	Klant newKlant() throws IOException{
		Klant klant = new Klant(voornaamPrompt(), tussenvoegselPrompt(), achternaamPrompt(), emailPrompt());
		return klant;
	}
	
	Adres newAdres() throws IOException{
		Adres adres = new Adres(straatnaamPrompt(), huisnummerPrompt(), toevoegingPrompt(), postcodePrompt(), woonplaatsPrompt());
		return adres;
	}
	
	Bestelling newBestelling() throws IOException, SQLException{
		Bestelling bestelling = new Bestelling();
		ArtikelLijst aLijst = new ArtikelLijst();
		int artikelcount = 0;
		int stopInput = 0;
		while (stopInput == 0 && artikelcount < 3){
			int artikelId = artikelIdPrompt();
			if (artikelId == 0){
				stopInput = 1;
				return bestelling;
			}
			else{
				int artikelAantal = artikelAantalPrompt();
				bestelling.voegArtikelToeAanBestelling(aLijst.getArtikelWithArtikelId(artikelId), artikelAantal);
				artikelcount++;
			}
		}
			return bestelling;
		
	}
	*/
	public int updateBestellingPrompt() throws IOException{
		System.out.println("Wat wilt u doen?\n1. Artikel verwijderen\n2. Artikel toevoegen\n3. Artikel vervangen\n4. Terug");
		int userIn = scInput.nextInt();
		switch (userIn){
		case 1:	userIn = 1; break;
		case 2: userIn = 2; break;
		case 3: userIn = 3; break;
		case 4: break;
		default: 
			System.out.println("foute invoer, probeer opnieuw");
			updateBestellingPrompt();
		}
		return userIn;
	}
}
