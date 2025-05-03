package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteLine;
import com.falaisia.ship.eventreporting2.repository.BunkerReceivedNoteLineRepository;
import com.falaisia.ship.eventreporting2.service.dto.BunkerReceivedNoteLineDTO;
import com.falaisia.ship.eventreporting2.service.mapper.BunkerReceivedNoteLineMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteLine}.
 */
@Service
@Transactional
public class BunkerReceivedNoteLineService {

    private static final Logger LOG = LoggerFactory.getLogger(BunkerReceivedNoteLineService.class);

    private final BunkerReceivedNoteLineRepository bunkerReceivedNoteLineRepository;

    private final BunkerReceivedNoteLineMapper bunkerReceivedNoteLineMapper;

    public BunkerReceivedNoteLineService(
        BunkerReceivedNoteLineRepository bunkerReceivedNoteLineRepository,
        BunkerReceivedNoteLineMapper bunkerReceivedNoteLineMapper
    ) {
        this.bunkerReceivedNoteLineRepository = bunkerReceivedNoteLineRepository;
        this.bunkerReceivedNoteLineMapper = bunkerReceivedNoteLineMapper;
    }

    /**
     * Save a bunkerReceivedNoteLine.
     *
     * @param bunkerReceivedNoteLineDTO the entity to save.
     * @return the persisted entity.
     */
    public BunkerReceivedNoteLineDTO save(BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO) {
        LOG.debug("Request to save BunkerReceivedNoteLine : {}", bunkerReceivedNoteLineDTO);
        BunkerReceivedNoteLine bunkerReceivedNoteLine = bunkerReceivedNoteLineMapper.toEntity(bunkerReceivedNoteLineDTO);
        bunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.save(bunkerReceivedNoteLine);
        return bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLine);
    }

    /**
     * Update a bunkerReceivedNoteLine.
     *
     * @param bunkerReceivedNoteLineDTO the entity to save.
     * @return the persisted entity.
     */
    public BunkerReceivedNoteLineDTO update(BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO) {
        LOG.debug("Request to update BunkerReceivedNoteLine : {}", bunkerReceivedNoteLineDTO);
        BunkerReceivedNoteLine bunkerReceivedNoteLine = bunkerReceivedNoteLineMapper.toEntity(bunkerReceivedNoteLineDTO);
        bunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.save(bunkerReceivedNoteLine);
        return bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLine);
    }

    /**
     * Partially update a bunkerReceivedNoteLine.
     *
     * @param bunkerReceivedNoteLineDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BunkerReceivedNoteLineDTO> partialUpdate(BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO) {
        LOG.debug("Request to partially update BunkerReceivedNoteLine : {}", bunkerReceivedNoteLineDTO);

        return bunkerReceivedNoteLineRepository
            .findById(bunkerReceivedNoteLineDTO.getId())
            .map(existingBunkerReceivedNoteLine -> {
                bunkerReceivedNoteLineMapper.partialUpdate(existingBunkerReceivedNoteLine, bunkerReceivedNoteLineDTO);

                return existingBunkerReceivedNoteLine;
            })
            .map(bunkerReceivedNoteLineRepository::save)
            .map(bunkerReceivedNoteLineMapper::toDto);
    }

    /**
     * Get one bunkerReceivedNoteLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BunkerReceivedNoteLineDTO> findOne(Long id) {
        LOG.debug("Request to get BunkerReceivedNoteLine : {}", id);
        return bunkerReceivedNoteLineRepository.findById(id).map(bunkerReceivedNoteLineMapper::toDto);
    }

    /**
     * Delete the bunkerReceivedNoteLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete BunkerReceivedNoteLine : {}", id);
        bunkerReceivedNoteLineRepository.deleteById(id);
    }
}
