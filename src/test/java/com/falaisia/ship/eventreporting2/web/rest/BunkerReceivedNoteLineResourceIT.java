package com.falaisia.ship.eventreporting2.web.rest;

import static com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteLineAsserts.*;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.createUpdateProxyForBean;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.falaisia.ship.eventreporting2.IntegrationTest;
import com.falaisia.ship.eventreporting2.domain.BunkerReceivedNote;
import com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteLine;
import com.falaisia.ship.eventreporting2.domain.FuelType;
import com.falaisia.ship.eventreporting2.domain.enumeration.UnitOfMeasure;
import com.falaisia.ship.eventreporting2.repository.BunkerReceivedNoteLineRepository;
import com.falaisia.ship.eventreporting2.service.dto.BunkerReceivedNoteLineDTO;
import com.falaisia.ship.eventreporting2.service.mapper.BunkerReceivedNoteLineMapper;
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
 * Integration tests for the {@link BunkerReceivedNoteLineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BunkerReceivedNoteLineResourceIT {

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(1 - 1);

    private static final UnitOfMeasure DEFAULT_UNIT_OF_MEASURE = UnitOfMeasure.M_TONNES;
    private static final UnitOfMeasure UPDATED_UNIT_OF_MEASURE = UnitOfMeasure.M3;

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

    private static final String ENTITY_API_URL = "/api/bunker-received-note-lines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BunkerReceivedNoteLineRepository bunkerReceivedNoteLineRepository;

    @Autowired
    private BunkerReceivedNoteLineMapper bunkerReceivedNoteLineMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBunkerReceivedNoteLineMockMvc;

    private BunkerReceivedNoteLine bunkerReceivedNoteLine;

    private BunkerReceivedNoteLine insertedBunkerReceivedNoteLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BunkerReceivedNoteLine createEntity(EntityManager em) {
        BunkerReceivedNoteLine bunkerReceivedNoteLine = new BunkerReceivedNoteLine()
            .quantity(DEFAULT_QUANTITY)
            .unitOfMeasure(DEFAULT_UNIT_OF_MEASURE)
            .lowerCalorificValue(DEFAULT_LOWER_CALORIFIC_VALUE)
            .sulphurContent(DEFAULT_SULPHUR_CONTENT)
            .density(DEFAULT_DENSITY)
            .viscosity(DEFAULT_VISCOSITY)
            .waterContent(DEFAULT_WATER_CONTENT);
        // Add required entity
        BunkerReceivedNote bunkerReceivedNote;
        if (TestUtil.findAll(em, BunkerReceivedNote.class).isEmpty()) {
            bunkerReceivedNote = BunkerReceivedNoteResourceIT.createEntity(em);
            em.persist(bunkerReceivedNote);
            em.flush();
        } else {
            bunkerReceivedNote = TestUtil.findAll(em, BunkerReceivedNote.class).get(0);
        }
        bunkerReceivedNoteLine.setBunkerReceivedNote(bunkerReceivedNote);
        // Add required entity
        FuelType fuelType;
        if (TestUtil.findAll(em, FuelType.class).isEmpty()) {
            fuelType = FuelTypeResourceIT.createEntity();
            em.persist(fuelType);
            em.flush();
        } else {
            fuelType = TestUtil.findAll(em, FuelType.class).get(0);
        }
        bunkerReceivedNoteLine.setFuelType(fuelType);
        return bunkerReceivedNoteLine;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BunkerReceivedNoteLine createUpdatedEntity(EntityManager em) {
        BunkerReceivedNoteLine updatedBunkerReceivedNoteLine = new BunkerReceivedNoteLine()
            .quantity(UPDATED_QUANTITY)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .lowerCalorificValue(UPDATED_LOWER_CALORIFIC_VALUE)
            .sulphurContent(UPDATED_SULPHUR_CONTENT)
            .density(UPDATED_DENSITY)
            .viscosity(UPDATED_VISCOSITY)
            .waterContent(UPDATED_WATER_CONTENT);
        // Add required entity
        BunkerReceivedNote bunkerReceivedNote;
        if (TestUtil.findAll(em, BunkerReceivedNote.class).isEmpty()) {
            bunkerReceivedNote = BunkerReceivedNoteResourceIT.createUpdatedEntity(em);
            em.persist(bunkerReceivedNote);
            em.flush();
        } else {
            bunkerReceivedNote = TestUtil.findAll(em, BunkerReceivedNote.class).get(0);
        }
        updatedBunkerReceivedNoteLine.setBunkerReceivedNote(bunkerReceivedNote);
        // Add required entity
        FuelType fuelType;
        if (TestUtil.findAll(em, FuelType.class).isEmpty()) {
            fuelType = FuelTypeResourceIT.createUpdatedEntity();
            em.persist(fuelType);
            em.flush();
        } else {
            fuelType = TestUtil.findAll(em, FuelType.class).get(0);
        }
        updatedBunkerReceivedNoteLine.setFuelType(fuelType);
        return updatedBunkerReceivedNoteLine;
    }

    @BeforeEach
    void initTest() {
        bunkerReceivedNoteLine = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedBunkerReceivedNoteLine != null) {
            bunkerReceivedNoteLineRepository.delete(insertedBunkerReceivedNoteLine);
            insertedBunkerReceivedNoteLine = null;
        }
    }

    @Test
    @Transactional
    void createBunkerReceivedNoteLine() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BunkerReceivedNoteLine
        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO = bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLine);
        var returnedBunkerReceivedNoteLineDTO = om.readValue(
            restBunkerReceivedNoteLineMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(bunkerReceivedNoteLineDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BunkerReceivedNoteLineDTO.class
        );

        // Validate the BunkerReceivedNoteLine in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBunkerReceivedNoteLine = bunkerReceivedNoteLineMapper.toEntity(returnedBunkerReceivedNoteLineDTO);
        assertBunkerReceivedNoteLineUpdatableFieldsEquals(
            returnedBunkerReceivedNoteLine,
            getPersistedBunkerReceivedNoteLine(returnedBunkerReceivedNoteLine)
        );

        insertedBunkerReceivedNoteLine = returnedBunkerReceivedNoteLine;
    }

    @Test
    @Transactional
    void createBunkerReceivedNoteLineWithExistingId() throws Exception {
        // Create the BunkerReceivedNoteLine with an existing ID
        bunkerReceivedNoteLine.setId(1L);
        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO = bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLine);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBunkerReceivedNoteLineMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bunkerReceivedNoteLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNoteLine in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bunkerReceivedNoteLine.setQuantity(null);

        // Create the BunkerReceivedNoteLine, which fails.
        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO = bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLine);

        restBunkerReceivedNoteLineMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bunkerReceivedNoteLineDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnitOfMeasureIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bunkerReceivedNoteLine.setUnitOfMeasure(null);

        // Create the BunkerReceivedNoteLine, which fails.
        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO = bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLine);

        restBunkerReceivedNoteLineMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bunkerReceivedNoteLineDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLines() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList
        restBunkerReceivedNoteLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bunkerReceivedNoteLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(sameNumber(DEFAULT_QUANTITY))))
            .andExpect(jsonPath("$.[*].unitOfMeasure").value(hasItem(DEFAULT_UNIT_OF_MEASURE.toString())))
            .andExpect(jsonPath("$.[*].lowerCalorificValue").value(hasItem(sameNumber(DEFAULT_LOWER_CALORIFIC_VALUE))))
            .andExpect(jsonPath("$.[*].sulphurContent").value(hasItem(sameNumber(DEFAULT_SULPHUR_CONTENT))))
            .andExpect(jsonPath("$.[*].density").value(hasItem(sameNumber(DEFAULT_DENSITY))))
            .andExpect(jsonPath("$.[*].viscosity").value(hasItem(sameNumber(DEFAULT_VISCOSITY))))
            .andExpect(jsonPath("$.[*].waterContent").value(hasItem(sameNumber(DEFAULT_WATER_CONTENT))));
    }

    @Test
    @Transactional
    void getBunkerReceivedNoteLine() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get the bunkerReceivedNoteLine
        restBunkerReceivedNoteLineMockMvc
            .perform(get(ENTITY_API_URL_ID, bunkerReceivedNoteLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bunkerReceivedNoteLine.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(sameNumber(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.unitOfMeasure").value(DEFAULT_UNIT_OF_MEASURE.toString()))
            .andExpect(jsonPath("$.lowerCalorificValue").value(sameNumber(DEFAULT_LOWER_CALORIFIC_VALUE)))
            .andExpect(jsonPath("$.sulphurContent").value(sameNumber(DEFAULT_SULPHUR_CONTENT)))
            .andExpect(jsonPath("$.density").value(sameNumber(DEFAULT_DENSITY)))
            .andExpect(jsonPath("$.viscosity").value(sameNumber(DEFAULT_VISCOSITY)))
            .andExpect(jsonPath("$.waterContent").value(sameNumber(DEFAULT_WATER_CONTENT)));
    }

    @Test
    @Transactional
    void getBunkerReceivedNoteLinesByIdFiltering() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        Long id = bunkerReceivedNoteLine.getId();

        defaultBunkerReceivedNoteLineFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultBunkerReceivedNoteLineFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultBunkerReceivedNoteLineFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where quantity equals to
        defaultBunkerReceivedNoteLineFiltering("quantity.equals=" + DEFAULT_QUANTITY, "quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where quantity in
        defaultBunkerReceivedNoteLineFiltering(
            "quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY,
            "quantity.in=" + UPDATED_QUANTITY
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where quantity is not null
        defaultBunkerReceivedNoteLineFiltering("quantity.specified=true", "quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where quantity is greater than or equal to
        defaultBunkerReceivedNoteLineFiltering(
            "quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY,
            "quantity.greaterThanOrEqual=" + UPDATED_QUANTITY
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where quantity is less than or equal to
        defaultBunkerReceivedNoteLineFiltering(
            "quantity.lessThanOrEqual=" + DEFAULT_QUANTITY,
            "quantity.lessThanOrEqual=" + SMALLER_QUANTITY
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where quantity is less than
        defaultBunkerReceivedNoteLineFiltering("quantity.lessThan=" + UPDATED_QUANTITY, "quantity.lessThan=" + DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where quantity is greater than
        defaultBunkerReceivedNoteLineFiltering("quantity.greaterThan=" + SMALLER_QUANTITY, "quantity.greaterThan=" + DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByUnitOfMeasureIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where unitOfMeasure equals to
        defaultBunkerReceivedNoteLineFiltering(
            "unitOfMeasure.equals=" + DEFAULT_UNIT_OF_MEASURE,
            "unitOfMeasure.equals=" + UPDATED_UNIT_OF_MEASURE
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByUnitOfMeasureIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where unitOfMeasure in
        defaultBunkerReceivedNoteLineFiltering(
            "unitOfMeasure.in=" + DEFAULT_UNIT_OF_MEASURE + "," + UPDATED_UNIT_OF_MEASURE,
            "unitOfMeasure.in=" + UPDATED_UNIT_OF_MEASURE
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByUnitOfMeasureIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where unitOfMeasure is not null
        defaultBunkerReceivedNoteLineFiltering("unitOfMeasure.specified=true", "unitOfMeasure.specified=false");
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByLowerCalorificValueIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where lowerCalorificValue equals to
        defaultBunkerReceivedNoteLineFiltering(
            "lowerCalorificValue.equals=" + DEFAULT_LOWER_CALORIFIC_VALUE,
            "lowerCalorificValue.equals=" + UPDATED_LOWER_CALORIFIC_VALUE
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByLowerCalorificValueIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where lowerCalorificValue in
        defaultBunkerReceivedNoteLineFiltering(
            "lowerCalorificValue.in=" + DEFAULT_LOWER_CALORIFIC_VALUE + "," + UPDATED_LOWER_CALORIFIC_VALUE,
            "lowerCalorificValue.in=" + UPDATED_LOWER_CALORIFIC_VALUE
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByLowerCalorificValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where lowerCalorificValue is not null
        defaultBunkerReceivedNoteLineFiltering("lowerCalorificValue.specified=true", "lowerCalorificValue.specified=false");
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByLowerCalorificValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where lowerCalorificValue is greater than or equal to
        defaultBunkerReceivedNoteLineFiltering(
            "lowerCalorificValue.greaterThanOrEqual=" + DEFAULT_LOWER_CALORIFIC_VALUE,
            "lowerCalorificValue.greaterThanOrEqual=" + UPDATED_LOWER_CALORIFIC_VALUE
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByLowerCalorificValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where lowerCalorificValue is less than or equal to
        defaultBunkerReceivedNoteLineFiltering(
            "lowerCalorificValue.lessThanOrEqual=" + DEFAULT_LOWER_CALORIFIC_VALUE,
            "lowerCalorificValue.lessThanOrEqual=" + SMALLER_LOWER_CALORIFIC_VALUE
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByLowerCalorificValueIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where lowerCalorificValue is less than
        defaultBunkerReceivedNoteLineFiltering(
            "lowerCalorificValue.lessThan=" + UPDATED_LOWER_CALORIFIC_VALUE,
            "lowerCalorificValue.lessThan=" + DEFAULT_LOWER_CALORIFIC_VALUE
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByLowerCalorificValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where lowerCalorificValue is greater than
        defaultBunkerReceivedNoteLineFiltering(
            "lowerCalorificValue.greaterThan=" + SMALLER_LOWER_CALORIFIC_VALUE,
            "lowerCalorificValue.greaterThan=" + DEFAULT_LOWER_CALORIFIC_VALUE
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesBySulphurContentIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where sulphurContent equals to
        defaultBunkerReceivedNoteLineFiltering(
            "sulphurContent.equals=" + DEFAULT_SULPHUR_CONTENT,
            "sulphurContent.equals=" + UPDATED_SULPHUR_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesBySulphurContentIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where sulphurContent in
        defaultBunkerReceivedNoteLineFiltering(
            "sulphurContent.in=" + DEFAULT_SULPHUR_CONTENT + "," + UPDATED_SULPHUR_CONTENT,
            "sulphurContent.in=" + UPDATED_SULPHUR_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesBySulphurContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where sulphurContent is not null
        defaultBunkerReceivedNoteLineFiltering("sulphurContent.specified=true", "sulphurContent.specified=false");
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesBySulphurContentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where sulphurContent is greater than or equal to
        defaultBunkerReceivedNoteLineFiltering(
            "sulphurContent.greaterThanOrEqual=" + DEFAULT_SULPHUR_CONTENT,
            "sulphurContent.greaterThanOrEqual=" + UPDATED_SULPHUR_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesBySulphurContentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where sulphurContent is less than or equal to
        defaultBunkerReceivedNoteLineFiltering(
            "sulphurContent.lessThanOrEqual=" + DEFAULT_SULPHUR_CONTENT,
            "sulphurContent.lessThanOrEqual=" + SMALLER_SULPHUR_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesBySulphurContentIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where sulphurContent is less than
        defaultBunkerReceivedNoteLineFiltering(
            "sulphurContent.lessThan=" + UPDATED_SULPHUR_CONTENT,
            "sulphurContent.lessThan=" + DEFAULT_SULPHUR_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesBySulphurContentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where sulphurContent is greater than
        defaultBunkerReceivedNoteLineFiltering(
            "sulphurContent.greaterThan=" + SMALLER_SULPHUR_CONTENT,
            "sulphurContent.greaterThan=" + DEFAULT_SULPHUR_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByDensityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where density equals to
        defaultBunkerReceivedNoteLineFiltering("density.equals=" + DEFAULT_DENSITY, "density.equals=" + UPDATED_DENSITY);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByDensityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where density in
        defaultBunkerReceivedNoteLineFiltering("density.in=" + DEFAULT_DENSITY + "," + UPDATED_DENSITY, "density.in=" + UPDATED_DENSITY);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByDensityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where density is not null
        defaultBunkerReceivedNoteLineFiltering("density.specified=true", "density.specified=false");
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByDensityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where density is greater than or equal to
        defaultBunkerReceivedNoteLineFiltering(
            "density.greaterThanOrEqual=" + DEFAULT_DENSITY,
            "density.greaterThanOrEqual=" + UPDATED_DENSITY
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByDensityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where density is less than or equal to
        defaultBunkerReceivedNoteLineFiltering("density.lessThanOrEqual=" + DEFAULT_DENSITY, "density.lessThanOrEqual=" + SMALLER_DENSITY);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByDensityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where density is less than
        defaultBunkerReceivedNoteLineFiltering("density.lessThan=" + UPDATED_DENSITY, "density.lessThan=" + DEFAULT_DENSITY);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByDensityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where density is greater than
        defaultBunkerReceivedNoteLineFiltering("density.greaterThan=" + SMALLER_DENSITY, "density.greaterThan=" + DEFAULT_DENSITY);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByViscosityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where viscosity equals to
        defaultBunkerReceivedNoteLineFiltering("viscosity.equals=" + DEFAULT_VISCOSITY, "viscosity.equals=" + UPDATED_VISCOSITY);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByViscosityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where viscosity in
        defaultBunkerReceivedNoteLineFiltering(
            "viscosity.in=" + DEFAULT_VISCOSITY + "," + UPDATED_VISCOSITY,
            "viscosity.in=" + UPDATED_VISCOSITY
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByViscosityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where viscosity is not null
        defaultBunkerReceivedNoteLineFiltering("viscosity.specified=true", "viscosity.specified=false");
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByViscosityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where viscosity is greater than or equal to
        defaultBunkerReceivedNoteLineFiltering(
            "viscosity.greaterThanOrEqual=" + DEFAULT_VISCOSITY,
            "viscosity.greaterThanOrEqual=" + UPDATED_VISCOSITY
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByViscosityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where viscosity is less than or equal to
        defaultBunkerReceivedNoteLineFiltering(
            "viscosity.lessThanOrEqual=" + DEFAULT_VISCOSITY,
            "viscosity.lessThanOrEqual=" + SMALLER_VISCOSITY
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByViscosityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where viscosity is less than
        defaultBunkerReceivedNoteLineFiltering("viscosity.lessThan=" + UPDATED_VISCOSITY, "viscosity.lessThan=" + DEFAULT_VISCOSITY);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByViscosityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where viscosity is greater than
        defaultBunkerReceivedNoteLineFiltering("viscosity.greaterThan=" + SMALLER_VISCOSITY, "viscosity.greaterThan=" + DEFAULT_VISCOSITY);
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByWaterContentIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where waterContent equals to
        defaultBunkerReceivedNoteLineFiltering(
            "waterContent.equals=" + DEFAULT_WATER_CONTENT,
            "waterContent.equals=" + UPDATED_WATER_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByWaterContentIsInShouldWork() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where waterContent in
        defaultBunkerReceivedNoteLineFiltering(
            "waterContent.in=" + DEFAULT_WATER_CONTENT + "," + UPDATED_WATER_CONTENT,
            "waterContent.in=" + UPDATED_WATER_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByWaterContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where waterContent is not null
        defaultBunkerReceivedNoteLineFiltering("waterContent.specified=true", "waterContent.specified=false");
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByWaterContentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where waterContent is greater than or equal to
        defaultBunkerReceivedNoteLineFiltering(
            "waterContent.greaterThanOrEqual=" + DEFAULT_WATER_CONTENT,
            "waterContent.greaterThanOrEqual=" + UPDATED_WATER_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByWaterContentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where waterContent is less than or equal to
        defaultBunkerReceivedNoteLineFiltering(
            "waterContent.lessThanOrEqual=" + DEFAULT_WATER_CONTENT,
            "waterContent.lessThanOrEqual=" + SMALLER_WATER_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByWaterContentIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where waterContent is less than
        defaultBunkerReceivedNoteLineFiltering(
            "waterContent.lessThan=" + UPDATED_WATER_CONTENT,
            "waterContent.lessThan=" + DEFAULT_WATER_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByWaterContentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        // Get all the bunkerReceivedNoteLineList where waterContent is greater than
        defaultBunkerReceivedNoteLineFiltering(
            "waterContent.greaterThan=" + SMALLER_WATER_CONTENT,
            "waterContent.greaterThan=" + DEFAULT_WATER_CONTENT
        );
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByBunkerReceivedNoteIsEqualToSomething() throws Exception {
        BunkerReceivedNote bunkerReceivedNote;
        if (TestUtil.findAll(em, BunkerReceivedNote.class).isEmpty()) {
            bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);
            bunkerReceivedNote = BunkerReceivedNoteResourceIT.createEntity(em);
        } else {
            bunkerReceivedNote = TestUtil.findAll(em, BunkerReceivedNote.class).get(0);
        }
        em.persist(bunkerReceivedNote);
        em.flush();
        bunkerReceivedNoteLine.setBunkerReceivedNote(bunkerReceivedNote);
        bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);
        Long bunkerReceivedNoteId = bunkerReceivedNote.getId();
        // Get all the bunkerReceivedNoteLineList where bunkerReceivedNote equals to bunkerReceivedNoteId
        defaultBunkerReceivedNoteLineShouldBeFound("bunkerReceivedNoteId.equals=" + bunkerReceivedNoteId);

        // Get all the bunkerReceivedNoteLineList where bunkerReceivedNote equals to (bunkerReceivedNoteId + 1)
        defaultBunkerReceivedNoteLineShouldNotBeFound("bunkerReceivedNoteId.equals=" + (bunkerReceivedNoteId + 1));
    }

    @Test
    @Transactional
    void getAllBunkerReceivedNoteLinesByFuelTypeIsEqualToSomething() throws Exception {
        FuelType fuelType;
        if (TestUtil.findAll(em, FuelType.class).isEmpty()) {
            bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);
            fuelType = FuelTypeResourceIT.createEntity();
        } else {
            fuelType = TestUtil.findAll(em, FuelType.class).get(0);
        }
        em.persist(fuelType);
        em.flush();
        bunkerReceivedNoteLine.setFuelType(fuelType);
        bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);
        Long fuelTypeId = fuelType.getId();
        // Get all the bunkerReceivedNoteLineList where fuelType equals to fuelTypeId
        defaultBunkerReceivedNoteLineShouldBeFound("fuelTypeId.equals=" + fuelTypeId);

        // Get all the bunkerReceivedNoteLineList where fuelType equals to (fuelTypeId + 1)
        defaultBunkerReceivedNoteLineShouldNotBeFound("fuelTypeId.equals=" + (fuelTypeId + 1));
    }

    private void defaultBunkerReceivedNoteLineFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultBunkerReceivedNoteLineShouldBeFound(shouldBeFound);
        defaultBunkerReceivedNoteLineShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBunkerReceivedNoteLineShouldBeFound(String filter) throws Exception {
        restBunkerReceivedNoteLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bunkerReceivedNoteLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(sameNumber(DEFAULT_QUANTITY))))
            .andExpect(jsonPath("$.[*].unitOfMeasure").value(hasItem(DEFAULT_UNIT_OF_MEASURE.toString())))
            .andExpect(jsonPath("$.[*].lowerCalorificValue").value(hasItem(sameNumber(DEFAULT_LOWER_CALORIFIC_VALUE))))
            .andExpect(jsonPath("$.[*].sulphurContent").value(hasItem(sameNumber(DEFAULT_SULPHUR_CONTENT))))
            .andExpect(jsonPath("$.[*].density").value(hasItem(sameNumber(DEFAULT_DENSITY))))
            .andExpect(jsonPath("$.[*].viscosity").value(hasItem(sameNumber(DEFAULT_VISCOSITY))))
            .andExpect(jsonPath("$.[*].waterContent").value(hasItem(sameNumber(DEFAULT_WATER_CONTENT))));

        // Check, that the count call also returns 1
        restBunkerReceivedNoteLineMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBunkerReceivedNoteLineShouldNotBeFound(String filter) throws Exception {
        restBunkerReceivedNoteLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBunkerReceivedNoteLineMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBunkerReceivedNoteLine() throws Exception {
        // Get the bunkerReceivedNoteLine
        restBunkerReceivedNoteLineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBunkerReceivedNoteLine() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bunkerReceivedNoteLine
        BunkerReceivedNoteLine updatedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository
            .findById(bunkerReceivedNoteLine.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedBunkerReceivedNoteLine are not directly saved in db
        em.detach(updatedBunkerReceivedNoteLine);
        updatedBunkerReceivedNoteLine
            .quantity(UPDATED_QUANTITY)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .lowerCalorificValue(UPDATED_LOWER_CALORIFIC_VALUE)
            .sulphurContent(UPDATED_SULPHUR_CONTENT)
            .density(UPDATED_DENSITY)
            .viscosity(UPDATED_VISCOSITY)
            .waterContent(UPDATED_WATER_CONTENT);
        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO = bunkerReceivedNoteLineMapper.toDto(updatedBunkerReceivedNoteLine);

        restBunkerReceivedNoteLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bunkerReceivedNoteLineDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bunkerReceivedNoteLineDTO))
            )
            .andExpect(status().isOk());

        // Validate the BunkerReceivedNoteLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBunkerReceivedNoteLineToMatchAllProperties(updatedBunkerReceivedNoteLine);
    }

    @Test
    @Transactional
    void putNonExistingBunkerReceivedNoteLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bunkerReceivedNoteLine.setId(longCount.incrementAndGet());

        // Create the BunkerReceivedNoteLine
        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO = bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bunkerReceivedNoteLineDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bunkerReceivedNoteLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNoteLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBunkerReceivedNoteLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bunkerReceivedNoteLine.setId(longCount.incrementAndGet());

        // Create the BunkerReceivedNoteLine
        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO = bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bunkerReceivedNoteLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNoteLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBunkerReceivedNoteLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bunkerReceivedNoteLine.setId(longCount.incrementAndGet());

        // Create the BunkerReceivedNoteLine
        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO = bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteLineMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bunkerReceivedNoteLineDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BunkerReceivedNoteLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBunkerReceivedNoteLineWithPatch() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bunkerReceivedNoteLine using partial update
        BunkerReceivedNoteLine partialUpdatedBunkerReceivedNoteLine = new BunkerReceivedNoteLine();
        partialUpdatedBunkerReceivedNoteLine.setId(bunkerReceivedNoteLine.getId());

        partialUpdatedBunkerReceivedNoteLine.waterContent(UPDATED_WATER_CONTENT);

        restBunkerReceivedNoteLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBunkerReceivedNoteLine.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBunkerReceivedNoteLine))
            )
            .andExpect(status().isOk());

        // Validate the BunkerReceivedNoteLine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBunkerReceivedNoteLineUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBunkerReceivedNoteLine, bunkerReceivedNoteLine),
            getPersistedBunkerReceivedNoteLine(bunkerReceivedNoteLine)
        );
    }

    @Test
    @Transactional
    void fullUpdateBunkerReceivedNoteLineWithPatch() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bunkerReceivedNoteLine using partial update
        BunkerReceivedNoteLine partialUpdatedBunkerReceivedNoteLine = new BunkerReceivedNoteLine();
        partialUpdatedBunkerReceivedNoteLine.setId(bunkerReceivedNoteLine.getId());

        partialUpdatedBunkerReceivedNoteLine
            .quantity(UPDATED_QUANTITY)
            .unitOfMeasure(UPDATED_UNIT_OF_MEASURE)
            .lowerCalorificValue(UPDATED_LOWER_CALORIFIC_VALUE)
            .sulphurContent(UPDATED_SULPHUR_CONTENT)
            .density(UPDATED_DENSITY)
            .viscosity(UPDATED_VISCOSITY)
            .waterContent(UPDATED_WATER_CONTENT);

        restBunkerReceivedNoteLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBunkerReceivedNoteLine.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBunkerReceivedNoteLine))
            )
            .andExpect(status().isOk());

        // Validate the BunkerReceivedNoteLine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBunkerReceivedNoteLineUpdatableFieldsEquals(
            partialUpdatedBunkerReceivedNoteLine,
            getPersistedBunkerReceivedNoteLine(partialUpdatedBunkerReceivedNoteLine)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBunkerReceivedNoteLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bunkerReceivedNoteLine.setId(longCount.incrementAndGet());

        // Create the BunkerReceivedNoteLine
        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO = bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bunkerReceivedNoteLineDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bunkerReceivedNoteLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNoteLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBunkerReceivedNoteLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bunkerReceivedNoteLine.setId(longCount.incrementAndGet());

        // Create the BunkerReceivedNoteLine
        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO = bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bunkerReceivedNoteLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BunkerReceivedNoteLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBunkerReceivedNoteLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bunkerReceivedNoteLine.setId(longCount.incrementAndGet());

        // Create the BunkerReceivedNoteLine
        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO = bunkerReceivedNoteLineMapper.toDto(bunkerReceivedNoteLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBunkerReceivedNoteLineMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bunkerReceivedNoteLineDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BunkerReceivedNoteLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBunkerReceivedNoteLine() throws Exception {
        // Initialize the database
        insertedBunkerReceivedNoteLine = bunkerReceivedNoteLineRepository.saveAndFlush(bunkerReceivedNoteLine);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bunkerReceivedNoteLine
        restBunkerReceivedNoteLineMockMvc
            .perform(delete(ENTITY_API_URL_ID, bunkerReceivedNoteLine.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bunkerReceivedNoteLineRepository.count();
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

    protected BunkerReceivedNoteLine getPersistedBunkerReceivedNoteLine(BunkerReceivedNoteLine bunkerReceivedNoteLine) {
        return bunkerReceivedNoteLineRepository.findById(bunkerReceivedNoteLine.getId()).orElseThrow();
    }

    protected void assertPersistedBunkerReceivedNoteLineToMatchAllProperties(BunkerReceivedNoteLine expectedBunkerReceivedNoteLine) {
        assertBunkerReceivedNoteLineAllPropertiesEquals(
            expectedBunkerReceivedNoteLine,
            getPersistedBunkerReceivedNoteLine(expectedBunkerReceivedNoteLine)
        );
    }

    protected void assertPersistedBunkerReceivedNoteLineToMatchUpdatableProperties(BunkerReceivedNoteLine expectedBunkerReceivedNoteLine) {
        assertBunkerReceivedNoteLineAllUpdatablePropertiesEquals(
            expectedBunkerReceivedNoteLine,
            getPersistedBunkerReceivedNoteLine(expectedBunkerReceivedNoteLine)
        );
    }
}
