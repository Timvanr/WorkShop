package workshop.dao;


import workshop.model.Factuur;

public interface FactuurDAOInterface {

	Factuur readFactuurMetFactuurId(long factuur_id);
	void updateFactuurBedrag(long factuur_id);
	Factuur createFactuur(int bestelling_id);
	Factuur readFactuurMetBestellingId(long bestelling_id);
	
	
}
