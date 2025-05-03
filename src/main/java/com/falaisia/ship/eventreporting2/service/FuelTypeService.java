package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.FuelType;
import com.falaisia.ship.eventreporting2.repository.FuelTypeRepository;
import com.falaisia.ship.eventreporting2.service.dto.FuelTypeDTO;
import com.falaisia.ship.eventreporting2.service.mapper.FuelTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.falaisia.ship.eventreporting2.domain.FuelType}.
 */
@Service
@Transactional
public class FuelTypeService {

    private static final Logger LOG = LoggerFactory.getLogger(FuelTypeService.class);

    private final FuelTypeRepository fuelTypeRepository;

    private final FuelTypeMapper fuelTypeMapper;

    public FuelTypeService(FuelTypeRepository fuelTypeRepository, FuelTypeMapper fuelTypeMapper) {
        this.fuelTypeRepository = fuelTypeRepository;
        this.fuelTypeMapper = fuelTypeMapper;
    }

    /**
     * Save a fuelType.
     *
     * @param fuelTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public FuelTypeDTO save(FuelTypeDTO fuelTypeDTO) {
        LOG.debug("Request to save FuelType : {}", fuelTypeDTO);
        FuelType fuelType = fuelTypeMapper.toEntity(fuelTypeDTO);
        fuelType = fuelTypeRepository.save(fuelType);
        return fuelTypeMapper.toDto(fuelType);
    }

    /**
     * Update a fuelType.
     *
     * @param fuelTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public FuelTypeDTO update(FuelTypeDTO fuelTypeDTO) {
        LOG.debug("Request to update FuelType : {}", fuelTypeDTO);
        FuelType fuelType = fuelTypeMapper.toEntity(fuelTypeDTO);
        fuelType = fuelTypeRepository.save(fuelType);
        return fuelTypeMapper.toDto(fuelType);
    }

    /**
     * Partially update a fuelType.
     *
     * @param fuelTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FuelTypeDTO> partialUpdate(FuelTypeDTO fuelTypeDTO) {
        LOG.debug("Request to partially update FuelType : {}", fuelTypeDTO);

        return fuelTypeRepository
            .findById(fuelTypeDTO.getId())
            .map(existingFuelType -> {
                fuelTypeMapper.partialUpdate(existingFuelType, fuelTypeDTO);

                return existingFuelType;
            })
            .map(fuelTypeRepository::save)
            .map(fuelTypeMapper::toDto);
    }

    /**
     * Get one fuelType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FuelTypeDTO> findOne(Long id) {
        LOG.debug("Request to get FuelType : {}", id);
        return fuelTypeRepository.findById(id).map(fuelTypeMapper::toDto);
    }

    /**
     * Delete the fuelType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete FuelType : {}", id);
        fuelTypeRepository.deleteById(id);
    }
}
