package com.falaisia.ship.eventreporting2.repository;

import com.falaisia.ship.eventreporting2.domain.Ship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipRepository extends JpaRepository<Ship, Long>, JpaSpecificationExecutor<Ship> {}
