package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.*; // for static metamodels
import com.falaisia.ship.eventreporting2.domain.MachineryOperationLine;
import com.falaisia.ship.eventreporting2.repository.MachineryOperationLineRepository;
import com.falaisia.ship.eventreporting2.service.criteria.MachineryOperationLineCriteria;
import com.falaisia.ship.eventreporting2.service.dto.MachineryOperationLineDTO;
import com.falaisia.ship.eventreporting2.service.mapper.MachineryOperationLineMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link MachineryOperationLine} entities in the database.
 * The main input is a {@link MachineryOperationLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MachineryOperationLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MachineryOperationLineQueryService extends QueryService<MachineryOperationLine> {

    private static final Logger LOG = LoggerFactory.getLogger(MachineryOperationLineQueryService.class);

    private final MachineryOperationLineRepository machineryOperationLineRepository;

    private final MachineryOperationLineMapper machineryOperationLineMapper;

    public MachineryOperationLineQueryService(
        MachineryOperationLineRepository machineryOperationLineRepository,
        MachineryOperationLineMapper machineryOperationLineMapper
    ) {
        this.machineryOperationLineRepository = machineryOperationLineRepository;
        this.machineryOperationLineMapper = machineryOperationLineMapper;
    }

    /**
     * Return a {@link List} of {@link MachineryOperationLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MachineryOperationLineDTO> findByCriteria(MachineryOperationLineCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<MachineryOperationLine> specification = createSpecification(criteria);
        return machineryOperationLineMapper.toDto(machineryOperationLineRepository.findAll(specification));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MachineryOperationLineCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<MachineryOperationLine> specification = createSpecification(criteria);
        return machineryOperationLineRepository.count(specification);
    }

    /**
     * Function to convert {@link MachineryOperationLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MachineryOperationLine> createSpecification(MachineryOperationLineCriteria criteria) {
        Specification<MachineryOperationLine> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), MachineryOperationLine_.id),
                buildRangeSpecification(criteria.getRunningHours(), MachineryOperationLine_.runningHours),
                buildRangeSpecification(criteria.getPowerOutput(), MachineryOperationLine_.powerOutput),
                buildRangeSpecification(criteria.getAverageRpm(), MachineryOperationLine_.averageRpm),
                buildSpecification(criteria.getEventReportId(), root ->
                    root.join(MachineryOperationLine_.eventReport, JoinType.LEFT).get(EventReport_.id)
                )
            );
        }
        return specification;
    }
}
