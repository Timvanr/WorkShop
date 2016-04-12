
public class DAOFactory {

	public KlantDAO getKlantbestand(int dataOpslagType){
		
		KlantDAO klantDao = null;
		
		if (dataOpslagType == 1){
			return new KlantbestandMySql();
		}
		else if (dataOpslagType == 2){
			return new KlantbestandFirebird();
		}
		
		else if (dataOpslagType == 3){
			return new KlantbestandJson();
		}
		
		else if (dataOpslagType == 4){
			return new KlantbestandXML();
		}
		
		else {
			System.out.println("Voer een getal van 1 t/m 4 in");
		}
		
	}
	
	
	public Adreslijst getAdreslijst(int dataOpslagType){
		
			AdresDAO adresDao = null;
			
		if (dataOpslagType == 1){
			return new AdreslijstMySql()
		}
		else if (dataOpslagType == 2){
			return new AdreslijstFirebird();
		}
		
		else if (dataOpslagType == 3){
			return new AdreslijstJson();
		}
		
		else if (dataOpslagType == 4){
			return new AdreslijstXML();
		}
		
		else {
			System.out.println("Voer een getal van 1 t/m 4 in");
		}
	}
	
	public Bestellijst getBestellijst(int dataOpslagType){
		
		BestellingDAO bestellingDao = null;
		
		if (dataOpslagType == 1){
			return new BestellijstMySql()
		}
		else if (dataOpslagType == 2){
			return new BestellijstFirebird();
		}
		
		else if (dataOpslagType == 3){
			return new BestellijstJson();
		}
		
		else if (dataOpslagType == 4){
			return new BestellijstXML();
		}
		
		else {
			System.out.println("Voer een getal van 1 t/m 4 in");
		}
	}
	
	// Deze hebben we nu (nog) niet maar moeten we wel nog opnemen
	public ArtikelLijst getArtikelLijst(int dataOpslagType){
		
		ArtikelDAO artikelDao = null;
		
		if (dataOpslagType == 1){
			return new ArtikellijstMySql()
		}
		else if (dataOpslagType == 2){
			return new ArtikellijstFirebird();
		}
		
		else if (dataOpslagType == 3){
			return new ArtikellijstJson();
		}
		
		else if (dataOpslagType == 4){
			return new ArtikellijstXML();
		}
		
		else {
			System.out.println("Voer een getal van 1 t/m 4 in");
		}
		
	}
	
}
