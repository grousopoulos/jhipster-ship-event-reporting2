package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.Ship;
import com.falaisia.ship.eventreporting2.repository.ShipRepository;
import com.falaisia.ship.eventreporting2.service.dto.ShipDTO;
import com.falaisia.ship.eventreporting2.service.mapper.ShipMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.falaisia.ship.eventreporting2.domain.Ship}.
 */
@Service
@Transactional
public class ShipService {

    private static final Logger LOG = LoggerFactory.getLogger(ShipService.class);

    private final ShipRepository shipRepository;

    private final ShipMapper shipMapper;

    public ShipService(ShipRepository shipRepository, ShipMapper shipMapper) {
        this.shipRepository = shipRepository;
        this.shipMapper = shipMapper;
    }

    /**
     * Save a ship.
     *
     * @param shipDTO the entity to save.
     * @return the persisted entity.
     */
    public ShipDTO save(ShipDTO shipDTO) {
        LOG.debug("Request to save Ship : {}", shipDTO);
        Ship ship = shipMapper.toEntity(shipDTO);
        ship = shipRepository.save(ship);
        return shipMapper.toDto(ship);
    }

    /**
     * Update a ship.
     *
     * @param shipDTO the entity to save.
     * @return the persisted entity.
     */
    public ShipDTO update(ShipDTO shipDTO) {
        LOG.debug("Request to update Ship : {}", shipDTO);
        Ship ship = shipMapper.toEntity(shipDTO);
        ship = shipRepository.save(ship);
        return shipMapper.toDto(ship);
    }

    /**
     * Partially update a ship.
     *
     * @param shipDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ShipDTO> partialUpdate(ShipDTO shipDTO) {
        LOG.debug("Request to partially update Ship : {}", shipDTO);

        return shipRepository
            .findById(shipDTO.getId())
            .map(existingShip -> {
                shipMapper.partialUpdate(existingShip, shipDTO);

                return existingShip;
            })
            .map(shipRepository::save)
            .map(shipMapper::toDto);
    }

    /**
     * Get one ship by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ShipDTO> findOne(Long id) {
        LOG.debug("Request to get Ship : {}", id);
        return shipRepository.findById(id).map(shipMapper::toDto);
    }

    /**
     * Delete the ship by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Ship : {}", id);
        shipRepository.deleteById(id);
    }
}
