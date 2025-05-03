package com.falaisia.ship.eventreporting2.web.rest;

import static com.falaisia.ship.eventreporting2.domain.FuelTypeAsserts.*;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.createUpdateProxyForBean;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.falaisia.ship.eventreporting2.IntegrationTest;
import com.falaisia.ship.eventreporting2.domain.FuelType;
import com.falaisia.ship.eventreporting2.domain.enumeration.FuelTypeCode;
import com.falaisia.ship.eventreporting2.repository.FuelTypeRepository;
import com.falaisia.ship.eventreporting2.service.dto.FuelTypeDTO;
import com.falaisia.ship.eventreporting2.service.mapper.FuelTypeMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link FuelTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuelTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final FuelTypeCode DEFAULT_FUEL_TYPE_CODE = FuelTypeCode.MDO;
    private static final FuelTypeCode UPDATED_FUEL_TYPE_CODE = FuelTypeCode.ETHANOL;

    private static final BigDecimal DEFAULT_CARBON_FACTORY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CARBON_FACTORY = new BigDecimal(2);
    private static final BigDecimal SMALLER_CARBON_FACTORY = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/fuel-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FuelTypeRepository fuelTypeRepository;

    @Autowired
    private FuelTypeMapper fuelTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuelTypeMockMvc;

    private FuelType fuelType;

    private FuelType insertedFuelType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelType createEntity() {
        return new FuelType().name(DEFAULT_NAME).fuelTypeCode(DEFAULT_FUEL_TYPE_CODE).carbonFactory(DEFAULT_CARBON_FACTORY);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelType createUpdatedEntity() {
        return new FuelType().name(UPDATED_NAME).fuelTypeCode(UPDATED_FUEL_TYPE_CODE).carbonFactory(UPDATED_CARBON_FACTORY);
    }

    @BeforeEach
    void initTest() {
        fuelType = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFuelType != null) {
            fuelTypeRepository.delete(insertedFuelType);
            insertedFuelType = null;
        }
    }

    @Test
    @Transactional
    void createFuelType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FuelType
        FuelTypeDTO fuelTypeDTO = fuelTypeMapper.toDto(fuelType);
        var returnedFuelTypeDTO = om.readValue(
            restFuelTypeMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTypeDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FuelTypeDTO.class
        );

        // Validate the FuelType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFuelType = fuelTypeMapper.toEntity(returnedFuelTypeDTO);
        assertFuelTypeUpdatableFieldsEquals(returnedFuelType, getPersistedFuelType(returnedFuelType));

        insertedFuelType = returnedFuelType;
    }

    @Test
    @Transactional
    void createFuelTypeWithExistingId() throws Exception {
        // Create the FuelType with an existing ID
        fuelType.setId(1L);
        FuelTypeDTO fuelTypeDTO = fuelTypeMapper.toDto(fuelType);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuelTypeMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FuelType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fuelType.setName(null);

        // Create the FuelType, which fails.
        FuelTypeDTO fuelTypeDTO = fuelTypeMapper.toDto(fuelType);

        restFuelTypeMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTypeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFuelTypeCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fuelType.setFuelTypeCode(null);

        // Create the FuelType, which fails.
        FuelTypeDTO fuelTypeDTO = fuelTypeMapper.toDto(fuelType);

        restFuelTypeMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTypeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCarbonFactoryIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fuelType.setCarbonFactory(null);

        // Create the FuelType, which fails.
        FuelTypeDTO fuelTypeDTO = fuelTypeMapper.toDto(fuelType);

        restFuelTypeMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTypeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFuelTypes() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList
        restFuelTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fuelTypeCode").value(hasItem(DEFAULT_FUEL_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].carbonFactory").value(hasItem(sameNumber(DEFAULT_CARBON_FACTORY))));
    }

    @Test
    @Transactional
    void getFuelType() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get the fuelType
        restFuelTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, fuelType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fuelType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fuelTypeCode").value(DEFAULT_FUEL_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.carbonFactory").value(sameNumber(DEFAULT_CARBON_FACTORY)));
    }

    @Test
    @Transactional
    void getFuelTypesByIdFiltering() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        Long id = fuelType.getId();

        defaultFuelTypeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFuelTypeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFuelTypeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFuelTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where name equals to
        defaultFuelTypeFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFuelTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where name in
        defaultFuelTypeFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFuelTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where name is not null
        defaultFuelTypeFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where name contains
        defaultFuelTypeFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllFuelTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where name does not contain
        defaultFuelTypeFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllFuelTypesByFuelTypeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where fuelTypeCode equals to
        defaultFuelTypeFiltering("fuelTypeCode.equals=" + DEFAULT_FUEL_TYPE_CODE, "fuelTypeCode.equals=" + UPDATED_FUEL_TYPE_CODE);
    }

    @Test
    @Transactional
    void getAllFuelTypesByFuelTypeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where fuelTypeCode in
        defaultFuelTypeFiltering(
            "fuelTypeCode.in=" + DEFAULT_FUEL_TYPE_CODE + "," + UPDATED_FUEL_TYPE_CODE,
            "fuelTypeCode.in=" + UPDATED_FUEL_TYPE_CODE
        );
    }

    @Test
    @Transactional
    void getAllFuelTypesByFuelTypeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where fuelTypeCode is not null
        defaultFuelTypeFiltering("fuelTypeCode.specified=true", "fuelTypeCode.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelTypesByCarbonFactoryIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where carbonFactory equals to
        defaultFuelTypeFiltering("carbonFactory.equals=" + DEFAULT_CARBON_FACTORY, "carbonFactory.equals=" + UPDATED_CARBON_FACTORY);
    }

    @Test
    @Transactional
    void getAllFuelTypesByCarbonFactoryIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where carbonFactory in
        defaultFuelTypeFiltering(
            "carbonFactory.in=" + DEFAULT_CARBON_FACTORY + "," + UPDATED_CARBON_FACTORY,
            "carbonFactory.in=" + UPDATED_CARBON_FACTORY
        );
    }

    @Test
    @Transactional
    void getAllFuelTypesByCarbonFactoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where carbonFactory is not null
        defaultFuelTypeFiltering("carbonFactory.specified=true", "carbonFactory.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelTypesByCarbonFactoryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where carbonFactory is greater than or equal to
        defaultFuelTypeFiltering(
            "carbonFactory.greaterThanOrEqual=" + DEFAULT_CARBON_FACTORY,
            "carbonFactory.greaterThanOrEqual=" + UPDATED_CARBON_FACTORY
        );
    }

    @Test
    @Transactional
    void getAllFuelTypesByCarbonFactoryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where carbonFactory is less than or equal to
        defaultFuelTypeFiltering(
            "carbonFactory.lessThanOrEqual=" + DEFAULT_CARBON_FACTORY,
            "carbonFactory.lessThanOrEqual=" + SMALLER_CARBON_FACTORY
        );
    }

    @Test
    @Transactional
    void getAllFuelTypesByCarbonFactoryIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where carbonFactory is less than
        defaultFuelTypeFiltering("carbonFactory.lessThan=" + UPDATED_CARBON_FACTORY, "carbonFactory.lessThan=" + DEFAULT_CARBON_FACTORY);
    }

    @Test
    @Transactional
    void getAllFuelTypesByCarbonFactoryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        // Get all the fuelTypeList where carbonFactory is greater than
        defaultFuelTypeFiltering(
            "carbonFactory.greaterThan=" + SMALLER_CARBON_FACTORY,
            "carbonFactory.greaterThan=" + DEFAULT_CARBON_FACTORY
        );
    }

    private void defaultFuelTypeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFuelTypeShouldBeFound(shouldBeFound);
        defaultFuelTypeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFuelTypeShouldBeFound(String filter) throws Exception {
        restFuelTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fuelTypeCode").value(hasItem(DEFAULT_FUEL_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].carbonFactory").value(hasItem(sameNumber(DEFAULT_CARBON_FACTORY))));

        // Check, that the count call also returns 1
        restFuelTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFuelTypeShouldNotBeFound(String filter) throws Exception {
        restFuelTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFuelTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFuelType() throws Exception {
        // Get the fuelType
        restFuelTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuelType() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelType
        FuelType updatedFuelType = fuelTypeRepository.findById(fuelType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFuelType are not directly saved in db
        em.detach(updatedFuelType);
        updatedFuelType.name(UPDATED_NAME).fuelTypeCode(UPDATED_FUEL_TYPE_CODE).carbonFactory(UPDATED_CARBON_FACTORY);
        FuelTypeDTO fuelTypeDTO = fuelTypeMapper.toDto(updatedFuelType);

        restFuelTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelTypeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the FuelType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFuelTypeToMatchAllProperties(updatedFuelType);
    }

    @Test
    @Transactional
    void putNonExistingFuelType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelType.setId(longCount.incrementAndGet());

        // Create the FuelType
        FuelTypeDTO fuelTypeDTO = fuelTypeMapper.toDto(fuelType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelTypeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuelType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelType.setId(longCount.incrementAndGet());

        // Create the FuelType
        FuelTypeDTO fuelTypeDTO = fuelTypeMapper.toDto(fuelType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuelType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelType.setId(longCount.incrementAndGet());

        // Create the FuelType
        FuelTypeDTO fuelTypeDTO = fuelTypeMapper.toDto(fuelType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuelTypeWithPatch() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelType using partial update
        FuelType partialUpdatedFuelType = new FuelType();
        partialUpdatedFuelType.setId(fuelType.getId());

        partialUpdatedFuelType.name(UPDATED_NAME).fuelTypeCode(UPDATED_FUEL_TYPE_CODE);

        restFuelTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuelType))
            )
            .andExpect(status().isOk());

        // Validate the FuelType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuelTypeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFuelType, fuelType), getPersistedFuelType(fuelType));
    }

    @Test
    @Transactional
    void fullUpdateFuelTypeWithPatch() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelType using partial update
        FuelType partialUpdatedFuelType = new FuelType();
        partialUpdatedFuelType.setId(fuelType.getId());

        partialUpdatedFuelType.name(UPDATED_NAME).fuelTypeCode(UPDATED_FUEL_TYPE_CODE).carbonFactory(UPDATED_CARBON_FACTORY);

        restFuelTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuelType))
            )
            .andExpect(status().isOk());

        // Validate the FuelType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuelTypeUpdatableFieldsEquals(partialUpdatedFuelType, getPersistedFuelType(partialUpdatedFuelType));
    }

    @Test
    @Transactional
    void patchNonExistingFuelType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelType.setId(longCount.incrementAndGet());

        // Create the FuelType
        FuelTypeDTO fuelTypeDTO = fuelTypeMapper.toDto(fuelType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fuelTypeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuelType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelType.setId(longCount.incrementAndGet());

        // Create the FuelType
        FuelTypeDTO fuelTypeDTO = fuelTypeMapper.toDto(fuelType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuelType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelType.setId(longCount.incrementAndGet());

        // Create the FuelType
        FuelTypeDTO fuelTypeDTO = fuelTypeMapper.toDto(fuelType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fuelTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuelType() throws Exception {
        // Initialize the database
        insertedFuelType = fuelTypeRepository.saveAndFlush(fuelType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fuelType
        restFuelTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, fuelType.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fuelTypeRepository.count();
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

    protected FuelType getPersistedFuelType(FuelType fuelType) {
        return fuelTypeRepository.findById(fuelType.getId()).orElseThrow();
    }

    protected void assertPersistedFuelTypeToMatchAllProperties(FuelType expectedFuelType) {
        assertFuelTypeAllPropertiesEquals(expectedFuelType, getPersistedFuelType(expectedFuelType));
    }

    protected void assertPersistedFuelTypeToMatchUpdatableProperties(FuelType expectedFuelType) {
        assertFuelTypeAllUpdatablePropertiesEquals(expectedFuelType, getPersistedFuelType(expectedFuelType));
    }
}
