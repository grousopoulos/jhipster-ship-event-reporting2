package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.*; // for static metamodels
import com.falaisia.ship.eventreporting2.domain.ClassificationSociety;
import com.falaisia.ship.eventreporting2.repository.ClassificationSocietyRepository;
import com.falaisia.ship.eventreporting2.service.criteria.ClassificationSocietyCriteria;
import com.falaisia.ship.eventreporting2.service.dto.ClassificationSocietyDTO;
import com.falaisia.ship.eventreporting2.service.mapper.ClassificationSocietyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ClassificationSociety} entities in the database.
 * The main input is a {@link ClassificationSocietyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ClassificationSocietyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClassificationSocietyQueryService extends QueryService<ClassificationSociety> {

    private static final Logger LOG = LoggerFactory.getLogger(ClassificationSocietyQueryService.class);

    private final ClassificationSocietyRepository classificationSocietyRepository;

    private final ClassificationSocietyMapper classificationSocietyMapper;

    public ClassificationSocietyQueryService(
        ClassificationSocietyRepository classificationSocietyRepository,
        ClassificationSocietyMapper classificationSocietyMapper
    ) {
        this.classificationSocietyRepository = classificationSocietyRepository;
        this.classificationSocietyMapper = classificationSocietyMapper;
    }

    /**
     * Return a {@link Page} of {@link ClassificationSocietyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassificationSocietyDTO> findByCriteria(ClassificationSocietyCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClassificationSociety> specification = createSpecification(criteria);
        return classificationSocietyRepository.findAll(specification, page).map(classificationSocietyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClassificationSocietyCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ClassificationSociety> specification = createSpecification(criteria);
        return classificationSocietyRepository.count(specification);
    }

    /**
     * Function to convert {@link ClassificationSocietyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClassificationSociety> createSpecification(ClassificationSocietyCriteria criteria) {
        Specification<ClassificationSociety> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), ClassificationSociety_.id),
                buildStringSpecification(criteria.getCode(), ClassificationSociety_.code),
                buildStringSpecification(criteria.getName(), ClassificationSociety_.name)
            );
        }
        return specification;
    }
}
