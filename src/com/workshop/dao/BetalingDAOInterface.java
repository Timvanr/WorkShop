package com.workshop.dao;

import java.math.BigDecimal;

import com.workshop.model.Betaling;
import com.workshop.model.Betaling.Betaalwijze;

public interface BetalingDAOInterface {

	Betaling findByFactuur_id(long factuur_id);
	
}
