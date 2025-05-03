package com.falaisia.ship.eventreporting2.web.rest;

import com.falaisia.ship.eventreporting2.repository.EventReportRepository;
import com.falaisia.ship.eventreporting2.service.EventReportQueryService;
import com.falaisia.ship.eventreporting2.service.EventReportService;
import com.falaisia.ship.eventreporting2.service.criteria.EventReportCriteria;
import com.falaisia.ship.eventreporting2.service.dto.EventReportDTO;
import com.falaisia.ship.eventreporting2.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.falaisia.ship.eventreporting2.domain.EventReport}.
 */
@RestController
@RequestMapping("/api/event-reports")
public class EventReportResource {

    private static final Logger LOG = LoggerFactory.getLogger(EventReportResource.class);

    private static final String ENTITY_NAME = "eventReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventReportService eventReportService;

    private final EventReportRepository eventReportRepository;

    private final EventReportQueryService eventReportQueryService;

    public EventReportResource(
        EventReportService eventReportService,
        EventReportRepository eventReportRepository,
        EventReportQueryService eventReportQueryService
    ) {
        this.eventReportService = eventReportService;
        this.eventReportRepository = eventReportRepository;
        this.eventReportQueryService = eventReportQueryService;
    }

    /**
     * {@code POST  /event-reports} : Create a new eventReport.
     *
     * @param eventReportDTO the eventReportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventReportDTO, or with status {@code 400 (Bad Request)} if the eventReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EventReportDTO> createEventReport(@Valid @RequestBody EventReportDTO eventReportDTO) throws URISyntaxException {
        LOG.debug("REST request to save EventReport : {}", eventReportDTO);
        if (eventReportDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eventReportDTO = eventReportService.save(eventReportDTO);
        return ResponseEntity.created(new URI("/api/event-reports/" + eventReportDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, eventReportDTO.getId().toString()))
            .body(eventReportDTO);
    }

    /**
     * {@code PUT  /event-reports/:id} : Updates an existing eventReport.
     *
     * @param id the id of the eventReportDTO to save.
     * @param eventReportDTO the eventReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventReportDTO,
     * or with status {@code 400 (Bad Request)} if the eventReportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventReportDTO> updateEventReport(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EventReportDTO eventReportDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update EventReport : {}, {}", id, eventReportDTO);
        if (eventReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        eventReportDTO = eventReportService.update(eventReportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventReportDTO.getId().toString()))
            .body(eventReportDTO);
    }

    /**
     * {@code PATCH  /event-reports/:id} : Partial updates given fields of an existing eventReport, field will ignore if it is null
     *
     * @param id the id of the eventReportDTO to save.
     * @param eventReportDTO the eventReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventReportDTO,
     * or with status {@code 400 (Bad Request)} if the eventReportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventReportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventReportDTO> partialUpdateEventReport(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EventReportDTO eventReportDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EventReport partially : {}, {}", id, eventReportDTO);
        if (eventReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventReportDTO> result = eventReportService.partialUpdate(eventReportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventReportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /event-reports} : get all the eventReports.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventReports in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EventReportDTO>> getAllEventReports(
        EventReportCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get EventReports by criteria: {}", criteria);

        Page<EventReportDTO> page = eventReportQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-reports/count} : count all the eventReports.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEventReports(EventReportCriteria criteria) {
        LOG.debug("REST request to count EventReports by criteria: {}", criteria);
        return ResponseEntity.ok().body(eventReportQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /event-reports/:id} : get the "id" eventReport.
     *
     * @param id the id of the eventReportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventReportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventReportDTO> getEventReport(@PathVariable("id") Long id) {
        LOG.debug("REST request to get EventReport : {}", id);
        Optional<EventReportDTO> eventReportDTO = eventReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventReportDTO);
    }

    /**
     * {@code DELETE  /event-reports/:id} : delete the "id" eventReport.
     *
     * @param id the id of the eventReportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventReport(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete EventReport : {}", id);
        eventReportService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
