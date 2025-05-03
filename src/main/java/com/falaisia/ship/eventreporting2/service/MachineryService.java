package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.Machinery;
import com.falaisia.ship.eventreporting2.repository.MachineryRepository;
import com.falaisia.ship.eventreporting2.service.dto.MachineryDTO;
import com.falaisia.ship.eventreporting2.service.mapper.MachineryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.falaisia.ship.eventreporting2.domain.Machinery}.
 */
@Service
@Transactional
public class MachineryService {

    private static final Logger LOG = LoggerFactory.getLogger(MachineryService.class);

    private final MachineryRepository machineryRepository;

    private final MachineryMapper machineryMapper;

    public MachineryService(MachineryRepository machineryRepository, MachineryMapper machineryMapper) {
        this.machineryRepository = machineryRepository;
        this.machineryMapper = machineryMapper;
    }

    /**
     * Save a machinery.
     *
     * @param machineryDTO the entity to save.
     * @return the persisted entity.
     */
    public MachineryDTO save(MachineryDTO machineryDTO) {
        LOG.debug("Request to save Machinery : {}", machineryDTO);
        Machinery machinery = machineryMapper.toEntity(machineryDTO);
        machinery = machineryRepository.save(machinery);
        return machineryMapper.toDto(machinery);
    }

    /**
     * Update a machinery.
     *
     * @param machineryDTO the entity to save.
     * @return the persisted entity.
     */
    public MachineryDTO update(MachineryDTO machineryDTO) {
        LOG.debug("Request to update Machinery : {}", machineryDTO);
        Machinery machinery = machineryMapper.toEntity(machineryDTO);
        machinery = machineryRepository.save(machinery);
        return machineryMapper.toDto(machinery);
    }

    /**
     * Partially update a machinery.
     *
     * @param machineryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MachineryDTO> partialUpdate(MachineryDTO machineryDTO) {
        LOG.debug("Request to partially update Machinery : {}", machineryDTO);

        return machineryRepository
            .findById(machineryDTO.getId())
            .map(existingMachinery -> {
                machineryMapper.partialUpdate(existingMachinery, machineryDTO);

                return existingMachinery;
            })
            .map(machineryRepository::save)
            .map(machineryMapper::toDto);
    }

    /**
     * Get one machinery by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MachineryDTO> findOne(Long id) {
        LOG.debug("Request to get Machinery : {}", id);
        return machineryRepository.findById(id).map(machineryMapper::toDto);
    }

    /**
     * Delete the machinery by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Machinery : {}", id);
        machineryRepository.deleteById(id);
    }
}
