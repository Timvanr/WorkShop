package workshop.controller;


import workshop.Service;
import workshop.dao.AdresDAOInterface;

import java.io.IOException;
import java.sql.SQLException;

public class AdresController {
	
	private Service service; // view
	private AdresDAOInterface adreslijst; // model
	
	public AdresController(Service service, AdresDAOInterface adreslijst) {

		this.service = service;
		this.adreslijst = adreslijst;
	}
	/*
	public void printAdresMetId() throws SQLException, IOException{
		service.printAdres(adreslijst.readAdresmetAdresId(service.adresIdPrompt()));
	}

	public void printAdresMetPCEnHuisnummer() throws SQLException, IOException{
		service.printAdres(adreslijst.readAdresMetPostcodeEnHuisnummer(
				service.postcodePrompt(), service.huisnummerPrompt(), service.toevoegingPrompt()));
	}
	
	public void printAdresMetStraatnaam() throws IOException{
		service.printAdressen(adreslijst.readAdresMetStraat(
				service.straatnaamPrompt(), service.woonplaatsPrompt()));
	}
	
	public void printAdresMetKlantId() throws IOException{
		service.printAdressen(adreslijst.readAdressenPerKlant(
				service.id_Prompt()));
	}
	
	public void printAlleAdressen(){
		service.printAdressen(adreslijst.readAll());
				
	}
	
	public void updateAdres() throws IOException{
		Adres adres = service.newAdres();
		adreslijst.updateAdres(service.id_Prompt(), adres);
	}
	
	public void deleteAdresvanKlant() throws IOException{
		adreslijst.deleteKlantAdresPair(service.id_Prompt(), service.adresIdPrompt());
	}
	*/
}
