package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.*; // for static metamodels
import com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteLine;
import com.falaisia.ship.eventreporting2.repository.BunkerReceivedNoteLineRepository;
import com.falaisia.ship.eventreporting2.service.criteria.BunkerReceivedNoteLineCriteria;
import com.falaisia.ship.eventreporting2.service.dto.BunkerReceivedNoteLineDTO;
import com.falaisia.ship.eventreporting2.service.mapper.BunkerReceivedNoteLineMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link BunkerReceivedNoteLine} entities in the database.
 * The main input is a {@link BunkerReceivedNoteLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BunkerReceivedNoteLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BunkerReceivedNoteLineQueryService extends QueryService<BunkerReceivedNoteLine> {

    private static final Logger LOG = LoggerFactory.getLogger(BunkerReceivedNoteLineQueryService.class);

    private final BunkerReceivedNoteLineRepository bunkerReceivedNoteLineRepository;

    private final BunkerReceivedNoteLineMapper bunkerReceivedNoteLineMapper;

    public BunkerReceivedNoteLineQueryService(
        BunkerReceivedNoteLineRepository bunkerReceivedNoteLineRepository,
        BunkerReceivedNoteLineMapper bunkerReceivedNoteLineMapper
    ) {
        this.bunkerReceivedNoteLineRepository = bunkerReceivedNoteLineRepository;
        this.bunkerReceivedNoteLineMapper = bunkerReceivedNoteLineMapper;
    }

    /**
     * Return a {@link List} of {@link BunkerReceivedNoteLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BunkerReceivedNoteLineDTO> findByCriteria(BunkerReceivedNoteLineCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<BunkerReceivedNoteLine> specification = createSpecification(criteria);
        return bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLineRepository.findAll(specification));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BunkerReceivedNoteLineCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<BunkerReceivedNoteLine> specification = createSpecification(criteria);
        return bunkerReceivedNoteLineRepository.count(specification);
    }

    /**
     * Function to convert {@link BunkerReceivedNoteLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BunkerReceivedNoteLine> createSpecification(BunkerReceivedNoteLineCriteria criteria) {
        Specification<BunkerReceivedNoteLine> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), BunkerReceivedNoteLine_.id),
                buildRangeSpecification(criteria.getQuantity(), BunkerReceivedNoteLine_.quantity),
                buildSpecification(criteria.getUnitOfMeasure(), BunkerReceivedNoteLine_.unitOfMeasure),
                buildRangeSpecification(criteria.getLowerCalorificValue(), BunkerReceivedNoteLine_.lowerCalorificValue),
                buildRangeSpecification(criteria.getSulphurContent(), BunkerReceivedNoteLine_.sulphurContent),
                buildRangeSpecification(criteria.getDensity(), BunkerReceivedNoteLine_.density),
                buildRangeSpecification(criteria.getViscosity(), BunkerReceivedNoteLine_.viscosity),
                buildRangeSpecification(criteria.getWaterContent(), BunkerReceivedNoteLine_.waterContent),
                buildSpecification(criteria.getBunkerReceivedNoteId(), root ->
                    root.join(BunkerReceivedNoteLine_.bunkerReceivedNote, JoinType.LEFT).get(BunkerReceivedNote_.id)
                ),
                buildSpecification(criteria.getFuelTypeId(), root ->
                    root.join(BunkerReceivedNoteLine_.fuelType, JoinType.LEFT).get(FuelType_.id)
                )
            );
        }
        return specification;
    }
}
