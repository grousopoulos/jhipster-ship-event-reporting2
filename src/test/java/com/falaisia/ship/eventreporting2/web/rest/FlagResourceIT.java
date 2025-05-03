package com.falaisia.ship.eventreporting2.web.rest;

import static com.falaisia.ship.eventreporting2.domain.FlagAsserts.*;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.falaisia.ship.eventreporting2.IntegrationTest;
import com.falaisia.ship.eventreporting2.domain.Flag;
import com.falaisia.ship.eventreporting2.repository.FlagRepository;
import com.falaisia.ship.eventreporting2.service.dto.FlagDTO;
import com.falaisia.ship.eventreporting2.service.mapper.FlagMapper;
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
 * Integration tests for the {@link FlagResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FlagResourceIT {

    private static final String DEFAULT_CODE = "AA";
    private static final String UPDATED_CODE = "BB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/flags";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FlagRepository flagRepository;

    @Autowired
    private FlagMapper flagMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFlagMockMvc;

    private Flag flag;

    private Flag insertedFlag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Flag createEntity() {
        return new Flag().code(DEFAULT_CODE).name(DEFAULT_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Flag createUpdatedEntity() {
        return new Flag().code(UPDATED_CODE).name(UPDATED_NAME);
    }

    @BeforeEach
    void initTest() {
        flag = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFlag != null) {
            flagRepository.delete(insertedFlag);
            insertedFlag = null;
        }
    }

    @Test
    @Transactional
    void createFlag() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Flag
        FlagDTO flagDTO = flagMapper.toDto(flag);
        var returnedFlagDTO = om.readValue(
            restFlagMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(flagDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FlagDTO.class
        );

        // Validate the Flag in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFlag = flagMapper.toEntity(returnedFlagDTO);
        assertFlagUpdatableFieldsEquals(returnedFlag, getPersistedFlag(returnedFlag));

        insertedFlag = returnedFlag;
    }

    @Test
    @Transactional
    void createFlagWithExistingId() throws Exception {
        // Create the Flag with an existing ID
        flag.setId(1L);
        FlagDTO flagDTO = flagMapper.toDto(flag);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFlagMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(flagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Flag in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        flag.setCode(null);

        // Create the Flag, which fails.
        FlagDTO flagDTO = flagMapper.toDto(flag);

        restFlagMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(flagDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        flag.setName(null);

        // Create the Flag, which fails.
        FlagDTO flagDTO = flagMapper.toDto(flag);

        restFlagMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(flagDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFlags() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        // Get all the flagList
        restFlagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flag.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getFlag() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        // Get the flag
        restFlagMockMvc
            .perform(get(ENTITY_API_URL_ID, flag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(flag.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getFlagsByIdFiltering() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        Long id = flag.getId();

        defaultFlagFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFlagFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFlagFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFlagsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        // Get all the flagList where code equals to
        defaultFlagFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllFlagsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        // Get all the flagList where code in
        defaultFlagFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllFlagsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        // Get all the flagList where code is not null
        defaultFlagFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllFlagsByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        // Get all the flagList where code contains
        defaultFlagFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllFlagsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        // Get all the flagList where code does not contain
        defaultFlagFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllFlagsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        // Get all the flagList where name equals to
        defaultFlagFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFlagsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        // Get all the flagList where name in
        defaultFlagFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFlagsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        // Get all the flagList where name is not null
        defaultFlagFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllFlagsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        // Get all the flagList where name contains
        defaultFlagFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFlagsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        // Get all the flagList where name does not contain
        defaultFlagFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    private void defaultFlagFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFlagShouldBeFound(shouldBeFound);
        defaultFlagShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFlagShouldBeFound(String filter) throws Exception {
        restFlagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flag.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restFlagMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFlagShouldNotBeFound(String filter) throws Exception {
        restFlagMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFlagMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFlag() throws Exception {
        // Get the flag
        restFlagMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFlag() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the flag
        Flag updatedFlag = flagRepository.findById(flag.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFlag are not directly saved in db
        em.detach(updatedFlag);
        updatedFlag.code(UPDATED_CODE).name(UPDATED_NAME);
        FlagDTO flagDTO = flagMapper.toDto(updatedFlag);

        restFlagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, flagDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(flagDTO))
            )
            .andExpect(status().isOk());

        // Validate the Flag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFlagToMatchAllProperties(updatedFlag);
    }

    @Test
    @Transactional
    void putNonExistingFlag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        flag.setId(longCount.incrementAndGet());

        // Create the Flag
        FlagDTO flagDTO = flagMapper.toDto(flag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, flagDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(flagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFlag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        flag.setId(longCount.incrementAndGet());

        // Create the Flag
        FlagDTO flagDTO = flagMapper.toDto(flag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlagMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(flagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFlag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        flag.setId(longCount.incrementAndGet());

        // Create the Flag
        FlagDTO flagDTO = flagMapper.toDto(flag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlagMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(flagDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Flag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFlagWithPatch() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the flag using partial update
        Flag partialUpdatedFlag = new Flag();
        partialUpdatedFlag.setId(flag.getId());

        partialUpdatedFlag.name(UPDATED_NAME);

        restFlagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlag.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFlag))
            )
            .andExpect(status().isOk());

        // Validate the Flag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFlagUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFlag, flag), getPersistedFlag(flag));
    }

    @Test
    @Transactional
    void fullUpdateFlagWithPatch() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the flag using partial update
        Flag partialUpdatedFlag = new Flag();
        partialUpdatedFlag.setId(flag.getId());

        partialUpdatedFlag.code(UPDATED_CODE).name(UPDATED_NAME);

        restFlagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlag.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFlag))
            )
            .andExpect(status().isOk());

        // Validate the Flag in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFlagUpdatableFieldsEquals(partialUpdatedFlag, getPersistedFlag(partialUpdatedFlag));
    }

    @Test
    @Transactional
    void patchNonExistingFlag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        flag.setId(longCount.incrementAndGet());

        // Create the Flag
        FlagDTO flagDTO = flagMapper.toDto(flag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, flagDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(flagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFlag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        flag.setId(longCount.incrementAndGet());

        // Create the Flag
        FlagDTO flagDTO = flagMapper.toDto(flag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlagMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(flagDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFlag() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        flag.setId(longCount.incrementAndGet());

        // Create the Flag
        FlagDTO flagDTO = flagMapper.toDto(flag);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlagMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(flagDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Flag in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFlag() throws Exception {
        // Initialize the database
        insertedFlag = flagRepository.saveAndFlush(flag);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the flag
        restFlagMockMvc
            .perform(delete(ENTITY_API_URL_ID, flag.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return flagRepository.count();
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

    protected Flag getPersistedFlag(Flag flag) {
        return flagRepository.findById(flag.getId()).orElseThrow();
    }

    protected void assertPersistedFlagToMatchAllProperties(Flag expectedFlag) {
        assertFlagAllPropertiesEquals(expectedFlag, getPersistedFlag(expectedFlag));
    }

    protected void assertPersistedFlagToMatchUpdatableProperties(Flag expectedFlag) {
        assertFlagAllUpdatablePropertiesEquals(expectedFlag, getPersistedFlag(expectedFlag));
    }
}
