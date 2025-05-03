package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.ConsumptionLine;
import com.falaisia.ship.eventreporting2.repository.ConsumptionLineRepository;
import com.falaisia.ship.eventreporting2.service.dto.ConsumptionLineDTO;
import com.falaisia.ship.eventreporting2.service.mapper.ConsumptionLineMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.falaisia.ship.eventreporting2.domain.ConsumptionLine}.
 */
@Service
@Transactional
public class ConsumptionLineService {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumptionLineService.class);

    private final ConsumptionLineRepository consumptionLineRepository;

    private final ConsumptionLineMapper consumptionLineMapper;

    public ConsumptionLineService(ConsumptionLineRepository consumptionLineRepository, ConsumptionLineMapper consumptionLineMapper) {
        this.consumptionLineRepository = consumptionLineRepository;
        this.consumptionLineMapper = consumptionLineMapper;
    }

    /**
     * Save a consumptionLine.
     *
     * @param consumptionLineDTO the entity to save.
     * @return the persisted entity.
     */
    public ConsumptionLineDTO save(ConsumptionLineDTO consumptionLineDTO) {
        LOG.debug("Request to save ConsumptionLine : {}", consumptionLineDTO);
        ConsumptionLine consumptionLine = consumptionLineMapper.toEntity(consumptionLineDTO);
        consumptionLine = consumptionLineRepository.save(consumptionLine);
        return consumptionLineMapper.toDto(consumptionLine);
    }

    /**
     * Update a consumptionLine.
     *
     * @param consumptionLineDTO the entity to save.
     * @return the persisted entity.
     */
    public ConsumptionLineDTO update(ConsumptionLineDTO consumptionLineDTO) {
        LOG.debug("Request to update ConsumptionLine : {}", consumptionLineDTO);
        ConsumptionLine consumptionLine = consumptionLineMapper.toEntity(consumptionLineDTO);
        consumptionLine = consumptionLineRepository.save(consumptionLine);
        return consumptionLineMapper.toDto(consumptionLine);
    }

    /**
     * Partially update a consumptionLine.
     *
     * @param consumptionLineDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConsumptionLineDTO> partialUpdate(ConsumptionLineDTO consumptionLineDTO) {
        LOG.debug("Request to partially update ConsumptionLine : {}", consumptionLineDTO);

        return consumptionLineRepository
            .findById(consumptionLineDTO.getId())
            .map(existingConsumptionLine -> {
                consumptionLineMapper.partialUpdate(existingConsumptionLine, consumptionLineDTO);

                return existingConsumptionLine;
            })
            .map(consumptionLineRepository::save)
            .map(consumptionLineMapper::toDto);
    }

    /**
     * Get one consumptionLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConsumptionLineDTO> findOne(Long id) {
        LOG.debug("Request to get ConsumptionLine : {}", id);
        return consumptionLineRepository.findById(id).map(consumptionLineMapper::toDto);
    }

    /**
     * Delete the consumptionLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete ConsumptionLine : {}", id);
        consumptionLineRepository.deleteById(id);
    }
}
