package com.falaisia.ship.eventreporting2.web.rest;

import com.falaisia.ship.eventreporting2.repository.ConsumptionLineRepository;
import com.falaisia.ship.eventreporting2.service.ConsumptionLineQueryService;
import com.falaisia.ship.eventreporting2.service.ConsumptionLineService;
import com.falaisia.ship.eventreporting2.service.criteria.ConsumptionLineCriteria;
import com.falaisia.ship.eventreporting2.service.dto.ConsumptionLineDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.falaisia.ship.eventreporting2.domain.ConsumptionLine}.
 */
@RestController
@RequestMapping("/api/consumption-lines")
public class ConsumptionLineResource {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumptionLineResource.class);

    private static final String ENTITY_NAME = "consumptionLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsumptionLineService consumptionLineService;

    private final ConsumptionLineRepository consumptionLineRepository;

    private final ConsumptionLineQueryService consumptionLineQueryService;

    public ConsumptionLineResource(
        ConsumptionLineService consumptionLineService,
        ConsumptionLineRepository consumptionLineRepository,
        ConsumptionLineQueryService consumptionLineQueryService
    ) {
        this.consumptionLineService = consumptionLineService;
        this.consumptionLineRepository = consumptionLineRepository;
        this.consumptionLineQueryService = consumptionLineQueryService;
    }

    /**
     * {@code POST  /consumption-lines} : Create a new consumptionLine.
     *
     * @param consumptionLineDTO the consumptionLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consumptionLineDTO, or with status {@code 400 (Bad Request)} if the consumptionLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ConsumptionLineDTO> createConsumptionLine(@Valid @RequestBody ConsumptionLineDTO consumptionLineDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ConsumptionLine : {}", consumptionLineDTO);
        if (consumptionLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new consumptionLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        consumptionLineDTO = consumptionLineService.save(consumptionLineDTO);
        return ResponseEntity.created(new URI("/api/consumption-lines/" + consumptionLineDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, consumptionLineDTO.getId().toString()))
            .body(consumptionLineDTO);
    }

    /**
     * {@code PUT  /consumption-lines/:id} : Updates an existing consumptionLine.
     *
     * @param id the id of the consumptionLineDTO to save.
     * @param consumptionLineDTO the consumptionLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consumptionLineDTO,
     * or with status {@code 400 (Bad Request)} if the consumptionLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consumptionLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConsumptionLineDTO> updateConsumptionLine(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ConsumptionLineDTO consumptionLineDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ConsumptionLine : {}, {}", id, consumptionLineDTO);
        if (consumptionLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consumptionLineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consumptionLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        consumptionLineDTO = consumptionLineService.update(consumptionLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consumptionLineDTO.getId().toString()))
            .body(consumptionLineDTO);
    }

    /**
     * {@code PATCH  /consumption-lines/:id} : Partial updates given fields of an existing consumptionLine, field will ignore if it is null
     *
     * @param id the id of the consumptionLineDTO to save.
     * @param consumptionLineDTO the consumptionLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consumptionLineDTO,
     * or with status {@code 400 (Bad Request)} if the consumptionLineDTO is not valid,
     * or with status {@code 404 (Not Found)} if the consumptionLineDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the consumptionLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConsumptionLineDTO> partialUpdateConsumptionLine(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ConsumptionLineDTO consumptionLineDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ConsumptionLine partially : {}, {}", id, consumptionLineDTO);
        if (consumptionLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consumptionLineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consumptionLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConsumptionLineDTO> result = consumptionLineService.partialUpdate(consumptionLineDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consumptionLineDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /consumption-lines} : get all the consumptionLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consumptionLines in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ConsumptionLineDTO>> getAllConsumptionLines(ConsumptionLineCriteria criteria) {
        LOG.debug("REST request to get ConsumptionLines by criteria: {}", criteria);

        List<ConsumptionLineDTO> entityList = consumptionLineQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /consumption-lines/count} : count all the consumptionLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countConsumptionLines(ConsumptionLineCriteria criteria) {
        LOG.debug("REST request to count ConsumptionLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(consumptionLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /consumption-lines/:id} : get the "id" consumptionLine.
     *
     * @param id the id of the consumptionLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consumptionLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConsumptionLineDTO> getConsumptionLine(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ConsumptionLine : {}", id);
        Optional<ConsumptionLineDTO> consumptionLineDTO = consumptionLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consumptionLineDTO);
    }

    /**
     * {@code DELETE  /consumption-lines/:id} : delete the "id" consumptionLine.
     *
     * @param id the id of the consumptionLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumptionLine(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ConsumptionLine : {}", id);
        consumptionLineService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
