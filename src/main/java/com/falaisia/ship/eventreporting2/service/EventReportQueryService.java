package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.*; // for static metamodels
import com.falaisia.ship.eventreporting2.domain.EventReport;
import com.falaisia.ship.eventreporting2.repository.EventReportRepository;
import com.falaisia.ship.eventreporting2.service.criteria.EventReportCriteria;
import com.falaisia.ship.eventreporting2.service.dto.EventReportDTO;
import com.falaisia.ship.eventreporting2.service.mapper.EventReportMapper;
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
 * Service for executing complex queries for {@link EventReport} entities in the database.
 * The main input is a {@link EventReportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link EventReportDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EventReportQueryService extends QueryService<EventReport> {

    private static final Logger LOG = LoggerFactory.getLogger(EventReportQueryService.class);

    private final EventReportRepository eventReportRepository;

    private final EventReportMapper eventReportMapper;

    public EventReportQueryService(EventReportRepository eventReportRepository, EventReportMapper eventReportMapper) {
        this.eventReportRepository = eventReportRepository;
        this.eventReportMapper = eventReportMapper;
    }

    /**
     * Return a {@link Page} of {@link EventReportDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EventReportDTO> findByCriteria(EventReportCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EventReport> specification = createSpecification(criteria);
        return eventReportRepository.findAll(specification, page).map(eventReportMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EventReportCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<EventReport> specification = createSpecification(criteria);
        return eventReportRepository.count(specification);
    }

    /**
     * Function to convert {@link EventReportCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EventReport> createSpecification(EventReportCriteria criteria) {
        Specification<EventReport> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), EventReport_.id),
                buildRangeSpecification(criteria.getDocumentDateAndTime(), EventReport_.documentDateAndTime),
                buildRangeSpecification(criteria.getSpeedGps(), EventReport_.speedGps),
                buildStringSpecification(criteria.getDocumentDisplayNumber(), EventReport_.documentDisplayNumber),
                buildRangeSpecification(criteria.getLeg(), EventReport_.leg),
                buildRangeSpecification(criteria.getDistanceTravelled(), EventReport_.distanceTravelled),
                buildRangeSpecification(criteria.getHoursUnderway(), EventReport_.hoursUnderway),
                buildSpecification(criteria.getEventStatus(), EventReport_.eventStatus),
                buildSpecification(criteria.getLoadingCondition(), EventReport_.loadingCondition),
                buildRangeSpecification(criteria.getCargoCarried(), EventReport_.cargoCarried),
                buildStringSpecification(criteria.getCoordinatesLatitude(), EventReport_.coordinatesLatitude),
                buildStringSpecification(criteria.getCoordinatesLongitude(), EventReport_.coordinatesLongitude),
                buildStringSpecification(criteria.getShipsHeading(), EventReport_.shipsHeading),
                buildRangeSpecification(criteria.getBeaufortNo(), EventReport_.beaufortNo),
                buildStringSpecification(criteria.getWeatherCondition(), EventReport_.weatherCondition),
                buildSpecification(criteria.getSwell(), EventReport_.swell),
                buildSpecification(criteria.getVoyageId(), root -> root.join(EventReport_.voyage, JoinType.LEFT).get(Voyage_.id)),
                buildSpecification(criteria.getLinesId(), root -> root.join(EventReport_.lines, JoinType.LEFT).get(ConsumptionLine_.id)),
                buildSpecification(criteria.getOperationLinesId(), root ->
                    root.join(EventReport_.operationLines, JoinType.LEFT).get(MachineryOperationLine_.id)
                )
            );
        }
        return specification;
    }
}
