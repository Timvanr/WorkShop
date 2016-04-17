import java.io.IOException;
import java.sql.SQLException;

public class KlantController {

	private Service service; // view
	private KlantDAO klantbestand; // model
	
	public KlantController(Service service, KlantDAO klantbestand2) {

		this.service = service;
		this.klantbestand = klantbestand2;
	}

	public void printKlantmetId() throws SQLException, IOException{
		service.printKlant(klantbestand.readKlantWithId(service.id_Prompt()));
	}

	public void printKlantmetNaam() throws SQLException, IOException{
		service.printKlant(klantbestand.readKlantWithFirstLastName(service.voornaamPrompt(), service.tussenvoegselPrompt(), service.achternaamPrompt()));
	}
	
	public void printKlantmetVoornaam() throws SQLException, IOException{
		service.printKlanten(klantbestand.readKlantWithFirstName(service.voornaamPrompt()));
	}
	public void printAlleKlanten() throws SQLException{
		service.printKlanten(klantbestand.readAll());
	}
	
	public void createKlant() throws SQLException, IOException{
		Klant klant = new Klant(service.voornaamPrompt(), service.tussenvoegselPrompt(), service.achternaamPrompt(), service.emailPrompt());
		klantbestand.createKlant(klant);
	}
	
	public void createKlantenAdres() throws SQLException, IOException{ //dit zou evt ook in de servicelaag kunnen komen
		Klant klant = service.newKlant();
		Adres adres = service.newAdres();
		klantbestand.createKlantEnAdres(klant, adres);
	}
	
	public void updateKlant() throws SQLException, IOException{
		klantbestand.UpdateKlantNaam(service.id_Prompt());
	}
	
	public void updateKlantEmail() throws SQLException, IOException{
		klantbestand.updateEmail(service.id_Prompt());
	}
	
	public void deleteKlantmetKlantId() throws SQLException, IOException{
		klantbestand.deleteAllFromKlantId(service.id_Prompt());
		System.out.println("Klant succesvol verwijderd.");
	}
	
	public void deleteKlantmetKlantNaam() throws SQLException, IOException{
		Klant klant = klantbestand.readKlantWithFirstLastName(service.voornaamPrompt(), service.tussenvoegselPrompt(), service.achternaamPrompt());
		klantbestand.deleteAllFromKlantId(klant.getId());
		System.out.println("Klant succesvol verwijderd.");
	}
}
