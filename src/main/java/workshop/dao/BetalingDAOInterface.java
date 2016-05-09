package workshop.dao;

import java.math.BigDecimal;

import workshop.model.Betaling;
import workshop.model.Betaling.Betaalwijze;

public interface BetalingDAOInterface {

	Betaling createBetaling(long factuur_id, BigDecimal bedrag, Betaalwijze bwijze);
	void readAlleBetalingen(long factuur_id);
}
