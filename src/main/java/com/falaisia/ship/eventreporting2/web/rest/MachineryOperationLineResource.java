package com.falaisia.ship.eventreporting2.web.rest;

import com.falaisia.ship.eventreporting2.repository.MachineryOperationLineRepository;
import com.falaisia.ship.eventreporting2.service.MachineryOperationLineQueryService;
import com.falaisia.ship.eventreporting2.service.MachineryOperationLineService;
import com.falaisia.ship.eventreporting2.service.criteria.MachineryOperationLineCriteria;
import com.falaisia.ship.eventreporting2.service.dto.MachineryOperationLineDTO;
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
 * REST controller for managing {@link com.falaisia.ship.eventreporting2.domain.MachineryOperationLine}.
 */
@RestController
@RequestMapping("/api/machinery-operation-lines")
public class MachineryOperationLineResource {

    private static final Logger LOG = LoggerFactory.getLogger(MachineryOperationLineResource.class);

    private static final String ENTITY_NAME = "machineryOperationLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MachineryOperationLineService machineryOperationLineService;

    private final MachineryOperationLineRepository machineryOperationLineRepository;

    private final MachineryOperationLineQueryService machineryOperationLineQueryService;

    public MachineryOperationLineResource(
        MachineryOperationLineService machineryOperationLineService,
        MachineryOperationLineRepository machineryOperationLineRepository,
        MachineryOperationLineQueryService machineryOperationLineQueryService
    ) {
        this.machineryOperationLineService = machineryOperationLineService;
        this.machineryOperationLineRepository = machineryOperationLineRepository;
        this.machineryOperationLineQueryService = machineryOperationLineQueryService;
    }

    /**
     * {@code POST  /machinery-operation-lines} : Create a new machineryOperationLine.
     *
     * @param machineryOperationLineDTO the machineryOperationLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new machineryOperationLineDTO, or with status {@code 400 (Bad Request)} if the machineryOperationLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MachineryOperationLineDTO> createMachineryOperationLine(
        @Valid @RequestBody MachineryOperationLineDTO machineryOperationLineDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save MachineryOperationLine : {}", machineryOperationLineDTO);
        if (machineryOperationLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new machineryOperationLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        machineryOperationLineDTO = machineryOperationLineService.save(machineryOperationLineDTO);
        return ResponseEntity.created(new URI("/api/machinery-operation-lines/" + machineryOperationLineDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, machineryOperationLineDTO.getId().toString()))
            .body(machineryOperationLineDTO);
    }

    /**
     * {@code PUT  /machinery-operation-lines/:id} : Updates an existing machineryOperationLine.
     *
     * @param id the id of the machineryOperationLineDTO to save.
     * @param machineryOperationLineDTO the machineryOperationLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated machineryOperationLineDTO,
     * or with status {@code 400 (Bad Request)} if the machineryOperationLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the machineryOperationLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MachineryOperationLineDTO> updateMachineryOperationLine(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MachineryOperationLineDTO machineryOperationLineDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MachineryOperationLine : {}, {}", id, machineryOperationLineDTO);
        if (machineryOperationLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, machineryOperationLineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!machineryOperationLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        machineryOperationLineDTO = machineryOperationLineService.update(machineryOperationLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, machineryOperationLineDTO.getId().toString()))
            .body(machineryOperationLineDTO);
    }

    /**
     * {@code PATCH  /machinery-operation-lines/:id} : Partial updates given fields of an existing machineryOperationLine, field will ignore if it is null
     *
     * @param id the id of the machineryOperationLineDTO to save.
     * @param machineryOperationLineDTO the machineryOperationLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated machineryOperationLineDTO,
     * or with status {@code 400 (Bad Request)} if the machineryOperationLineDTO is not valid,
     * or with status {@code 404 (Not Found)} if the machineryOperationLineDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the machineryOperationLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MachineryOperationLineDTO> partialUpdateMachineryOperationLine(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MachineryOperationLineDTO machineryOperationLineDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MachineryOperationLine partially : {}, {}", id, machineryOperationLineDTO);
        if (machineryOperationLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, machineryOperationLineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!machineryOperationLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MachineryOperationLineDTO> result = machineryOperationLineService.partialUpdate(machineryOperationLineDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, machineryOperationLineDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /machinery-operation-lines} : get all the machineryOperationLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of machineryOperationLines in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MachineryOperationLineDTO>> getAllMachineryOperationLines(MachineryOperationLineCriteria criteria) {
        LOG.debug("REST request to get MachineryOperationLines by criteria: {}", criteria);

        List<MachineryOperationLineDTO> entityList = machineryOperationLineQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /machinery-operation-lines/count} : count all the machineryOperationLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countMachineryOperationLines(MachineryOperationLineCriteria criteria) {
        LOG.debug("REST request to count MachineryOperationLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(machineryOperationLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /machinery-operation-lines/:id} : get the "id" machineryOperationLine.
     *
     * @param id the id of the machineryOperationLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the machineryOperationLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MachineryOperationLineDTO> getMachineryOperationLine(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MachineryOperationLine : {}", id);
        Optional<MachineryOperationLineDTO> machineryOperationLineDTO = machineryOperationLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(machineryOperationLineDTO);
    }

    /**
     * {@code DELETE  /machinery-operation-lines/:id} : delete the "id" machineryOperationLine.
     *
     * @param id the id of the machineryOperationLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachineryOperationLine(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MachineryOperationLine : {}", id);
        machineryOperationLineService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
