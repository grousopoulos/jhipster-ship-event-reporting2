package com.falaisia.ship.eventreporting2.web.rest;

import com.falaisia.ship.eventreporting2.repository.BunkerReceivedNoteLineRepository;
import com.falaisia.ship.eventreporting2.service.BunkerReceivedNoteLineQueryService;
import com.falaisia.ship.eventreporting2.service.BunkerReceivedNoteLineService;
import com.falaisia.ship.eventreporting2.service.criteria.BunkerReceivedNoteLineCriteria;
import com.falaisia.ship.eventreporting2.service.dto.BunkerReceivedNoteLineDTO;
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
 * REST controller for managing {@link com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteLine}.
 */
@RestController
@RequestMapping("/api/bunker-received-note-lines")
public class BunkerReceivedNoteLineResource {

    private static final Logger LOG = LoggerFactory.getLogger(BunkerReceivedNoteLineResource.class);

    private static final String ENTITY_NAME = "bunkerReceivedNoteLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BunkerReceivedNoteLineService bunkerReceivedNoteLineService;

    private final BunkerReceivedNoteLineRepository bunkerReceivedNoteLineRepository;

    private final BunkerReceivedNoteLineQueryService bunkerReceivedNoteLineQueryService;

    public BunkerReceivedNoteLineResource(
        BunkerReceivedNoteLineService bunkerReceivedNoteLineService,
        BunkerReceivedNoteLineRepository bunkerReceivedNoteLineRepository,
        BunkerReceivedNoteLineQueryService bunkerReceivedNoteLineQueryService
    ) {
        this.bunkerReceivedNoteLineService = bunkerReceivedNoteLineService;
        this.bunkerReceivedNoteLineRepository = bunkerReceivedNoteLineRepository;
        this.bunkerReceivedNoteLineQueryService = bunkerReceivedNoteLineQueryService;
    }

    /**
     * {@code POST  /bunker-received-note-lines} : Create a new bunkerReceivedNoteLine.
     *
     * @param bunkerReceivedNoteLineDTO the bunkerReceivedNoteLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bunkerReceivedNoteLineDTO, or with status {@code 400 (Bad Request)} if the bunkerReceivedNoteLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BunkerReceivedNoteLineDTO> createBunkerReceivedNoteLine(
        @Valid @RequestBody BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save BunkerReceivedNoteLine : {}", bunkerReceivedNoteLineDTO);
        if (bunkerReceivedNoteLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new bunkerReceivedNoteLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bunkerReceivedNoteLineDTO = bunkerReceivedNoteLineService.save(bunkerReceivedNoteLineDTO);
        return ResponseEntity.created(new URI("/api/bunker-received-note-lines/" + bunkerReceivedNoteLineDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bunkerReceivedNoteLineDTO.getId().toString()))
            .body(bunkerReceivedNoteLineDTO);
    }

    /**
     * {@code PUT  /bunker-received-note-lines/:id} : Updates an existing bunkerReceivedNoteLine.
     *
     * @param id the id of the bunkerReceivedNoteLineDTO to save.
     * @param bunkerReceivedNoteLineDTO the bunkerReceivedNoteLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bunkerReceivedNoteLineDTO,
     * or with status {@code 400 (Bad Request)} if the bunkerReceivedNoteLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bunkerReceivedNoteLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BunkerReceivedNoteLineDTO> updateBunkerReceivedNoteLine(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BunkerReceivedNoteLine : {}, {}", id, bunkerReceivedNoteLineDTO);
        if (bunkerReceivedNoteLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bunkerReceivedNoteLineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bunkerReceivedNoteLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bunkerReceivedNoteLineDTO = bunkerReceivedNoteLineService.update(bunkerReceivedNoteLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bunkerReceivedNoteLineDTO.getId().toString()))
            .body(bunkerReceivedNoteLineDTO);
    }

    /**
     * {@code PATCH  /bunker-received-note-lines/:id} : Partial updates given fields of an existing bunkerReceivedNoteLine, field will ignore if it is null
     *
     * @param id the id of the bunkerReceivedNoteLineDTO to save.
     * @param bunkerReceivedNoteLineDTO the bunkerReceivedNoteLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bunkerReceivedNoteLineDTO,
     * or with status {@code 400 (Bad Request)} if the bunkerReceivedNoteLineDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bunkerReceivedNoteLineDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bunkerReceivedNoteLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BunkerReceivedNoteLineDTO> partialUpdateBunkerReceivedNoteLine(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BunkerReceivedNoteLine partially : {}, {}", id, bunkerReceivedNoteLineDTO);
        if (bunkerReceivedNoteLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bunkerReceivedNoteLineDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bunkerReceivedNoteLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BunkerReceivedNoteLineDTO> result = bunkerReceivedNoteLineService.partialUpdate(bunkerReceivedNoteLineDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bunkerReceivedNoteLineDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bunker-received-note-lines} : get all the bunkerReceivedNoteLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bunkerReceivedNoteLines in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BunkerReceivedNoteLineDTO>> getAllBunkerReceivedNoteLines(BunkerReceivedNoteLineCriteria criteria) {
        LOG.debug("REST request to get BunkerReceivedNoteLines by criteria: {}", criteria);

        List<BunkerReceivedNoteLineDTO> entityList = bunkerReceivedNoteLineQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /bunker-received-note-lines/count} : count all the bunkerReceivedNoteLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countBunkerReceivedNoteLines(BunkerReceivedNoteLineCriteria criteria) {
        LOG.debug("REST request to count BunkerReceivedNoteLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(bunkerReceivedNoteLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bunker-received-note-lines/:id} : get the "id" bunkerReceivedNoteLine.
     *
     * @param id the id of the bunkerReceivedNoteLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bunkerReceivedNoteLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BunkerReceivedNoteLineDTO> getBunkerReceivedNoteLine(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BunkerReceivedNoteLine : {}", id);
        Optional<BunkerReceivedNoteLineDTO> bunkerReceivedNoteLineDTO = bunkerReceivedNoteLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bunkerReceivedNoteLineDTO);
    }

    /**
     * {@code DELETE  /bunker-received-note-lines/:id} : delete the "id" bunkerReceivedNoteLine.
     *
     * @param id the id of the bunkerReceivedNoteLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBunkerReceivedNoteLine(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BunkerReceivedNoteLine : {}", id);
        bunkerReceivedNoteLineService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
