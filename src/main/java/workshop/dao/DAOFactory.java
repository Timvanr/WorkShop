
public class DAOFactory {

	public KlantDAO getKlantbestand(int dataOpslagType){
		
		KlantDAO klantDao = null;
		
		if (dataOpslagType == 1){
			return new Klantbestand();
		}
		else if (dataOpslagType == 2){
			return new KlantDAOFireBird();
		}
		
		/*else if (dataOpslagType == 3){
			return new KlantbestandJson();
		}
		
		else if (dataOpslagType == 4){
			return new KlantbestandXML();
		}
		*/
		else {
			System.out.println("Voer een getal van 1 t/m 4 in");
			return klantDao;
		}
		
	}
	
	
	public AdresDAO getAdreslijst(int dataOpslagType){
		
			AdresDAO adresDao = null;
			
		if (dataOpslagType == 1){
			return new Adreslijst();
		}
		else if (dataOpslagType == 2){
			return new AdresDAOFireBird();
		}
		
		/*else if (dataOpslagType == 3){
			return new AdreslijstJson();
		}
		
		else if (dataOpslagType == 4){
			return new AdreslijstXML();
		}
		*/
		else {
			System.out.println("Voer een getal van 1 t/m 4 in");
			return adresDao;
		}
	}
	
	public BestellingDAO getBestellijst(int dataOpslagType){
		
		BestellingDAO bestellingDao = null;
		
		if (dataOpslagType == 1){
			return new Bestellijst();
		}
		else if (dataOpslagType == 2){
			return new BestellingDAOFireBird();
		}
		
		/*else if (dataOpslagType == 3){
			return new BestellijstJson();
		}
		
		else if (dataOpslagType == 4){
			return new BestellijstXML();
		}*/
		
		else {
			System.out.println("Voer een getal van 1 t/m 4 in");
			return bestellingDao;
		}
	}
	
	// Deze hebben we nu (nog) niet maar moeten we wel nog opnemen
	public ArtikelDAO getArtikelLijst(int dataOpslagType){
		
		ArtikelDAO artikelDao = null;
		
		if (dataOpslagType == 1){
			return new ArtikelLijst();
		}
		else if (dataOpslagType == 2){
			return new ArtikelLijstDAOFireBird();
		}
		
		/*else if (dataOpslagType == 3){
			return new ArtikellijstJson();
		}
		
		else if (dataOpslagType == 4){
			return new ArtikellijstXML();
		}
		*/
		else {
			System.out.println("Voer een getal van 1 t/m 4 in");
			return artikelDao;
		}
		
	}
	
}
