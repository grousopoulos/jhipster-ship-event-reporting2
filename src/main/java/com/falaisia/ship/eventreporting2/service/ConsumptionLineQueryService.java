package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.*; // for static metamodels
import com.falaisia.ship.eventreporting2.domain.ConsumptionLine;
import com.falaisia.ship.eventreporting2.repository.ConsumptionLineRepository;
import com.falaisia.ship.eventreporting2.service.criteria.ConsumptionLineCriteria;
import com.falaisia.ship.eventreporting2.service.dto.ConsumptionLineDTO;
import com.falaisia.ship.eventreporting2.service.mapper.ConsumptionLineMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ConsumptionLine} entities in the database.
 * The main input is a {@link ConsumptionLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConsumptionLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConsumptionLineQueryService extends QueryService<ConsumptionLine> {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumptionLineQueryService.class);

    private final ConsumptionLineRepository consumptionLineRepository;

    private final ConsumptionLineMapper consumptionLineMapper;

    public ConsumptionLineQueryService(ConsumptionLineRepository consumptionLineRepository, ConsumptionLineMapper consumptionLineMapper) {
        this.consumptionLineRepository = consumptionLineRepository;
        this.consumptionLineMapper = consumptionLineMapper;
    }

    /**
     * Return a {@link List} of {@link ConsumptionLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConsumptionLineDTO> findByCriteria(ConsumptionLineCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<ConsumptionLine> specification = createSpecification(criteria);
        return consumptionLineMapper.toDto(consumptionLineRepository.findAll(specification));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConsumptionLineCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<ConsumptionLine> specification = createSpecification(criteria);
        return consumptionLineRepository.count(specification);
    }

    /**
     * Function to convert {@link ConsumptionLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ConsumptionLine> createSpecification(ConsumptionLineCriteria criteria) {
        Specification<ConsumptionLine> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), ConsumptionLine_.id),
                buildRangeSpecification(criteria.getQuantity(), ConsumptionLine_.quantity),
                buildSpecification(criteria.getUnitOfMeasure(), ConsumptionLine_.unitOfMeasure),
                buildSpecification(criteria.getCo2EmissionSourceTypeCode(), ConsumptionLine_.co2EmissionSourceTypeCode),
                buildRangeSpecification(criteria.getLowerCalorificValue(), ConsumptionLine_.lowerCalorificValue),
                buildRangeSpecification(criteria.getSulphurContent(), ConsumptionLine_.sulphurContent),
                buildRangeSpecification(criteria.getDensity(), ConsumptionLine_.density),
                buildRangeSpecification(criteria.getViscosity(), ConsumptionLine_.viscosity),
                buildRangeSpecification(criteria.getWaterContent(), ConsumptionLine_.waterContent),
                buildSpecification(criteria.getPortActivityCode(), ConsumptionLine_.portActivityCode),
                buildSpecification(criteria.getDiffCriterionCode(), ConsumptionLine_.diffCriterionCode),
                buildSpecification(criteria.getEventReportId(), root ->
                    root.join(ConsumptionLine_.eventReport, JoinType.LEFT).get(EventReport_.id)
                ),
                buildSpecification(criteria.getFuelTypeId(), root -> root.join(ConsumptionLine_.fuelType, JoinType.LEFT).get(FuelType_.id))
            );
        }
        return specification;
    }
}
