package com.falaisia.ship.eventreporting2.web.rest;

import com.falaisia.ship.eventreporting2.repository.FuelEuRegulationRepository;
import com.falaisia.ship.eventreporting2.service.FuelEuRegulationQueryService;
import com.falaisia.ship.eventreporting2.service.FuelEuRegulationService;
import com.falaisia.ship.eventreporting2.service.criteria.FuelEuRegulationCriteria;
import com.falaisia.ship.eventreporting2.service.dto.FuelEuRegulationDTO;
import com.falaisia.ship.eventreporting2.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.falaisia.ship.eventreporting2.domain.FuelEuRegulation}.
 */
@RestController
@RequestMapping("/api/fuel-eu-regulations")
public class FuelEuRegulationResource {

    private static final Logger LOG = LoggerFactory.getLogger(FuelEuRegulationResource.class);

    private static final String ENTITY_NAME = "fuelEuRegulation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuelEuRegulationService fuelEuRegulationService;

    private final FuelEuRegulationRepository fuelEuRegulationRepository;

    private final FuelEuRegulationQueryService fuelEuRegulationQueryService;

    public FuelEuRegulationResource(
        FuelEuRegulationService fuelEuRegulationService,
        FuelEuRegulationRepository fuelEuRegulationRepository,
        FuelEuRegulationQueryService fuelEuRegulationQueryService
    ) {
        this.fuelEuRegulationService = fuelEuRegulationService;
        this.fuelEuRegulationRepository = fuelEuRegulationRepository;
        this.fuelEuRegulationQueryService = fuelEuRegulationQueryService;
    }

    /**
     * {@code POST  /fuel-eu-regulations} : Create a new fuelEuRegulation.
     *
     * @param fuelEuRegulationDTO the fuelEuRegulationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuelEuRegulationDTO, or with status {@code 400 (Bad Request)} if the fuelEuRegulation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FuelEuRegulationDTO> createFuelEuRegulation(@RequestBody FuelEuRegulationDTO fuelEuRegulationDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save FuelEuRegulation : {}", fuelEuRegulationDTO);
        if (fuelEuRegulationDTO.getId() != null) {
            throw new BadRequestAlertException("A new fuelEuRegulation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fuelEuRegulationDTO = fuelEuRegulationService.save(fuelEuRegulationDTO);
        return ResponseEntity.created(new URI("/api/fuel-eu-regulations/" + fuelEuRegulationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fuelEuRegulationDTO.getId().toString()))
            .body(fuelEuRegulationDTO);
    }

    /**
     * {@code PUT  /fuel-eu-regulations/:id} : Updates an existing fuelEuRegulation.
     *
     * @param id the id of the fuelEuRegulationDTO to save.
     * @param fuelEuRegulationDTO the fuelEuRegulationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelEuRegulationDTO,
     * or with status {@code 400 (Bad Request)} if the fuelEuRegulationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuelEuRegulationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FuelEuRegulationDTO> updateFuelEuRegulation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelEuRegulationDTO fuelEuRegulationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FuelEuRegulation : {}, {}", id, fuelEuRegulationDTO);
        if (fuelEuRegulationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelEuRegulationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelEuRegulationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fuelEuRegulationDTO = fuelEuRegulationService.update(fuelEuRegulationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelEuRegulationDTO.getId().toString()))
            .body(fuelEuRegulationDTO);
    }

    /**
     * {@code PATCH  /fuel-eu-regulations/:id} : Partial updates given fields of an existing fuelEuRegulation, field will ignore if it is null
     *
     * @param id the id of the fuelEuRegulationDTO to save.
     * @param fuelEuRegulationDTO the fuelEuRegulationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelEuRegulationDTO,
     * or with status {@code 400 (Bad Request)} if the fuelEuRegulationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fuelEuRegulationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fuelEuRegulationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuelEuRegulationDTO> partialUpdateFuelEuRegulation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelEuRegulationDTO fuelEuRegulationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FuelEuRegulation partially : {}, {}", id, fuelEuRegulationDTO);
        if (fuelEuRegulationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelEuRegulationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelEuRegulationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuelEuRegulationDTO> result = fuelEuRegulationService.partialUpdate(fuelEuRegulationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelEuRegulationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fuel-eu-regulations} : get all the fuelEuRegulations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuelEuRegulations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FuelEuRegulationDTO>> getAllFuelEuRegulations(FuelEuRegulationCriteria criteria) {
        LOG.debug("REST request to get FuelEuRegulations by criteria: {}", criteria);

        List<FuelEuRegulationDTO> entityList = fuelEuRegulationQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /fuel-eu-regulations/count} : count all the fuelEuRegulations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFuelEuRegulations(FuelEuRegulationCriteria criteria) {
        LOG.debug("REST request to count FuelEuRegulations by criteria: {}", criteria);
        return ResponseEntity.ok().body(fuelEuRegulationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fuel-eu-regulations/:id} : get the "id" fuelEuRegulation.
     *
     * @param id the id of the fuelEuRegulationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuelEuRegulationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FuelEuRegulationDTO> getFuelEuRegulation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FuelEuRegulation : {}", id);
        Optional<FuelEuRegulationDTO> fuelEuRegulationDTO = fuelEuRegulationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fuelEuRegulationDTO);
    }

    /**
     * {@code DELETE  /fuel-eu-regulations/:id} : delete the "id" fuelEuRegulation.
     *
     * @param id the id of the fuelEuRegulationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelEuRegulation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FuelEuRegulation : {}", id);
        fuelEuRegulationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
