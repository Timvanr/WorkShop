import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Service {

	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	Scanner scInput = new Scanner(System.in);
	
	int id_Prompt() throws IOException {
		System.out.println("Geef uw klant ID: ");
		String IDstr = input.readLine();
		int id = Integer.parseInt(IDstr);
		return id;
	}
	String voornaamPrompt() throws IOException {
		System.out.print("Voornaam: ");
		String voornaam = input.readLine();
		return voornaam;
		}
	String tussenvoegselPrompt() throws IOException {
		System.out.print("Tussenvoegsel: ");
		String tussenvoegsel = input.readLine();
		return tussenvoegsel;
		}
	String achternaamPrompt() throws IOException {
		System.out.print("Achternaam: ");
		String achternaam = input.readLine();
		return achternaam;
		}
	String emailPrompt() throws IOException {
		System.out.print("Email: ");
		String email = input.readLine();
		return email;
		}
	int adresIdPrompt() throws IOException {
		System.out.print("Adres ID: ");
		String adres_idString = input.readLine();
		int adres_id = Integer.parseInt(adres_idString);
		return adres_id;
		}
	String straatnaamPrompt() throws IOException {
		System.out.print("Straatnaam: ");
		String straatnaam = input.readLine();
		return straatnaam;
		}
	int huisnummerPrompt() throws IOException {
		System.out.print("Huisnummer: ");
		String huisnummerstr = input.readLine();
		int huisnummer = Integer.parseInt(huisnummerstr);
		return huisnummer;
	}
	String toevoegingPrompt() throws IOException {
		System.out.print("Toevoeging: ");
		String toevoeging = input.readLine();
		return toevoeging;
		}
	String postcodePrompt() throws IOException {
		System.out.print("Postcode: ");
		String postcode = input.readLine();
		return postcode;
		}
	String woonplaatsPrompt() throws IOException {
		System.out.print("Woonplaats: ");
		String woonplaats = input.readLine();
		return woonplaats;
		}
	int artikelAantalPrompt() throws IOException {
		System.out.print("Hoeveel wilt u van dit atikel: ");
		String artikelAantalstr = input.readLine();
		int artikelAantal = Integer.parseInt(artikelAantalstr);
		return artikelAantal;
	}
	
	int bestellingIdPrompt() throws IOException {
		System.out.println("Bestelling ID:");
		String bestellingidString = input.readLine();
		int bestelling_id = Integer.parseInt(bestellingidString);
		return bestelling_id;
	}
	BigDecimal artikelPrijsPrompt() throws IOException {
		System.out.print("Wat is de prijs van dit artikel: ");
		String artikelPrijsstr = input.readLine();
		BigDecimal artikelPrijs = new BigDecimal(artikelPrijsstr);
		return artikelPrijs;
	}
	String artikelNaamPrompt() throws IOException {
		System.out.print("Welk artikel wilt u toevoegen: ");
		String artikelNaam = input.readLine();
		return artikelNaam;
	}
	int arikelIdPrompt() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}
		
	void printKlant(Klant klant){
		System.out.printf("%-20s %-20s %-20s %-20s\n", "Voornaam", "Tussenvoegsel", "Achternaam", "E-mail");
		System.out.printf("%-20s %-20s %-20s %-20s", klant.getVoornaam(), klant.getTussenvoegsel(), klant.getAchternaam(), klant.getEmail());
	}
	
	void printKlanten(Set<Klant> klantset){
		System.out.printf("%-10s %-20s %-20s %-20s %-20s\n", "Klant id", "Voornaam", "Tussenvoegsel", "Achternaam", "E-mail");
		for(Klant klant: klantset){
			System.out.printf("%-20s %-20s %-20s %-20s\n", klant.getVoornaam(), klant.getTussenvoegsel(), klant.getAchternaam(), klant.getEmail());
		}
	}

	void printAdres(Adres adres){
		System.out.println("Het adres van deze klant:");
		System.out.printf("%-10s %-20s %-11s %-8s %-20s %-20s\n", "Adres id", "Straatnaam", "Huisnummer", "Toevoeging", "Postcode", "Woonplaats");
		System.out.printf("%-10d %-20s %-11d %-8s %-20s %-20s\n", adres.getId(), adres.getStraatnaam(), adres.getHuisnummer(), adres.getToevoeging(), adres.getPostcode(), adres.getWoonplaats());
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
		String artikelLijst = "";
		for (Map.Entry<Artikel, Integer> entry: bestelling.artikelen.entrySet()){
				artikelLijst += entry.getKey().toString() + " " + entry.getValue() + "\n";
			}
			System.out.println("Bestellingnummer: " + bestelling.bestelling_id + " Klantnummer: " + bestelling.klant_id + "\n" + artikelLijst);
		}
	
	void printBestellingen(ArrayList<Bestelling> bestellijst){
		System.out.println("Dit zijn alle bekende bestellingen");
		for (Bestelling bestelling: bestellijst){
			String artikelLijst = "";
			for (Map.Entry<Artikel, Integer> entry: bestelling.artikelen.entrySet()){
				artikelLijst += entry.getKey().toString() + " " + entry.getValue() + "\n";
			}
			System.out.println("Bestellingnummer: " + bestelling.bestelling_id + " Klantnummer: " + bestelling.klant_id + "\n" + artikelLijst);
		}
	}
	
	
	int kiesConnectie(){
		boolean invalidInput = true;
		boolean invalidInput2 = true;
		int connectieKeuze = 0;
		while (invalidInput){
		System.out.println("Wilt u een connectietype kiezen? \n 1. Ja \n 2. Nee \n");
		int userInput = scInput.nextInt();
		
		if (userInput == 1){
			invalidInput = false;
			while (invalidInput2){
				System.out.println("Wilt u connectie via HikariCP of via c3p0? \n 1. HikariCP \n 2. c3p0 \n");
				userInput = scInput.nextInt();
				if (userInput == 1){
					invalidInput2 = false;
					connectieKeuze = 1;
				}
				else if (userInput == 2){
					invalidInput2 = false;
					connectieKeuze = 2;
				}
				else 
					System.out.println("Verkeerde opgave, probeer opnieuw");
				}
			}
			else if (userInput == 2){
				invalidInput = false;	
				connectieKeuze = 1;
			}
			else
				System.out.println("Verkeerde opgave probeer opnieuw");
		}
		return connectieKeuze;
	}
	
	Klant newKlant() throws IOException{
		Klant klant = new Klant(voornaamPrompt(), tussenvoegselPrompt(), achternaamPrompt(), emailPrompt());
		return klant;
	}
	
	Adres newAdres() throws IOException{
		Adres adres = new Adres(straatnaamPrompt(), huisnummerPrompt(), toevoegingPrompt(), postcodePrompt(), woonplaatsPrompt());
		return adres;
	}
	
	
}
