package workshop.dao;


public class DAOFactory {
	private static KlantDAOInterface klantDAO;
	private static AdresDAOInterface adresDAO;
	private static BestellingDAOInterface bestellingDAO;
	private static ArtikelDAOInterface artikelDAO;
	private static FactuurDAOInterface factuurDAO;
	private static BetalingDAOInterface betalingDAO;
	private static int dataOpslagType;
	
	public static void setDataOpslagType(int opslagType) {
		dataOpslagType = opslagType;
	}
	
	public static KlantDAOInterface getKlantDAO(){
		if (klantDAO == null){
			if (dataOpslagType == 1){
				klantDAO = new workshop.dao.mysql.KlantDAO();
			}
			else if (dataOpslagType == 2){
				klantDAO = new workshop.dao.firebird.KlantDAO();
			}
			
			/*else if (dataOpslagType == 3){
				klantDAO = new KlantDAOJson();
			}
			
			else if (dataOpslagType == 4){
				klantDAO = new KlantDAOXML();
			}
			*/
		}
		return klantDAO;
		
	}
	
	
	public static AdresDAOInterface getAdresDAO(){
		if (adresDAO == null){
			if (dataOpslagType == 1){
				adresDAO = new workshop.dao.mysql.AdresDAO();
			}
			else if (dataOpslagType == 2){
				adresDAO = new workshop.dao.firebird.AdresDAO();
			}
			
			/*else if (dataOpslagType == 3){
				return new AdreslijstJson();
			}
			
			else if (dataOpslagType == 4){
				return new AdreslijstXML();
			}
			*/
			
		}
		return adresDAO;
	}
	
	public static BestellingDAOInterface getBestellingDAO(){
		if (bestellingDAO == null){
			if (dataOpslagType == 1){
				bestellingDAO = new workshop.dao.mysql.BestellingDAO();
			}
			else if (dataOpslagType == 2){
				bestellingDAO = new workshop.dao.firebird.BestellingDAO();
			}
			
			/*else if (dataOpslagType == 3){
				return new BestellijstJson();
			}
			
			else if (dataOpslagType == 4){
				return new BestellijstXML();
			}*/
			
		}
		return bestellingDAO;
	}
	
	// Deze hebben we nu (nog) niet maar moeten we wel nog opnemen
	public static ArtikelDAOInterface getArtikelDAO(){
		if (artikelDAO == null){
			if (dataOpslagType == 1){
				artikelDAO = new workshop.dao.mysql.ArtikelDAO();
			}
			else if (dataOpslagType == 2){
				artikelDAO = new workshop.dao.firebird.ArtikelDAO();
			}
			
			/*else if (dataOpslagType == 3){
				return new ArtikellijstJson();
			}
			
			else if (dataOpslagType == 4){
				return new ArtikellijstXML();
			}
			*/
		}
		return artikelDAO;
	}


public static BetalingDAOInterface getBetalingDAO(){
	if (betalingDAO == null){
		if (dataOpslagType == 1){
			betalingDAO = new workshop.dao.mysql.BetalingDAO();
		}
	/*	else if (dataOpslagType == 2){
			betalingDAO = new workshop.dao.firebird.BetalingDAO();
		}
		
		else if (dataOpslagType == 3){
			return new betalinglijstJson();
		}
		
		else if (dataOpslagType == 4){
			return new betalinglijstXML();
		}
		*/
	}
	return betalingDAO;
}

public static FactuurDAOInterface getFactuurDAO(){
	if (factuurDAO == null){
		if (dataOpslagType == 1){
			factuurDAO = new workshop.dao.mysql.FactuurDAO();
		}
		/*else if (dataOpslagType == 2){
			factuurDAO = new workshop.dao.firebird.FactuurDAO();
		}
		
		else if (dataOpslagType == 3){
			return new factuurlijstJson();
		}
		
		else if (dataOpslagType == 4){
			return new factuurlijstXML();
		}
		*/
	}
	return factuurDAO;
}

}

