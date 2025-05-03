package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.*; // for static metamodels
import com.falaisia.ship.eventreporting2.domain.Port;
import com.falaisia.ship.eventreporting2.repository.PortRepository;
import com.falaisia.ship.eventreporting2.service.criteria.PortCriteria;
import com.falaisia.ship.eventreporting2.service.dto.PortDTO;
import com.falaisia.ship.eventreporting2.service.mapper.PortMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Port} entities in the database.
 * The main input is a {@link PortCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PortDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PortQueryService extends QueryService<Port> {

    private static final Logger LOG = LoggerFactory.getLogger(PortQueryService.class);

    private final PortRepository portRepository;

    private final PortMapper portMapper;

    public PortQueryService(PortRepository portRepository, PortMapper portMapper) {
        this.portRepository = portRepository;
        this.portMapper = portMapper;
    }

    /**
     * Return a {@link Page} of {@link PortDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PortDTO> findByCriteria(PortCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Port> specification = createSpecification(criteria);
        return portRepository.findAll(specification, page).map(portMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PortCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Port> specification = createSpecification(criteria);
        return portRepository.count(specification);
    }

    /**
     * Function to convert {@link PortCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Port> createSpecification(PortCriteria criteria) {
        Specification<Port> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Port_.id),
                buildStringSpecification(criteria.getName(), Port_.name),
                buildStringSpecification(criteria.getUnlocode(), Port_.unlocode)
            );
        }
        return specification;
    }
}
