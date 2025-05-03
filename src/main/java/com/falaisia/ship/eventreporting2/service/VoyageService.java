package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.Voyage;
import com.falaisia.ship.eventreporting2.repository.VoyageRepository;
import com.falaisia.ship.eventreporting2.service.dto.VoyageDTO;
import com.falaisia.ship.eventreporting2.service.mapper.VoyageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.falaisia.ship.eventreporting2.domain.Voyage}.
 */
@Service
@Transactional
public class VoyageService {

    private static final Logger LOG = LoggerFactory.getLogger(VoyageService.class);

    private final VoyageRepository voyageRepository;

    private final VoyageMapper voyageMapper;

    public VoyageService(VoyageRepository voyageRepository, VoyageMapper voyageMapper) {
        this.voyageRepository = voyageRepository;
        this.voyageMapper = voyageMapper;
    }

    /**
     * Save a voyage.
     *
     * @param voyageDTO the entity to save.
     * @return the persisted entity.
     */
    public VoyageDTO save(VoyageDTO voyageDTO) {
        LOG.debug("Request to save Voyage : {}", voyageDTO);
        Voyage voyage = voyageMapper.toEntity(voyageDTO);
        voyage = voyageRepository.save(voyage);
        return voyageMapper.toDto(voyage);
    }

    /**
     * Update a voyage.
     *
     * @param voyageDTO the entity to save.
     * @return the persisted entity.
     */
    public VoyageDTO update(VoyageDTO voyageDTO) {
        LOG.debug("Request to update Voyage : {}", voyageDTO);
        Voyage voyage = voyageMapper.toEntity(voyageDTO);
        voyage = voyageRepository.save(voyage);
        return voyageMapper.toDto(voyage);
    }

    /**
     * Partially update a voyage.
     *
     * @param voyageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VoyageDTO> partialUpdate(VoyageDTO voyageDTO) {
        LOG.debug("Request to partially update Voyage : {}", voyageDTO);

        return voyageRepository
            .findById(voyageDTO.getId())
            .map(existingVoyage -> {
                voyageMapper.partialUpdate(existingVoyage, voyageDTO);

                return existingVoyage;
            })
            .map(voyageRepository::save)
            .map(voyageMapper::toDto);
    }

    /**
     * Get one voyage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VoyageDTO> findOne(Long id) {
        LOG.debug("Request to get Voyage : {}", id);
        return voyageRepository.findById(id).map(voyageMapper::toDto);
    }

    /**
     * Delete the voyage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Voyage : {}", id);
        voyageRepository.deleteById(id);
    }
}
