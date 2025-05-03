package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.ClassificationSociety;
import com.falaisia.ship.eventreporting2.repository.ClassificationSocietyRepository;
import com.falaisia.ship.eventreporting2.service.dto.ClassificationSocietyDTO;
import com.falaisia.ship.eventreporting2.service.mapper.ClassificationSocietyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.falaisia.ship.eventreporting2.domain.ClassificationSociety}.
 */
@Service
@Transactional
public class ClassificationSocietyService {

    private static final Logger LOG = LoggerFactory.getLogger(ClassificationSocietyService.class);

    private final ClassificationSocietyRepository classificationSocietyRepository;

    private final ClassificationSocietyMapper classificationSocietyMapper;

    public ClassificationSocietyService(
        ClassificationSocietyRepository classificationSocietyRepository,
        ClassificationSocietyMapper classificationSocietyMapper
    ) {
        this.classificationSocietyRepository = classificationSocietyRepository;
        this.classificationSocietyMapper = classificationSocietyMapper;
    }

    /**
     * Save a classificationSociety.
     *
     * @param classificationSocietyDTO the entity to save.
     * @return the persisted entity.
     */
    public ClassificationSocietyDTO save(ClassificationSocietyDTO classificationSocietyDTO) {
        LOG.debug("Request to save ClassificationSociety : {}", classificationSocietyDTO);
        ClassificationSociety classificationSociety = classificationSocietyMapper.toEntity(classificationSocietyDTO);
        classificationSociety = classificationSocietyRepository.save(classificationSociety);
        return classificationSocietyMapper.toDto(classificationSociety);
    }

    /**
     * Update a classificationSociety.
     *
     * @param classificationSocietyDTO the entity to save.
     * @return the persisted entity.
     */
    public ClassificationSocietyDTO update(ClassificationSocietyDTO classificationSocietyDTO) {
        LOG.debug("Request to update ClassificationSociety : {}", classificationSocietyDTO);
        ClassificationSociety classificationSociety = classificationSocietyMapper.toEntity(classificationSocietyDTO);
        classificationSociety = classificationSocietyRepository.save(classificationSociety);
        return classificationSocietyMapper.toDto(classificationSociety);
    }

    /**
     * Partially update a classificationSociety.
     *
     * @param classificationSocietyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClassificationSocietyDTO> partialUpdate(ClassificationSocietyDTO classificationSocietyDTO) {
        LOG.debug("Request to partially update ClassificationSociety : {}", classificationSocietyDTO);

        return classificationSocietyRepository
            .findById(classificationSocietyDTO.getId())
            .map(existingClassificationSociety -> {
                classificationSocietyMapper.partialUpdate(existingClassificationSociety, classificationSocietyDTO);

                return existingClassificationSociety;
            })
            .map(classificationSocietyRepository::save)
            .map(classificationSocietyMapper::toDto);
    }

    /**
     * Get one classificationSociety by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClassificationSocietyDTO> findOne(Long id) {
        LOG.debug("Request to get ClassificationSociety : {}", id);
        return classificationSocietyRepository.findById(id).map(classificationSocietyMapper::toDto);
    }

    /**
     * Delete the classificationSociety by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete ClassificationSociety : {}", id);
        classificationSocietyRepository.deleteById(id);
    }
}
