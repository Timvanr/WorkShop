package com.workshop.service;

import com.workshop.model.Factuur;

public interface FactuurService {

	void createFactuur(Factuur factuur);
	//weet niet of verwijder nodig is hoor...
	void verwijderFactuur(Long factuur_id);
	Factuur findFactuur(Long factuur_id);
	Factuur findFactuurByBestelling_id(Long bestelling_id);
}
