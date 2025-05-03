package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.*; // for static metamodels
import com.falaisia.ship.eventreporting2.domain.FuelEuRegulation;
import com.falaisia.ship.eventreporting2.repository.FuelEuRegulationRepository;
import com.falaisia.ship.eventreporting2.service.criteria.FuelEuRegulationCriteria;
import com.falaisia.ship.eventreporting2.service.dto.FuelEuRegulationDTO;
import com.falaisia.ship.eventreporting2.service.mapper.FuelEuRegulationMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link FuelEuRegulation} entities in the database.
 * The main input is a {@link FuelEuRegulationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FuelEuRegulationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FuelEuRegulationQueryService extends QueryService<FuelEuRegulation> {

    private static final Logger LOG = LoggerFactory.getLogger(FuelEuRegulationQueryService.class);

    private final FuelEuRegulationRepository fuelEuRegulationRepository;

    private final FuelEuRegulationMapper fuelEuRegulationMapper;

    public FuelEuRegulationQueryService(
        FuelEuRegulationRepository fuelEuRegulationRepository,
        FuelEuRegulationMapper fuelEuRegulationMapper
    ) {
        this.fuelEuRegulationRepository = fuelEuRegulationRepository;
        this.fuelEuRegulationMapper = fuelEuRegulationMapper;
    }

    /**
     * Return a {@link List} of {@link FuelEuRegulationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FuelEuRegulationDTO> findByCriteria(FuelEuRegulationCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<FuelEuRegulation> specification = createSpecification(criteria);
        return fuelEuRegulationMapper.toDto(fuelEuRegulationRepository.findAll(specification));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FuelEuRegulationCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<FuelEuRegulation> specification = createSpecification(criteria);
        return fuelEuRegulationRepository.count(specification);
    }

    /**
     * Function to convert {@link FuelEuRegulationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FuelEuRegulation> createSpecification(FuelEuRegulationCriteria criteria) {
        Specification<FuelEuRegulation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), FuelEuRegulation_.id),
                buildRangeSpecification(criteria.getYear(), FuelEuRegulation_.year),
                buildRangeSpecification(criteria.getCo2Gwp(), FuelEuRegulation_.co2Gwp),
                buildRangeSpecification(criteria.getMethaneGwp(), FuelEuRegulation_.methaneGwp),
                buildRangeSpecification(criteria.getNitrousGwp(), FuelEuRegulation_.nitrousGwp),
                buildRangeSpecification(criteria.getTargetIntensity(), FuelEuRegulation_.targetIntensity),
                buildRangeSpecification(criteria.getBaselineIntensity(), FuelEuRegulation_.baselineIntensity),
                buildRangeSpecification(criteria.getReductionFactorPercent(), FuelEuRegulation_.reductionFactorPercent),
                buildRangeSpecification(criteria.getVlsfoEnergyContentPerTonMj(), FuelEuRegulation_.vlsfoEnergyContentPerTonMj),
                buildRangeSpecification(criteria.getVlsfoPenaltyEurPerTon(), FuelEuRegulation_.vlsfoPenaltyEurPerTon),
                buildRangeSpecification(criteria.getEnergyAllowanceMultiplier(), FuelEuRegulation_.energyAllowanceMultiplier),
                buildRangeSpecification(criteria.getNonBioFuelRewardFactor(), FuelEuRegulation_.nonBioFuelRewardFactor)
            );
        }
        return specification;
    }
}
