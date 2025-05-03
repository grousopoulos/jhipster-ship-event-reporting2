package com.falaisia.ship.eventreporting2.web.rest;

import static com.falaisia.ship.eventreporting2.domain.VoyageAsserts.*;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.falaisia.ship.eventreporting2.IntegrationTest;
import com.falaisia.ship.eventreporting2.domain.Ship;
import com.falaisia.ship.eventreporting2.domain.Voyage;
import com.falaisia.ship.eventreporting2.repository.VoyageRepository;
import com.falaisia.ship.eventreporting2.service.dto.VoyageDTO;
import com.falaisia.ship.eventreporting2.service.mapper.VoyageMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link VoyageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoyageResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/voyages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VoyageRepository voyageRepository;

    @Autowired
    private VoyageMapper voyageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoyageMockMvc;

    private Voyage voyage;

    private Voyage insertedVoyage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voyage createEntity(EntityManager em) {
        Voyage voyage = new Voyage().number(DEFAULT_NUMBER);
        // Add required entity
        Ship ship;
        if (TestUtil.findAll(em, Ship.class).isEmpty()) {
            ship = ShipResourceIT.createEntity();
            em.persist(ship);
            em.flush();
        } else {
            ship = TestUtil.findAll(em, Ship.class).get(0);
        }
        voyage.setShip(ship);
        return voyage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voyage createUpdatedEntity(EntityManager em) {
        Voyage updatedVoyage = new Voyage().number(UPDATED_NUMBER);
        // Add required entity
        Ship ship;
        if (TestUtil.findAll(em, Ship.class).isEmpty()) {
            ship = ShipResourceIT.createUpdatedEntity();
            em.persist(ship);
            em.flush();
        } else {
            ship = TestUtil.findAll(em, Ship.class).get(0);
        }
        updatedVoyage.setShip(ship);
        return updatedVoyage;
    }

    @BeforeEach
    void initTest() {
        voyage = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedVoyage != null) {
            voyageRepository.delete(insertedVoyage);
            insertedVoyage = null;
        }
    }

    @Test
    @Transactional
    void createVoyage() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Voyage
        VoyageDTO voyageDTO = voyageMapper.toDto(voyage);
        var returnedVoyageDTO = om.readValue(
            restVoyageMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voyageDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            VoyageDTO.class
        );

        // Validate the Voyage in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedVoyage = voyageMapper.toEntity(returnedVoyageDTO);
        assertVoyageUpdatableFieldsEquals(returnedVoyage, getPersistedVoyage(returnedVoyage));

        insertedVoyage = returnedVoyage;
    }

    @Test
    @Transactional
    void createVoyageWithExistingId() throws Exception {
        // Create the Voyage with an existing ID
        voyage.setId(1L);
        VoyageDTO voyageDTO = voyageMapper.toDto(voyage);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoyageMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voyageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumberIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        voyage.setNumber(null);

        // Create the Voyage, which fails.
        VoyageDTO voyageDTO = voyageMapper.toDto(voyage);

        restVoyageMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voyageDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVoyages() throws Exception {
        // Initialize the database
        insertedVoyage = voyageRepository.saveAndFlush(voyage);

        // Get all the voyageList
        restVoyageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voyage.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }

    @Test
    @Transactional
    void getVoyage() throws Exception {
        // Initialize the database
        insertedVoyage = voyageRepository.saveAndFlush(voyage);

        // Get the voyage
        restVoyageMockMvc
            .perform(get(ENTITY_API_URL_ID, voyage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voyage.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    void getVoyagesByIdFiltering() throws Exception {
        // Initialize the database
        insertedVoyage = voyageRepository.saveAndFlush(voyage);

        Long id = voyage.getId();

        defaultVoyageFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultVoyageFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultVoyageFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVoyagesByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedVoyage = voyageRepository.saveAndFlush(voyage);

        // Get all the voyageList where number equals to
        defaultVoyageFiltering("number.equals=" + DEFAULT_NUMBER, "number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllVoyagesByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedVoyage = voyageRepository.saveAndFlush(voyage);

        // Get all the voyageList where number in
        defaultVoyageFiltering("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER, "number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllVoyagesByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedVoyage = voyageRepository.saveAndFlush(voyage);

        // Get all the voyageList where number is not null
        defaultVoyageFiltering("number.specified=true", "number.specified=false");
    }

    @Test
    @Transactional
    void getAllVoyagesByNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedVoyage = voyageRepository.saveAndFlush(voyage);

        // Get all the voyageList where number contains
        defaultVoyageFiltering("number.contains=" + DEFAULT_NUMBER, "number.contains=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllVoyagesByNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedVoyage = voyageRepository.saveAndFlush(voyage);

        // Get all the voyageList where number does not contain
        defaultVoyageFiltering("number.doesNotContain=" + UPDATED_NUMBER, "number.doesNotContain=" + DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    void getAllVoyagesByShipIsEqualToSomething() throws Exception {
        Ship ship;
        if (TestUtil.findAll(em, Ship.class).isEmpty()) {
            voyageRepository.saveAndFlush(voyage);
            ship = ShipResourceIT.createEntity();
        } else {
            ship = TestUtil.findAll(em, Ship.class).get(0);
        }
        em.persist(ship);
        em.flush();
        voyage.setShip(ship);
        voyageRepository.saveAndFlush(voyage);
        Long shipId = ship.getId();
        // Get all the voyageList where ship equals to shipId
        defaultVoyageShouldBeFound("shipId.equals=" + shipId);

        // Get all the voyageList where ship equals to (shipId + 1)
        defaultVoyageShouldNotBeFound("shipId.equals=" + (shipId + 1));
    }

    private void defaultVoyageFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultVoyageShouldBeFound(shouldBeFound);
        defaultVoyageShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVoyageShouldBeFound(String filter) throws Exception {
        restVoyageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voyage.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));

        // Check, that the count call also returns 1
        restVoyageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVoyageShouldNotBeFound(String filter) throws Exception {
        restVoyageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVoyageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVoyage() throws Exception {
        // Get the voyage
        restVoyageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVoyage() throws Exception {
        // Initialize the database
        insertedVoyage = voyageRepository.saveAndFlush(voyage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voyage
        Voyage updatedVoyage = voyageRepository.findById(voyage.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVoyage are not directly saved in db
        em.detach(updatedVoyage);
        updatedVoyage.number(UPDATED_NUMBER);
        VoyageDTO voyageDTO = voyageMapper.toDto(updatedVoyage);

        restVoyageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voyageDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(voyageDTO))
            )
            .andExpect(status().isOk());

        // Validate the Voyage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVoyageToMatchAllProperties(updatedVoyage);
    }

    @Test
    @Transactional
    void putNonExistingVoyage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voyage.setId(longCount.incrementAndGet());

        // Create the Voyage
        VoyageDTO voyageDTO = voyageMapper.toDto(voyage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voyageDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(voyageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoyage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voyage.setId(longCount.incrementAndGet());

        // Create the Voyage
        VoyageDTO voyageDTO = voyageMapper.toDto(voyage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(voyageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoyage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voyage.setId(longCount.incrementAndGet());

        // Create the Voyage
        VoyageDTO voyageDTO = voyageMapper.toDto(voyage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(voyageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voyage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoyageWithPatch() throws Exception {
        // Initialize the database
        insertedVoyage = voyageRepository.saveAndFlush(voyage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voyage using partial update
        Voyage partialUpdatedVoyage = new Voyage();
        partialUpdatedVoyage.setId(voyage.getId());

        restVoyageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoyage.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVoyage))
            )
            .andExpect(status().isOk());

        // Validate the Voyage in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVoyageUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVoyage, voyage), getPersistedVoyage(voyage));
    }

    @Test
    @Transactional
    void fullUpdateVoyageWithPatch() throws Exception {
        // Initialize the database
        insertedVoyage = voyageRepository.saveAndFlush(voyage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the voyage using partial update
        Voyage partialUpdatedVoyage = new Voyage();
        partialUpdatedVoyage.setId(voyage.getId());

        partialUpdatedVoyage.number(UPDATED_NUMBER);

        restVoyageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoyage.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVoyage))
            )
            .andExpect(status().isOk());

        // Validate the Voyage in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVoyageUpdatableFieldsEquals(partialUpdatedVoyage, getPersistedVoyage(partialUpdatedVoyage));
    }

    @Test
    @Transactional
    void patchNonExistingVoyage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voyage.setId(longCount.incrementAndGet());

        // Create the Voyage
        VoyageDTO voyageDTO = voyageMapper.toDto(voyage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voyageDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(voyageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoyage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voyage.setId(longCount.incrementAndGet());

        // Create the Voyage
        VoyageDTO voyageDTO = voyageMapper.toDto(voyage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(voyageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Voyage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoyage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        voyage.setId(longCount.incrementAndGet());

        // Create the Voyage
        VoyageDTO voyageDTO = voyageMapper.toDto(voyage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoyageMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(voyageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Voyage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoyage() throws Exception {
        // Initialize the database
        insertedVoyage = voyageRepository.saveAndFlush(voyage);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the voyage
        restVoyageMockMvc
            .perform(delete(ENTITY_API_URL_ID, voyage.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return voyageRepository.count();
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

    protected Voyage getPersistedVoyage(Voyage voyage) {
        return voyageRepository.findById(voyage.getId()).orElseThrow();
    }

    protected void assertPersistedVoyageToMatchAllProperties(Voyage expectedVoyage) {
        assertVoyageAllPropertiesEquals(expectedVoyage, getPersistedVoyage(expectedVoyage));
    }

    protected void assertPersistedVoyageToMatchUpdatableProperties(Voyage expectedVoyage) {
        assertVoyageAllUpdatablePropertiesEquals(expectedVoyage, getPersistedVoyage(expectedVoyage));
    }
}
