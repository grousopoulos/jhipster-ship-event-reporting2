package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.*; // for static metamodels
import com.falaisia.ship.eventreporting2.domain.Flag;
import com.falaisia.ship.eventreporting2.repository.FlagRepository;
import com.falaisia.ship.eventreporting2.service.criteria.FlagCriteria;
import com.falaisia.ship.eventreporting2.service.dto.FlagDTO;
import com.falaisia.ship.eventreporting2.service.mapper.FlagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Flag} entities in the database.
 * The main input is a {@link FlagCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FlagDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FlagQueryService extends QueryService<Flag> {

    private static final Logger LOG = LoggerFactory.getLogger(FlagQueryService.class);

    private final FlagRepository flagRepository;

    private final FlagMapper flagMapper;

    public FlagQueryService(FlagRepository flagRepository, FlagMapper flagMapper) {
        this.flagRepository = flagRepository;
        this.flagMapper = flagMapper;
    }

    /**
     * Return a {@link Page} of {@link FlagDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FlagDTO> findByCriteria(FlagCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Flag> specification = createSpecification(criteria);
        return flagRepository.findAll(specification, page).map(flagMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FlagCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Flag> specification = createSpecification(criteria);
        return flagRepository.count(specification);
    }

    /**
     * Function to convert {@link FlagCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Flag> createSpecification(FlagCriteria criteria) {
        Specification<Flag> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Flag_.id),
                buildStringSpecification(criteria.getCode(), Flag_.code),
                buildStringSpecification(criteria.getName(), Flag_.name)
            );
        }
        return specification;
    }
}
