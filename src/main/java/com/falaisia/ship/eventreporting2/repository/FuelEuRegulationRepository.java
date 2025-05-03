package com.falaisia.ship.eventreporting2.repository;

import com.falaisia.ship.eventreporting2.domain.FuelEuRegulation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FuelEuRegulation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuelEuRegulationRepository extends JpaRepository<FuelEuRegulation, Long>, JpaSpecificationExecutor<FuelEuRegulation> {}
