package com.falaisia.ship.eventreporting2.repository;

import com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BunkerReceivedNoteLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BunkerReceivedNoteLineRepository
    extends JpaRepository<BunkerReceivedNoteLine, Long>, JpaSpecificationExecutor<BunkerReceivedNoteLine> {}
