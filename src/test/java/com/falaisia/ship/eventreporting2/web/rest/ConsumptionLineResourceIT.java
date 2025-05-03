package com.falaisia.ship.eventreporting2.web.rest;

import static com.falaisia.ship.eventreporting2.domain.ConsumptionLineAsserts.*;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.createUpdateProxyForBean;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.falaisia.ship.eventreporting2.IntegrationTest;
import com.falaisia.ship.eventreporting2.domain.ConsumptionLine;
import com.falaisia.ship.eventreporting2.domain.EventReport;
import com.falaisia.ship.eventreporting2.domain.FuelType;
import com.falaisia.ship.eventreporting2.domain.enumeration.Co2EmissionSourceTypeCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.DiffCriterionCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.PortActivityCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.UnitOfMeasure;
import com.falaisia.ship.eventreporting2.repository.ConsumptionLineRepository;
import com.falaisia.ship.eventreporting2.service.dto.ConsumptionLineDTO;
import com.falaisia.ship.eventreporting2.service.mapper.ConsumptionLineMapper;
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
 * Integration tests for the {@link ConsumptionLineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConsumptionLineResourceIT {

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(1 - 1);

    private static final UnitOfMeasure DEFAULT_UNIT_OF_MEASURE = UnitOfMeasure.M_TONNES;
    private static final UnitOfMeasure UPDATED_UNIT_OF_MEASURE = UnitOfMeasure.M3;

    private static final Co2EmissionSourceTypeCode DEFAULT_CO_2_EMISSION_SOURCE_TYPE_CODE = Co2EmissionSourceTypeCode.AUX_ENGINE;
    private static final Co2EmissionSourceTypeCode UPDATED_CO_2_EMISSION_SOURCE_TYPE_CODE = Co2EmissionSourceTypeCode.BOILER;

    private static final BigDecimal DEFAULT_LOWER_CALORIFIC_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LOWER_CALORIFIC_VALUE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LOWER_CALORIFIC_VALUE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SULPHUR_CONTENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_SULPHUR_CONTENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_SULPHUR_CONTENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DENSITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_DENSITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_DENSITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VISCOSITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_VISCOSITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_VISCOSITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_WATER_CONTENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_WATER_CONTENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_WATER_CONTENT = new BigDecimal(1 - 1);

    private static final PortActivityCode DEFAULT_PORT_ACTIVITY_CODE = PortActivityCode.AT_BERTH;
    private static final PortActivityCode UPDATED_PORT_ACTIVITY_CODE = PortActivityCode.MOVEMENT;

    private static final DiffCriterionCode DEFAULT_DIFF_CRITERION_CODE = DiffCriterionCode.ON_BALLAST;
    private static final DiffCriterionCode UPDATED_DIFF_CRITERION_CODE = DiffCriterionCode.ON_LADEN;

    private static final String ENTITY_API_URL = "/api/consumption-lines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ConsumptionLineRepository consumptionLineRepository;

    @Autowired
    private ConsumptionLineMapper consumptionLineMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConsumptionLineMockMvc;

    private ConsumptionLine consumptionLine;

    private ConsumptionLine insertedConsumptionLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsumptionLine createEntity(EntityManager em) {
        ConsumptionLine consumptionLine = new ConsumptionLine()
            .quantity(DEFAULT_QUANTITY)
            .unitOfMeasure(DEFAULT_UNIT_OF_MEASURE)
            .co2EmissionSourceTypeCode(DEFAULT_CO_2_EMISSION_SOURCE_TYPE_CODE)
            .lowerCalorificValue(DEFAULT_LOWER_CALORIFIC_VALUE)
            .sulphurContent(DEFAULT_SULPHUR_CONTENT)
            .density(DEFAULT_DENSITY)
            .viscosity(DEFAULT_VISCOSITY)
            .waterContent(DEFAULT_WATER_CONTENT)
            .portActivityCode(DEFAULT_PORT_ACTIVITY_CODE)
            .diffCriterionCode(DEFAULT_DIFF_CRITERION_CODE);
        // Add required entity
        EventReport eventReport;
        if (TestUtil.findAll(em, EventReport.class).isEmpty()) {
            eventReport = EventReportResourceIT.createEntity(em);
            em.persist(eventReport);
            em.flush();
        } else {
            eventReport = TestUtil.findAll(em, EventReport.class).get(0);
        }
        consumptionLine.setEventReport(eventReport);
        // Add required entity
        FuelType fuelType;
        if (TestUtil.findAll(em, FuelType.class).isEmpty()) {
            fuelType = FuelTypeResourceIT.createEntity();
            em.persist(fuelType);
            em.flush();
        } else {
            fuelType = TestUtil.findAll(em, FuelType.class).get(0);
        }
        consumptionLine.setFuelType(fuelType);
        return consumptionLine;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsumptionLine createUpdatedEntity(EntityManager em) {
        ConsumptionLine updatedConsumptionLine = new ConsumptionLine()
            .quantity(UPDATED_QUANTITY)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .co2EmissionSourceTypeCode(UPDATED_CO_2_EMISSION_SOURCE_TYPE_CODE)
            .lowerCalorificValue(UPDATED_LOWER_CALORIFIC_VALUE)
            .sulphurContent(UPDATED_SULPHUR_CONTENT)
            .density(UPDATED_DENSITY)
            .viscosity(UPDATED_VISCOSITY)
            .waterContent(UPDATED_WATER_CONTENT)
            .portActivityCode(UPDATED_PORT_ACTIVITY_CODE)
            .diffCriterionCode(UPDATED_DIFF_CRITERION_CODE);
        // Add required entity
        EventReport eventReport;
        if (TestUtil.findAll(em, EventReport.class).isEmpty()) {
            eventReport = EventReportResourceIT.createUpdatedEntity(em);
            em.persist(eventReport);
            em.flush();
        } else {
            eventReport = TestUtil.findAll(em, EventReport.class).get(0);
        }
        updatedConsumptionLine.setEventReport(eventReport);
        // Add required entity
        FuelType fuelType;
        if (TestUtil.findAll(em, FuelType.class).isEmpty()) {
            fuelType = FuelTypeResourceIT.createUpdatedEntity();
            em.persist(fuelType);
            em.flush();
        } else {
            fuelType = TestUtil.findAll(em, FuelType.class).get(0);
        }
        updatedConsumptionLine.setFuelType(fuelType);
        return updatedConsumptionLine;
    }

    @BeforeEach
    void initTest() {
        consumptionLine = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedConsumptionLine != null) {
            consumptionLineRepository.delete(insertedConsumptionLine);
            insertedConsumptionLine = null;
        }
    }

    @Test
    @Transactional
    void createConsumptionLine() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ConsumptionLine
        ConsumptionLineDTO consumptionLineDTO = consumptionLineMapper.toDto(consumptionLine);
        var returnedConsumptionLineDTO = om.readValue(
            restConsumptionLineMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(consumptionLineDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ConsumptionLineDTO.class
        );

        // Validate the ConsumptionLine in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedConsumptionLine = consumptionLineMapper.toEntity(returnedConsumptionLineDTO);
        assertConsumptionLineUpdatableFieldsEquals(returnedConsumptionLine, getPersistedConsumptionLine(returnedConsumptionLine));

        insertedConsumptionLine = returnedConsumptionLine;
    }

    @Test
    @Transactional
    void createConsumptionLineWithExistingId() throws Exception {
        // Create the ConsumptionLine with an existing ID
        consumptionLine.setId(1L);
        ConsumptionLineDTO consumptionLineDTO = consumptionLineMapper.toDto(consumptionLine);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsumptionLineMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(consumptionLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        consumptionLine.setQuantity(null);

        // Create the ConsumptionLine, which fails.
        ConsumptionLineDTO consumptionLineDTO = consumptionLineMapper.toDto(consumptionLine);

        restConsumptionLineMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(consumptionLineDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnitOfMeasureIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        consumptionLine.setUnitOfMeasure(null);

        // Create the ConsumptionLine, which fails.
        ConsumptionLineDTO consumptionLineDTO = consumptionLineMapper.toDto(consumptionLine);

        restConsumptionLineMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(consumptionLineDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConsumptionLines() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList
        restConsumptionLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumptionLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(sameNumber(DEFAULT_QUANTITY))))
            .andExpect(jsonPath("$.[*].unitOfMeasure").value(hasItem(DEFAULT_UNIT_OF_MEASURE.toString())))
            .andExpect(jsonPath("$.[*].co2EmissionSourceTypeCode").value(hasItem(DEFAULT_CO_2_EMISSION_SOURCE_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].lowerCalorificValue").value(hasItem(sameNumber(DEFAULT_LOWER_CALORIFIC_VALUE))))
            .andExpect(jsonPath("$.[*].sulphurContent").value(hasItem(sameNumber(DEFAULT_SULPHUR_CONTENT))))
            .andExpect(jsonPath("$.[*].density").value(hasItem(sameNumber(DEFAULT_DENSITY))))
            .andExpect(jsonPath("$.[*].viscosity").value(hasItem(sameNumber(DEFAULT_VISCOSITY))))
            .andExpect(jsonPath("$.[*].waterContent").value(hasItem(sameNumber(DEFAULT_WATER_CONTENT))))
            .andExpect(jsonPath("$.[*].portActivityCode").value(hasItem(DEFAULT_PORT_ACTIVITY_CODE.toString())))
            .andExpect(jsonPath("$.[*].diffCriterionCode").value(hasItem(DEFAULT_DIFF_CRITERION_CODE.toString())));
    }

    @Test
    @Transactional
    void getConsumptionLine() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get the consumptionLine
        restConsumptionLineMockMvc
            .perform(get(ENTITY_API_URL_ID, consumptionLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consumptionLine.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(sameNumber(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.unitOfMeasure").value(DEFAULT_UNIT_OF_MEASURE.toString()))
            .andExpect(jsonPath("$.co2EmissionSourceTypeCode").value(DEFAULT_CO_2_EMISSION_SOURCE_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.lowerCalorificValue").value(sameNumber(DEFAULT_LOWER_CALORIFIC_VALUE)))
            .andExpect(jsonPath("$.sulphurContent").value(sameNumber(DEFAULT_SULPHUR_CONTENT)))
            .andExpect(jsonPath("$.density").value(sameNumber(DEFAULT_DENSITY)))
            .andExpect(jsonPath("$.viscosity").value(sameNumber(DEFAULT_VISCOSITY)))
            .andExpect(jsonPath("$.waterContent").value(sameNumber(DEFAULT_WATER_CONTENT)))
            .andExpect(jsonPath("$.portActivityCode").value(DEFAULT_PORT_ACTIVITY_CODE.toString()))
            .andExpect(jsonPath("$.diffCriterionCode").value(DEFAULT_DIFF_CRITERION_CODE.toString()));
    }

    @Test
    @Transactional
    void getConsumptionLinesByIdFiltering() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        Long id = consumptionLine.getId();

        defaultConsumptionLineFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultConsumptionLineFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultConsumptionLineFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where quantity equals to
        defaultConsumptionLineFiltering("quantity.equals=" + DEFAULT_QUANTITY, "quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where quantity in
        defaultConsumptionLineFiltering("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY, "quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where quantity is not null
        defaultConsumptionLineFiltering("quantity.specified=true", "quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where quantity is greater than or equal to
        defaultConsumptionLineFiltering(
            "quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY,
            "quantity.greaterThanOrEqual=" + UPDATED_QUANTITY
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where quantity is less than or equal to
        defaultConsumptionLineFiltering("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY, "quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where quantity is less than
        defaultConsumptionLineFiltering("quantity.lessThan=" + UPDATED_QUANTITY, "quantity.lessThan=" + DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where quantity is greater than
        defaultConsumptionLineFiltering("quantity.greaterThan=" + SMALLER_QUANTITY, "quantity.greaterThan=" + DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByUnitOfMeasureIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where unitOfMeasure equals to
        defaultConsumptionLineFiltering(
            "unitOfMeasure.equals=" + DEFAULT_UNIT_OF_MEASURE,
            "unitOfMeasure.equals=" + UPDATED_UNIT_OF_MEASURE
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByUnitOfMeasureIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where unitOfMeasure in
        defaultConsumptionLineFiltering(
            "unitOfMeasure.in=" + DEFAULT_UNIT_OF_MEASURE + "," + UPDATED_UNIT_OF_MEASURE,
            "unitOfMeasure.in=" + UPDATED_UNIT_OF_MEASURE
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByUnitOfMeasureIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where unitOfMeasure is not null
        defaultConsumptionLineFiltering("unitOfMeasure.specified=true", "unitOfMeasure.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByCo2EmissionSourceTypeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where co2EmissionSourceTypeCode equals to
        defaultConsumptionLineFiltering(
            "co2EmissionSourceTypeCode.equals=" + DEFAULT_CO_2_EMISSION_SOURCE_TYPE_CODE,
            "co2EmissionSourceTypeCode.equals=" + UPDATED_CO_2_EMISSION_SOURCE_TYPE_CODE
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByCo2EmissionSourceTypeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where co2EmissionSourceTypeCode in
        defaultConsumptionLineFiltering(
            "co2EmissionSourceTypeCode.in=" + DEFAULT_CO_2_EMISSION_SOURCE_TYPE_CODE + "," + UPDATED_CO_2_EMISSION_SOURCE_TYPE_CODE,
            "co2EmissionSourceTypeCode.in=" + UPDATED_CO_2_EMISSION_SOURCE_TYPE_CODE
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByCo2EmissionSourceTypeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where co2EmissionSourceTypeCode is not null
        defaultConsumptionLineFiltering("co2EmissionSourceTypeCode.specified=true", "co2EmissionSourceTypeCode.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByLowerCalorificValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where lowerCalorificValue equals to
        defaultConsumptionLineFiltering(
            "lowerCalorificValue.equals=" + DEFAULT_LOWER_CALORIFIC_VALUE,
            "lowerCalorificValue.equals=" + UPDATED_LOWER_CALORIFIC_VALUE
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByLowerCalorificValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where lowerCalorificValue in
        defaultConsumptionLineFiltering(
            "lowerCalorificValue.in=" + DEFAULT_LOWER_CALORIFIC_VALUE + "," + UPDATED_LOWER_CALORIFIC_VALUE,
            "lowerCalorificValue.in=" + UPDATED_LOWER_CALORIFIC_VALUE
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByLowerCalorificValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where lowerCalorificValue is not null
        defaultConsumptionLineFiltering("lowerCalorificValue.specified=true", "lowerCalorificValue.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByLowerCalorificValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where lowerCalorificValue is greater than or equal to
        defaultConsumptionLineFiltering(
            "lowerCalorificValue.greaterThanOrEqual=" + DEFAULT_LOWER_CALORIFIC_VALUE,
            "lowerCalorificValue.greaterThanOrEqual=" + UPDATED_LOWER_CALORIFIC_VALUE
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByLowerCalorificValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where lowerCalorificValue is less than or equal to
        defaultConsumptionLineFiltering(
            "lowerCalorificValue.lessThanOrEqual=" + DEFAULT_LOWER_CALORIFIC_VALUE,
            "lowerCalorificValue.lessThanOrEqual=" + SMALLER_LOWER_CALORIFIC_VALUE
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByLowerCalorificValueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where lowerCalorificValue is less than
        defaultConsumptionLineFiltering(
            "lowerCalorificValue.lessThan=" + UPDATED_LOWER_CALORIFIC_VALUE,
            "lowerCalorificValue.lessThan=" + DEFAULT_LOWER_CALORIFIC_VALUE
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByLowerCalorificValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where lowerCalorificValue is greater than
        defaultConsumptionLineFiltering(
            "lowerCalorificValue.greaterThan=" + SMALLER_LOWER_CALORIFIC_VALUE,
            "lowerCalorificValue.greaterThan=" + DEFAULT_LOWER_CALORIFIC_VALUE
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesBySulphurContentIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where sulphurContent equals to
        defaultConsumptionLineFiltering(
            "sulphurContent.equals=" + DEFAULT_SULPHUR_CONTENT,
            "sulphurContent.equals=" + UPDATED_SULPHUR_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesBySulphurContentIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where sulphurContent in
        defaultConsumptionLineFiltering(
            "sulphurContent.in=" + DEFAULT_SULPHUR_CONTENT + "," + UPDATED_SULPHUR_CONTENT,
            "sulphurContent.in=" + UPDATED_SULPHUR_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesBySulphurContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where sulphurContent is not null
        defaultConsumptionLineFiltering("sulphurContent.specified=true", "sulphurContent.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionLinesBySulphurContentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where sulphurContent is greater than or equal to
        defaultConsumptionLineFiltering(
            "sulphurContent.greaterThanOrEqual=" + DEFAULT_SULPHUR_CONTENT,
            "sulphurContent.greaterThanOrEqual=" + UPDATED_SULPHUR_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesBySulphurContentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where sulphurContent is less than or equal to
        defaultConsumptionLineFiltering(
            "sulphurContent.lessThanOrEqual=" + DEFAULT_SULPHUR_CONTENT,
            "sulphurContent.lessThanOrEqual=" + SMALLER_SULPHUR_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesBySulphurContentIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where sulphurContent is less than
        defaultConsumptionLineFiltering(
            "sulphurContent.lessThan=" + UPDATED_SULPHUR_CONTENT,
            "sulphurContent.lessThan=" + DEFAULT_SULPHUR_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesBySulphurContentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where sulphurContent is greater than
        defaultConsumptionLineFiltering(
            "sulphurContent.greaterThan=" + SMALLER_SULPHUR_CONTENT,
            "sulphurContent.greaterThan=" + DEFAULT_SULPHUR_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByDensityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where density equals to
        defaultConsumptionLineFiltering("density.equals=" + DEFAULT_DENSITY, "density.equals=" + UPDATED_DENSITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByDensityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where density in
        defaultConsumptionLineFiltering("density.in=" + DEFAULT_DENSITY + "," + UPDATED_DENSITY, "density.in=" + UPDATED_DENSITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByDensityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where density is not null
        defaultConsumptionLineFiltering("density.specified=true", "density.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByDensityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where density is greater than or equal to
        defaultConsumptionLineFiltering("density.greaterThanOrEqual=" + DEFAULT_DENSITY, "density.greaterThanOrEqual=" + UPDATED_DENSITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByDensityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where density is less than or equal to
        defaultConsumptionLineFiltering("density.lessThanOrEqual=" + DEFAULT_DENSITY, "density.lessThanOrEqual=" + SMALLER_DENSITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByDensityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where density is less than
        defaultConsumptionLineFiltering("density.lessThan=" + UPDATED_DENSITY, "density.lessThan=" + DEFAULT_DENSITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByDensityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where density is greater than
        defaultConsumptionLineFiltering("density.greaterThan=" + SMALLER_DENSITY, "density.greaterThan=" + DEFAULT_DENSITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByViscosityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where viscosity equals to
        defaultConsumptionLineFiltering("viscosity.equals=" + DEFAULT_VISCOSITY, "viscosity.equals=" + UPDATED_VISCOSITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByViscosityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where viscosity in
        defaultConsumptionLineFiltering("viscosity.in=" + DEFAULT_VISCOSITY + "," + UPDATED_VISCOSITY, "viscosity.in=" + UPDATED_VISCOSITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByViscosityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where viscosity is not null
        defaultConsumptionLineFiltering("viscosity.specified=true", "viscosity.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByViscosityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where viscosity is greater than or equal to
        defaultConsumptionLineFiltering(
            "viscosity.greaterThanOrEqual=" + DEFAULT_VISCOSITY,
            "viscosity.greaterThanOrEqual=" + UPDATED_VISCOSITY
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByViscosityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where viscosity is less than or equal to
        defaultConsumptionLineFiltering("viscosity.lessThanOrEqual=" + DEFAULT_VISCOSITY, "viscosity.lessThanOrEqual=" + SMALLER_VISCOSITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByViscosityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where viscosity is less than
        defaultConsumptionLineFiltering("viscosity.lessThan=" + UPDATED_VISCOSITY, "viscosity.lessThan=" + DEFAULT_VISCOSITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByViscosityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where viscosity is greater than
        defaultConsumptionLineFiltering("viscosity.greaterThan=" + SMALLER_VISCOSITY, "viscosity.greaterThan=" + DEFAULT_VISCOSITY);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByWaterContentIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where waterContent equals to
        defaultConsumptionLineFiltering("waterContent.equals=" + DEFAULT_WATER_CONTENT, "waterContent.equals=" + UPDATED_WATER_CONTENT);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByWaterContentIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where waterContent in
        defaultConsumptionLineFiltering(
            "waterContent.in=" + DEFAULT_WATER_CONTENT + "," + UPDATED_WATER_CONTENT,
            "waterContent.in=" + UPDATED_WATER_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByWaterContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where waterContent is not null
        defaultConsumptionLineFiltering("waterContent.specified=true", "waterContent.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByWaterContentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where waterContent is greater than or equal to
        defaultConsumptionLineFiltering(
            "waterContent.greaterThanOrEqual=" + DEFAULT_WATER_CONTENT,
            "waterContent.greaterThanOrEqual=" + UPDATED_WATER_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByWaterContentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where waterContent is less than or equal to
        defaultConsumptionLineFiltering(
            "waterContent.lessThanOrEqual=" + DEFAULT_WATER_CONTENT,
            "waterContent.lessThanOrEqual=" + SMALLER_WATER_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByWaterContentIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where waterContent is less than
        defaultConsumptionLineFiltering("waterContent.lessThan=" + UPDATED_WATER_CONTENT, "waterContent.lessThan=" + DEFAULT_WATER_CONTENT);
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByWaterContentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where waterContent is greater than
        defaultConsumptionLineFiltering(
            "waterContent.greaterThan=" + SMALLER_WATER_CONTENT,
            "waterContent.greaterThan=" + DEFAULT_WATER_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByPortActivityCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where portActivityCode equals to
        defaultConsumptionLineFiltering(
            "portActivityCode.equals=" + DEFAULT_PORT_ACTIVITY_CODE,
            "portActivityCode.equals=" + UPDATED_PORT_ACTIVITY_CODE
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByPortActivityCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where portActivityCode in
        defaultConsumptionLineFiltering(
            "portActivityCode.in=" + DEFAULT_PORT_ACTIVITY_CODE + "," + UPDATED_PORT_ACTIVITY_CODE,
            "portActivityCode.in=" + UPDATED_PORT_ACTIVITY_CODE
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByPortActivityCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where portActivityCode is not null
        defaultConsumptionLineFiltering("portActivityCode.specified=true", "portActivityCode.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByDiffCriterionCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where diffCriterionCode equals to
        defaultConsumptionLineFiltering(
            "diffCriterionCode.equals=" + DEFAULT_DIFF_CRITERION_CODE,
            "diffCriterionCode.equals=" + UPDATED_DIFF_CRITERION_CODE
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByDiffCriterionCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where diffCriterionCode in
        defaultConsumptionLineFiltering(
            "diffCriterionCode.in=" + DEFAULT_DIFF_CRITERION_CODE + "," + UPDATED_DIFF_CRITERION_CODE,
            "diffCriterionCode.in=" + UPDATED_DIFF_CRITERION_CODE
        );
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByDiffCriterionCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        // Get all the consumptionLineList where diffCriterionCode is not null
        defaultConsumptionLineFiltering("diffCriterionCode.specified=true", "diffCriterionCode.specified=false");
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByEventReportIsEqualToSomething() throws Exception {
        EventReport eventReport;
        if (TestUtil.findAll(em, EventReport.class).isEmpty()) {
            consumptionLineRepository.saveAndFlush(consumptionLine);
            eventReport = EventReportResourceIT.createEntity(em);
        } else {
            eventReport = TestUtil.findAll(em, EventReport.class).get(0);
        }
        em.persist(eventReport);
        em.flush();
        consumptionLine.setEventReport(eventReport);
        consumptionLineRepository.saveAndFlush(consumptionLine);
        Long eventReportId = eventReport.getId();
        // Get all the consumptionLineList where eventReport equals to eventReportId
        defaultConsumptionLineShouldBeFound("eventReportId.equals=" + eventReportId);

        // Get all the consumptionLineList where eventReport equals to (eventReportId + 1)
        defaultConsumptionLineShouldNotBeFound("eventReportId.equals=" + (eventReportId + 1));
    }

    @Test
    @Transactional
    void getAllConsumptionLinesByFuelTypeIsEqualToSomething() throws Exception {
        FuelType fuelType;
        if (TestUtil.findAll(em, FuelType.class).isEmpty()) {
            consumptionLineRepository.saveAndFlush(consumptionLine);
            fuelType = FuelTypeResourceIT.createEntity();
        } else {
            fuelType = TestUtil.findAll(em, FuelType.class).get(0);
        }
        em.persist(fuelType);
        em.flush();
        consumptionLine.setFuelType(fuelType);
        consumptionLineRepository.saveAndFlush(consumptionLine);
        Long fuelTypeId = fuelType.getId();
        // Get all the consumptionLineList where fuelType equals to fuelTypeId
        defaultConsumptionLineShouldBeFound("fuelTypeId.equals=" + fuelTypeId);

        // Get all the consumptionLineList where fuelType equals to (fuelTypeId + 1)
        defaultConsumptionLineShouldNotBeFound("fuelTypeId.equals=" + (fuelTypeId + 1));
    }

    private void defaultConsumptionLineFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultConsumptionLineShouldBeFound(shouldBeFound);
        defaultConsumptionLineShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConsumptionLineShouldBeFound(String filter) throws Exception {
        restConsumptionLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumptionLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(sameNumber(DEFAULT_QUANTITY))))
            .andExpect(jsonPath("$.[*].unitOfMeasure").value(hasItem(DEFAULT_UNIT_OF_MEASURE.toString())))
            .andExpect(jsonPath("$.[*].co2EmissionSourceTypeCode").value(hasItem(DEFAULT_CO_2_EMISSION_SOURCE_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].lowerCalorificValue").value(hasItem(sameNumber(DEFAULT_LOWER_CALORIFIC_VALUE))))
            .andExpect(jsonPath("$.[*].sulphurContent").value(hasItem(sameNumber(DEFAULT_SULPHUR_CONTENT))))
            .andExpect(jsonPath("$.[*].density").value(hasItem(sameNumber(DEFAULT_DENSITY))))
            .andExpect(jsonPath("$.[*].viscosity").value(hasItem(sameNumber(DEFAULT_VISCOSITY))))
            .andExpect(jsonPath("$.[*].waterContent").value(hasItem(sameNumber(DEFAULT_WATER_CONTENT))))
            .andExpect(jsonPath("$.[*].portActivityCode").value(hasItem(DEFAULT_PORT_ACTIVITY_CODE.toString())))
            .andExpect(jsonPath("$.[*].diffCriterionCode").value(hasItem(DEFAULT_DIFF_CRITERION_CODE.toString())));

        // Check, that the count call also returns 1
        restConsumptionLineMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConsumptionLineShouldNotBeFound(String filter) throws Exception {
        restConsumptionLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConsumptionLineMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConsumptionLine() throws Exception {
        // Get the consumptionLine
        restConsumptionLineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConsumptionLine() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the consumptionLine
        ConsumptionLine updatedConsumptionLine = consumptionLineRepository.findById(consumptionLine.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedConsumptionLine are not directly saved in db
        em.detach(updatedConsumptionLine);
        updatedConsumptionLine
            .quantity(UPDATED_QUANTITY)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .co2EmissionSourceTypeCode(UPDATED_CO_2_EMISSION_SOURCE_TYPE_CODE)
            .lowerCalorificValue(UPDATED_LOWER_CALORIFIC_VALUE)
            .sulphurContent(UPDATED_SULPHUR_CONTENT)
            .density(UPDATED_DENSITY)
            .viscosity(UPDATED_VISCOSITY)
            .waterContent(UPDATED_WATER_CONTENT)
            .portActivityCode(UPDATED_PORT_ACTIVITY_CODE)
            .diffCriterionCode(UPDATED_DIFF_CRITERION_CODE);
        ConsumptionLineDTO consumptionLineDTO = consumptionLineMapper.toDto(updatedConsumptionLine);

        restConsumptionLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consumptionLineDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(consumptionLineDTO))
            )
            .andExpect(status().isOk());

        // Validate the ConsumptionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedConsumptionLineToMatchAllProperties(updatedConsumptionLine);
    }

    @Test
    @Transactional
    void putNonExistingConsumptionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        consumptionLine.setId(longCount.incrementAndGet());

        // Create the ConsumptionLine
        ConsumptionLineDTO consumptionLineDTO = consumptionLineMapper.toDto(consumptionLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumptionLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consumptionLineDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(consumptionLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConsumptionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        consumptionLine.setId(longCount.incrementAndGet());

        // Create the ConsumptionLine
        ConsumptionLineDTO consumptionLineDTO = consumptionLineMapper.toDto(consumptionLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumptionLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(consumptionLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConsumptionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        consumptionLine.setId(longCount.incrementAndGet());

        // Create the ConsumptionLine
        ConsumptionLineDTO consumptionLineDTO = consumptionLineMapper.toDto(consumptionLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumptionLineMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(consumptionLineDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConsumptionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConsumptionLineWithPatch() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the consumptionLine using partial update
        ConsumptionLine partialUpdatedConsumptionLine = new ConsumptionLine();
        partialUpdatedConsumptionLine.setId(consumptionLine.getId());

        partialUpdatedConsumptionLine
            .quantity(UPDATED_QUANTITY)
            .density(UPDATED_DENSITY)
            .viscosity(UPDATED_VISCOSITY)
            .diffCriterionCode(UPDATED_DIFF_CRITERION_CODE);

        restConsumptionLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsumptionLine.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConsumptionLine))
            )
            .andExpect(status().isOk());

        // Validate the ConsumptionLine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConsumptionLineUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedConsumptionLine, consumptionLine),
            getPersistedConsumptionLine(consumptionLine)
        );
    }

    @Test
    @Transactional
    void fullUpdateConsumptionLineWithPatch() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the consumptionLine using partial update
        ConsumptionLine partialUpdatedConsumptionLine = new ConsumptionLine();
        partialUpdatedConsumptionLine.setId(consumptionLine.getId());

        partialUpdatedConsumptionLine
            .quantity(UPDATED_QUANTITY)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .co2EmissionSourceTypeCode(UPDATED_CO_2_EMISSION_SOURCE_TYPE_CODE)
            .lowerCalorificValue(UPDATED_LOWER_CALORIFIC_VALUE)
            .sulphurContent(UPDATED_SULPHUR_CONTENT)
            .density(UPDATED_DENSITY)
            .viscosity(UPDATED_VISCOSITY)
            .waterContent(UPDATED_WATER_CONTENT)
            .portActivityCode(UPDATED_PORT_ACTIVITY_CODE)
            .diffCriterionCode(UPDATED_DIFF_CRITERION_CODE);

        restConsumptionLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsumptionLine.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConsumptionLine))
            )
            .andExpect(status().isOk());

        // Validate the ConsumptionLine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConsumptionLineUpdatableFieldsEquals(
            partialUpdatedConsumptionLine,
            getPersistedConsumptionLine(partialUpdatedConsumptionLine)
        );
    }

    @Test
    @Transactional
    void patchNonExistingConsumptionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        consumptionLine.setId(longCount.incrementAndGet());

        // Create the ConsumptionLine
        ConsumptionLineDTO consumptionLineDTO = consumptionLineMapper.toDto(consumptionLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumptionLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, consumptionLineDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(consumptionLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConsumptionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        consumptionLine.setId(longCount.incrementAndGet());

        // Create the ConsumptionLine
        ConsumptionLineDTO consumptionLineDTO = consumptionLineMapper.toDto(consumptionLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumptionLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(consumptionLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsumptionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConsumptionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        consumptionLine.setId(longCount.incrementAndGet());

        // Create the ConsumptionLine
        ConsumptionLineDTO consumptionLineDTO = consumptionLineMapper.toDto(consumptionLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsumptionLineMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(consumptionLineDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConsumptionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConsumptionLine() throws Exception {
        // Initialize the database
        insertedConsumptionLine = consumptionLineRepository.saveAndFlush(consumptionLine);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the consumptionLine
        restConsumptionLineMockMvc
            .perform(delete(ENTITY_API_URL_ID, consumptionLine.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return consumptionLineRepository.count();
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

    protected ConsumptionLine getPersistedConsumptionLine(ConsumptionLine consumptionLine) {
        return consumptionLineRepository.findById(consumptionLine.getId()).orElseThrow();
    }

    protected void assertPersistedConsumptionLineToMatchAllProperties(ConsumptionLine expectedConsumptionLine) {
        assertConsumptionLineAllPropertiesEquals(expectedConsumptionLine, getPersistedConsumptionLine(expectedConsumptionLine));
    }

    protected void assertPersistedConsumptionLineToMatchUpdatableProperties(ConsumptionLine expectedConsumptionLine) {
        assertConsumptionLineAllUpdatablePropertiesEquals(expectedConsumptionLine, getPersistedConsumptionLine(expectedConsumptionLine));
    }
}
