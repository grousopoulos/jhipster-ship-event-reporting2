package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.MachineryOperationLine;
import com.falaisia.ship.eventreporting2.repository.MachineryOperationLineRepository;
import com.falaisia.ship.eventreporting2.service.dto.MachineryOperationLineDTO;
import com.falaisia.ship.eventreporting2.service.mapper.MachineryOperationLineMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.falaisia.ship.eventreporting2.domain.MachineryOperationLine}.
 */
@Service
@Transactional
public class MachineryOperationLineService {

    private static final Logger LOG = LoggerFactory.getLogger(MachineryOperationLineService.class);

    private final MachineryOperationLineRepository machineryOperationLineRepository;

    private final MachineryOperationLineMapper machineryOperationLineMapper;

    public MachineryOperationLineService(
        MachineryOperationLineRepository machineryOperationLineRepository,
        MachineryOperationLineMapper machineryOperationLineMapper
    ) {
        this.machineryOperationLineRepository = machineryOperationLineRepository;
        this.machineryOperationLineMapper = machineryOperationLineMapper;
    }

    /**
     * Save a machineryOperationLine.
     *
     * @param machineryOperationLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MachineryOperationLineDTO save(MachineryOperationLineDTO machineryOperationLineDTO) {
        LOG.debug("Request to save MachineryOperationLine : {}", machineryOperationLineDTO);
        MachineryOperationLine machineryOperationLine = machineryOperationLineMapper.toEntity(machineryOperationLineDTO);
        machineryOperationLine = machineryOperationLineRepository.save(machineryOperationLine);
        return machineryOperationLineMapper.toDto(machineryOperationLine);
    }

    /**
     * Update a machineryOperationLine.
     *
     * @param machineryOperationLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MachineryOperationLineDTO update(MachineryOperationLineDTO machineryOperationLineDTO) {
        LOG.debug("Request to update MachineryOperationLine : {}", machineryOperationLineDTO);
        MachineryOperationLine machineryOperationLine = machineryOperationLineMapper.toEntity(machineryOperationLineDTO);
        machineryOperationLine = machineryOperationLineRepository.save(machineryOperationLine);
        return machineryOperationLineMapper.toDto(machineryOperationLine);
    }

    /**
     * Partially update a machineryOperationLine.
     *
     * @param machineryOperationLineDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MachineryOperationLineDTO> partialUpdate(MachineryOperationLineDTO machineryOperationLineDTO) {
        LOG.debug("Request to partially update MachineryOperationLine : {}", machineryOperationLineDTO);

        return machineryOperationLineRepository
            .findById(machineryOperationLineDTO.getId())
            .map(existingMachineryOperationLine -> {
                machineryOperationLineMapper.partialUpdate(existingMachineryOperationLine, machineryOperationLineDTO);

                return existingMachineryOperationLine;
            })
            .map(machineryOperationLineRepository::save)
            .map(machineryOperationLineMapper::toDto);
    }

    /**
     * Get one machineryOperationLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MachineryOperationLineDTO> findOne(Long id) {
        LOG.debug("Request to get MachineryOperationLine : {}", id);
        return machineryOperationLineRepository.findById(id).map(machineryOperationLineMapper::toDto);
    }

    /**
     * Delete the machineryOperationLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete MachineryOperationLine : {}", id);
        machineryOperationLineRepository.deleteById(id);
    }
}
