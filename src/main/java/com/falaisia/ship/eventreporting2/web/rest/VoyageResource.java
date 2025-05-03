package com.falaisia.ship.eventreporting2.web.rest;

import com.falaisia.ship.eventreporting2.repository.VoyageRepository;
import com.falaisia.ship.eventreporting2.service.VoyageQueryService;
import com.falaisia.ship.eventreporting2.service.VoyageService;
import com.falaisia.ship.eventreporting2.service.criteria.VoyageCriteria;
import com.falaisia.ship.eventreporting2.service.dto.VoyageDTO;
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
 * REST controller for managing {@link com.falaisia.ship.eventreporting2.domain.Voyage}.
 */
@RestController
@RequestMapping("/api/voyages")
public class VoyageResource {

    private static final Logger LOG = LoggerFactory.getLogger(VoyageResource.class);

    private static final String ENTITY_NAME = "voyage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoyageService voyageService;

    private final VoyageRepository voyageRepository;

    private final VoyageQueryService voyageQueryService;

    public VoyageResource(VoyageService voyageService, VoyageRepository voyageRepository, VoyageQueryService voyageQueryService) {
        this.voyageService = voyageService;
        this.voyageRepository = voyageRepository;
        this.voyageQueryService = voyageQueryService;
    }

    /**
     * {@code POST  /voyages} : Create a new voyage.
     *
     * @param voyageDTO the voyageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voyageDTO, or with status {@code 400 (Bad Request)} if the voyage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<VoyageDTO> createVoyage(@Valid @RequestBody VoyageDTO voyageDTO) throws URISyntaxException {
        LOG.debug("REST request to save Voyage : {}", voyageDTO);
        if (voyageDTO.getId() != null) {
            throw new BadRequestAlertException("A new voyage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        voyageDTO = voyageService.save(voyageDTO);
        return ResponseEntity.created(new URI("/api/voyages/" + voyageDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, voyageDTO.getId().toString()))
            .body(voyageDTO);
    }

    /**
     * {@code PUT  /voyages/:id} : Updates an existing voyage.
     *
     * @param id the id of the voyageDTO to save.
     * @param voyageDTO the voyageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voyageDTO,
     * or with status {@code 400 (Bad Request)} if the voyageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voyageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VoyageDTO> updateVoyage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VoyageDTO voyageDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Voyage : {}, {}", id, voyageDTO);
        if (voyageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voyageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voyageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        voyageDTO = voyageService.update(voyageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voyageDTO.getId().toString()))
            .body(voyageDTO);
    }

    /**
     * {@code PATCH  /voyages/:id} : Partial updates given fields of an existing voyage, field will ignore if it is null
     *
     * @param id the id of the voyageDTO to save.
     * @param voyageDTO the voyageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voyageDTO,
     * or with status {@code 400 (Bad Request)} if the voyageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the voyageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the voyageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VoyageDTO> partialUpdateVoyage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VoyageDTO voyageDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Voyage partially : {}, {}", id, voyageDTO);
        if (voyageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, voyageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!voyageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VoyageDTO> result = voyageService.partialUpdate(voyageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voyageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /voyages} : get all the voyages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of voyages in body.
     */
    @GetMapping("")
    public ResponseEntity<List<VoyageDTO>> getAllVoyages(
        VoyageCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Voyages by criteria: {}", criteria);

        Page<VoyageDTO> page = voyageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /voyages/count} : count all the voyages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countVoyages(VoyageCriteria criteria) {
        LOG.debug("REST request to count Voyages by criteria: {}", criteria);
        return ResponseEntity.ok().body(voyageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /voyages/:id} : get the "id" voyage.
     *
     * @param id the id of the voyageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voyageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VoyageDTO> getVoyage(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Voyage : {}", id);
        Optional<VoyageDTO> voyageDTO = voyageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voyageDTO);
    }

    /**
     * {@code DELETE  /voyages/:id} : delete the "id" voyage.
     *
     * @param id the id of the voyageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoyage(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Voyage : {}", id);
        voyageService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
