package com.falaisia.ship.eventreporting2.web.rest;

import static com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteAsserts.*;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.createUpdateProxyForBean;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.falaisia.ship.eventreporting2.IntegrationTest;
import com.falaisia.ship.eventreporting2.domain.BunkerReceivedNote;
import com.falaisia.ship.eventreporting2.domain.Voyage;
import com.falaisia.ship.eventreporting2.repository.BunkerReceivedNoteRepository;
import com.falaisia.ship.eventreporting2.service.dto.BunkerReceivedNoteDTO;
import com.falaisia.ship.eventreporting2.service.mapper.BunkerReceivedNoteMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BunkerReceivedNoteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BunkerReceivedNoteResourceIT {

    private static final ZonedDateTime DEFAULT_DOCUMENT_DATE_AND_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DOCUMENT_DATE_AND_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DOCUMENT_DATE_AND_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DOCUMENT_DISPLAY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_DISPLAY_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bunker-received-notes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BunkerReceivedNoteRepository bunkerReceivedNoteRepository;

    @Autowired
    private BunkerReceivedNoteMapper bunkerReceivedNoteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBunkerReceivedNoteMockMvc;

    private BunkerReceivedNote bunkerReceivedNote;

    private BunkerReceivedNote insertedBunkerReceivedNote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BunkerReceivedNote createEntity(EntityManager em) {
        BunkerReceivedNote bunkerReceivedNote = new BunkerReceivedNote()
            .documentDateAndTime(DEFAULT_DOCUMENT_DATE_AND_TIME)
            .documentDisplayNumber(DEFAULT_DOCUMENT_DISPLAY_NUMBER);
        // Add required entity
        Voyage voyage;
        if (TestUtil.findAll(em, Voyage.class).isEmpty()) {
            voyage = VoyageResourceIT.createEntity(em);
            em.persist(voyage);
            em.flush();
        } else {
            voyage = TestUtil.findAll(em, Voyage.class).get(0);
        }
        bunkerReceivedNote.setVoyage(voyage);
        return bunkerReceivedNote;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BunkerReceivedNote createUpdatedEntity(EntityManager em) {
        BunkerReceivedNote updatedBunkerReceivedNote = new BunkerReceivedNote()
            .documentDateAndTime(UPDATED_DOCUMENT_DATE_AND_TIME)
            .documentDisplayNumber(UPDATED_DOCUMENT_DISPLAY_NUMBER);
        // Add required entity
        Voyage voyage;
        if (TestUtil.findAll(em, Voyage.class).isEmpty()) {
            voyage = VoyageResourceIT.createUpdatedEntity(em);
            em.persist(voyage);
            em.flush();
        } else {
            voyage = TestUtil.findAll(em, Voyage.class).get(0);
        }
        updatedBunkerReceivedNote.setVoyage(voyage);
        return updatedBunkerReceivedNote;
    }

    @BeforeEach
    void initTest() {
        bunkerReceivedNote = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedBunkerReceivedNote != null) {
            bunkerReceivedNoteRepository.delete(insertedBunkerReceivedNote);
            insertedBunkerReceivedNote = null;
        }
    }

    @Test
    @Transactional
    void createBunkerReceivedNote() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BunkerReceivedNote
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);
        var returnedBunkerReceivedNoteDTO = om.readValue(
            restBunkerReceivedNoteMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(bunkerReceivedNoteDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BunkerReceivedNoteDTO.class
        );

        // Validate the BunkerReceivedNote in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBunkerReceivedNote = bunkerReceivedNoteMapper.toEntity(returnedBunkerReceivedNoteDTO);
        assertBunkerReceivedNoteUpdatableFieldsEquals(
            returnedBunkerReceivedNote,
            getPersistedBunkerReceivedNote(returnedBunkerReceivedNote)
        );

        insertedBunkerReceivedNote = returnedBunkerReceivedNote;
    }

    @Test
    @Transactional
    void createBunkerReceivedNoteWithExistingId() throws Exception {
        // Create the BunkerReceivedNote with an existing ID
        bunkerReceivedNote.setId(1L);
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBunkerReceivedNoteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNote in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDocumentDateAndTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bunkerReceivedNote.setDocumentDateAndTime(null);

        // Create the BunkerReceivedNote, which fails.
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        restBunkerReceivedNoteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotes() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get all the bunkerReceivedNoteList
        restBunkerReceivedNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bunkerReceivedNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentDateAndTime").value(hasItem(sameInstant(DEFAULT_DOCUMENT_DATE_AND_TIME))))
            .andExpect(jsonPath("$.[*].documentDisplayNumber").value(hasItem(DEFAULT_DOCUMENT_DISPLAY_NUMBER)));
    }

    @Test
    @Transactional
    void getBunkerReceivedNote() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get the bunkerReceivedNote
        restBunkerReceivedNoteMockMvc
            .perform(get(ENTITY_API_URL_ID, bunkerReceivedNote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bunkerReceivedNote.getId().intValue()))
            .andExpect(jsonPath("$.documentDateAndTime").value(sameInstant(DEFAULT_DOCUMENT_DATE_AND_TIME)))
            .andExpect(jsonPath("$.documentDisplayNumber").value(DEFAULT_DOCUMENT_DISPLAY_NUMBER));
    }

    @Test
    @Transactional
    void getBunkerReceivedNotesByIdFiltering() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        Long id = bunkerReceivedNote.getId();

        defaultBunkerReceivedNoteFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultBunkerReceivedNoteFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultBunkerReceivedNoteFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotesByDocumentDateAndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get all the bunkerReceivedNoteList where documentDateAndTime equals to
        defaultBunkerReceivedNoteFiltering(
            "documentDateAndTime.equals=" + DEFAULT_DOCUMENT_DATE_AND_TIME,
            "documentDateAndTime.equals=" + UPDATED_DOCUMENT_DATE_AND_TIME
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotesByDocumentDateAndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get all the bunkerReceivedNoteList where documentDateAndTime in
        defaultBunkerReceivedNoteFiltering(
            "documentDateAndTime.in=" + DEFAULT_DOCUMENT_DATE_AND_TIME + "," + UPDATED_DOCUMENT_DATE_AND_TIME,
            "documentDateAndTime.in=" + UPDATED_DOCUMENT_DATE_AND_TIME
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotesByDocumentDateAndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get all the bunkerReceivedNoteList where documentDateAndTime is not null
        defaultBunkerReceivedNoteFiltering("documentDateAndTime.specified=true", "documentDateAndTime.specified=false");
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotesByDocumentDateAndTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get all the bunkerReceivedNoteList where documentDateAndTime is greater than or equal to
        defaultBunkerReceivedNoteFiltering(
            "documentDateAndTime.greaterThanOrEqual=" + DEFAULT_DOCUMENT_DATE_AND_TIME,
            "documentDateAndTime.greaterThanOrEqual=" + UPDATED_DOCUMENT_DATE_AND_TIME
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotesByDocumentDateAndTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get all the bunkerReceivedNoteList where documentDateAndTime is less than or equal to
        defaultBunkerReceivedNoteFiltering(
            "documentDateAndTime.lessThanOrEqual=" + DEFAULT_DOCUMENT_DATE_AND_TIME,
            "documentDateAndTime.lessThanOrEqual=" + SMALLER_DOCUMENT_DATE_AND_TIME
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotesByDocumentDateAndTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get all the bunkerReceivedNoteList where documentDateAndTime is less than
        defaultBunkerReceivedNoteFiltering(
            "documentDateAndTime.lessThan=" + UPDATED_DOCUMENT_DATE_AND_TIME,
            "documentDateAndTime.lessThan=" + DEFAULT_DOCUMENT_DATE_AND_TIME
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotesByDocumentDateAndTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get all the bunkerReceivedNoteList where documentDateAndTime is greater than
        defaultBunkerReceivedNoteFiltering(
            "documentDateAndTime.greaterThan=" + SMALLER_DOCUMENT_DATE_AND_TIME,
            "documentDateAndTime.greaterThan=" + DEFAULT_DOCUMENT_DATE_AND_TIME
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotesByDocumentDisplayNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get all the bunkerReceivedNoteList where documentDisplayNumber equals to
        defaultBunkerReceivedNoteFiltering(
            "documentDisplayNumber.equals=" + DEFAULT_DOCUMENT_DISPLAY_NUMBER,
            "documentDisplayNumber.equals=" + UPDATED_DOCUMENT_DISPLAY_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotesByDocumentDisplayNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get all the bunkerReceivedNoteList where documentDisplayNumber in
        defaultBunkerReceivedNoteFiltering(
            "documentDisplayNumber.in=" + DEFAULT_DOCUMENT_DISPLAY_NUMBER + "," + UPDATED_DOCUMENT_DISPLAY_NUMBER,
            "documentDisplayNumber.in=" + UPDATED_DOCUMENT_DISPLAY_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotesByDocumentDisplayNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get all the bunkerReceivedNoteList where documentDisplayNumber is not null
        defaultBunkerReceivedNoteFiltering("documentDisplayNumber.specified=true", "documentDisplayNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotesByDocumentDisplayNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get all the bunkerReceivedNoteList where documentDisplayNumber contains
        defaultBunkerReceivedNoteFiltering(
            "documentDisplayNumber.contains=" + DEFAULT_DOCUMENT_DISPLAY_NUMBER,
            "documentDisplayNumber.contains=" + UPDATED_DOCUMENT_DISPLAY_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotesByDocumentDisplayNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        // Get all the bunkerReceivedNoteList where documentDisplayNumber does not contain
        defaultBunkerReceivedNoteFiltering(
            "documentDisplayNumber.doesNotContain=" + UPDATED_DOCUMENT_DISPLAY_NUMBER,
            "documentDisplayNumber.doesNotContain=" + DEFAULT_DOCUMENT_DISPLAY_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNotesByVoyageIsEqualToSomething() throws Exception {
        Voyage voyage;
        if (TestUtil.findAll(em, Voyage.class).isEmpty()) {
            bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);
            voyage = VoyageResourceIT.createEntity(em);
        } else {
            voyage = TestUtil.findAll(em, Voyage.class).get(0);
        }
        em.persist(voyage);
        em.flush();
        bunkerReceivedNote.setVoyage(voyage);
        bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);
        Long voyageId = voyage.getId();
        // Get all the bunkerReceivedNoteList where voyage equals to voyageId
        defaultBunkerReceivedNoteShouldBeFound("voyageId.equals=" + voyageId);

        // Get all the bunkerReceivedNoteList where voyage equals to (voyageId + 1)
        defaultBunkerReceivedNoteShouldNotBeFound("voyageId.equals=" + (voyageId + 1));
    }

    private void defaultBunkerReceivedNoteFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultBunkerReceivedNoteShouldBeFound(shouldBeFound);
        defaultBunkerReceivedNoteShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBunkerReceivedNoteShouldBeFound(String filter) throws Exception {
        restBunkerReceivedNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bunkerReceivedNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentDateAndTime").value(hasItem(sameInstant(DEFAULT_DOCUMENT_DATE_AND_TIME))))
            .andExpect(jsonPath("$.[*].documentDisplayNumber").value(hasItem(DEFAULT_DOCUMENT_DISPLAY_NUMBER)));

        // Check, that the count call also returns 1
        restBunkerReceivedNoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBunkerReceivedNoteShouldNotBeFound(String filter) throws Exception {
        restBunkerReceivedNoteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBunkerReceivedNoteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBunkerReceivedNote() throws Exception {
        // Get the bunkerReceivedNote
        restBunkerReceivedNoteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBunkerReceivedNote() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bunkerReceivedNote
        BunkerReceivedNote updatedBunkerReceivedNote = bunkerReceivedNoteRepository.findById(bunkerReceivedNote.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBunkerReceivedNote are not directly saved in db
        em.detach(updatedBunkerReceivedNote);
        updatedBunkerReceivedNote
            .documentDateAndTime(UPDATED_DOCUMENT_DATE_AND_TIME)
            .documentDisplayNumber(UPDATED_DOCUMENT_DISPLAY_NUMBER);
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(updatedBunkerReceivedNote);

        restBunkerReceivedNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bunkerReceivedNoteDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isOk());

        // Validate the BunkerReceivedNote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBunkerReceivedNoteToMatchAllProperties(updatedBunkerReceivedNote);
    }

    @Test
    @Transactional
    void putNonExistingBunkerReceivedNote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bunkerReceivedNote.setId(longCount.incrementAndGet());

        // Create the BunkerReceivedNote
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bunkerReceivedNoteDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBunkerReceivedNote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bunkerReceivedNote.setId(longCount.incrementAndGet());

        // Create the BunkerReceivedNote
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBunkerReceivedNote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bunkerReceivedNote.setId(longCount.incrementAndGet());

        // Create the BunkerReceivedNote
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BunkerReceivedNote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBunkerReceivedNoteWithPatch() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bunkerReceivedNote using partial update
        BunkerReceivedNote partialUpdatedBunkerReceivedNote = new BunkerReceivedNote();
        partialUpdatedBunkerReceivedNote.setId(bunkerReceivedNote.getId());

        partialUpdatedBunkerReceivedNote.documentDateAndTime(UPDATED_DOCUMENT_DATE_AND_TIME);

        restBunkerReceivedNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBunkerReceivedNote.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBunkerReceivedNote))
            )
            .andExpect(status().isOk());

        // Validate the BunkerReceivedNote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBunkerReceivedNoteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBunkerReceivedNote, bunkerReceivedNote),
            getPersistedBunkerReceivedNote(bunkerReceivedNote)
        );
    }

    @Test
    @Transactional
    void fullUpdateBunkerReceivedNoteWithPatch() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bunkerReceivedNote using partial update
        BunkerReceivedNote partialUpdatedBunkerReceivedNote = new BunkerReceivedNote();
        partialUpdatedBunkerReceivedNote.setId(bunkerReceivedNote.getId());

        partialUpdatedBunkerReceivedNote
            .documentDateAndTime(UPDATED_DOCUMENT_DATE_AND_TIME)
            .documentDisplayNumber(UPDATED_DOCUMENT_DISPLAY_NUMBER);

        restBunkerReceivedNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBunkerReceivedNote.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBunkerReceivedNote))
            )
            .andExpect(status().isOk());

        // Validate the BunkerReceivedNote in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBunkerReceivedNoteUpdatableFieldsEquals(
            partialUpdatedBunkerReceivedNote,
            getPersistedBunkerReceivedNote(partialUpdatedBunkerReceivedNote)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBunkerReceivedNote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bunkerReceivedNote.setId(longCount.incrementAndGet());

        // Create the BunkerReceivedNote
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bunkerReceivedNoteDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBunkerReceivedNote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bunkerReceivedNote.setId(longCount.incrementAndGet());

        // Create the BunkerReceivedNote
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBunkerReceivedNote() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bunkerReceivedNote.setId(longCount.incrementAndGet());

        // Create the BunkerReceivedNote
        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = bunkerReceivedNoteMapper.toDto(bunkerReceivedNote);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bunkerReceivedNoteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BunkerReceivedNote in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBunkerReceivedNote() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNote = bunkerReceivedNoteRepository.saveAndFlush(bunkerReceivedNote);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bunkerReceivedNote
        restBunkerReceivedNoteMockMvc
            .perform(delete(ENTITY_API_URL_ID, bunkerReceivedNote.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bunkerReceivedNoteRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected BunkerReceivedNote getPersistedBunkerReceivedNote(BunkerReceivedNote bunkerReceivedNote) {
        return bunkerReceivedNoteRepository.findById(bunkerReceivedNote.getId()).orElseThrow();
    }

    protected void assertPersistedBunkerReceivedNoteToMatchAllProperties(BunkerReceivedNote expectedBunkerReceivedNote) {
        assertBunkerReceivedNoteAllPropertiesEquals(expectedBunkerReceivedNote, getPersistedBunkerReceivedNote(expectedBunkerReceivedNote));
    }

    protected void assertPersistedBunkerReceivedNoteToMatchUpdatableProperties(BunkerReceivedNote expectedBunkerReceivedNote) {
        assertBunkerReceivedNoteAllUpdatablePropertiesEquals(
            expectedBunkerReceivedNote,
            getPersistedBunkerReceivedNote(expectedBunkerReceivedNote)
        );
    }
}
