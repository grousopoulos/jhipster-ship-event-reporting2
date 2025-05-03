package com.falaisia.ship.eventreporting2.repository;

import com.falaisia.ship.eventreporting2.domain.ClassificationSociety;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ClassificationSociety entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassificationSocietyRepository
    extends JpaRepository<ClassificationSociety, Long>, JpaSpecificationExecutor<ClassificationSociety> {}
