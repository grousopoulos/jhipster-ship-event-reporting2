package com.falaisia.ship.eventreporting2.repository;

import com.falaisia.ship.eventreporting2.domain.Machinery;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Machinery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MachineryRepository extends JpaRepository<Machinery, Long>, JpaSpecificationExecutor<Machinery> {}
