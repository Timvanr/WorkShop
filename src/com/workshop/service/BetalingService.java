package com.workshop.service;

import java.util.List;

import com.workshop.model.Betaling;
import com.workshop.model.Betaling.Betaalwijze;

public interface BetalingService {

	Betaling findBetaling(Long betaling_id);
	List<Betaling> findBetalingPerFactuur (Long factuur_id);
	void doeBetaling(Betaling betaling);
	List<Betaling> findBetalingPerType (Betaalwijze betaalwijze);
}
