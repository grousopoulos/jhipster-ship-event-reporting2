package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.EventReport;
import com.falaisia.ship.eventreporting2.repository.EventReportRepository;
import com.falaisia.ship.eventreporting2.service.dto.EventReportDTO;
import com.falaisia.ship.eventreporting2.service.mapper.EventReportMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.falaisia.ship.eventreporting2.domain.EventReport}.
 */
@Service
@Transactional
public class EventReportService {

    private static final Logger LOG = LoggerFactory.getLogger(EventReportService.class);

    private final EventReportRepository eventReportRepository;

    private final EventReportMapper eventReportMapper;

    public EventReportService(EventReportRepository eventReportRepository, EventReportMapper eventReportMapper) {
        this.eventReportRepository = eventReportRepository;
        this.eventReportMapper = eventReportMapper;
    }

    /**
     * Save a eventReport.
     *
     * @param eventReportDTO the entity to save.
     * @return the persisted entity.
     */
    public EventReportDTO save(EventReportDTO eventReportDTO) {
        LOG.debug("Request to save EventReport : {}", eventReportDTO);
        EventReport eventReport = eventReportMapper.toEntity(eventReportDTO);
        eventReport = eventReportRepository.save(eventReport);
        return eventReportMapper.toDto(eventReport);
    }

    /**
     * Update a eventReport.
     *
     * @param eventReportDTO the entity to save.
     * @return the persisted entity.
     */
    public EventReportDTO update(EventReportDTO eventReportDTO) {
        LOG.debug("Request to update EventReport : {}", eventReportDTO);
        EventReport eventReport = eventReportMapper.toEntity(eventReportDTO);
        eventReport = eventReportRepository.save(eventReport);
        return eventReportMapper.toDto(eventReport);
    }

    /**
     * Partially update a eventReport.
     *
     * @param eventReportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EventReportDTO> partialUpdate(EventReportDTO eventReportDTO) {
        LOG.debug("Request to partially update EventReport : {}", eventReportDTO);

        return eventReportRepository
            .findById(eventReportDTO.getId())
            .map(existingEventReport -> {
                eventReportMapper.partialUpdate(existingEventReport, eventReportDTO);

                return existingEventReport;
            })
            .map(eventReportRepository::save)
            .map(eventReportMapper::toDto);
    }

    /**
     * Get one eventReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventReportDTO> findOne(Long id) {
        LOG.debug("Request to get EventReport : {}", id);
        return eventReportRepository.findById(id).map(eventReportMapper::toDto);
    }

    /**
     * Delete the eventReport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete EventReport : {}", id);
        eventReportRepository.deleteById(id);
    }
}
