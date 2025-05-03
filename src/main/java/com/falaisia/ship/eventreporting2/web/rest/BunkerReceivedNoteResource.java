package com.falaisia.ship.eventreporting2.web.rest;

import com.falaisia.ship.eventreporting2.repository.BunkerReceivedNoteRepository;
import com.falaisia.ship.eventreporting2.service.BunkerReceivedNoteQueryService;
import com.falaisia.ship.eventreporting2.service.BunkerReceivedNoteService;
import com.falaisia.ship.eventreporting2.service.criteria.BunkerReceivedNoteCriteria;
import com.falaisia.ship.eventreporting2.service.dto.BunkerReceivedNoteDTO;
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
 * REST controller for managing {@link com.falaisia.ship.eventreporting2.domain.BunkerReceivedNote}.
 */
@RestController
@RequestMapping("/api/bunker-received-notes")
public class BunkerReceivedNoteResource {

    private static final Logger LOG = LoggerFactory.getLogger(BunkerReceivedNoteResource.class);

    private static final String ENTITY_NAME = "bunkerReceivedNote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BunkerReceivedNoteService bunkerReceivedNoteService;

    private final BunkerReceivedNoteRepository bunkerReceivedNoteRepository;

    private final BunkerReceivedNoteQueryService bunkerReceivedNoteQueryService;

    public BunkerReceivedNoteResource(
        BunkerReceivedNoteService bunkerReceivedNoteService,
        BunkerReceivedNoteRepository bunkerReceivedNoteRepository,
        BunkerReceivedNoteQueryService bunkerReceivedNoteQueryService
    ) {
        this.bunkerReceivedNoteService = bunkerReceivedNoteService;
        this.bunkerReceivedNoteRepository = bunkerReceivedNoteRepository;
        this.bunkerReceivedNoteQueryService = bunkerReceivedNoteQueryService;
    }

    /**
     * {@code POST  /bunker-received-notes} : Create a new bunkerReceivedNote.
     *
     * @param bunkerReceivedNoteDTO the bunkerReceivedNoteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bunkerReceivedNoteDTO, or with status {@code 400 (Bad Request)} if the bunkerReceivedNote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BunkerReceivedNoteDTO> createBunkerReceivedNote(@Valid @RequestBody BunkerReceivedNoteDTO bunkerReceivedNoteDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save BunkerReceivedNote : {}", bunkerReceivedNoteDTO);
        if (bunkerReceivedNoteDTO.getId() != null) {
            throw new BadRequestAlertException("A new bunkerReceivedNote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bunkerReceivedNoteDTO = bunkerReceivedNoteService.save(bunkerReceivedNoteDTO);
        return ResponseEntity.created(new URI("/api/bunker-received-notes/" + bunkerReceivedNoteDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bunkerReceivedNoteDTO.getId().toString()))
            .body(bunkerReceivedNoteDTO);
    }

    /**
     * {@code PUT  /bunker-received-notes/:id} : Updates an existing bunkerReceivedNote.
     *
     * @param id the id of the bunkerReceivedNoteDTO to save.
     * @param bunkerReceivedNoteDTO the bunkerReceivedNoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bunkerReceivedNoteDTO,
     * or with status {@code 400 (Bad Request)} if the bunkerReceivedNoteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bunkerReceivedNoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BunkerReceivedNoteDTO> updateBunkerReceivedNote(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BunkerReceivedNoteDTO bunkerReceivedNoteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BunkerReceivedNote : {}, {}", id, bunkerReceivedNoteDTO);
        if (bunkerReceivedNoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bunkerReceivedNoteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bunkerReceivedNoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bunkerReceivedNoteDTO = bunkerReceivedNoteService.update(bunkerReceivedNoteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bunkerReceivedNoteDTO.getId().toString()))
            .body(bunkerReceivedNoteDTO);
    }

    /**
     * {@code PATCH  /bunker-received-notes/:id} : Partial updates given fields of an existing bunkerReceivedNote, field will ignore if it is null
     *
     * @param id the id of the bunkerReceivedNoteDTO to save.
     * @param bunkerReceivedNoteDTO the bunkerReceivedNoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bunkerReceivedNoteDTO,
     * or with status {@code 400 (Bad Request)} if the bunkerReceivedNoteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bunkerReceivedNoteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bunkerReceivedNoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BunkerReceivedNoteDTO> partialUpdateBunkerReceivedNote(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BunkerReceivedNoteDTO bunkerReceivedNoteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BunkerReceivedNote partially : {}, {}", id, bunkerReceivedNoteDTO);
        if (bunkerReceivedNoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bunkerReceivedNoteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bunkerReceivedNoteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BunkerReceivedNoteDTO> result = bunkerReceivedNoteService.partialUpdate(bunkerReceivedNoteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bunkerReceivedNoteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bunker-received-notes} : get all the bunkerReceivedNotes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bunkerReceivedNotes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BunkerReceivedNoteDTO>> getAllBunkerReceivedNotes(
        BunkerReceivedNoteCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get BunkerReceivedNotes by criteria: {}", criteria);

        Page<BunkerReceivedNoteDTO> page = bunkerReceivedNoteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bunker-received-notes/count} : count all the bunkerReceivedNotes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countBunkerReceivedNotes(BunkerReceivedNoteCriteria criteria) {
        LOG.debug("REST request to count BunkerReceivedNotes by criteria: {}", criteria);
        return ResponseEntity.ok().body(bunkerReceivedNoteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bunker-received-notes/:id} : get the "id" bunkerReceivedNote.
     *
     * @param id the id of the bunkerReceivedNoteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bunkerReceivedNoteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BunkerReceivedNoteDTO> getBunkerReceivedNote(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BunkerReceivedNote : {}", id);
        Optional<BunkerReceivedNoteDTO> bunkerReceivedNoteDTO = bunkerReceivedNoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bunkerReceivedNoteDTO);
    }

    /**
     * {@code DELETE  /bunker-received-notes/:id} : delete the "id" bunkerReceivedNote.
     *
     * @param id the id of the bunkerReceivedNoteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBunkerReceivedNote(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BunkerReceivedNote : {}", id);
        bunkerReceivedNoteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
