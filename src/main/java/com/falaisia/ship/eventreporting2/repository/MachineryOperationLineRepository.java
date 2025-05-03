package com.falaisia.ship.eventreporting2.repository;

import com.falaisia.ship.eventreporting2.domain.MachineryOperationLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MachineryOperationLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MachineryOperationLineRepository
    extends JpaRepository<MachineryOperationLine, Long>, JpaSpecificationExecutor<MachineryOperationLine> {}
