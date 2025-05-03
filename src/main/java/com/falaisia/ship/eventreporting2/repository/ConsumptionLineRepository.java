package com.falaisia.ship.eventreporting2.repository;

import com.falaisia.ship.eventreporting2.domain.ConsumptionLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ConsumptionLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumptionLineRepository extends JpaRepository<ConsumptionLine, Long>, JpaSpecificationExecutor<ConsumptionLine> {}
