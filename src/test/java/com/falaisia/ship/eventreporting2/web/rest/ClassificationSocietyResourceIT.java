package com.falaisia.ship.eventreporting2.web.rest;

import static com.falaisia.ship.eventreporting2.domain.ClassificationSocietyAsserts.*;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.falaisia.ship.eventreporting2.IntegrationTest;
import com.falaisia.ship.eventreporting2.domain.ClassificationSociety;
import com.falaisia.ship.eventreporting2.repository.ClassificationSocietyRepository;
import com.falaisia.ship.eventreporting2.service.dto.ClassificationSocietyDTO;
import com.falaisia.ship.eventreporting2.service.mapper.ClassificationSocietyMapper;
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
 * Integration tests for the {@link ClassificationSocietyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClassificationSocietyResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/classification-societies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClassificationSocietyRepository classificationSocietyRepository;

    @Autowired
    private ClassificationSocietyMapper classificationSocietyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassificationSocietyMockMvc;

    private ClassificationSociety classificationSociety;

    private ClassificationSociety insertedClassificationSociety;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassificationSociety createEntity() {
        return new ClassificationSociety().code(DEFAULT_CODE).name(DEFAULT_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassificationSociety createUpdatedEntity() {
        return new ClassificationSociety().code(UPDATED_CODE).name(UPDATED_NAME);
    }

    @BeforeEach
    void initTest() {
        classificationSociety = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedClassificationSociety != null) {
            classificationSocietyRepository.delete(insertedClassificationSociety);
            insertedClassificationSociety = null;
        }
    }

    @Test
    @Transactional
    void createClassificationSociety() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ClassificationSociety
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);
        var returnedClassificationSocietyDTO = om.readValue(
            restClassificationSocietyMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(classificationSocietyDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ClassificationSocietyDTO.class
        );

        // Validate the ClassificationSociety in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedClassificationSociety = classificationSocietyMapper.toEntity(returnedClassificationSocietyDTO);
        assertClassificationSocietyUpdatableFieldsEquals(
            returnedClassificationSociety,
            getPersistedClassificationSociety(returnedClassificationSociety)
        );

        insertedClassificationSociety = returnedClassificationSociety;
    }

    @Test
    @Transactional
    void createClassificationSocietyWithExistingId() throws Exception {
        // Create the ClassificationSociety with an existing ID
        classificationSociety.setId(1L);
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassificationSocietyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classificationSocietyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassificationSociety in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        classificationSociety.setCode(null);

        // Create the ClassificationSociety, which fails.
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);

        restClassificationSocietyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classificationSocietyDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        classificationSociety.setName(null);

        // Create the ClassificationSociety, which fails.
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);

        restClassificationSocietyMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classificationSocietyDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClassificationSocieties() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList
        restClassificationSocietyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classificationSociety.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getClassificationSociety() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get the classificationSociety
        restClassificationSocietyMockMvc
            .perform(get(ENTITY_API_URL_ID, classificationSociety.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classificationSociety.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getClassificationSocietiesByIdFiltering() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        Long id = classificationSociety.getId();

        defaultClassificationSocietyFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultClassificationSocietyFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultClassificationSocietyFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClassificationSocietiesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where code equals to
        defaultClassificationSocietyFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllClassificationSocietiesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where code in
        defaultClassificationSocietyFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllClassificationSocietiesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where code is not null
        defaultClassificationSocietyFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllClassificationSocietiesByCodeContainsSomething() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where code contains
        defaultClassificationSocietyFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllClassificationSocietiesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where code does not contain
        defaultClassificationSocietyFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllClassificationSocietiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where name equals to
        defaultClassificationSocietyFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClassificationSocietiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where name in
        defaultClassificationSocietyFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClassificationSocietiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where name is not null
        defaultClassificationSocietyFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllClassificationSocietiesByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where name contains
        defaultClassificationSocietyFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClassificationSocietiesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        // Get all the classificationSocietyList where name does not contain
        defaultClassificationSocietyFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    private void defaultClassificationSocietyFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultClassificationSocietyShouldBeFound(shouldBeFound);
        defaultClassificationSocietyShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClassificationSocietyShouldBeFound(String filter) throws Exception {
        restClassificationSocietyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classificationSociety.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restClassificationSocietyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClassificationSocietyShouldNotBeFound(String filter) throws Exception {
        restClassificationSocietyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClassificationSocietyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClassificationSociety() throws Exception {
        // Get the classificationSociety
        restClassificationSocietyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClassificationSociety() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classificationSociety
        ClassificationSociety updatedClassificationSociety = classificationSocietyRepository
            .findById(classificationSociety.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedClassificationSociety are not directly saved in db
        em.detach(updatedClassificationSociety);
        updatedClassificationSociety.code(UPDATED_CODE).name(UPDATED_NAME);
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(updatedClassificationSociety);

        restClassificationSocietyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classificationSocietyDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classificationSocietyDTO))
            )
            .andExpect(status().isOk());

        // Validate the ClassificationSociety in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedClassificationSocietyToMatchAllProperties(updatedClassificationSociety);
    }

    @Test
    @Transactional
    void putNonExistingClassificationSociety() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classificationSociety.setId(longCount.incrementAndGet());

        // Create the ClassificationSociety
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassificationSocietyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classificationSocietyDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classificationSocietyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassificationSociety in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClassificationSociety() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classificationSociety.setId(longCount.incrementAndGet());

        // Create the ClassificationSociety
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassificationSocietyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classificationSocietyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassificationSociety in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClassificationSociety() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classificationSociety.setId(longCount.incrementAndGet());

        // Create the ClassificationSociety
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassificationSocietyMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(classificationSocietyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClassificationSociety in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClassificationSocietyWithPatch() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classificationSociety using partial update
        ClassificationSociety partialUpdatedClassificationSociety = new ClassificationSociety();
        partialUpdatedClassificationSociety.setId(classificationSociety.getId());

        partialUpdatedClassificationSociety.code(UPDATED_CODE).name(UPDATED_NAME);

        restClassificationSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassificationSociety.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClassificationSociety))
            )
            .andExpect(status().isOk());

        // Validate the ClassificationSociety in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClassificationSocietyUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedClassificationSociety, classificationSociety),
            getPersistedClassificationSociety(classificationSociety)
        );
    }

    @Test
    @Transactional
    void fullUpdateClassificationSocietyWithPatch() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the classificationSociety using partial update
        ClassificationSociety partialUpdatedClassificationSociety = new ClassificationSociety();
        partialUpdatedClassificationSociety.setId(classificationSociety.getId());

        partialUpdatedClassificationSociety.code(UPDATED_CODE).name(UPDATED_NAME);

        restClassificationSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassificationSociety.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClassificationSociety))
            )
            .andExpect(status().isOk());

        // Validate the ClassificationSociety in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClassificationSocietyUpdatableFieldsEquals(
            partialUpdatedClassificationSociety,
            getPersistedClassificationSociety(partialUpdatedClassificationSociety)
        );
    }

    @Test
    @Transactional
    void patchNonExistingClassificationSociety() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classificationSociety.setId(longCount.incrementAndGet());

        // Create the ClassificationSociety
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassificationSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classificationSocietyDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(classificationSocietyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassificationSociety in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClassificationSociety() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classificationSociety.setId(longCount.incrementAndGet());

        // Create the ClassificationSociety
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassificationSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(classificationSocietyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassificationSociety in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClassificationSociety() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        classificationSociety.setId(longCount.incrementAndGet());

        // Create the ClassificationSociety
        ClassificationSocietyDTO classificationSocietyDTO = classificationSocietyMapper.toDto(classificationSociety);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassificationSocietyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(classificationSocietyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClassificationSociety in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClassificationSociety() throws Exception {
        // Initialize the database
        insertedClassificationSociety = classificationSocietyRepository.saveAndFlush(classificationSociety);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the classificationSociety
        restClassificationSocietyMockMvc
            .perform(delete(ENTITY_API_URL_ID, classificationSociety.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return classificationSocietyRepository.count();
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

    protected ClassificationSociety getPersistedClassificationSociety(ClassificationSociety classificationSociety) {
        return classificationSocietyRepository.findById(classificationSociety.getId()).orElseThrow();
    }

    protected void assertPersistedClassificationSocietyToMatchAllProperties(ClassificationSociety expectedClassificationSociety) {
        assertClassificationSocietyAllPropertiesEquals(
            expectedClassificationSociety,
            getPersistedClassificationSociety(expectedClassificationSociety)
        );
    }

    protected void assertPersistedClassificationSocietyToMatchUpdatableProperties(ClassificationSociety expectedClassificationSociety) {
        assertClassificationSocietyAllUpdatablePropertiesEquals(
            expectedClassificationSociety,
            getPersistedClassificationSociety(expectedClassificationSociety)
        );
    }
}
