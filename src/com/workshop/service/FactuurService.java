package com.workshop.service;

import com.workshop.model.Factuur;

public interface FactuurService {

	public Factuur saveFactuur(long bestelling_id);
	public Factuur findFactuurByBestelling_id(long bestelling_id);
}
