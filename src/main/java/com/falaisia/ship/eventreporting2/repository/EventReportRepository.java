package com.falaisia.ship.eventreporting2.repository;

import com.falaisia.ship.eventreporting2.domain.EventReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventReportRepository extends JpaRepository<EventReport, Long>, JpaSpecificationExecutor<EventReport> {}
