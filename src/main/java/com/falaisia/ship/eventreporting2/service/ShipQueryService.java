package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.*; // for static metamodels
import com.falaisia.ship.eventreporting2.domain.Ship;
import com.falaisia.ship.eventreporting2.repository.ShipRepository;
import com.falaisia.ship.eventreporting2.service.criteria.ShipCriteria;
import com.falaisia.ship.eventreporting2.service.dto.ShipDTO;
import com.falaisia.ship.eventreporting2.service.mapper.ShipMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Ship} entities in the database.
 * The main input is a {@link ShipCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ShipDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShipQueryService extends QueryService<Ship> {

    private static final Logger LOG = LoggerFactory.getLogger(ShipQueryService.class);

    private final ShipRepository shipRepository;

    private final ShipMapper shipMapper;

    public ShipQueryService(ShipRepository shipRepository, ShipMapper shipMapper) {
        this.shipRepository = shipRepository;
        this.shipMapper = shipMapper;
    }

    /**
     * Return a {@link Page} of {@link ShipDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShipDTO> findByCriteria(ShipCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ship> specification = createSpecification(criteria);
        return shipRepository.findAll(specification, page).map(shipMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ShipCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Ship> specification = createSpecification(criteria);
        return shipRepository.count(specification);
    }

    /**
     * Function to convert {@link ShipCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ship> createSpecification(ShipCriteria criteria) {
        Specification<Ship> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Ship_.id),
                buildStringSpecification(criteria.getName(), Ship_.name),
                buildStringSpecification(criteria.getCallSign(), Ship_.callSign),
                buildSpecification(criteria.getIceClassPolarCode(), Ship_.iceClassPolarCode),
                buildSpecification(criteria.getTechnicalEfficiencyCode(), Ship_.technicalEfficiencyCode),
                buildSpecification(criteria.getShipType(), Ship_.shipType),
                buildSpecification(criteria.getMonitoringMethodCode(), Ship_.monitoringMethodCode),
                buildSpecification(criteria.getOwnerCountryId(), root -> root.join(Ship_.ownerCountry, JoinType.LEFT).get(Country_.id)),
                buildSpecification(criteria.getFlagId(), root -> root.join(Ship_.flag, JoinType.LEFT).get(Flag_.id)),
                buildSpecification(criteria.getClassificationSocietyId(), root ->
                    root.join(Ship_.classificationSociety, JoinType.LEFT).get(ClassificationSociety_.id)
                )
            );
        }
        return specification;
    }
}
