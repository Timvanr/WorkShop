package com.workshop.dao;


import org.springframework.stereotype.Repository;

import com.workshop.model.Factuur;
@Repository
public interface FactuurDAOInterface extends GenericDao<Factuur, Long> {

	//Factuur saveBybestelling_id(long bestelling_id);
	//Factuur findBybestelling_id(long bestelling_id);

}
