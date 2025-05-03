package com.falaisia.ship.eventreporting2.web.rest;

import static com.falaisia.ship.eventreporting2.domain.ShipAsserts.*;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.falaisia.ship.eventreporting2.IntegrationTest;
import com.falaisia.ship.eventreporting2.domain.ClassificationSociety;
import com.falaisia.ship.eventreporting2.domain.Country;
import com.falaisia.ship.eventreporting2.domain.Flag;
import com.falaisia.ship.eventreporting2.domain.Ship;
import com.falaisia.ship.eventreporting2.domain.enumeration.IceClassPolarCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.MonitoringMethodCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.ShipType;
import com.falaisia.ship.eventreporting2.domain.enumeration.TechnicalEfficiencyCode;
import com.falaisia.ship.eventreporting2.repository.ShipRepository;
import com.falaisia.ship.eventreporting2.service.dto.ShipDTO;
import com.falaisia.ship.eventreporting2.service.mapper.ShipMapper;
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
 * Integration tests for the {@link ShipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShipResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CALL_SIGN = "AAAAAAAAAA";
    private static final String UPDATED_CALL_SIGN = "BBBBBBBBBB";

    private static final IceClassPolarCode DEFAULT_ICE_CLASS_POLAR_CODE = IceClassPolarCode.IA;
    private static final IceClassPolarCode UPDATED_ICE_CLASS_POLAR_CODE = IceClassPolarCode.IA_SUPER;

    private static final TechnicalEfficiencyCode DEFAULT_TECHNICAL_EFFICIENCY_CODE = TechnicalEfficiencyCode.EEDI;
    private static final TechnicalEfficiencyCode UPDATED_TECHNICAL_EFFICIENCY_CODE = TechnicalEfficiencyCode.EIV;

    private static final ShipType DEFAULT_SHIP_TYPE = ShipType.BULK;
    private static final ShipType UPDATED_SHIP_TYPE = ShipType.CHEM;

    private static final MonitoringMethodCode DEFAULT_MONITORING_METHOD_CODE = MonitoringMethodCode.BDN;
    private static final MonitoringMethodCode UPDATED_MONITORING_METHOD_CODE = MonitoringMethodCode.BUNKER_TANK;

    private static final String ENTITY_API_URL = "/api/ships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private ShipMapper shipMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShipMockMvc;

    private Ship ship;

    private Ship insertedShip;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ship createEntity() {
        return new Ship()
            .name(DEFAULT_NAME)
            .callSign(DEFAULT_CALL_SIGN)
            .iceClassPolarCode(DEFAULT_ICE_CLASS_POLAR_CODE)
            .technicalEfficiencyCode(DEFAULT_TECHNICAL_EFFICIENCY_CODE)
            .shipType(DEFAULT_SHIP_TYPE)
            .monitoringMethodCode(DEFAULT_MONITORING_METHOD_CODE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ship createUpdatedEntity() {
        return new Ship()
            .name(UPDATED_NAME)
            .callSign(UPDATED_CALL_SIGN)
            .iceClassPolarCode(UPDATED_ICE_CLASS_POLAR_CODE)
            .technicalEfficiencyCode(UPDATED_TECHNICAL_EFFICIENCY_CODE)
            .shipType(UPDATED_SHIP_TYPE)
            .monitoringMethodCode(UPDATED_MONITORING_METHOD_CODE);
    }

    @BeforeEach
    void initTest() {
        ship = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedShip != null) {
            shipRepository.delete(insertedShip);
            insertedShip = null;
        }
    }

    @Test
    @Transactional
    void createShip() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ship
        ShipDTO shipDTO = shipMapper.toDto(ship);
        var returnedShipDTO = om.readValue(
            restShipMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ShipDTO.class
        );

        // Validate the Ship in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedShip = shipMapper.toEntity(returnedShipDTO);
        assertShipUpdatableFieldsEquals(returnedShip, getPersistedShip(returnedShip));

        insertedShip = returnedShip;
    }

    @Test
    @Transactional
    void createShipWithExistingId() throws Exception {
        // Create the Ship with an existing ID
        ship.setId(1L);
        ShipDTO shipDTO = shipMapper.toDto(ship);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ship in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ship.setName(null);

        // Create the Ship, which fails.
        ShipDTO shipDTO = shipMapper.toDto(ship);

        restShipMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllShips() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList
        restShipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ship.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].callSign").value(hasItem(DEFAULT_CALL_SIGN)))
            .andExpect(jsonPath("$.[*].iceClassPolarCode").value(hasItem(DEFAULT_ICE_CLASS_POLAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].technicalEfficiencyCode").value(hasItem(DEFAULT_TECHNICAL_EFFICIENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].shipType").value(hasItem(DEFAULT_SHIP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].monitoringMethodCode").value(hasItem(DEFAULT_MONITORING_METHOD_CODE.toString())));
    }

    @Test
    @Transactional
    void getShip() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get the ship
        restShipMockMvc
            .perform(get(ENTITY_API_URL_ID, ship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ship.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.callSign").value(DEFAULT_CALL_SIGN))
            .andExpect(jsonPath("$.iceClassPolarCode").value(DEFAULT_ICE_CLASS_POLAR_CODE.toString()))
            .andExpect(jsonPath("$.technicalEfficiencyCode").value(DEFAULT_TECHNICAL_EFFICIENCY_CODE.toString()))
            .andExpect(jsonPath("$.shipType").value(DEFAULT_SHIP_TYPE.toString()))
            .andExpect(jsonPath("$.monitoringMethodCode").value(DEFAULT_MONITORING_METHOD_CODE.toString()));
    }

    @Test
    @Transactional
    void getShipsByIdFiltering() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        Long id = ship.getId();

        defaultShipFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultShipFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultShipFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllShipsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where name equals to
        defaultShipFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllShipsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where name in
        defaultShipFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllShipsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where name is not null
        defaultShipFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllShipsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where name contains
        defaultShipFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllShipsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where name does not contain
        defaultShipFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllShipsByCallSignIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where callSign equals to
        defaultShipFiltering("callSign.equals=" + DEFAULT_CALL_SIGN, "callSign.equals=" + UPDATED_CALL_SIGN);
    }

    @Test
    @Transactional
    void getAllShipsByCallSignIsInShouldWork() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where callSign in
        defaultShipFiltering("callSign.in=" + DEFAULT_CALL_SIGN + "," + UPDATED_CALL_SIGN, "callSign.in=" + UPDATED_CALL_SIGN);
    }

    @Test
    @Transactional
    void getAllShipsByCallSignIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where callSign is not null
        defaultShipFiltering("callSign.specified=true", "callSign.specified=false");
    }

    @Test
    @Transactional
    void getAllShipsByCallSignContainsSomething() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where callSign contains
        defaultShipFiltering("callSign.contains=" + DEFAULT_CALL_SIGN, "callSign.contains=" + UPDATED_CALL_SIGN);
    }

    @Test
    @Transactional
    void getAllShipsByCallSignNotContainsSomething() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where callSign does not contain
        defaultShipFiltering("callSign.doesNotContain=" + UPDATED_CALL_SIGN, "callSign.doesNotContain=" + DEFAULT_CALL_SIGN);
    }

    @Test
    @Transactional
    void getAllShipsByIceClassPolarCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where iceClassPolarCode equals to
        defaultShipFiltering(
            "iceClassPolarCode.equals=" + DEFAULT_ICE_CLASS_POLAR_CODE,
            "iceClassPolarCode.equals=" + UPDATED_ICE_CLASS_POLAR_CODE
        );
    }

    @Test
    @Transactional
    void getAllShipsByIceClassPolarCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where iceClassPolarCode in
        defaultShipFiltering(
            "iceClassPolarCode.in=" + DEFAULT_ICE_CLASS_POLAR_CODE + "," + UPDATED_ICE_CLASS_POLAR_CODE,
            "iceClassPolarCode.in=" + UPDATED_ICE_CLASS_POLAR_CODE
        );
    }

    @Test
    @Transactional
    void getAllShipsByIceClassPolarCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where iceClassPolarCode is not null
        defaultShipFiltering("iceClassPolarCode.specified=true", "iceClassPolarCode.specified=false");
    }

    @Test
    @Transactional
    void getAllShipsByTechnicalEfficiencyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where technicalEfficiencyCode equals to
        defaultShipFiltering(
            "technicalEfficiencyCode.equals=" + DEFAULT_TECHNICAL_EFFICIENCY_CODE,
            "technicalEfficiencyCode.equals=" + UPDATED_TECHNICAL_EFFICIENCY_CODE
        );
    }

    @Test
    @Transactional
    void getAllShipsByTechnicalEfficiencyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where technicalEfficiencyCode in
        defaultShipFiltering(
            "technicalEfficiencyCode.in=" + DEFAULT_TECHNICAL_EFFICIENCY_CODE + "," + UPDATED_TECHNICAL_EFFICIENCY_CODE,
            "technicalEfficiencyCode.in=" + UPDATED_TECHNICAL_EFFICIENCY_CODE
        );
    }

    @Test
    @Transactional
    void getAllShipsByTechnicalEfficiencyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where technicalEfficiencyCode is not null
        defaultShipFiltering("technicalEfficiencyCode.specified=true", "technicalEfficiencyCode.specified=false");
    }

    @Test
    @Transactional
    void getAllShipsByShipTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where shipType equals to
        defaultShipFiltering("shipType.equals=" + DEFAULT_SHIP_TYPE, "shipType.equals=" + UPDATED_SHIP_TYPE);
    }

    @Test
    @Transactional
    void getAllShipsByShipTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where shipType in
        defaultShipFiltering("shipType.in=" + DEFAULT_SHIP_TYPE + "," + UPDATED_SHIP_TYPE, "shipType.in=" + UPDATED_SHIP_TYPE);
    }

    @Test
    @Transactional
    void getAllShipsByShipTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where shipType is not null
        defaultShipFiltering("shipType.specified=true", "shipType.specified=false");
    }

    @Test
    @Transactional
    void getAllShipsByMonitoringMethodCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where monitoringMethodCode equals to
        defaultShipFiltering(
            "monitoringMethodCode.equals=" + DEFAULT_MONITORING_METHOD_CODE,
            "monitoringMethodCode.equals=" + UPDATED_MONITORING_METHOD_CODE
        );
    }

    @Test
    @Transactional
    void getAllShipsByMonitoringMethodCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where monitoringMethodCode in
        defaultShipFiltering(
            "monitoringMethodCode.in=" + DEFAULT_MONITORING_METHOD_CODE + "," + UPDATED_MONITORING_METHOD_CODE,
            "monitoringMethodCode.in=" + UPDATED_MONITORING_METHOD_CODE
        );
    }

    @Test
    @Transactional
    void getAllShipsByMonitoringMethodCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        // Get all the shipList where monitoringMethodCode is not null
        defaultShipFiltering("monitoringMethodCode.specified=true", "monitoringMethodCode.specified=false");
    }

    @Test
    @Transactional
    void getAllShipsByOwnerCountryIsEqualToSomething() throws Exception {
        Country ownerCountry;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            shipRepository.saveAndFlush(ship);
            ownerCountry = CountryResourceIT.createEntity();
        } else {
            ownerCountry = TestUtil.findAll(em, Country.class).get(0);
        }
        em.persist(ownerCountry);
        em.flush();
        ship.setOwnerCountry(ownerCountry);
        shipRepository.saveAndFlush(ship);
        Long ownerCountryId = ownerCountry.getId();
        // Get all the shipList where ownerCountry equals to ownerCountryId
        defaultShipShouldBeFound("ownerCountryId.equals=" + ownerCountryId);

        // Get all the shipList where ownerCountry equals to (ownerCountryId + 1)
        defaultShipShouldNotBeFound("ownerCountryId.equals=" + (ownerCountryId + 1));
    }

    @Test
    @Transactional
    void getAllShipsByFlagIsEqualToSomething() throws Exception {
        Flag flag;
        if (TestUtil.findAll(em, Flag.class).isEmpty()) {
            shipRepository.saveAndFlush(ship);
            flag = FlagResourceIT.createEntity();
        } else {
            flag = TestUtil.findAll(em, Flag.class).get(0);
        }
        em.persist(flag);
        em.flush();
        ship.setFlag(flag);
        shipRepository.saveAndFlush(ship);
        Long flagId = flag.getId();
        // Get all the shipList where flag equals to flagId
        defaultShipShouldBeFound("flagId.equals=" + flagId);

        // Get all the shipList where flag equals to (flagId + 1)
        defaultShipShouldNotBeFound("flagId.equals=" + (flagId + 1));
    }

    @Test
    @Transactional
    void getAllShipsByClassificationSocietyIsEqualToSomething() throws Exception {
        ClassificationSociety classificationSociety;
        if (TestUtil.findAll(em, ClassificationSociety.class).isEmpty()) {
            shipRepository.saveAndFlush(ship);
            classificationSociety = ClassificationSocietyResourceIT.createEntity();
        } else {
            classificationSociety = TestUtil.findAll(em, ClassificationSociety.class).get(0);
        }
        em.persist(classificationSociety);
        em.flush();
        ship.setClassificationSociety(classificationSociety);
        shipRepository.saveAndFlush(ship);
        Long classificationSocietyId = classificationSociety.getId();
        // Get all the shipList where classificationSociety equals to classificationSocietyId
        defaultShipShouldBeFound("classificationSocietyId.equals=" + classificationSocietyId);

        // Get all the shipList where classificationSociety equals to (classificationSocietyId + 1)
        defaultShipShouldNotBeFound("classificationSocietyId.equals=" + (classificationSocietyId + 1));
    }

    private void defaultShipFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultShipShouldBeFound(shouldBeFound);
        defaultShipShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultShipShouldBeFound(String filter) throws Exception {
        restShipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ship.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].callSign").value(hasItem(DEFAULT_CALL_SIGN)))
            .andExpect(jsonPath("$.[*].iceClassPolarCode").value(hasItem(DEFAULT_ICE_CLASS_POLAR_CODE.toString())))
            .andExpect(jsonPath("$.[*].technicalEfficiencyCode").value(hasItem(DEFAULT_TECHNICAL_EFFICIENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].shipType").value(hasItem(DEFAULT_SHIP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].monitoringMethodCode").value(hasItem(DEFAULT_MONITORING_METHOD_CODE.toString())));

        // Check, that the count call also returns 1
        restShipMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultShipShouldNotBeFound(String filter) throws Exception {
        restShipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restShipMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingShip() throws Exception {
        // Get the ship
        restShipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingShip() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ship
        Ship updatedShip = shipRepository.findById(ship.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedShip are not directly saved in db
        em.detach(updatedShip);
        updatedShip
            .name(UPDATED_NAME)
            .callSign(UPDATED_CALL_SIGN)
            .iceClassPolarCode(UPDATED_ICE_CLASS_POLAR_CODE)
            .technicalEfficiencyCode(UPDATED_TECHNICAL_EFFICIENCY_CODE)
            .shipType(UPDATED_SHIP_TYPE)
            .monitoringMethodCode(UPDATED_MONITORING_METHOD_CODE);
        ShipDTO shipDTO = shipMapper.toDto(updatedShip);

        restShipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ship in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedShipToMatchAllProperties(updatedShip);
    }

    @Test
    @Transactional
    void putNonExistingShip() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ship.setId(longCount.incrementAndGet());

        // Create the Ship
        ShipDTO shipDTO = shipMapper.toDto(ship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ship in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShip() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ship.setId(longCount.incrementAndGet());

        // Create the Ship
        ShipDTO shipDTO = shipMapper.toDto(ship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(shipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ship in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShip() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ship.setId(longCount.incrementAndGet());

        // Create the Ship
        ShipDTO shipDTO = shipMapper.toDto(ship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(shipDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ship in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShipWithPatch() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ship using partial update
        Ship partialUpdatedShip = new Ship();
        partialUpdatedShip.setId(ship.getId());

        partialUpdatedShip.callSign(UPDATED_CALL_SIGN).iceClassPolarCode(UPDATED_ICE_CLASS_POLAR_CODE);

        restShipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShip.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShip))
            )
            .andExpect(status().isOk());

        // Validate the Ship in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedShip, ship), getPersistedShip(ship));
    }

    @Test
    @Transactional
    void fullUpdateShipWithPatch() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ship using partial update
        Ship partialUpdatedShip = new Ship();
        partialUpdatedShip.setId(ship.getId());

        partialUpdatedShip
            .name(UPDATED_NAME)
            .callSign(UPDATED_CALL_SIGN)
            .iceClassPolarCode(UPDATED_ICE_CLASS_POLAR_CODE)
            .technicalEfficiencyCode(UPDATED_TECHNICAL_EFFICIENCY_CODE)
            .shipType(UPDATED_SHIP_TYPE)
            .monitoringMethodCode(UPDATED_MONITORING_METHOD_CODE);

        restShipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShip.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedShip))
            )
            .andExpect(status().isOk());

        // Validate the Ship in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertShipUpdatableFieldsEquals(partialUpdatedShip, getPersistedShip(partialUpdatedShip));
    }

    @Test
    @Transactional
    void patchNonExistingShip() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ship.setId(longCount.incrementAndGet());

        // Create the Ship
        ShipDTO shipDTO = shipMapper.toDto(ship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ship in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShip() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ship.setId(longCount.incrementAndGet());

        // Create the Ship
        ShipDTO shipDTO = shipMapper.toDto(ship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(shipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ship in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShip() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ship.setId(longCount.incrementAndGet());

        // Create the Ship
        ShipDTO shipDTO = shipMapper.toDto(ship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(shipDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ship in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShip() throws Exception {
        // Initialize the database
        insertedShip = shipRepository.saveAndFlush(ship);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ship
        restShipMockMvc
            .perform(delete(ENTITY_API_URL_ID, ship.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return shipRepository.count();
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

    protected Ship getPersistedShip(Ship ship) {
        return shipRepository.findById(ship.getId()).orElseThrow();
    }

    protected void assertPersistedShipToMatchAllProperties(Ship expectedShip) {
        assertShipAllPropertiesEquals(expectedShip, getPersistedShip(expectedShip));
    }

    protected void assertPersistedShipToMatchUpdatableProperties(Ship expectedShip) {
        assertShipAllUpdatablePropertiesEquals(expectedShip, getPersistedShip(expectedShip));
    }
}
