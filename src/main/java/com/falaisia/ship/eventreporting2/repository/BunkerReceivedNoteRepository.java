package com.falaisia.ship.eventreporting2.repository;

import com.falaisia.ship.eventreporting2.domain.BunkerReceivedNote;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BunkerReceivedNote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BunkerReceivedNoteRepository
    extends JpaRepository<BunkerReceivedNote, Long>, JpaSpecificationExecutor<BunkerReceivedNote> {}
