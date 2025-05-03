package com.falaisia.ship.eventreporting2.repository;

import com.falaisia.ship.eventreporting2.domain.Voyage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Voyage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Long>, JpaSpecificationExecutor<Voyage> {}
