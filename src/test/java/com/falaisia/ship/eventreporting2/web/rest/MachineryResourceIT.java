package com.falaisia.ship.eventreporting2.web.rest;

import static com.falaisia.ship.eventreporting2.domain.MachineryAsserts.*;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.falaisia.ship.eventreporting2.IntegrationTest;
import com.falaisia.ship.eventreporting2.domain.Machinery;
import com.falaisia.ship.eventreporting2.domain.Ship;
import com.falaisia.ship.eventreporting2.repository.MachineryRepository;
import com.falaisia.ship.eventreporting2.service.dto.MachineryDTO;
import com.falaisia.ship.eventreporting2.service.mapper.MachineryMapper;
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
 * Integration tests for the {@link MachineryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MachineryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/machinery";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MachineryRepository machineryRepository;

    @Autowired
    private MachineryMapper machineryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMachineryMockMvc;

    private Machinery machinery;

    private Machinery insertedMachinery;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Machinery createEntity() {
        return new Machinery().name(DEFAULT_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Machinery createUpdatedEntity() {
        return new Machinery().name(UPDATED_NAME);
    }

    @BeforeEach
    void initTest() {
        machinery = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedMachinery != null) {
            machineryRepository.delete(insertedMachinery);
            insertedMachinery = null;
        }
    }

    @Test
    @Transactional
    void createMachinery() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Machinery
        MachineryDTO machineryDTO = machineryMapper.toDto(machinery);
        var returnedMachineryDTO = om.readValue(
            restMachineryMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(machineryDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MachineryDTO.class
        );

        // Validate the Machinery in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMachinery = machineryMapper.toEntity(returnedMachineryDTO);
        assertMachineryUpdatableFieldsEquals(returnedMachinery, getPersistedMachinery(returnedMachinery));

        insertedMachinery = returnedMachinery;
    }

    @Test
    @Transactional
    void createMachineryWithExistingId() throws Exception {
        // Create the Machinery with an existing ID
        machinery.setId(1L);
        MachineryDTO machineryDTO = machineryMapper.toDto(machinery);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMachineryMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(machineryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Machinery in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        machinery.setName(null);

        // Create the Machinery, which fails.
        MachineryDTO machineryDTO = machineryMapper.toDto(machinery);

        restMachineryMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(machineryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMachinery() throws Exception {
        // Initialize the database
        insertedMachinery = machineryRepository.saveAndFlush(machinery);

        // Get all the machineryList
        restMachineryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(machinery.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getMachinery() throws Exception {
        // Initialize the database
        insertedMachinery = machineryRepository.saveAndFlush(machinery);

        // Get the machinery
        restMachineryMockMvc
            .perform(get(ENTITY_API_URL_ID, machinery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(machinery.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getMachineryByIdFiltering() throws Exception {
        // Initialize the database
        insertedMachinery = machineryRepository.saveAndFlush(machinery);

        Long id = machinery.getId();

        defaultMachineryFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultMachineryFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultMachineryFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMachineryByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMachinery = machineryRepository.saveAndFlush(machinery);

        // Get all the machineryList where name equals to
        defaultMachineryFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMachineryByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMachinery = machineryRepository.saveAndFlush(machinery);

        // Get all the machineryList where name in
        defaultMachineryFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMachineryByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMachinery = machineryRepository.saveAndFlush(machinery);

        // Get all the machineryList where name is not null
        defaultMachineryFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllMachineryByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedMachinery = machineryRepository.saveAndFlush(machinery);

        // Get all the machineryList where name contains
        defaultMachineryFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMachineryByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedMachinery = machineryRepository.saveAndFlush(machinery);

        // Get all the machineryList where name does not contain
        defaultMachineryFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllMachineryByShipIsEqualToSomething() throws Exception {
        Ship ship;
        if (TestUtil.findAll(em, Ship.class).isEmpty()) {
            machineryRepository.saveAndFlush(machinery);
            ship = ShipResourceIT.createEntity();
        } else {
            ship = TestUtil.findAll(em, Ship.class).get(0);
        }
        em.persist(ship);
        em.flush();
        machinery.setShip(ship);
        machineryRepository.saveAndFlush(machinery);
        Long shipId = ship.getId();
        // Get all the machineryList where ship equals to shipId
        defaultMachineryShouldBeFound("shipId.equals=" + shipId);

        // Get all the machineryList where ship equals to (shipId + 1)
        defaultMachineryShouldNotBeFound("shipId.equals=" + (shipId + 1));
    }

    private void defaultMachineryFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultMachineryShouldBeFound(shouldBeFound);
        defaultMachineryShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMachineryShouldBeFound(String filter) throws Exception {
        restMachineryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(machinery.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restMachineryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMachineryShouldNotBeFound(String filter) throws Exception {
        restMachineryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMachineryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMachinery() throws Exception {
        // Get the machinery
        restMachineryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMachinery() throws Exception {
        // Initialize the database
        insertedMachinery = machineryRepository.saveAndFlush(machinery);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the machinery
        Machinery updatedMachinery = machineryRepository.findById(machinery.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMachinery are not directly saved in db
        em.detach(updatedMachinery);
        updatedMachinery.name(UPDATED_NAME);
        MachineryDTO machineryDTO = machineryMapper.toDto(updatedMachinery);

        restMachineryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, machineryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Machinery in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMachineryToMatchAllProperties(updatedMachinery);
    }

    @Test
    @Transactional
    void putNonExistingMachinery() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machinery.setId(longCount.incrementAndGet());

        // Create the Machinery
        MachineryDTO machineryDTO = machineryMapper.toDto(machinery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMachineryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, machineryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Machinery in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMachinery() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machinery.setId(longCount.incrementAndGet());

        // Create the Machinery
        MachineryDTO machineryDTO = machineryMapper.toDto(machinery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Machinery in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMachinery() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machinery.setId(longCount.incrementAndGet());

        // Create the Machinery
        MachineryDTO machineryDTO = machineryMapper.toDto(machinery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineryMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(machineryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Machinery in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMachineryWithPatch() throws Exception {
        // Initialize the database
        insertedMachinery = machineryRepository.saveAndFlush(machinery);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the machinery using partial update
        Machinery partialUpdatedMachinery = new Machinery();
        partialUpdatedMachinery.setId(machinery.getId());

        restMachineryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMachinery.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMachinery))
            )
            .andExpect(status().isOk());

        // Validate the Machinery in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMachineryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMachinery, machinery),
            getPersistedMachinery(machinery)
        );
    }

    @Test
    @Transactional
    void fullUpdateMachineryWithPatch() throws Exception {
        // Initialize the database
        insertedMachinery = machineryRepository.saveAndFlush(machinery);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the machinery using partial update
        Machinery partialUpdatedMachinery = new Machinery();
        partialUpdatedMachinery.setId(machinery.getId());

        partialUpdatedMachinery.name(UPDATED_NAME);

        restMachineryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMachinery.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMachinery))
            )
            .andExpect(status().isOk());

        // Validate the Machinery in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMachineryUpdatableFieldsEquals(partialUpdatedMachinery, getPersistedMachinery(partialUpdatedMachinery));
    }

    @Test
    @Transactional
    void patchNonExistingMachinery() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machinery.setId(longCount.incrementAndGet());

        // Create the Machinery
        MachineryDTO machineryDTO = machineryMapper.toDto(machinery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMachineryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, machineryDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(machineryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Machinery in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMachinery() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machinery.setId(longCount.incrementAndGet());

        // Create the Machinery
        MachineryDTO machineryDTO = machineryMapper.toDto(machinery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(machineryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Machinery in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMachinery() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machinery.setId(longCount.incrementAndGet());

        // Create the Machinery
        MachineryDTO machineryDTO = machineryMapper.toDto(machinery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineryMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(machineryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Machinery in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMachinery() throws Exception {
        // Initialize the database
        insertedMachinery = machineryRepository.saveAndFlush(machinery);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the machinery
        restMachineryMockMvc
            .perform(delete(ENTITY_API_URL_ID, machinery.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return machineryRepository.count();
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

    protected Machinery getPersistedMachinery(Machinery machinery) {
        return machineryRepository.findById(machinery.getId()).orElseThrow();
    }

    protected void assertPersistedMachineryToMatchAllProperties(Machinery expectedMachinery) {
        assertMachineryAllPropertiesEquals(expectedMachinery, getPersistedMachinery(expectedMachinery));
    }

    protected void assertPersistedMachineryToMatchUpdatableProperties(Machinery expectedMachinery) {
        assertMachineryAllUpdatablePropertiesEquals(expectedMachinery, getPersistedMachinery(expectedMachinery));
    }
}
