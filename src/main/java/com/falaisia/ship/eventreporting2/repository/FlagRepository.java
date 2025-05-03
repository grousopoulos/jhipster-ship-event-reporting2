package com.falaisia.ship.eventreporting2.repository;

import com.falaisia.ship.eventreporting2.domain.Flag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Flag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FlagRepository extends JpaRepository<Flag, Long>, JpaSpecificationExecutor<Flag> {}
