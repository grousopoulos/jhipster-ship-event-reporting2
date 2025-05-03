package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.*; // for static metamodels
import com.falaisia.ship.eventreporting2.domain.Voyage;
import com.falaisia.ship.eventreporting2.repository.VoyageRepository;
import com.falaisia.ship.eventreporting2.service.criteria.VoyageCriteria;
import com.falaisia.ship.eventreporting2.service.dto.VoyageDTO;
import com.falaisia.ship.eventreporting2.service.mapper.VoyageMapper;
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
 * Service for executing complex queries for {@link Voyage} entities in the database.
 * The main input is a {@link VoyageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link VoyageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VoyageQueryService extends QueryService<Voyage> {

    private static final Logger LOG = LoggerFactory.getLogger(VoyageQueryService.class);

    private final VoyageRepository voyageRepository;

    private final VoyageMapper voyageMapper;

    public VoyageQueryService(VoyageRepository voyageRepository, VoyageMapper voyageMapper) {
        this.voyageRepository = voyageRepository;
        this.voyageMapper = voyageMapper;
    }

    /**
     * Return a {@link Page} of {@link VoyageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VoyageDTO> findByCriteria(VoyageCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Voyage> specification = createSpecification(criteria);
        return voyageRepository.findAll(specification, page).map(voyageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VoyageCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Voyage> specification = createSpecification(criteria);
        return voyageRepository.count(specification);
    }

    /**
     * Function to convert {@link VoyageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Voyage> createSpecification(VoyageCriteria criteria) {
        Specification<Voyage> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Voyage_.id),
                buildStringSpecification(criteria.getNumber(), Voyage_.number),
                buildSpecification(criteria.getShipId(), root -> root.join(Voyage_.ship, JoinType.LEFT).get(Ship_.id))
            );
        }
        return specification;
    }
}
