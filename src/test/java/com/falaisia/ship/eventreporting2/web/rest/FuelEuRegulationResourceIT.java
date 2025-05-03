package com.falaisia.ship.eventreporting2.web.rest;

import static com.falaisia.ship.eventreporting2.domain.FuelEuRegulationAsserts.*;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.createUpdateProxyForBean;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.falaisia.ship.eventreporting2.IntegrationTest;
import com.falaisia.ship.eventreporting2.domain.FuelEuRegulation;
import com.falaisia.ship.eventreporting2.repository.FuelEuRegulationRepository;
import com.falaisia.ship.eventreporting2.service.dto.FuelEuRegulationDTO;
import com.falaisia.ship.eventreporting2.service.mapper.FuelEuRegulationMapper;
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
 * Integration tests for the {@link FuelEuRegulationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuelEuRegulationResourceIT {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final Integer DEFAULT_CO_2_GWP = 1;
    private static final Integer UPDATED_CO_2_GWP = 2;
    private static final Integer SMALLER_CO_2_GWP = 1 - 1;

    private static final Integer DEFAULT_METHANE_GWP = 1;
    private static final Integer UPDATED_METHANE_GWP = 2;
    private static final Integer SMALLER_METHANE_GWP = 1 - 1;

    private static final Integer DEFAULT_NITROUS_GWP = 1;
    private static final Integer UPDATED_NITROUS_GWP = 2;
    private static final Integer SMALLER_NITROUS_GWP = 1 - 1;

    private static final BigDecimal DEFAULT_TARGET_INTENSITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_TARGET_INTENSITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_TARGET_INTENSITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_BASELINE_INTENSITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASELINE_INTENSITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_BASELINE_INTENSITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_REDUCTION_FACTOR_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_REDUCTION_FACTOR_PERCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_REDUCTION_FACTOR_PERCENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VLSFO_ENERGY_CONTENT_PER_TON_MJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_VLSFO_ENERGY_CONTENT_PER_TON_MJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_VLSFO_ENERGY_CONTENT_PER_TON_MJ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VLSFO_PENALTY_EUR_PER_TON = new BigDecimal(1);
    private static final BigDecimal UPDATED_VLSFO_PENALTY_EUR_PER_TON = new BigDecimal(2);
    private static final BigDecimal SMALLER_VLSFO_PENALTY_EUR_PER_TON = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ENERGY_ALLOWANCE_MULTIPLIER = new BigDecimal(1);
    private static final BigDecimal UPDATED_ENERGY_ALLOWANCE_MULTIPLIER = new BigDecimal(2);
    private static final BigDecimal SMALLER_ENERGY_ALLOWANCE_MULTIPLIER = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_NON_BIO_FUEL_REWARD_FACTOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_NON_BIO_FUEL_REWARD_FACTOR = new BigDecimal(2);
    private static final BigDecimal SMALLER_NON_BIO_FUEL_REWARD_FACTOR = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/fuel-eu-regulations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FuelEuRegulationRepository fuelEuRegulationRepository;

    @Autowired
    private FuelEuRegulationMapper fuelEuRegulationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuelEuRegulationMockMvc;

    private FuelEuRegulation fuelEuRegulation;

    private FuelEuRegulation insertedFuelEuRegulation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelEuRegulation createEntity() {
        return new FuelEuRegulation()
            .year(DEFAULT_YEAR)
            .co2Gwp(DEFAULT_CO_2_GWP)
            .methaneGwp(DEFAULT_METHANE_GWP)
            .nitrousGwp(DEFAULT_NITROUS_GWP)
            .targetIntensity(DEFAULT_TARGET_INTENSITY)
            .baselineIntensity(DEFAULT_BASELINE_INTENSITY)
            .reductionFactorPercent(DEFAULT_REDUCTION_FACTOR_PERCENT)
            .vlsfoEnergyContentPerTonMj(DEFAULT_VLSFO_ENERGY_CONTENT_PER_TON_MJ)
            .vlsfoPenaltyEurPerTon(DEFAULT_VLSFO_PENALTY_EUR_PER_TON)
            .energyAllowanceMultiplier(DEFAULT_ENERGY_ALLOWANCE_MULTIPLIER)
            .nonBioFuelRewardFactor(DEFAULT_NON_BIO_FUEL_REWARD_FACTOR);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelEuRegulation createUpdatedEntity() {
        return new FuelEuRegulation()
            .year(UPDATED_YEAR)
            .co2Gwp(UPDATED_CO_2_GWP)
            .methaneGwp(UPDATED_METHANE_GWP)
            .nitrousGwp(UPDATED_NITROUS_GWP)
            .targetIntensity(UPDATED_TARGET_INTENSITY)
            .baselineIntensity(UPDATED_BASELINE_INTENSITY)
            .reductionFactorPercent(UPDATED_REDUCTION_FACTOR_PERCENT)
            .vlsfoEnergyContentPerTonMj(UPDATED_VLSFO_ENERGY_CONTENT_PER_TON_MJ)
            .vlsfoPenaltyEurPerTon(UPDATED_VLSFO_PENALTY_EUR_PER_TON)
            .energyAllowanceMultiplier(UPDATED_ENERGY_ALLOWANCE_MULTIPLIER)
            .nonBioFuelRewardFactor(UPDATED_NON_BIO_FUEL_REWARD_FACTOR);
    }

    @BeforeEach
    void initTest() {
        fuelEuRegulation = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFuelEuRegulation != null) {
            fuelEuRegulationRepository.delete(insertedFuelEuRegulation);
            insertedFuelEuRegulation = null;
        }
    }

    @Test
    @Transactional
    void createFuelEuRegulation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FuelEuRegulation
        FuelEuRegulationDTO fuelEuRegulationDTO = fuelEuRegulationMapper.toDto(fuelEuRegulation);
        var returnedFuelEuRegulationDTO = om.readValue(
            restFuelEuRegulationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(fuelEuRegulationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FuelEuRegulationDTO.class
        );

        // Validate the FuelEuRegulation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFuelEuRegulation = fuelEuRegulationMapper.toEntity(returnedFuelEuRegulationDTO);
        assertFuelEuRegulationUpdatableFieldsEquals(returnedFuelEuRegulation, getPersistedFuelEuRegulation(returnedFuelEuRegulation));

        insertedFuelEuRegulation = returnedFuelEuRegulation;
    }

    @Test
    @Transactional
    void createFuelEuRegulationWithExistingId() throws Exception {
        // Create the FuelEuRegulation with an existing ID
        fuelEuRegulation.setId(1L);
        FuelEuRegulationDTO fuelEuRegulationDTO = fuelEuRegulationMapper.toDto(fuelEuRegulation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuelEuRegulationMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelEuRegulationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelEuRegulation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulations() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList
        restFuelEuRegulationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelEuRegulation.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].co2Gwp").value(hasItem(DEFAULT_CO_2_GWP)))
            .andExpect(jsonPath("$.[*].methaneGwp").value(hasItem(DEFAULT_METHANE_GWP)))
            .andExpect(jsonPath("$.[*].nitrousGwp").value(hasItem(DEFAULT_NITROUS_GWP)))
            .andExpect(jsonPath("$.[*].targetIntensity").value(hasItem(sameNumber(DEFAULT_TARGET_INTENSITY))))
            .andExpect(jsonPath("$.[*].baselineIntensity").value(hasItem(sameNumber(DEFAULT_BASELINE_INTENSITY))))
            .andExpect(jsonPath("$.[*].reductionFactorPercent").value(hasItem(sameNumber(DEFAULT_REDUCTION_FACTOR_PERCENT))))
            .andExpect(jsonPath("$.[*].vlsfoEnergyContentPerTonMj").value(hasItem(sameNumber(DEFAULT_VLSFO_ENERGY_CONTENT_PER_TON_MJ))))
            .andExpect(jsonPath("$.[*].vlsfoPenaltyEurPerTon").value(hasItem(sameNumber(DEFAULT_VLSFO_PENALTY_EUR_PER_TON))))
            .andExpect(jsonPath("$.[*].energyAllowanceMultiplier").value(hasItem(sameNumber(DEFAULT_ENERGY_ALLOWANCE_MULTIPLIER))))
            .andExpect(jsonPath("$.[*].nonBioFuelRewardFactor").value(hasItem(sameNumber(DEFAULT_NON_BIO_FUEL_REWARD_FACTOR))));
    }

    @Test
    @Transactional
    void getFuelEuRegulation() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get the fuelEuRegulation
        restFuelEuRegulationMockMvc
            .perform(get(ENTITY_API_URL_ID, fuelEuRegulation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fuelEuRegulation.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.co2Gwp").value(DEFAULT_CO_2_GWP))
            .andExpect(jsonPath("$.methaneGwp").value(DEFAULT_METHANE_GWP))
            .andExpect(jsonPath("$.nitrousGwp").value(DEFAULT_NITROUS_GWP))
            .andExpect(jsonPath("$.targetIntensity").value(sameNumber(DEFAULT_TARGET_INTENSITY)))
            .andExpect(jsonPath("$.baselineIntensity").value(sameNumber(DEFAULT_BASELINE_INTENSITY)))
            .andExpect(jsonPath("$.reductionFactorPercent").value(sameNumber(DEFAULT_REDUCTION_FACTOR_PERCENT)))
            .andExpect(jsonPath("$.vlsfoEnergyContentPerTonMj").value(sameNumber(DEFAULT_VLSFO_ENERGY_CONTENT_PER_TON_MJ)))
            .andExpect(jsonPath("$.vlsfoPenaltyEurPerTon").value(sameNumber(DEFAULT_VLSFO_PENALTY_EUR_PER_TON)))
            .andExpect(jsonPath("$.energyAllowanceMultiplier").value(sameNumber(DEFAULT_ENERGY_ALLOWANCE_MULTIPLIER)))
            .andExpect(jsonPath("$.nonBioFuelRewardFactor").value(sameNumber(DEFAULT_NON_BIO_FUEL_REWARD_FACTOR)));
    }

    @Test
    @Transactional
    void getFuelEuRegulationsByIdFiltering() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        Long id = fuelEuRegulation.getId();

        defaultFuelEuRegulationFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFuelEuRegulationFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFuelEuRegulationFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where year equals to
        defaultFuelEuRegulationFiltering("year.equals=" + DEFAULT_YEAR, "year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where year in
        defaultFuelEuRegulationFiltering("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR, "year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where year is not null
        defaultFuelEuRegulationFiltering("year.specified=true", "year.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where year is greater than or equal to
        defaultFuelEuRegulationFiltering("year.greaterThanOrEqual=" + DEFAULT_YEAR, "year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where year is less than or equal to
        defaultFuelEuRegulationFiltering("year.lessThanOrEqual=" + DEFAULT_YEAR, "year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where year is less than
        defaultFuelEuRegulationFiltering("year.lessThan=" + UPDATED_YEAR, "year.lessThan=" + DEFAULT_YEAR);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where year is greater than
        defaultFuelEuRegulationFiltering("year.greaterThan=" + SMALLER_YEAR, "year.greaterThan=" + DEFAULT_YEAR);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByCo2GwpIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where co2Gwp equals to
        defaultFuelEuRegulationFiltering("co2Gwp.equals=" + DEFAULT_CO_2_GWP, "co2Gwp.equals=" + UPDATED_CO_2_GWP);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByCo2GwpIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where co2Gwp in
        defaultFuelEuRegulationFiltering("co2Gwp.in=" + DEFAULT_CO_2_GWP + "," + UPDATED_CO_2_GWP, "co2Gwp.in=" + UPDATED_CO_2_GWP);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByCo2GwpIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where co2Gwp is not null
        defaultFuelEuRegulationFiltering("co2Gwp.specified=true", "co2Gwp.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByCo2GwpIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where co2Gwp is greater than or equal to
        defaultFuelEuRegulationFiltering("co2Gwp.greaterThanOrEqual=" + DEFAULT_CO_2_GWP, "co2Gwp.greaterThanOrEqual=" + UPDATED_CO_2_GWP);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByCo2GwpIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where co2Gwp is less than or equal to
        defaultFuelEuRegulationFiltering("co2Gwp.lessThanOrEqual=" + DEFAULT_CO_2_GWP, "co2Gwp.lessThanOrEqual=" + SMALLER_CO_2_GWP);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByCo2GwpIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where co2Gwp is less than
        defaultFuelEuRegulationFiltering("co2Gwp.lessThan=" + UPDATED_CO_2_GWP, "co2Gwp.lessThan=" + DEFAULT_CO_2_GWP);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByCo2GwpIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where co2Gwp is greater than
        defaultFuelEuRegulationFiltering("co2Gwp.greaterThan=" + SMALLER_CO_2_GWP, "co2Gwp.greaterThan=" + DEFAULT_CO_2_GWP);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByMethaneGwpIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where methaneGwp equals to
        defaultFuelEuRegulationFiltering("methaneGwp.equals=" + DEFAULT_METHANE_GWP, "methaneGwp.equals=" + UPDATED_METHANE_GWP);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByMethaneGwpIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where methaneGwp in
        defaultFuelEuRegulationFiltering(
            "methaneGwp.in=" + DEFAULT_METHANE_GWP + "," + UPDATED_METHANE_GWP,
            "methaneGwp.in=" + UPDATED_METHANE_GWP
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByMethaneGwpIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where methaneGwp is not null
        defaultFuelEuRegulationFiltering("methaneGwp.specified=true", "methaneGwp.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByMethaneGwpIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where methaneGwp is greater than or equal to
        defaultFuelEuRegulationFiltering(
            "methaneGwp.greaterThanOrEqual=" + DEFAULT_METHANE_GWP,
            "methaneGwp.greaterThanOrEqual=" + UPDATED_METHANE_GWP
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByMethaneGwpIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where methaneGwp is less than or equal to
        defaultFuelEuRegulationFiltering(
            "methaneGwp.lessThanOrEqual=" + DEFAULT_METHANE_GWP,
            "methaneGwp.lessThanOrEqual=" + SMALLER_METHANE_GWP
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByMethaneGwpIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where methaneGwp is less than
        defaultFuelEuRegulationFiltering("methaneGwp.lessThan=" + UPDATED_METHANE_GWP, "methaneGwp.lessThan=" + DEFAULT_METHANE_GWP);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByMethaneGwpIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where methaneGwp is greater than
        defaultFuelEuRegulationFiltering("methaneGwp.greaterThan=" + SMALLER_METHANE_GWP, "methaneGwp.greaterThan=" + DEFAULT_METHANE_GWP);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByNitrousGwpIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where nitrousGwp equals to
        defaultFuelEuRegulationFiltering("nitrousGwp.equals=" + DEFAULT_NITROUS_GWP, "nitrousGwp.equals=" + UPDATED_NITROUS_GWP);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByNitrousGwpIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where nitrousGwp in
        defaultFuelEuRegulationFiltering(
            "nitrousGwp.in=" + DEFAULT_NITROUS_GWP + "," + UPDATED_NITROUS_GWP,
            "nitrousGwp.in=" + UPDATED_NITROUS_GWP
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByNitrousGwpIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where nitrousGwp is not null
        defaultFuelEuRegulationFiltering("nitrousGwp.specified=true", "nitrousGwp.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByNitrousGwpIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where nitrousGwp is greater than or equal to
        defaultFuelEuRegulationFiltering(
            "nitrousGwp.greaterThanOrEqual=" + DEFAULT_NITROUS_GWP,
            "nitrousGwp.greaterThanOrEqual=" + UPDATED_NITROUS_GWP
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByNitrousGwpIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where nitrousGwp is less than or equal to
        defaultFuelEuRegulationFiltering(
            "nitrousGwp.lessThanOrEqual=" + DEFAULT_NITROUS_GWP,
            "nitrousGwp.lessThanOrEqual=" + SMALLER_NITROUS_GWP
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByNitrousGwpIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where nitrousGwp is less than
        defaultFuelEuRegulationFiltering("nitrousGwp.lessThan=" + UPDATED_NITROUS_GWP, "nitrousGwp.lessThan=" + DEFAULT_NITROUS_GWP);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByNitrousGwpIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where nitrousGwp is greater than
        defaultFuelEuRegulationFiltering("nitrousGwp.greaterThan=" + SMALLER_NITROUS_GWP, "nitrousGwp.greaterThan=" + DEFAULT_NITROUS_GWP);
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByTargetIntensityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where targetIntensity equals to
        defaultFuelEuRegulationFiltering(
            "targetIntensity.equals=" + DEFAULT_TARGET_INTENSITY,
            "targetIntensity.equals=" + UPDATED_TARGET_INTENSITY
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByTargetIntensityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where targetIntensity in
        defaultFuelEuRegulationFiltering(
            "targetIntensity.in=" + DEFAULT_TARGET_INTENSITY + "," + UPDATED_TARGET_INTENSITY,
            "targetIntensity.in=" + UPDATED_TARGET_INTENSITY
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByTargetIntensityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where targetIntensity is not null
        defaultFuelEuRegulationFiltering("targetIntensity.specified=true", "targetIntensity.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByTargetIntensityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where targetIntensity is greater than or equal to
        defaultFuelEuRegulationFiltering(
            "targetIntensity.greaterThanOrEqual=" + DEFAULT_TARGET_INTENSITY,
            "targetIntensity.greaterThanOrEqual=" + UPDATED_TARGET_INTENSITY
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByTargetIntensityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where targetIntensity is less than or equal to
        defaultFuelEuRegulationFiltering(
            "targetIntensity.lessThanOrEqual=" + DEFAULT_TARGET_INTENSITY,
            "targetIntensity.lessThanOrEqual=" + SMALLER_TARGET_INTENSITY
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByTargetIntensityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where targetIntensity is less than
        defaultFuelEuRegulationFiltering(
            "targetIntensity.lessThan=" + UPDATED_TARGET_INTENSITY,
            "targetIntensity.lessThan=" + DEFAULT_TARGET_INTENSITY
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByTargetIntensityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where targetIntensity is greater than
        defaultFuelEuRegulationFiltering(
            "targetIntensity.greaterThan=" + SMALLER_TARGET_INTENSITY,
            "targetIntensity.greaterThan=" + DEFAULT_TARGET_INTENSITY
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByBaselineIntensityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where baselineIntensity equals to
        defaultFuelEuRegulationFiltering(
            "baselineIntensity.equals=" + DEFAULT_BASELINE_INTENSITY,
            "baselineIntensity.equals=" + UPDATED_BASELINE_INTENSITY
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByBaselineIntensityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where baselineIntensity in
        defaultFuelEuRegulationFiltering(
            "baselineIntensity.in=" + DEFAULT_BASELINE_INTENSITY + "," + UPDATED_BASELINE_INTENSITY,
            "baselineIntensity.in=" + UPDATED_BASELINE_INTENSITY
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByBaselineIntensityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where baselineIntensity is not null
        defaultFuelEuRegulationFiltering("baselineIntensity.specified=true", "baselineIntensity.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByBaselineIntensityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where baselineIntensity is greater than or equal to
        defaultFuelEuRegulationFiltering(
            "baselineIntensity.greaterThanOrEqual=" + DEFAULT_BASELINE_INTENSITY,
            "baselineIntensity.greaterThanOrEqual=" + UPDATED_BASELINE_INTENSITY
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByBaselineIntensityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where baselineIntensity is less than or equal to
        defaultFuelEuRegulationFiltering(
            "baselineIntensity.lessThanOrEqual=" + DEFAULT_BASELINE_INTENSITY,
            "baselineIntensity.lessThanOrEqual=" + SMALLER_BASELINE_INTENSITY
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByBaselineIntensityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where baselineIntensity is less than
        defaultFuelEuRegulationFiltering(
            "baselineIntensity.lessThan=" + UPDATED_BASELINE_INTENSITY,
            "baselineIntensity.lessThan=" + DEFAULT_BASELINE_INTENSITY
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByBaselineIntensityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where baselineIntensity is greater than
        defaultFuelEuRegulationFiltering(
            "baselineIntensity.greaterThan=" + SMALLER_BASELINE_INTENSITY,
            "baselineIntensity.greaterThan=" + DEFAULT_BASELINE_INTENSITY
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByReductionFactorPercentIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where reductionFactorPercent equals to
        defaultFuelEuRegulationFiltering(
            "reductionFactorPercent.equals=" + DEFAULT_REDUCTION_FACTOR_PERCENT,
            "reductionFactorPercent.equals=" + UPDATED_REDUCTION_FACTOR_PERCENT
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByReductionFactorPercentIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where reductionFactorPercent in
        defaultFuelEuRegulationFiltering(
            "reductionFactorPercent.in=" + DEFAULT_REDUCTION_FACTOR_PERCENT + "," + UPDATED_REDUCTION_FACTOR_PERCENT,
            "reductionFactorPercent.in=" + UPDATED_REDUCTION_FACTOR_PERCENT
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByReductionFactorPercentIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where reductionFactorPercent is not null
        defaultFuelEuRegulationFiltering("reductionFactorPercent.specified=true", "reductionFactorPercent.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByReductionFactorPercentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where reductionFactorPercent is greater than or equal to
        defaultFuelEuRegulationFiltering(
            "reductionFactorPercent.greaterThanOrEqual=" + DEFAULT_REDUCTION_FACTOR_PERCENT,
            "reductionFactorPercent.greaterThanOrEqual=" + UPDATED_REDUCTION_FACTOR_PERCENT
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByReductionFactorPercentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where reductionFactorPercent is less than or equal to
        defaultFuelEuRegulationFiltering(
            "reductionFactorPercent.lessThanOrEqual=" + DEFAULT_REDUCTION_FACTOR_PERCENT,
            "reductionFactorPercent.lessThanOrEqual=" + SMALLER_REDUCTION_FACTOR_PERCENT
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByReductionFactorPercentIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where reductionFactorPercent is less than
        defaultFuelEuRegulationFiltering(
            "reductionFactorPercent.lessThan=" + UPDATED_REDUCTION_FACTOR_PERCENT,
            "reductionFactorPercent.lessThan=" + DEFAULT_REDUCTION_FACTOR_PERCENT
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByReductionFactorPercentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where reductionFactorPercent is greater than
        defaultFuelEuRegulationFiltering(
            "reductionFactorPercent.greaterThan=" + SMALLER_REDUCTION_FACTOR_PERCENT,
            "reductionFactorPercent.greaterThan=" + DEFAULT_REDUCTION_FACTOR_PERCENT
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByVlsfoEnergyContentPerTonMjIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where vlsfoEnergyContentPerTonMj equals to
        defaultFuelEuRegulationFiltering(
            "vlsfoEnergyContentPerTonMj.equals=" + DEFAULT_VLSFO_ENERGY_CONTENT_PER_TON_MJ,
            "vlsfoEnergyContentPerTonMj.equals=" + UPDATED_VLSFO_ENERGY_CONTENT_PER_TON_MJ
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByVlsfoEnergyContentPerTonMjIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where vlsfoEnergyContentPerTonMj in
        defaultFuelEuRegulationFiltering(
            "vlsfoEnergyContentPerTonMj.in=" + DEFAULT_VLSFO_ENERGY_CONTENT_PER_TON_MJ + "," + UPDATED_VLSFO_ENERGY_CONTENT_PER_TON_MJ,
            "vlsfoEnergyContentPerTonMj.in=" + UPDATED_VLSFO_ENERGY_CONTENT_PER_TON_MJ
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByVlsfoEnergyContentPerTonMjIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where vlsfoEnergyContentPerTonMj is not null
        defaultFuelEuRegulationFiltering("vlsfoEnergyContentPerTonMj.specified=true", "vlsfoEnergyContentPerTonMj.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByVlsfoEnergyContentPerTonMjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where vlsfoEnergyContentPerTonMj is greater than or equal to
        defaultFuelEuRegulationFiltering(
            "vlsfoEnergyContentPerTonMj.greaterThanOrEqual=" + DEFAULT_VLSFO_ENERGY_CONTENT_PER_TON_MJ,
            "vlsfoEnergyContentPerTonMj.greaterThanOrEqual=" + UPDATED_VLSFO_ENERGY_CONTENT_PER_TON_MJ
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByVlsfoEnergyContentPerTonMjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where vlsfoEnergyContentPerTonMj is less than or equal to
        defaultFuelEuRegulationFiltering(
            "vlsfoEnergyContentPerTonMj.lessThanOrEqual=" + DEFAULT_VLSFO_ENERGY_CONTENT_PER_TON_MJ,
            "vlsfoEnergyContentPerTonMj.lessThanOrEqual=" + SMALLER_VLSFO_ENERGY_CONTENT_PER_TON_MJ
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByVlsfoEnergyContentPerTonMjIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where vlsfoEnergyContentPerTonMj is less than
        defaultFuelEuRegulationFiltering(
            "vlsfoEnergyContentPerTonMj.lessThan=" + UPDATED_VLSFO_ENERGY_CONTENT_PER_TON_MJ,
            "vlsfoEnergyContentPerTonMj.lessThan=" + DEFAULT_VLSFO_ENERGY_CONTENT_PER_TON_MJ
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByVlsfoEnergyContentPerTonMjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where vlsfoEnergyContentPerTonMj is greater than
        defaultFuelEuRegulationFiltering(
            "vlsfoEnergyContentPerTonMj.greaterThan=" + SMALLER_VLSFO_ENERGY_CONTENT_PER_TON_MJ,
            "vlsfoEnergyContentPerTonMj.greaterThan=" + DEFAULT_VLSFO_ENERGY_CONTENT_PER_TON_MJ
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByVlsfoPenaltyEurPerTonIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where vlsfoPenaltyEurPerTon equals to
        defaultFuelEuRegulationFiltering(
            "vlsfoPenaltyEurPerTon.equals=" + DEFAULT_VLSFO_PENALTY_EUR_PER_TON,
            "vlsfoPenaltyEurPerTon.equals=" + UPDATED_VLSFO_PENALTY_EUR_PER_TON
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByVlsfoPenaltyEurPerTonIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where vlsfoPenaltyEurPerTon in
        defaultFuelEuRegulationFiltering(
            "vlsfoPenaltyEurPerTon.in=" + DEFAULT_VLSFO_PENALTY_EUR_PER_TON + "," + UPDATED_VLSFO_PENALTY_EUR_PER_TON,
            "vlsfoPenaltyEurPerTon.in=" + UPDATED_VLSFO_PENALTY_EUR_PER_TON
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByVlsfoPenaltyEurPerTonIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where vlsfoPenaltyEurPerTon is not null
        defaultFuelEuRegulationFiltering("vlsfoPenaltyEurPerTon.specified=true", "vlsfoPenaltyEurPerTon.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByVlsfoPenaltyEurPerTonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where vlsfoPenaltyEurPerTon is greater than or equal to
        defaultFuelEuRegulationFiltering(
            "vlsfoPenaltyEurPerTon.greaterThanOrEqual=" + DEFAULT_VLSFO_PENALTY_EUR_PER_TON,
            "vlsfoPenaltyEurPerTon.greaterThanOrEqual=" + UPDATED_VLSFO_PENALTY_EUR_PER_TON
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByVlsfoPenaltyEurPerTonIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where vlsfoPenaltyEurPerTon is less than or equal to
        defaultFuelEuRegulationFiltering(
            "vlsfoPenaltyEurPerTon.lessThanOrEqual=" + DEFAULT_VLSFO_PENALTY_EUR_PER_TON,
            "vlsfoPenaltyEurPerTon.lessThanOrEqual=" + SMALLER_VLSFO_PENALTY_EUR_PER_TON
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByVlsfoPenaltyEurPerTonIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where vlsfoPenaltyEurPerTon is less than
        defaultFuelEuRegulationFiltering(
            "vlsfoPenaltyEurPerTon.lessThan=" + UPDATED_VLSFO_PENALTY_EUR_PER_TON,
            "vlsfoPenaltyEurPerTon.lessThan=" + DEFAULT_VLSFO_PENALTY_EUR_PER_TON
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByVlsfoPenaltyEurPerTonIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where vlsfoPenaltyEurPerTon is greater than
        defaultFuelEuRegulationFiltering(
            "vlsfoPenaltyEurPerTon.greaterThan=" + SMALLER_VLSFO_PENALTY_EUR_PER_TON,
            "vlsfoPenaltyEurPerTon.greaterThan=" + DEFAULT_VLSFO_PENALTY_EUR_PER_TON
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByEnergyAllowanceMultiplierIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where energyAllowanceMultiplier equals to
        defaultFuelEuRegulationFiltering(
            "energyAllowanceMultiplier.equals=" + DEFAULT_ENERGY_ALLOWANCE_MULTIPLIER,
            "energyAllowanceMultiplier.equals=" + UPDATED_ENERGY_ALLOWANCE_MULTIPLIER
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByEnergyAllowanceMultiplierIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where energyAllowanceMultiplier in
        defaultFuelEuRegulationFiltering(
            "energyAllowanceMultiplier.in=" + DEFAULT_ENERGY_ALLOWANCE_MULTIPLIER + "," + UPDATED_ENERGY_ALLOWANCE_MULTIPLIER,
            "energyAllowanceMultiplier.in=" + UPDATED_ENERGY_ALLOWANCE_MULTIPLIER
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByEnergyAllowanceMultiplierIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where energyAllowanceMultiplier is not null
        defaultFuelEuRegulationFiltering("energyAllowanceMultiplier.specified=true", "energyAllowanceMultiplier.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByEnergyAllowanceMultiplierIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where energyAllowanceMultiplier is greater than or equal to
        defaultFuelEuRegulationFiltering(
            "energyAllowanceMultiplier.greaterThanOrEqual=" + DEFAULT_ENERGY_ALLOWANCE_MULTIPLIER,
            "energyAllowanceMultiplier.greaterThanOrEqual=" + UPDATED_ENERGY_ALLOWANCE_MULTIPLIER
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByEnergyAllowanceMultiplierIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where energyAllowanceMultiplier is less than or equal to
        defaultFuelEuRegulationFiltering(
            "energyAllowanceMultiplier.lessThanOrEqual=" + DEFAULT_ENERGY_ALLOWANCE_MULTIPLIER,
            "energyAllowanceMultiplier.lessThanOrEqual=" + SMALLER_ENERGY_ALLOWANCE_MULTIPLIER
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByEnergyAllowanceMultiplierIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where energyAllowanceMultiplier is less than
        defaultFuelEuRegulationFiltering(
            "energyAllowanceMultiplier.lessThan=" + UPDATED_ENERGY_ALLOWANCE_MULTIPLIER,
            "energyAllowanceMultiplier.lessThan=" + DEFAULT_ENERGY_ALLOWANCE_MULTIPLIER
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByEnergyAllowanceMultiplierIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where energyAllowanceMultiplier is greater than
        defaultFuelEuRegulationFiltering(
            "energyAllowanceMultiplier.greaterThan=" + SMALLER_ENERGY_ALLOWANCE_MULTIPLIER,
            "energyAllowanceMultiplier.greaterThan=" + DEFAULT_ENERGY_ALLOWANCE_MULTIPLIER
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByNonBioFuelRewardFactorIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where nonBioFuelRewardFactor equals to
        defaultFuelEuRegulationFiltering(
            "nonBioFuelRewardFactor.equals=" + DEFAULT_NON_BIO_FUEL_REWARD_FACTOR,
            "nonBioFuelRewardFactor.equals=" + UPDATED_NON_BIO_FUEL_REWARD_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByNonBioFuelRewardFactorIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where nonBioFuelRewardFactor in
        defaultFuelEuRegulationFiltering(
            "nonBioFuelRewardFactor.in=" + DEFAULT_NON_BIO_FUEL_REWARD_FACTOR + "," + UPDATED_NON_BIO_FUEL_REWARD_FACTOR,
            "nonBioFuelRewardFactor.in=" + UPDATED_NON_BIO_FUEL_REWARD_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByNonBioFuelRewardFactorIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where nonBioFuelRewardFactor is not null
        defaultFuelEuRegulationFiltering("nonBioFuelRewardFactor.specified=true", "nonBioFuelRewardFactor.specified=false");
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByNonBioFuelRewardFactorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where nonBioFuelRewardFactor is greater than or equal to
        defaultFuelEuRegulationFiltering(
            "nonBioFuelRewardFactor.greaterThanOrEqual=" + DEFAULT_NON_BIO_FUEL_REWARD_FACTOR,
            "nonBioFuelRewardFactor.greaterThanOrEqual=" + UPDATED_NON_BIO_FUEL_REWARD_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByNonBioFuelRewardFactorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where nonBioFuelRewardFactor is less than or equal to
        defaultFuelEuRegulationFiltering(
            "nonBioFuelRewardFactor.lessThanOrEqual=" + DEFAULT_NON_BIO_FUEL_REWARD_FACTOR,
            "nonBioFuelRewardFactor.lessThanOrEqual=" + SMALLER_NON_BIO_FUEL_REWARD_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByNonBioFuelRewardFactorIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where nonBioFuelRewardFactor is less than
        defaultFuelEuRegulationFiltering(
            "nonBioFuelRewardFactor.lessThan=" + UPDATED_NON_BIO_FUEL_REWARD_FACTOR,
            "nonBioFuelRewardFactor.lessThan=" + DEFAULT_NON_BIO_FUEL_REWARD_FACTOR
        );
    }

    @Test
    @Transactional
    void getAllFuelEuRegulationsByNonBioFuelRewardFactorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        // Get all the fuelEuRegulationList where nonBioFuelRewardFactor is greater than
        defaultFuelEuRegulationFiltering(
            "nonBioFuelRewardFactor.greaterThan=" + SMALLER_NON_BIO_FUEL_REWARD_FACTOR,
            "nonBioFuelRewardFactor.greaterThan=" + DEFAULT_NON_BIO_FUEL_REWARD_FACTOR
        );
    }

    private void defaultFuelEuRegulationFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFuelEuRegulationShouldBeFound(shouldBeFound);
        defaultFuelEuRegulationShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFuelEuRegulationShouldBeFound(String filter) throws Exception {
        restFuelEuRegulationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelEuRegulation.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].co2Gwp").value(hasItem(DEFAULT_CO_2_GWP)))
            .andExpect(jsonPath("$.[*].methaneGwp").value(hasItem(DEFAULT_METHANE_GWP)))
            .andExpect(jsonPath("$.[*].nitrousGwp").value(hasItem(DEFAULT_NITROUS_GWP)))
            .andExpect(jsonPath("$.[*].targetIntensity").value(hasItem(sameNumber(DEFAULT_TARGET_INTENSITY))))
            .andExpect(jsonPath("$.[*].baselineIntensity").value(hasItem(sameNumber(DEFAULT_BASELINE_INTENSITY))))
            .andExpect(jsonPath("$.[*].reductionFactorPercent").value(hasItem(sameNumber(DEFAULT_REDUCTION_FACTOR_PERCENT))))
            .andExpect(jsonPath("$.[*].vlsfoEnergyContentPerTonMj").value(hasItem(sameNumber(DEFAULT_VLSFO_ENERGY_CONTENT_PER_TON_MJ))))
            .andExpect(jsonPath("$.[*].vlsfoPenaltyEurPerTon").value(hasItem(sameNumber(DEFAULT_VLSFO_PENALTY_EUR_PER_TON))))
            .andExpect(jsonPath("$.[*].energyAllowanceMultiplier").value(hasItem(sameNumber(DEFAULT_ENERGY_ALLOWANCE_MULTIPLIER))))
            .andExpect(jsonPath("$.[*].nonBioFuelRewardFactor").value(hasItem(sameNumber(DEFAULT_NON_BIO_FUEL_REWARD_FACTOR))));

        // Check, that the count call also returns 1
        restFuelEuRegulationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFuelEuRegulationShouldNotBeFound(String filter) throws Exception {
        restFuelEuRegulationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFuelEuRegulationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFuelEuRegulation() throws Exception {
        // Get the fuelEuRegulation
        restFuelEuRegulationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuelEuRegulation() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelEuRegulation
        FuelEuRegulation updatedFuelEuRegulation = fuelEuRegulationRepository.findById(fuelEuRegulation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFuelEuRegulation are not directly saved in db
        em.detach(updatedFuelEuRegulation);
        updatedFuelEuRegulation
            .year(UPDATED_YEAR)
            .co2Gwp(UPDATED_CO_2_GWP)
            .methaneGwp(UPDATED_METHANE_GWP)
            .nitrousGwp(UPDATED_NITROUS_GWP)
            .targetIntensity(UPDATED_TARGET_INTENSITY)
            .baselineIntensity(UPDATED_BASELINE_INTENSITY)
            .reductionFactorPercent(UPDATED_REDUCTION_FACTOR_PERCENT)
            .vlsfoEnergyContentPerTonMj(UPDATED_VLSFO_ENERGY_CONTENT_PER_TON_MJ)
            .vlsfoPenaltyEurPerTon(UPDATED_VLSFO_PENALTY_EUR_PER_TON)
            .energyAllowanceMultiplier(UPDATED_ENERGY_ALLOWANCE_MULTIPLIER)
            .nonBioFuelRewardFactor(UPDATED_NON_BIO_FUEL_REWARD_FACTOR);
        FuelEuRegulationDTO fuelEuRegulationDTO = fuelEuRegulationMapper.toDto(updatedFuelEuRegulation);

        restFuelEuRegulationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelEuRegulationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelEuRegulationDTO))
            )
            .andExpect(status().isOk());

        // Validate the FuelEuRegulation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFuelEuRegulationToMatchAllProperties(updatedFuelEuRegulation);
    }

    @Test
    @Transactional
    void putNonExistingFuelEuRegulation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelEuRegulation.setId(longCount.incrementAndGet());

        // Create the FuelEuRegulation
        FuelEuRegulationDTO fuelEuRegulationDTO = fuelEuRegulationMapper.toDto(fuelEuRegulation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelEuRegulationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelEuRegulationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelEuRegulationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelEuRegulation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuelEuRegulation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelEuRegulation.setId(longCount.incrementAndGet());

        // Create the FuelEuRegulation
        FuelEuRegulationDTO fuelEuRegulationDTO = fuelEuRegulationMapper.toDto(fuelEuRegulation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelEuRegulationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelEuRegulationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelEuRegulation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuelEuRegulation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelEuRegulation.setId(longCount.incrementAndGet());

        // Create the FuelEuRegulation
        FuelEuRegulationDTO fuelEuRegulationDTO = fuelEuRegulationMapper.toDto(fuelEuRegulation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelEuRegulationMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelEuRegulationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelEuRegulation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuelEuRegulationWithPatch() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelEuRegulation using partial update
        FuelEuRegulation partialUpdatedFuelEuRegulation = new FuelEuRegulation();
        partialUpdatedFuelEuRegulation.setId(fuelEuRegulation.getId());

        partialUpdatedFuelEuRegulation
            .year(UPDATED_YEAR)
            .targetIntensity(UPDATED_TARGET_INTENSITY)
            .reductionFactorPercent(UPDATED_REDUCTION_FACTOR_PERCENT)
            .vlsfoEnergyContentPerTonMj(UPDATED_VLSFO_ENERGY_CONTENT_PER_TON_MJ)
            .energyAllowanceMultiplier(UPDATED_ENERGY_ALLOWANCE_MULTIPLIER)
            .nonBioFuelRewardFactor(UPDATED_NON_BIO_FUEL_REWARD_FACTOR);

        restFuelEuRegulationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelEuRegulation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuelEuRegulation))
            )
            .andExpect(status().isOk());

        // Validate the FuelEuRegulation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuelEuRegulationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFuelEuRegulation, fuelEuRegulation),
            getPersistedFuelEuRegulation(fuelEuRegulation)
        );
    }

    @Test
    @Transactional
    void fullUpdateFuelEuRegulationWithPatch() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelEuRegulation using partial update
        FuelEuRegulation partialUpdatedFuelEuRegulation = new FuelEuRegulation();
        partialUpdatedFuelEuRegulation.setId(fuelEuRegulation.getId());

        partialUpdatedFuelEuRegulation
            .year(UPDATED_YEAR)
            .co2Gwp(UPDATED_CO_2_GWP)
            .methaneGwp(UPDATED_METHANE_GWP)
            .nitrousGwp(UPDATED_NITROUS_GWP)
            .targetIntensity(UPDATED_TARGET_INTENSITY)
            .baselineIntensity(UPDATED_BASELINE_INTENSITY)
            .reductionFactorPercent(UPDATED_REDUCTION_FACTOR_PERCENT)
            .vlsfoEnergyContentPerTonMj(UPDATED_VLSFO_ENERGY_CONTENT_PER_TON_MJ)
            .vlsfoPenaltyEurPerTon(UPDATED_VLSFO_PENALTY_EUR_PER_TON)
            .energyAllowanceMultiplier(UPDATED_ENERGY_ALLOWANCE_MULTIPLIER)
            .nonBioFuelRewardFactor(UPDATED_NON_BIO_FUEL_REWARD_FACTOR);

        restFuelEuRegulationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelEuRegulation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuelEuRegulation))
            )
            .andExpect(status().isOk());

        // Validate the FuelEuRegulation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuelEuRegulationUpdatableFieldsEquals(
            partialUpdatedFuelEuRegulation,
            getPersistedFuelEuRegulation(partialUpdatedFuelEuRegulation)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFuelEuRegulation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelEuRegulation.setId(longCount.incrementAndGet());

        // Create the FuelEuRegulation
        FuelEuRegulationDTO fuelEuRegulationDTO = fuelEuRegulationMapper.toDto(fuelEuRegulation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelEuRegulationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fuelEuRegulationDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelEuRegulationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelEuRegulation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuelEuRegulation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelEuRegulation.setId(longCount.incrementAndGet());

        // Create the FuelEuRegulation
        FuelEuRegulationDTO fuelEuRegulationDTO = fuelEuRegulationMapper.toDto(fuelEuRegulation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelEuRegulationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelEuRegulationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelEuRegulation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuelEuRegulation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelEuRegulation.setId(longCount.incrementAndGet());

        // Create the FuelEuRegulation
        FuelEuRegulationDTO fuelEuRegulationDTO = fuelEuRegulationMapper.toDto(fuelEuRegulation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelEuRegulationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelEuRegulationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelEuRegulation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuelEuRegulation() throws Exception {
        // Initialize the database
        insertedFuelEuRegulation = fuelEuRegulationRepository.saveAndFlush(fuelEuRegulation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fuelEuRegulation
        restFuelEuRegulationMockMvc
            .perform(delete(ENTITY_API_URL_ID, fuelEuRegulation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fuelEuRegulationRepository.count();
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

    protected FuelEuRegulation getPersistedFuelEuRegulation(FuelEuRegulation fuelEuRegulation) {
        return fuelEuRegulationRepository.findById(fuelEuRegulation.getId()).orElseThrow();
    }

    protected void assertPersistedFuelEuRegulationToMatchAllProperties(FuelEuRegulation expectedFuelEuRegulation) {
        assertFuelEuRegulationAllPropertiesEquals(expectedFuelEuRegulation, getPersistedFuelEuRegulation(expectedFuelEuRegulation));
    }

    protected void assertPersistedFuelEuRegulationToMatchUpdatableProperties(FuelEuRegulation expectedFuelEuRegulation) {
        assertFuelEuRegulationAllUpdatablePropertiesEquals(
            expectedFuelEuRegulation,
            getPersistedFuelEuRegulation(expectedFuelEuRegulation)
        );
    }
}
