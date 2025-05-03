package com.falaisia.ship.eventreporting2.web.rest;

import com.falaisia.ship.eventreporting2.repository.ShipRepository;
import com.falaisia.ship.eventreporting2.service.ShipQueryService;
import com.falaisia.ship.eventreporting2.service.ShipService;
import com.falaisia.ship.eventreporting2.service.criteria.ShipCriteria;
import com.falaisia.ship.eventreporting2.service.dto.ShipDTO;
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
 * REST controller for managing {@link com.falaisia.ship.eventreporting2.domain.Ship}.
 */
@RestController
@RequestMapping("/api/ships")
public class ShipResource {

    private static final Logger LOG = LoggerFactory.getLogger(ShipResource.class);

    private static final String ENTITY_NAME = "ship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipService shipService;

    private final ShipRepository shipRepository;

    private final ShipQueryService shipQueryService;

    public ShipResource(ShipService shipService, ShipRepository shipRepository, ShipQueryService shipQueryService) {
        this.shipService = shipService;
        this.shipRepository = shipRepository;
        this.shipQueryService = shipQueryService;
    }

    /**
     * {@code POST  /ships} : Create a new ship.
     *
     * @param shipDTO the shipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipDTO, or with status {@code 400 (Bad Request)} if the ship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ShipDTO> createShip(@Valid @RequestBody ShipDTO shipDTO) throws URISyntaxException {
        LOG.debug("REST request to save Ship : {}", shipDTO);
        if (shipDTO.getId() != null) {
            throw new BadRequestAlertException("A new ship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        shipDTO = shipService.save(shipDTO);
        return ResponseEntity.created(new URI("/api/ships/" + shipDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, shipDTO.getId().toString()))
            .body(shipDTO);
    }

    /**
     * {@code PUT  /ships/:id} : Updates an existing ship.
     *
     * @param id the id of the shipDTO to save.
     * @param shipDTO the shipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipDTO,
     * or with status {@code 400 (Bad Request)} if the shipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShipDTO> updateShip(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShipDTO shipDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Ship : {}, {}", id, shipDTO);
        if (shipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        shipDTO = shipService.update(shipDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipDTO.getId().toString()))
            .body(shipDTO);
    }

    /**
     * {@code PATCH  /ships/:id} : Partial updates given fields of an existing ship, field will ignore if it is null
     *
     * @param id the id of the shipDTO to save.
     * @param shipDTO the shipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipDTO,
     * or with status {@code 400 (Bad Request)} if the shipDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shipDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShipDTO> partialUpdateShip(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShipDTO shipDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Ship partially : {}, {}", id, shipDTO);
        if (shipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShipDTO> result = shipService.partialUpdate(shipDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ships} : get all the ships.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ships in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ShipDTO>> getAllShips(
        ShipCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Ships by criteria: {}", criteria);

        Page<ShipDTO> page = shipQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ships/count} : count all the ships.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countShips(ShipCriteria criteria) {
        LOG.debug("REST request to count Ships by criteria: {}", criteria);
        return ResponseEntity.ok().body(shipQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ships/:id} : get the "id" ship.
     *
     * @param id the id of the shipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShipDTO> getShip(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Ship : {}", id);
        Optional<ShipDTO> shipDTO = shipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipDTO);
    }

    /**
     * {@code DELETE  /ships/:id} : delete the "id" ship.
     *
     * @param id the id of the shipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShip(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Ship : {}", id);
        shipService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
