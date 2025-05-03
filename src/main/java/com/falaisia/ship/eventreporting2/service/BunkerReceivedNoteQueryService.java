package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.*; // for static metamodels
import com.falaisia.ship.eventreporting2.domain.BunkerReceivedNote;
import com.falaisia.ship.eventreporting2.repository.BunkerReceivedNoteRepository;
import com.falaisia.ship.eventreporting2.service.criteria.BunkerReceivedNoteCriteria;
import com.falaisia.ship.eventreporting2.service.dto.BunkerReceivedNoteDTO;
import com.falaisia.ship.eventreporting2.service.mapper.BunkerReceivedNoteMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link BunkerReceivedNote} entities in the database.
 * The main input is a {@link BunkerReceivedNoteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link BunkerReceivedNoteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BunkerReceivedNoteQueryService extends QueryService<BunkerReceivedNote> {

    private static final Logger LOG = LoggerFactory.getLogger(BunkerReceivedNoteQueryService.class);

    private final BunkerReceivedNoteRepository bunkerReceivedNoteRepository;

    private final BunkerReceivedNoteMapper bunkerReceivedNoteMapper;

    public BunkerReceivedNoteQueryService(
        BunkerReceivedNoteRepository bunkerReceivedNoteRepository,
        BunkerReceivedNoteMapper bunkerReceivedNoteMapper
    ) {
        this.bunkerReceivedNoteRepository = bunkerReceivedNoteRepository;
        this.bunkerReceivedNoteMapper = bunkerReceivedNoteMapper;
    }

    /**
     * Return a {@link Page} of {@link BunkerReceivedNoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BunkerReceivedNoteDTO> findByCriteria(BunkerReceivedNoteCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BunkerReceivedNote> specification = createSpecification(criteria);
        return bunkerReceivedNoteRepository.findAll(specification, page).map(bunkerReceivedNoteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BunkerReceivedNoteCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<BunkerReceivedNote> specification = createSpecification(criteria);
        return bunkerReceivedNoteRepository.count(specification);
    }

    /**
     * Function to convert {@link BunkerReceivedNoteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BunkerReceivedNote> createSpecification(BunkerReceivedNoteCriteria criteria) {
        Specification<BunkerReceivedNote> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), BunkerReceivedNote_.id),
                buildRangeSpecification(criteria.getDocumentDateAndTime(), BunkerReceivedNote_.documentDateAndTime),
                buildStringSpecification(criteria.getDocumentDisplayNumber(), BunkerReceivedNote_.documentDisplayNumber),
                buildSpecification(criteria.getVoyageId(), root -> root.join(BunkerReceivedNote_.voyage, JoinType.LEFT).get(Voyage_.id)),
                buildSpecification(criteria.getLinesId(), root ->
                    root.join(BunkerReceivedNote_.lines, JoinType.LEFT).get(BunkerReceivedNoteLine_.id)
                )
            );
        }
        return specification;
    }
}
