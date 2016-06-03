package com.workshop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.model.Betaling;
import com.workshop.model.Betaling.Betaalwijze;

@Transactional
public interface BetalingDAO extends JpaRepository<Betaling, Long>{

	List<Betaling> findByFactuur(Long factuur_id);
	List<Betaling> findByBetaalwijze(Betaalwijze betaalwijze);

}
