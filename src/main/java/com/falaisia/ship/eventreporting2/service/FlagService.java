package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.Flag;
import com.falaisia.ship.eventreporting2.repository.FlagRepository;
import com.falaisia.ship.eventreporting2.service.dto.FlagDTO;
import com.falaisia.ship.eventreporting2.service.mapper.FlagMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.falaisia.ship.eventreporting2.domain.Flag}.
 */
@Service
@Transactional
public class FlagService {

    private static final Logger LOG = LoggerFactory.getLogger(FlagService.class);

    private final FlagRepository flagRepository;

    private final FlagMapper flagMapper;

    public FlagService(FlagRepository flagRepository, FlagMapper flagMapper) {
        this.flagRepository = flagRepository;
        this.flagMapper = flagMapper;
    }

    /**
     * Save a flag.
     *
     * @param flagDTO the entity to save.
     * @return the persisted entity.
     */
    public FlagDTO save(FlagDTO flagDTO) {
        LOG.debug("Request to save Flag : {}", flagDTO);
        Flag flag = flagMapper.toEntity(flagDTO);
        flag = flagRepository.save(flag);
        return flagMapper.toDto(flag);
    }

    /**
     * Update a flag.
     *
     * @param flagDTO the entity to save.
     * @return the persisted entity.
     */
    public FlagDTO update(FlagDTO flagDTO) {
        LOG.debug("Request to update Flag : {}", flagDTO);
        Flag flag = flagMapper.toEntity(flagDTO);
        flag = flagRepository.save(flag);
        return flagMapper.toDto(flag);
    }

    /**
     * Partially update a flag.
     *
     * @param flagDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FlagDTO> partialUpdate(FlagDTO flagDTO) {
        LOG.debug("Request to partially update Flag : {}", flagDTO);

        return flagRepository
            .findById(flagDTO.getId())
            .map(existingFlag -> {
                flagMapper.partialUpdate(existingFlag, flagDTO);

                return existingFlag;
            })
            .map(flagRepository::save)
            .map(flagMapper::toDto);
    }

    /**
     * Get one flag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FlagDTO> findOne(Long id) {
        LOG.debug("Request to get Flag : {}", id);
        return flagRepository.findById(id).map(flagMapper::toDto);
    }

    /**
     * Delete the flag by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Flag : {}", id);
        flagRepository.deleteById(id);
    }
}
