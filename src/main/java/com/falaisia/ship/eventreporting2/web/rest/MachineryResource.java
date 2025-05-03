package com.falaisia.ship.eventreporting2.web.rest;

import com.falaisia.ship.eventreporting2.repository.MachineryRepository;
import com.falaisia.ship.eventreporting2.service.MachineryQueryService;
import com.falaisia.ship.eventreporting2.service.MachineryService;
import com.falaisia.ship.eventreporting2.service.criteria.MachineryCriteria;
import com.falaisia.ship.eventreporting2.service.dto.MachineryDTO;
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
 * REST controller for managing {@link com.falaisia.ship.eventreporting2.domain.Machinery}.
 */
@RestController
@RequestMapping("/api/machinery")
public class MachineryResource {

    private static final Logger LOG = LoggerFactory.getLogger(MachineryResource.class);

    private static final String ENTITY_NAME = "machinery";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MachineryService machineryService;

    private final MachineryRepository machineryRepository;

    private final MachineryQueryService machineryQueryService;

    public MachineryResource(
        MachineryService machineryService,
        MachineryRepository machineryRepository,
        MachineryQueryService machineryQueryService
    ) {
        this.machineryService = machineryService;
        this.machineryRepository = machineryRepository;
        this.machineryQueryService = machineryQueryService;
    }

    /**
     * {@code POST  /machinery} : Create a new machinery.
     *
     * @param machineryDTO the machineryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new machineryDTO, or with status {@code 400 (Bad Request)} if the machinery has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MachineryDTO> createMachinery(@Valid @RequestBody MachineryDTO machineryDTO) throws URISyntaxException {
        LOG.debug("REST request to save Machinery : {}", machineryDTO);
        if (machineryDTO.getId() != null) {
            throw new BadRequestAlertException("A new machinery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        machineryDTO = machineryService.save(machineryDTO);
        return ResponseEntity.created(new URI("/api/machinery/" + machineryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, machineryDTO.getId().toString()))
            .body(machineryDTO);
    }

    /**
     * {@code PUT  /machinery/:id} : Updates an existing machinery.
     *
     * @param id the id of the machineryDTO to save.
     * @param machineryDTO the machineryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated machineryDTO,
     * or with status {@code 400 (Bad Request)} if the machineryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the machineryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MachineryDTO> updateMachinery(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MachineryDTO machineryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Machinery : {}, {}", id, machineryDTO);
        if (machineryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, machineryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!machineryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        machineryDTO = machineryService.update(machineryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, machineryDTO.getId().toString()))
            .body(machineryDTO);
    }

    /**
     * {@code PATCH  /machinery/:id} : Partial updates given fields of an existing machinery, field will ignore if it is null
     *
     * @param id the id of the machineryDTO to save.
     * @param machineryDTO the machineryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated machineryDTO,
     * or with status {@code 400 (Bad Request)} if the machineryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the machineryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the machineryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MachineryDTO> partialUpdateMachinery(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MachineryDTO machineryDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Machinery partially : {}, {}", id, machineryDTO);
        if (machineryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, machineryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!machineryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MachineryDTO> result = machineryService.partialUpdate(machineryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, machineryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /machinery} : get all the machinery.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of machinery in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MachineryDTO>> getAllMachinery(MachineryCriteria criteria) {
        LOG.debug("REST request to get Machinery by criteria: {}", criteria);

        List<MachineryDTO> entityList = machineryQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /machinery/count} : count all the machinery.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countMachinery(MachineryCriteria criteria) {
        LOG.debug("REST request to count Machinery by criteria: {}", criteria);
        return ResponseEntity.ok().body(machineryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /machinery/:id} : get the "id" machinery.
     *
     * @param id the id of the machineryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the machineryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MachineryDTO> getMachinery(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Machinery : {}", id);
        Optional<MachineryDTO> machineryDTO = machineryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(machineryDTO);
    }

    /**
     * {@code DELETE  /machinery/:id} : delete the "id" machinery.
     *
     * @param id the id of the machineryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachinery(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Machinery : {}", id);
        machineryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
