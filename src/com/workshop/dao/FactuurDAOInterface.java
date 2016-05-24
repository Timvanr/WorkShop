package com.workshop.dao;


import com.workshop.model.Factuur;

public interface FactuurDAOInterface {

	Factuur saveBybestelling_id(long bestelling_id);
	Factuur findBybestelling_id(long bestelling_id);

}
