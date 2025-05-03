package com.falaisia.ship.eventreporting2.web.rest;

import static com.falaisia.ship.eventreporting2.domain.MachineryOperationLineAsserts.*;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.createUpdateProxyForBean;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.falaisia.ship.eventreporting2.IntegrationTest;
import com.falaisia.ship.eventreporting2.domain.EventReport;
import com.falaisia.ship.eventreporting2.domain.MachineryOperationLine;
import com.falaisia.ship.eventreporting2.repository.MachineryOperationLineRepository;
import com.falaisia.ship.eventreporting2.service.dto.MachineryOperationLineDTO;
import com.falaisia.ship.eventreporting2.service.mapper.MachineryOperationLineMapper;
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
 * Integration tests for the {@link MachineryOperationLineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MachineryOperationLineResourceIT {

    private static final BigDecimal DEFAULT_RUNNING_HOURS = new BigDecimal(1);
    private static final BigDecimal UPDATED_RUNNING_HOURS = new BigDecimal(2);
    private static final BigDecimal SMALLER_RUNNING_HOURS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_POWER_OUTPUT = new BigDecimal(1);
    private static final BigDecimal UPDATED_POWER_OUTPUT = new BigDecimal(2);
    private static final BigDecimal SMALLER_POWER_OUTPUT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_AVERAGE_RPM = new BigDecimal(1);
    private static final BigDecimal UPDATED_AVERAGE_RPM = new BigDecimal(2);
    private static final BigDecimal SMALLER_AVERAGE_RPM = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/machinery-operation-lines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MachineryOperationLineRepository machineryOperationLineRepository;

    @Autowired
    private MachineryOperationLineMapper machineryOperationLineMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMachineryOperationLineMockMvc;

    private MachineryOperationLine machineryOperationLine;

    private MachineryOperationLine insertedMachineryOperationLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MachineryOperationLine createEntity(EntityManager em) {
        MachineryOperationLine machineryOperationLine = new MachineryOperationLine()
            .runningHours(DEFAULT_RUNNING_HOURS)
            .powerOutput(DEFAULT_POWER_OUTPUT)
            .averageRpm(DEFAULT_AVERAGE_RPM);
        // Add required entity
        EventReport eventReport;
        if (TestUtil.findAll(em, EventReport.class).isEmpty()) {
            eventReport = EventReportResourceIT.createEntity(em);
            em.persist(eventReport);
            em.flush();
        } else {
            eventReport = TestUtil.findAll(em, EventReport.class).get(0);
        }
        machineryOperationLine.setEventReport(eventReport);
        return machineryOperationLine;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MachineryOperationLine createUpdatedEntity(EntityManager em) {
        MachineryOperationLine updatedMachineryOperationLine = new MachineryOperationLine()
            .runningHours(UPDATED_RUNNING_HOURS)
            .powerOutput(UPDATED_POWER_OUTPUT)
            .averageRpm(UPDATED_AVERAGE_RPM);
        // Add required entity
        EventReport eventReport;
        if (TestUtil.findAll(em, EventReport.class).isEmpty()) {
            eventReport = EventReportResourceIT.createUpdatedEntity(em);
            em.persist(eventReport);
            em.flush();
        } else {
            eventReport = TestUtil.findAll(em, EventReport.class).get(0);
        }
        updatedMachineryOperationLine.setEventReport(eventReport);
        return updatedMachineryOperationLine;
    }

    @BeforeEach
    void initTest() {
        machineryOperationLine = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedMachineryOperationLine != null) {
            machineryOperationLineRepository.delete(insertedMachineryOperationLine);
            insertedMachineryOperationLine = null;
        }
    }

    @Test
    @Transactional
    void createMachineryOperationLine() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MachineryOperationLine
        MachineryOperationLineDTO machineryOperationLineDTO = machineryOperationLineMapper.toDto(machineryOperationLine);
        var returnedMachineryOperationLineDTO = om.readValue(
            restMachineryOperationLineMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(machineryOperationLineDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MachineryOperationLineDTO.class
        );

        // Validate the MachineryOperationLine in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMachineryOperationLine = machineryOperationLineMapper.toEntity(returnedMachineryOperationLineDTO);
        assertMachineryOperationLineUpdatableFieldsEquals(
            returnedMachineryOperationLine,
            getPersistedMachineryOperationLine(returnedMachineryOperationLine)
        );

        insertedMachineryOperationLine = returnedMachineryOperationLine;
    }

    @Test
    @Transactional
    void createMachineryOperationLineWithExistingId() throws Exception {
        // Create the MachineryOperationLine with an existing ID
        machineryOperationLine.setId(1L);
        MachineryOperationLineDTO machineryOperationLineDTO = machineryOperationLineMapper.toDto(machineryOperationLine);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMachineryOperationLineMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineryOperationLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineryOperationLine in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRunningHoursIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        machineryOperationLine.setRunningHours(null);

        // Create the MachineryOperationLine, which fails.
        MachineryOperationLineDTO machineryOperationLineDTO = machineryOperationLineMapper.toDto(machineryOperationLine);

        restMachineryOperationLineMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineryOperationLineDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMachineryOperationLines() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList
        restMachineryOperationLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(machineryOperationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].runningHours").value(hasItem(sameNumber(DEFAULT_RUNNING_HOURS))))
            .andExpect(jsonPath("$.[*].powerOutput").value(hasItem(sameNumber(DEFAULT_POWER_OUTPUT))))
            .andExpect(jsonPath("$.[*].averageRpm").value(hasItem(sameNumber(DEFAULT_AVERAGE_RPM))));
    }

    @Test
    @Transactional
    void getMachineryOperationLine() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get the machineryOperationLine
        restMachineryOperationLineMockMvc
            .perform(get(ENTITY_API_URL_ID, machineryOperationLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(machineryOperationLine.getId().intValue()))
            .andExpect(jsonPath("$.runningHours").value(sameNumber(DEFAULT_RUNNING_HOURS)))
            .andExpect(jsonPath("$.powerOutput").value(sameNumber(DEFAULT_POWER_OUTPUT)))
            .andExpect(jsonPath("$.averageRpm").value(sameNumber(DEFAULT_AVERAGE_RPM)));
    }

    @Test
    @Transactional
    void getMachineryOperationLinesByIdFiltering() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        Long id = machineryOperationLine.getId();

        defaultMachineryOperationLineFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultMachineryOperationLineFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultMachineryOperationLineFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByRunningHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where runningHours equals to
        defaultMachineryOperationLineFiltering(
            "runningHours.equals=" + DEFAULT_RUNNING_HOURS,
            "runningHours.equals=" + UPDATED_RUNNING_HOURS
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByRunningHoursIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where runningHours in
        defaultMachineryOperationLineFiltering(
            "runningHours.in=" + DEFAULT_RUNNING_HOURS + "," + UPDATED_RUNNING_HOURS,
            "runningHours.in=" + UPDATED_RUNNING_HOURS
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByRunningHoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where runningHours is not null
        defaultMachineryOperationLineFiltering("runningHours.specified=true", "runningHours.specified=false");
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByRunningHoursIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where runningHours is greater than or equal to
        defaultMachineryOperationLineFiltering(
            "runningHours.greaterThanOrEqual=" + DEFAULT_RUNNING_HOURS,
            "runningHours.greaterThanOrEqual=" + UPDATED_RUNNING_HOURS
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByRunningHoursIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where runningHours is less than or equal to
        defaultMachineryOperationLineFiltering(
            "runningHours.lessThanOrEqual=" + DEFAULT_RUNNING_HOURS,
            "runningHours.lessThanOrEqual=" + SMALLER_RUNNING_HOURS
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByRunningHoursIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where runningHours is less than
        defaultMachineryOperationLineFiltering(
            "runningHours.lessThan=" + UPDATED_RUNNING_HOURS,
            "runningHours.lessThan=" + DEFAULT_RUNNING_HOURS
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByRunningHoursIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where runningHours is greater than
        defaultMachineryOperationLineFiltering(
            "runningHours.greaterThan=" + SMALLER_RUNNING_HOURS,
            "runningHours.greaterThan=" + DEFAULT_RUNNING_HOURS
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByPowerOutputIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where powerOutput equals to
        defaultMachineryOperationLineFiltering("powerOutput.equals=" + DEFAULT_POWER_OUTPUT, "powerOutput.equals=" + UPDATED_POWER_OUTPUT);
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByPowerOutputIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where powerOutput in
        defaultMachineryOperationLineFiltering(
            "powerOutput.in=" + DEFAULT_POWER_OUTPUT + "," + UPDATED_POWER_OUTPUT,
            "powerOutput.in=" + UPDATED_POWER_OUTPUT
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByPowerOutputIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where powerOutput is not null
        defaultMachineryOperationLineFiltering("powerOutput.specified=true", "powerOutput.specified=false");
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByPowerOutputIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where powerOutput is greater than or equal to
        defaultMachineryOperationLineFiltering(
            "powerOutput.greaterThanOrEqual=" + DEFAULT_POWER_OUTPUT,
            "powerOutput.greaterThanOrEqual=" + UPDATED_POWER_OUTPUT
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByPowerOutputIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where powerOutput is less than or equal to
        defaultMachineryOperationLineFiltering(
            "powerOutput.lessThanOrEqual=" + DEFAULT_POWER_OUTPUT,
            "powerOutput.lessThanOrEqual=" + SMALLER_POWER_OUTPUT
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByPowerOutputIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where powerOutput is less than
        defaultMachineryOperationLineFiltering(
            "powerOutput.lessThan=" + UPDATED_POWER_OUTPUT,
            "powerOutput.lessThan=" + DEFAULT_POWER_OUTPUT
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByPowerOutputIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where powerOutput is greater than
        defaultMachineryOperationLineFiltering(
            "powerOutput.greaterThan=" + SMALLER_POWER_OUTPUT,
            "powerOutput.greaterThan=" + DEFAULT_POWER_OUTPUT
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByAverageRpmIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where averageRpm equals to
        defaultMachineryOperationLineFiltering("averageRpm.equals=" + DEFAULT_AVERAGE_RPM, "averageRpm.equals=" + UPDATED_AVERAGE_RPM);
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByAverageRpmIsInShouldWork() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where averageRpm in
        defaultMachineryOperationLineFiltering(
            "averageRpm.in=" + DEFAULT_AVERAGE_RPM + "," + UPDATED_AVERAGE_RPM,
            "averageRpm.in=" + UPDATED_AVERAGE_RPM
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByAverageRpmIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where averageRpm is not null
        defaultMachineryOperationLineFiltering("averageRpm.specified=true", "averageRpm.specified=false");
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByAverageRpmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where averageRpm is greater than or equal to
        defaultMachineryOperationLineFiltering(
            "averageRpm.greaterThanOrEqual=" + DEFAULT_AVERAGE_RPM,
            "averageRpm.greaterThanOrEqual=" + UPDATED_AVERAGE_RPM
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByAverageRpmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where averageRpm is less than or equal to
        defaultMachineryOperationLineFiltering(
            "averageRpm.lessThanOrEqual=" + DEFAULT_AVERAGE_RPM,
            "averageRpm.lessThanOrEqual=" + SMALLER_AVERAGE_RPM
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByAverageRpmIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where averageRpm is less than
        defaultMachineryOperationLineFiltering("averageRpm.lessThan=" + UPDATED_AVERAGE_RPM, "averageRpm.lessThan=" + DEFAULT_AVERAGE_RPM);
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByAverageRpmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        // Get all the machineryOperationLineList where averageRpm is greater than
        defaultMachineryOperationLineFiltering(
            "averageRpm.greaterThan=" + SMALLER_AVERAGE_RPM,
            "averageRpm.greaterThan=" + DEFAULT_AVERAGE_RPM
        );
    }

    @Test
    @Transactional
    void getAllMachineryOperationLinesByEventReportIsEqualToSomething() throws Exception {
        EventReport eventReport;
        if (TestUtil.findAll(em, EventReport.class).isEmpty()) {
            machineryOperationLineRepository.saveAndFlush(machineryOperationLine);
            eventReport = EventReportResourceIT.createEntity(em);
        } else {
            eventReport = TestUtil.findAll(em, EventReport.class).get(0);
        }
        em.persist(eventReport);
        em.flush();
        machineryOperationLine.setEventReport(eventReport);
        machineryOperationLineRepository.saveAndFlush(machineryOperationLine);
        Long eventReportId = eventReport.getId();
        // Get all the machineryOperationLineList where eventReport equals to eventReportId
        defaultMachineryOperationLineShouldBeFound("eventReportId.equals=" + eventReportId);

        // Get all the machineryOperationLineList where eventReport equals to (eventReportId + 1)
        defaultMachineryOperationLineShouldNotBeFound("eventReportId.equals=" + (eventReportId + 1));
    }

    private void defaultMachineryOperationLineFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultMachineryOperationLineShouldBeFound(shouldBeFound);
        defaultMachineryOperationLineShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMachineryOperationLineShouldBeFound(String filter) throws Exception {
        restMachineryOperationLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(machineryOperationLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].runningHours").value(hasItem(sameNumber(DEFAULT_RUNNING_HOURS))))
            .andExpect(jsonPath("$.[*].powerOutput").value(hasItem(sameNumber(DEFAULT_POWER_OUTPUT))))
            .andExpect(jsonPath("$.[*].averageRpm").value(hasItem(sameNumber(DEFAULT_AVERAGE_RPM))));

        // Check, that the count call also returns 1
        restMachineryOperationLineMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMachineryOperationLineShouldNotBeFound(String filter) throws Exception {
        restMachineryOperationLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMachineryOperationLineMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMachineryOperationLine() throws Exception {
        // Get the machineryOperationLine
        restMachineryOperationLineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMachineryOperationLine() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the machineryOperationLine
        MachineryOperationLine updatedMachineryOperationLine = machineryOperationLineRepository
            .findById(machineryOperationLine.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedMachineryOperationLine are not directly saved in db
        em.detach(updatedMachineryOperationLine);
        updatedMachineryOperationLine.runningHours(UPDATED_RUNNING_HOURS).powerOutput(UPDATED_POWER_OUTPUT).averageRpm(UPDATED_AVERAGE_RPM);
        MachineryOperationLineDTO machineryOperationLineDTO = machineryOperationLineMapper.toDto(updatedMachineryOperationLine);

        restMachineryOperationLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, machineryOperationLineDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineryOperationLineDTO))
            )
            .andExpect(status().isOk());

        // Validate the MachineryOperationLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMachineryOperationLineToMatchAllProperties(updatedMachineryOperationLine);
    }

    @Test
    @Transactional
    void putNonExistingMachineryOperationLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machineryOperationLine.setId(longCount.incrementAndGet());

        // Create the MachineryOperationLine
        MachineryOperationLineDTO machineryOperationLineDTO = machineryOperationLineMapper.toDto(machineryOperationLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMachineryOperationLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, machineryOperationLineDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineryOperationLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineryOperationLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMachineryOperationLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machineryOperationLine.setId(longCount.incrementAndGet());

        // Create the MachineryOperationLine
        MachineryOperationLineDTO machineryOperationLineDTO = machineryOperationLineMapper.toDto(machineryOperationLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineryOperationLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineryOperationLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineryOperationLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMachineryOperationLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machineryOperationLine.setId(longCount.incrementAndGet());

        // Create the MachineryOperationLine
        MachineryOperationLineDTO machineryOperationLineDTO = machineryOperationLineMapper.toDto(machineryOperationLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineryOperationLineMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(machineryOperationLineDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MachineryOperationLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMachineryOperationLineWithPatch() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the machineryOperationLine using partial update
        MachineryOperationLine partialUpdatedMachineryOperationLine = new MachineryOperationLine();
        partialUpdatedMachineryOperationLine.setId(machineryOperationLine.getId());

        restMachineryOperationLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMachineryOperationLine.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMachineryOperationLine))
            )
            .andExpect(status().isOk());

        // Validate the MachineryOperationLine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMachineryOperationLineUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMachineryOperationLine, machineryOperationLine),
            getPersistedMachineryOperationLine(machineryOperationLine)
        );
    }

    @Test
    @Transactional
    void fullUpdateMachineryOperationLineWithPatch() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the machineryOperationLine using partial update
        MachineryOperationLine partialUpdatedMachineryOperationLine = new MachineryOperationLine();
        partialUpdatedMachineryOperationLine.setId(machineryOperationLine.getId());

        partialUpdatedMachineryOperationLine
            .runningHours(UPDATED_RUNNING_HOURS)
            .powerOutput(UPDATED_POWER_OUTPUT)
            .averageRpm(UPDATED_AVERAGE_RPM);

        restMachineryOperationLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMachineryOperationLine.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMachineryOperationLine))
            )
            .andExpect(status().isOk());

        // Validate the MachineryOperationLine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMachineryOperationLineUpdatableFieldsEquals(
            partialUpdatedMachineryOperationLine,
            getPersistedMachineryOperationLine(partialUpdatedMachineryOperationLine)
        );
    }

    @Test
    @Transactional
    void patchNonExistingMachineryOperationLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machineryOperationLine.setId(longCount.incrementAndGet());

        // Create the MachineryOperationLine
        MachineryOperationLineDTO machineryOperationLineDTO = machineryOperationLineMapper.toDto(machineryOperationLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMachineryOperationLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, machineryOperationLineDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(machineryOperationLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineryOperationLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMachineryOperationLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machineryOperationLine.setId(longCount.incrementAndGet());

        // Create the MachineryOperationLine
        MachineryOperationLineDTO machineryOperationLineDTO = machineryOperationLineMapper.toDto(machineryOperationLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineryOperationLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(machineryOperationLineDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MachineryOperationLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMachineryOperationLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        machineryOperationLine.setId(longCount.incrementAndGet());

        // Create the MachineryOperationLine
        MachineryOperationLineDTO machineryOperationLineDTO = machineryOperationLineMapper.toDto(machineryOperationLine);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMachineryOperationLineMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(machineryOperationLineDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MachineryOperationLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMachineryOperationLine() throws Exception {
        // Initialize the database
        insertedMachineryOperationLine = machineryOperationLineRepository.saveAndFlush(machineryOperationLine);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the machineryOperationLine
        restMachineryOperationLineMockMvc
            .perform(delete(ENTITY_API_URL_ID, machineryOperationLine.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return machineryOperationLineRepository.count();
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

    protected MachineryOperationLine getPersistedMachineryOperationLine(MachineryOperationLine machineryOperationLine) {
        return machineryOperationLineRepository.findById(machineryOperationLine.getId()).orElseThrow();
    }

    protected void assertPersistedMachineryOperationLineToMatchAllProperties(MachineryOperationLine expectedMachineryOperationLine) {
        assertMachineryOperationLineAllPropertiesEquals(
            expectedMachineryOperationLine,
            getPersistedMachineryOperationLine(expectedMachineryOperationLine)
        );
    }

    protected void assertPersistedMachineryOperationLineToMatchUpdatableProperties(MachineryOperationLine expectedMachineryOperationLine) {
        assertMachineryOperationLineAllUpdatablePropertiesEquals(
            expectedMachineryOperationLine,
            getPersistedMachineryOperationLine(expectedMachineryOperationLine)
        );
    }
}
