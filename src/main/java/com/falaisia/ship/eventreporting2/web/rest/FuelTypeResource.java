package com.falaisia.ship.eventreporting2.web.rest;

import com.falaisia.ship.eventreporting2.repository.FuelTypeRepository;
import com.falaisia.ship.eventreporting2.service.FuelTypeQueryService;
import com.falaisia.ship.eventreporting2.service.FuelTypeService;
import com.falaisia.ship.eventreporting2.service.criteria.FuelTypeCriteria;
import com.falaisia.ship.eventreporting2.service.dto.FuelTypeDTO;
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
 * REST controller for managing {@link com.falaisia.ship.eventreporting2.domain.FuelType}.
 */
@RestController
@RequestMapping("/api/fuel-types")
public class FuelTypeResource {

    private static final Logger LOG = LoggerFactory.getLogger(FuelTypeResource.class);

    private static final String ENTITY_NAME = "fuelType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuelTypeService fuelTypeService;

    private final FuelTypeRepository fuelTypeRepository;

    private final FuelTypeQueryService fuelTypeQueryService;

    public FuelTypeResource(
        FuelTypeService fuelTypeService,
        FuelTypeRepository fuelTypeRepository,
        FuelTypeQueryService fuelTypeQueryService
    ) {
        this.fuelTypeService = fuelTypeService;
        this.fuelTypeRepository = fuelTypeRepository;
        this.fuelTypeQueryService = fuelTypeQueryService;
    }

    /**
     * {@code POST  /fuel-types} : Create a new fuelType.
     *
     * @param fuelTypeDTO the fuelTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuelTypeDTO, or with status {@code 400 (Bad Request)} if the fuelType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FuelTypeDTO> createFuelType(@Valid @RequestBody FuelTypeDTO fuelTypeDTO) throws URISyntaxException {
        LOG.debug("REST request to save FuelType : {}", fuelTypeDTO);
        if (fuelTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new fuelType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fuelTypeDTO = fuelTypeService.save(fuelTypeDTO);
        return ResponseEntity.created(new URI("/api/fuel-types/" + fuelTypeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fuelTypeDTO.getId().toString()))
            .body(fuelTypeDTO);
    }

    /**
     * {@code PUT  /fuel-types/:id} : Updates an existing fuelType.
     *
     * @param id the id of the fuelTypeDTO to save.
     * @param fuelTypeDTO the fuelTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelTypeDTO,
     * or with status {@code 400 (Bad Request)} if the fuelTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuelTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FuelTypeDTO> updateFuelType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FuelTypeDTO fuelTypeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FuelType : {}, {}", id, fuelTypeDTO);
        if (fuelTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fuelTypeDTO = fuelTypeService.update(fuelTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelTypeDTO.getId().toString()))
            .body(fuelTypeDTO);
    }

    /**
     * {@code PATCH  /fuel-types/:id} : Partial updates given fields of an existing fuelType, field will ignore if it is null
     *
     * @param id the id of the fuelTypeDTO to save.
     * @param fuelTypeDTO the fuelTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelTypeDTO,
     * or with status {@code 400 (Bad Request)} if the fuelTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fuelTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fuelTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuelTypeDTO> partialUpdateFuelType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FuelTypeDTO fuelTypeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FuelType partially : {}, {}", id, fuelTypeDTO);
        if (fuelTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuelTypeDTO> result = fuelTypeService.partialUpdate(fuelTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fuel-types} : get all the fuelTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuelTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FuelTypeDTO>> getAllFuelTypes(
        FuelTypeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get FuelTypes by criteria: {}", criteria);

        Page<FuelTypeDTO> page = fuelTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fuel-types/count} : count all the fuelTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFuelTypes(FuelTypeCriteria criteria) {
        LOG.debug("REST request to count FuelTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(fuelTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fuel-types/:id} : get the "id" fuelType.
     *
     * @param id the id of the fuelTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuelTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FuelTypeDTO> getFuelType(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FuelType : {}", id);
        Optional<FuelTypeDTO> fuelTypeDTO = fuelTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fuelTypeDTO);
    }

    /**
     * {@code DELETE  /fuel-types/:id} : delete the "id" fuelType.
     *
     * @param id the id of the fuelTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelType(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FuelType : {}", id);
        fuelTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
