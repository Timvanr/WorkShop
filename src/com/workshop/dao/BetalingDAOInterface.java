package com.workshop.dao;

import java.math.BigDecimal;

import com.workshop.model.Betaling;
import com.workshop.model.Betaling.Betaalwijze;

public interface BetalingDAOInterface {

	Betaling createBetaling(long factuur_id, BigDecimal bedrag, Betaalwijze bwijze);
	void readAlleBetalingen(long factuur_id);
}
