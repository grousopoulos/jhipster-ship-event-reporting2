package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.*; // for static metamodels
import com.falaisia.ship.eventreporting2.domain.Machinery;
import com.falaisia.ship.eventreporting2.repository.MachineryRepository;
import com.falaisia.ship.eventreporting2.service.criteria.MachineryCriteria;
import com.falaisia.ship.eventreporting2.service.dto.MachineryDTO;
import com.falaisia.ship.eventreporting2.service.mapper.MachineryMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Machinery} entities in the database.
 * The main input is a {@link MachineryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MachineryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MachineryQueryService extends QueryService<Machinery> {

    private static final Logger LOG = LoggerFactory.getLogger(MachineryQueryService.class);

    private final MachineryRepository machineryRepository;

    private final MachineryMapper machineryMapper;

    public MachineryQueryService(MachineryRepository machineryRepository, MachineryMapper machineryMapper) {
        this.machineryRepository = machineryRepository;
        this.machineryMapper = machineryMapper;
    }

    /**
     * Return a {@link List} of {@link MachineryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MachineryDTO> findByCriteria(MachineryCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<Machinery> specification = createSpecification(criteria);
        return machineryMapper.toDto(machineryRepository.findAll(specification));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MachineryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Machinery> specification = createSpecification(criteria);
        return machineryRepository.count(specification);
    }

    /**
     * Function to convert {@link MachineryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Machinery> createSpecification(MachineryCriteria criteria) {
        Specification<Machinery> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Machinery_.id),
                buildStringSpecification(criteria.getName(), Machinery_.name),
                buildSpecification(criteria.getShipId(), root -> root.join(Machinery_.ship, JoinType.LEFT).get(Ship_.id))
            );
        }
        return specification;
    }
}
