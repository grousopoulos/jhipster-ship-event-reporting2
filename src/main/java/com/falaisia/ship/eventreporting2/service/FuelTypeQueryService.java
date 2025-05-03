package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.*; // for static metamodels
import com.falaisia.ship.eventreporting2.domain.FuelType;
import com.falaisia.ship.eventreporting2.repository.FuelTypeRepository;
import com.falaisia.ship.eventreporting2.service.criteria.FuelTypeCriteria;
import com.falaisia.ship.eventreporting2.service.dto.FuelTypeDTO;
import com.falaisia.ship.eventreporting2.service.mapper.FuelTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link FuelType} entities in the database.
 * The main input is a {@link FuelTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FuelTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FuelTypeQueryService extends QueryService<FuelType> {

    private static final Logger LOG = LoggerFactory.getLogger(FuelTypeQueryService.class);

    private final FuelTypeRepository fuelTypeRepository;

    private final FuelTypeMapper fuelTypeMapper;

    public FuelTypeQueryService(FuelTypeRepository fuelTypeRepository, FuelTypeMapper fuelTypeMapper) {
        this.fuelTypeRepository = fuelTypeRepository;
        this.fuelTypeMapper = fuelTypeMapper;
    }

    /**
     * Return a {@link Page} of {@link FuelTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FuelTypeDTO> findByCriteria(FuelTypeCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FuelType> specification = createSpecification(criteria);
        return fuelTypeRepository.findAll(specification, page).map(fuelTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FuelTypeCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<FuelType> specification = createSpecification(criteria);
        return fuelTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link FuelTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FuelType> createSpecification(FuelTypeCriteria criteria) {
        Specification<FuelType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), FuelType_.id),
                buildStringSpecification(criteria.getName(), FuelType_.name),
                buildSpecification(criteria.getFuelTypeCode(), FuelType_.fuelTypeCode),
                buildRangeSpecification(criteria.getCarbonFactory(), FuelType_.carbonFactory)
            );
        }
        return specification;
    }
}
