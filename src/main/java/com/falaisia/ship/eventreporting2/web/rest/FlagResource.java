package com.falaisia.ship.eventreporting2.web.rest;

import com.falaisia.ship.eventreporting2.repository.FlagRepository;
import com.falaisia.ship.eventreporting2.service.FlagQueryService;
import com.falaisia.ship.eventreporting2.service.FlagService;
import com.falaisia.ship.eventreporting2.service.criteria.FlagCriteria;
import com.falaisia.ship.eventreporting2.service.dto.FlagDTO;
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
 * REST controller for managing {@link com.falaisia.ship.eventreporting2.domain.Flag}.
 */
@RestController
@RequestMapping("/api/flags")
public class FlagResource {

    private static final Logger LOG = LoggerFactory.getLogger(FlagResource.class);

    private static final String ENTITY_NAME = "flag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FlagService flagService;

    private final FlagRepository flagRepository;

    private final FlagQueryService flagQueryService;

    public FlagResource(FlagService flagService, FlagRepository flagRepository, FlagQueryService flagQueryService) {
        this.flagService = flagService;
        this.flagRepository = flagRepository;
        this.flagQueryService = flagQueryService;
    }

    /**
     * {@code POST  /flags} : Create a new flag.
     *
     * @param flagDTO the flagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new flagDTO, or with status {@code 400 (Bad Request)} if the flag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FlagDTO> createFlag(@Valid @RequestBody FlagDTO flagDTO) throws URISyntaxException {
        LOG.debug("REST request to save Flag : {}", flagDTO);
        if (flagDTO.getId() != null) {
            throw new BadRequestAlertException("A new flag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        flagDTO = flagService.save(flagDTO);
        return ResponseEntity.created(new URI("/api/flags/" + flagDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, flagDTO.getId().toString()))
            .body(flagDTO);
    }

    /**
     * {@code PUT  /flags/:id} : Updates an existing flag.
     *
     * @param id the id of the flagDTO to save.
     * @param flagDTO the flagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flagDTO,
     * or with status {@code 400 (Bad Request)} if the flagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the flagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FlagDTO> updateFlag(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FlagDTO flagDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Flag : {}, {}", id, flagDTO);
        if (flagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flagDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        flagDTO = flagService.update(flagDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, flagDTO.getId().toString()))
            .body(flagDTO);
    }

    /**
     * {@code PATCH  /flags/:id} : Partial updates given fields of an existing flag, field will ignore if it is null
     *
     * @param id the id of the flagDTO to save.
     * @param flagDTO the flagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flagDTO,
     * or with status {@code 400 (Bad Request)} if the flagDTO is not valid,
     * or with status {@code 404 (Not Found)} if the flagDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the flagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FlagDTO> partialUpdateFlag(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FlagDTO flagDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Flag partially : {}, {}", id, flagDTO);
        if (flagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flagDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flagRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FlagDTO> result = flagService.partialUpdate(flagDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, flagDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /flags} : get all the flags.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of flags in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FlagDTO>> getAllFlags(
        FlagCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Flags by criteria: {}", criteria);

        Page<FlagDTO> page = flagQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /flags/count} : count all the flags.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFlags(FlagCriteria criteria) {
        LOG.debug("REST request to count Flags by criteria: {}", criteria);
        return ResponseEntity.ok().body(flagQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /flags/:id} : get the "id" flag.
     *
     * @param id the id of the flagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the flagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FlagDTO> getFlag(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Flag : {}", id);
        Optional<FlagDTO> flagDTO = flagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(flagDTO);
    }

    /**
     * {@code DELETE  /flags/:id} : delete the "id" flag.
     *
     * @param id the id of the flagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlag(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Flag : {}", id);
        flagService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
