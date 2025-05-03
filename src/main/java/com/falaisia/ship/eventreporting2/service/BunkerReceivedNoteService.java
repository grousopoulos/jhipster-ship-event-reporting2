package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.BunkerReceivedNote;
import com.falaisia.ship.eventreporting2.repository.BunkerReceivedNoteRepository;
import com.falaisia.ship.eventreporting2.service.dto.BunkerReceivedNoteDTO;
import com.falaisia.ship.eventreporting2.service.mapper.BunkerReceivedNoteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.falaisia.ship.eventreporting2.domain.BunkerReceivedNote}.
 */
@Service
@Transactional
public class BunkerReceivedNoteService {

    private static final Logger LOG = LoggerFactory.getLogger(BunkerReceivedNoteService.class);

    private final BunkerReceivedNoteRepository bunkerReceivedNoteRepository;

    private final BunkerReceivedNoteMapper bunkerReceivedNoteMapper;

    public BunkerReceivedNoteService(
        BunkerReceivedNoteRepository bunkerReceivedNoteRepository,
        BunkerReceivedNoteMapper bunkerReceivedNoteMapper
    ) {
        this.bunkerReceivedNoteRepository = bunkerReceivedNoteRepository;
        this.bunkerReceivedNoteMapper = bunkerReceivedNoteMapper;
    }

    /**
     * Save a bunkerReceivedNote.
     *
     * @param bunkerReceivedNoteDTO the entity to save.
     * @return the persisted entity.
     */
    public BunkerReceivedNoteDTO save(BunkerReceivedNoteDTO bunkerReceivedNoteDTO) {
        LOG.debug("Request to save BunkerReceivedNote : {}", bunkerReceivedNoteDTO);
        BunkerReceivedNote bunkerReceivedNote = bunkerReceivedNoteMapper.toEntity(bunkerReceivedNoteDTO);
        bunkerReceivedNote = bunkerReceivedNoteRepository.save(bunkerReceivedNote);
        return bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);
    }

    /**
     * Update a bunkerReceivedNote.
     *
     * @param bunkerReceivedNoteDTO the entity to save.
     * @return the persisted entity.
     */
    public BunkerReceivedNoteDTO update(BunkerReceivedNoteDTO bunkerReceivedNoteDTO) {
        LOG.debug("Request to update BunkerReceivedNote : {}", bunkerReceivedNoteDTO);
        BunkerReceivedNote bunkerReceivedNote = bunkerReceivedNoteMapper.toEntity(bunkerReceivedNoteDTO);
        bunkerReceivedNote = bunkerReceivedNoteRepository.save(bunkerReceivedNote);
        return bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);
    }

    /**
     * Partially update a bunkerReceivedNote.
     *
     * @param bunkerReceivedNoteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BunkerReceivedNoteDTO> partialUpdate(BunkerReceivedNoteDTO bunkerReceivedNoteDTO) {
        LOG.debug("Request to partially update BunkerReceivedNote : {}", bunkerReceivedNoteDTO);

        return bunkerReceivedNoteRepository
            .findById(bunkerReceivedNoteDTO.getId())
            .map(existingBunkerReceivedNote -> {
                bunkerReceivedNoteMapper.partialUpdate(existingBunkerReceivedNote, bunkerReceivedNoteDTO);

                return existingBunkerReceivedNote;
            })
            .map(bunkerReceivedNoteRepository::save)
            .map(bunkerReceivedNoteMapper::toDto);
    }

    /**
     * Get one bunkerReceivedNote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BunkerReceivedNoteDTO> findOne(Long id) {
        LOG.debug("Request to get BunkerReceivedNote : {}", id);
        return bunkerReceivedNoteRepository.findById(id).map(bunkerReceivedNoteMapper::toDto);
    }

    /**
     * Delete the bunkerReceivedNote by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete BunkerReceivedNote : {}", id);
        bunkerReceivedNoteRepository.deleteById(id);
    }
}
