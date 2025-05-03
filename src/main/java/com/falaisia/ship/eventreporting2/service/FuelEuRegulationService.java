package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.FuelEuRegulation;
import com.falaisia.ship.eventreporting2.repository.FuelEuRegulationRepository;
import com.falaisia.ship.eventreporting2.service.dto.FuelEuRegulationDTO;
import com.falaisia.ship.eventreporting2.service.mapper.FuelEuRegulationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.falaisia.ship.eventreporting2.domain.FuelEuRegulation}.
 */
@Service
@Transactional
public class FuelEuRegulationService {

    private static final Logger LOG = LoggerFactory.getLogger(FuelEuRegulationService.class);

    private final FuelEuRegulationRepository fuelEuRegulationRepository;

    private final FuelEuRegulationMapper fuelEuRegulationMapper;

    public FuelEuRegulationService(FuelEuRegulationRepository fuelEuRegulationRepository, FuelEuRegulationMapper fuelEuRegulationMapper) {
        this.fuelEuRegulationRepository = fuelEuRegulationRepository;
        this.fuelEuRegulationMapper = fuelEuRegulationMapper;
    }

    /**
     * Save a fuelEuRegulation.
     *
     * @param fuelEuRegulationDTO the entity to save.
     * @return the persisted entity.
     */
    public FuelEuRegulationDTO save(FuelEuRegulationDTO fuelEuRegulationDTO) {
        LOG.debug("Request to save FuelEuRegulation : {}", fuelEuRegulationDTO);
        FuelEuRegulation fuelEuRegulation = fuelEuRegulationMapper.toEntity(fuelEuRegulationDTO);
        fuelEuRegulation = fuelEuRegulationRepository.save(fuelEuRegulation);
        return fuelEuRegulationMapper.toDto(fuelEuRegulation);
    }

    /**
     * Update a fuelEuRegulation.
     *
     * @param fuelEuRegulationDTO the entity to save.
     * @return the persisted entity.
     */
    public FuelEuRegulationDTO update(FuelEuRegulationDTO fuelEuRegulationDTO) {
        LOG.debug("Request to update FuelEuRegulation : {}", fuelEuRegulationDTO);
        FuelEuRegulation fuelEuRegulation = fuelEuRegulationMapper.toEntity(fuelEuRegulationDTO);
        fuelEuRegulation = fuelEuRegulationRepository.save(fuelEuRegulation);
        return fuelEuRegulationMapper.toDto(fuelEuRegulation);
    }

    /**
     * Partially update a fuelEuRegulation.
     *
     * @param fuelEuRegulationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FuelEuRegulationDTO> partialUpdate(FuelEuRegulationDTO fuelEuRegulationDTO) {
        LOG.debug("Request to partially update FuelEuRegulation : {}", fuelEuRegulationDTO);

        return fuelEuRegulationRepository
            .findById(fuelEuRegulationDTO.getId())
            .map(existingFuelEuRegulation -> {
                fuelEuRegulationMapper.partialUpdate(existingFuelEuRegulation, fuelEuRegulationDTO);

                return existingFuelEuRegulation;
            })
            .map(fuelEuRegulationRepository::save)
            .map(fuelEuRegulationMapper::toDto);
    }

    /**
     * Get one fuelEuRegulation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FuelEuRegulationDTO> findOne(Long id) {
        LOG.debug("Request to get FuelEuRegulation : {}", id);
        return fuelEuRegulationRepository.findById(id).map(fuelEuRegulationMapper::toDto);
    }

    /**
     * Delete the fuelEuRegulation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete FuelEuRegulation : {}", id);
        fuelEuRegulationRepository.deleteById(id);
    }
}
