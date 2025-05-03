package com.falaisia.ship.eventreporting2.web.rest;

import static com.falaisia.ship.eventreporting2.domain.EventReportAsserts.*;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.createUpdateProxyForBean;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.sameInstant;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.falaisia.ship.eventreporting2.IntegrationTest;
import com.falaisia.ship.eventreporting2.domain.EventReport;
import com.falaisia.ship.eventreporting2.domain.Voyage;
import com.falaisia.ship.eventreporting2.domain.enumeration.EventStatus;
import com.falaisia.ship.eventreporting2.domain.enumeration.LoadingCondition;
import com.falaisia.ship.eventreporting2.repository.EventReportRepository;
import com.falaisia.ship.eventreporting2.service.dto.EventReportDTO;
import com.falaisia.ship.eventreporting2.service.mapper.EventReportMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link EventReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventReportResourceIT {

    private static final ZonedDateTime DEFAULT_DOCUMENT_DATE_AND_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DOCUMENT_DATE_AND_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DOCUMENT_DATE_AND_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final BigDecimal DEFAULT_SPEED_GPS = new BigDecimal(1);
    private static final BigDecimal UPDATED_SPEED_GPS = new BigDecimal(2);
    private static final BigDecimal SMALLER_SPEED_GPS = new BigDecimal(1 - 1);

    private static final String DEFAULT_DOCUMENT_DISPLAY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_DISPLAY_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEG = 1;
    private static final Integer UPDATED_LEG = 2;
    private static final Integer SMALLER_LEG = 1 - 1;

    private static final BigDecimal DEFAULT_DISTANCE_TRAVELLED = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISTANCE_TRAVELLED = new BigDecimal(2);
    private static final BigDecimal SMALLER_DISTANCE_TRAVELLED = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HOURS_UNDERWAY = new BigDecimal(1);
    private static final BigDecimal UPDATED_HOURS_UNDERWAY = new BigDecimal(2);
    private static final BigDecimal SMALLER_HOURS_UNDERWAY = new BigDecimal(1 - 1);

    private static final EventStatus DEFAULT_EVENT_STATUS = EventStatus.DEPARTURE;
    private static final EventStatus UPDATED_EVENT_STATUS = EventStatus.ARRIVAL;

    private static final LoadingCondition DEFAULT_LOADING_CONDITION = LoadingCondition.LADEN;
    private static final LoadingCondition UPDATED_LOADING_CONDITION = LoadingCondition.BALLAST;

    private static final BigDecimal DEFAULT_CARGO_CARRIED = new BigDecimal(1);
    private static final BigDecimal UPDATED_CARGO_CARRIED = new BigDecimal(2);
    private static final BigDecimal SMALLER_CARGO_CARRIED = new BigDecimal(1 - 1);

    private static final String DEFAULT_COORDINATES_LATITUDE = "4060N";
    private static final String UPDATED_COORDINATES_LATITUDE = "9029S";

    private static final String DEFAULT_COORDINATES_LONGITUDE = "09715W";
    private static final String UPDATED_COORDINATES_LONGITUDE = "14960E";

    private static final String DEFAULT_SHIPS_HEADING = "AAAAAAAAAA";
    private static final String UPDATED_SHIPS_HEADING = "BBBBBBBBBB";

    private static final Integer DEFAULT_BEAUFORT_NO = 1;
    private static final Integer UPDATED_BEAUFORT_NO = 2;
    private static final Integer SMALLER_BEAUFORT_NO = 1 - 1;

    private static final String DEFAULT_WEATHER_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_WEATHER_CONDITION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SWELL = false;
    private static final Boolean UPDATED_SWELL = true;

    private static final String ENTITY_API_URL = "/api/event-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EventReportRepository eventReportRepository;

    @Autowired
    private EventReportMapper eventReportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventReportMockMvc;

    private EventReport eventReport;

    private EventReport insertedEventReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventReport createEntity(EntityManager em) {
        EventReport eventReport = new EventReport()
            .documentDateAndTime(DEFAULT_DOCUMENT_DATE_AND_TIME)
            .speedGps(DEFAULT_SPEED_GPS)
            .documentDisplayNumber(DEFAULT_DOCUMENT_DISPLAY_NUMBER)
            .leg(DEFAULT_LEG)
            .distanceTravelled(DEFAULT_DISTANCE_TRAVELLED)
            .hoursUnderway(DEFAULT_HOURS_UNDERWAY)
            .eventStatus(DEFAULT_EVENT_STATUS)
            .loadingCondition(DEFAULT_LOADING_CONDITION)
            .cargoCarried(DEFAULT_CARGO_CARRIED)
            .coordinatesLatitude(DEFAULT_COORDINATES_LATITUDE)
            .coordinatesLongitude(DEFAULT_COORDINATES_LONGITUDE)
            .shipsHeading(DEFAULT_SHIPS_HEADING)
            .beaufortNo(DEFAULT_BEAUFORT_NO)
            .weatherCondition(DEFAULT_WEATHER_CONDITION)
            .swell(DEFAULT_SWELL);
        // Add required entity
        Voyage voyage;
        if (TestUtil.findAll(em, Voyage.class).isEmpty()) {
            voyage = VoyageResourceIT.createEntity(em);
            em.persist(voyage);
            em.flush();
        } else {
            voyage = TestUtil.findAll(em, Voyage.class).get(0);
        }
        eventReport.setVoyage(voyage);
        return eventReport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventReport createUpdatedEntity(EntityManager em) {
        EventReport updatedEventReport = new EventReport()
            .documentDateAndTime(UPDATED_DOCUMENT_DATE_AND_TIME)
            .speedGps(UPDATED_SPEED_GPS)
            .documentDisplayNumber(UPDATED_DOCUMENT_DISPLAY_NUMBER)
            .leg(UPDATED_LEG)
            .distanceTravelled(UPDATED_DISTANCE_TRAVELLED)
            .hoursUnderway(UPDATED_HOURS_UNDERWAY)
            .eventStatus(UPDATED_EVENT_STATUS)
            .loadingCondition(UPDATED_LOADING_CONDITION)
            .cargoCarried(UPDATED_CARGO_CARRIED)
            .coordinatesLatitude(UPDATED_COORDINATES_LATITUDE)
            .coordinatesLongitude(UPDATED_COORDINATES_LONGITUDE)
            .shipsHeading(UPDATED_SHIPS_HEADING)
            .beaufortNo(UPDATED_BEAUFORT_NO)
            .weatherCondition(UPDATED_WEATHER_CONDITION)
            .swell(UPDATED_SWELL);
        // Add required entity
        Voyage voyage;
        if (TestUtil.findAll(em, Voyage.class).isEmpty()) {
            voyage = VoyageResourceIT.createUpdatedEntity(em);
            em.persist(voyage);
            em.flush();
        } else {
            voyage = TestUtil.findAll(em, Voyage.class).get(0);
        }
        updatedEventReport.setVoyage(voyage);
        return updatedEventReport;
    }

    @BeforeEach
    void initTest() {
        eventReport = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedEventReport != null) {
            eventReportRepository.delete(insertedEventReport);
            insertedEventReport = null;
        }
    }

    @Test
    @Transactional
    void createEventReport() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EventReport
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);
        var returnedEventReportDTO = om.readValue(
            restEventReportMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventReportDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EventReportDTO.class
        );

        // Validate the EventReport in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEventReport = eventReportMapper.toEntity(returnedEventReportDTO);
        assertEventReportUpdatableFieldsEquals(returnedEventReport, getPersistedEventReport(returnedEventReport));

        insertedEventReport = returnedEventReport;
    }

    @Test
    @Transactional
    void createEventReportWithExistingId() throws Exception {
        // Create the EventReport with an existing ID
        eventReport.setId(1L);
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventReportMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReport in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDocumentDateAndTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        eventReport.setDocumentDateAndTime(null);

        // Create the EventReport, which fails.
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        restEventReportMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventReportDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEventStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        eventReport.setEventStatus(null);

        // Create the EventReport, which fails.
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        restEventReportMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventReportDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLoadingConditionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        eventReport.setLoadingCondition(null);

        // Create the EventReport, which fails.
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        restEventReportMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventReportDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEventReports() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList
        restEventReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentDateAndTime").value(hasItem(sameInstant(DEFAULT_DOCUMENT_DATE_AND_TIME))))
            .andExpect(jsonPath("$.[*].speedGps").value(hasItem(sameNumber(DEFAULT_SPEED_GPS))))
            .andExpect(jsonPath("$.[*].documentDisplayNumber").value(hasItem(DEFAULT_DOCUMENT_DISPLAY_NUMBER)))
            .andExpect(jsonPath("$.[*].leg").value(hasItem(DEFAULT_LEG)))
            .andExpect(jsonPath("$.[*].distanceTravelled").value(hasItem(sameNumber(DEFAULT_DISTANCE_TRAVELLED))))
            .andExpect(jsonPath("$.[*].hoursUnderway").value(hasItem(sameNumber(DEFAULT_HOURS_UNDERWAY))))
            .andExpect(jsonPath("$.[*].eventStatus").value(hasItem(DEFAULT_EVENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].loadingCondition").value(hasItem(DEFAULT_LOADING_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].cargoCarried").value(hasItem(sameNumber(DEFAULT_CARGO_CARRIED))))
            .andExpect(jsonPath("$.[*].coordinatesLatitude").value(hasItem(DEFAULT_COORDINATES_LATITUDE)))
            .andExpect(jsonPath("$.[*].coordinatesLongitude").value(hasItem(DEFAULT_COORDINATES_LONGITUDE)))
            .andExpect(jsonPath("$.[*].shipsHeading").value(hasItem(DEFAULT_SHIPS_HEADING)))
            .andExpect(jsonPath("$.[*].beaufortNo").value(hasItem(DEFAULT_BEAUFORT_NO)))
            .andExpect(jsonPath("$.[*].weatherCondition").value(hasItem(DEFAULT_WEATHER_CONDITION)))
            .andExpect(jsonPath("$.[*].swell").value(hasItem(DEFAULT_SWELL)));
    }

    @Test
    @Transactional
    void getEventReport() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get the eventReport
        restEventReportMockMvc
            .perform(get(ENTITY_API_URL_ID, eventReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventReport.getId().intValue()))
            .andExpect(jsonPath("$.documentDateAndTime").value(sameInstant(DEFAULT_DOCUMENT_DATE_AND_TIME)))
            .andExpect(jsonPath("$.speedGps").value(sameNumber(DEFAULT_SPEED_GPS)))
            .andExpect(jsonPath("$.documentDisplayNumber").value(DEFAULT_DOCUMENT_DISPLAY_NUMBER))
            .andExpect(jsonPath("$.leg").value(DEFAULT_LEG))
            .andExpect(jsonPath("$.distanceTravelled").value(sameNumber(DEFAULT_DISTANCE_TRAVELLED)))
            .andExpect(jsonPath("$.hoursUnderway").value(sameNumber(DEFAULT_HOURS_UNDERWAY)))
            .andExpect(jsonPath("$.eventStatus").value(DEFAULT_EVENT_STATUS.toString()))
            .andExpect(jsonPath("$.loadingCondition").value(DEFAULT_LOADING_CONDITION.toString()))
            .andExpect(jsonPath("$.cargoCarried").value(sameNumber(DEFAULT_CARGO_CARRIED)))
            .andExpect(jsonPath("$.coordinatesLatitude").value(DEFAULT_COORDINATES_LATITUDE))
            .andExpect(jsonPath("$.coordinatesLongitude").value(DEFAULT_COORDINATES_LONGITUDE))
            .andExpect(jsonPath("$.shipsHeading").value(DEFAULT_SHIPS_HEADING))
            .andExpect(jsonPath("$.beaufortNo").value(DEFAULT_BEAUFORT_NO))
            .andExpect(jsonPath("$.weatherCondition").value(DEFAULT_WEATHER_CONDITION))
            .andExpect(jsonPath("$.swell").value(DEFAULT_SWELL));
    }

    @Test
    @Transactional
    void getEventReportsByIdFiltering() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        Long id = eventReport.getId();

        defaultEventReportFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultEventReportFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultEventReportFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEventReportsByDocumentDateAndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where documentDateAndTime equals to
        defaultEventReportFiltering(
            "documentDateAndTime.equals=" + DEFAULT_DOCUMENT_DATE_AND_TIME,
            "documentDateAndTime.equals=" + UPDATED_DOCUMENT_DATE_AND_TIME
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByDocumentDateAndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where documentDateAndTime in
        defaultEventReportFiltering(
            "documentDateAndTime.in=" + DEFAULT_DOCUMENT_DATE_AND_TIME + "," + UPDATED_DOCUMENT_DATE_AND_TIME,
            "documentDateAndTime.in=" + UPDATED_DOCUMENT_DATE_AND_TIME
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByDocumentDateAndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where documentDateAndTime is not null
        defaultEventReportFiltering("documentDateAndTime.specified=true", "documentDateAndTime.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsByDocumentDateAndTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where documentDateAndTime is greater than or equal to
        defaultEventReportFiltering(
            "documentDateAndTime.greaterThanOrEqual=" + DEFAULT_DOCUMENT_DATE_AND_TIME,
            "documentDateAndTime.greaterThanOrEqual=" + UPDATED_DOCUMENT_DATE_AND_TIME
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByDocumentDateAndTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where documentDateAndTime is less than or equal to
        defaultEventReportFiltering(
            "documentDateAndTime.lessThanOrEqual=" + DEFAULT_DOCUMENT_DATE_AND_TIME,
            "documentDateAndTime.lessThanOrEqual=" + SMALLER_DOCUMENT_DATE_AND_TIME
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByDocumentDateAndTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where documentDateAndTime is less than
        defaultEventReportFiltering(
            "documentDateAndTime.lessThan=" + UPDATED_DOCUMENT_DATE_AND_TIME,
            "documentDateAndTime.lessThan=" + DEFAULT_DOCUMENT_DATE_AND_TIME
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByDocumentDateAndTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where documentDateAndTime is greater than
        defaultEventReportFiltering(
            "documentDateAndTime.greaterThan=" + SMALLER_DOCUMENT_DATE_AND_TIME,
            "documentDateAndTime.greaterThan=" + DEFAULT_DOCUMENT_DATE_AND_TIME
        );
    }

    @Test
    @Transactional
    void getAllEventReportsBySpeedGpsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where speedGps equals to
        defaultEventReportFiltering("speedGps.equals=" + DEFAULT_SPEED_GPS, "speedGps.equals=" + UPDATED_SPEED_GPS);
    }

    @Test
    @Transactional
    void getAllEventReportsBySpeedGpsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where speedGps in
        defaultEventReportFiltering("speedGps.in=" + DEFAULT_SPEED_GPS + "," + UPDATED_SPEED_GPS, "speedGps.in=" + UPDATED_SPEED_GPS);
    }

    @Test
    @Transactional
    void getAllEventReportsBySpeedGpsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where speedGps is not null
        defaultEventReportFiltering("speedGps.specified=true", "speedGps.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsBySpeedGpsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where speedGps is greater than or equal to
        defaultEventReportFiltering("speedGps.greaterThanOrEqual=" + DEFAULT_SPEED_GPS, "speedGps.greaterThanOrEqual=" + UPDATED_SPEED_GPS);
    }

    @Test
    @Transactional
    void getAllEventReportsBySpeedGpsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where speedGps is less than or equal to
        defaultEventReportFiltering("speedGps.lessThanOrEqual=" + DEFAULT_SPEED_GPS, "speedGps.lessThanOrEqual=" + SMALLER_SPEED_GPS);
    }

    @Test
    @Transactional
    void getAllEventReportsBySpeedGpsIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where speedGps is less than
        defaultEventReportFiltering("speedGps.lessThan=" + UPDATED_SPEED_GPS, "speedGps.lessThan=" + DEFAULT_SPEED_GPS);
    }

    @Test
    @Transactional
    void getAllEventReportsBySpeedGpsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where speedGps is greater than
        defaultEventReportFiltering("speedGps.greaterThan=" + SMALLER_SPEED_GPS, "speedGps.greaterThan=" + DEFAULT_SPEED_GPS);
    }

    @Test
    @Transactional
    void getAllEventReportsByDocumentDisplayNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where documentDisplayNumber equals to
        defaultEventReportFiltering(
            "documentDisplayNumber.equals=" + DEFAULT_DOCUMENT_DISPLAY_NUMBER,
            "documentDisplayNumber.equals=" + UPDATED_DOCUMENT_DISPLAY_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByDocumentDisplayNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where documentDisplayNumber in
        defaultEventReportFiltering(
            "documentDisplayNumber.in=" + DEFAULT_DOCUMENT_DISPLAY_NUMBER + "," + UPDATED_DOCUMENT_DISPLAY_NUMBER,
            "documentDisplayNumber.in=" + UPDATED_DOCUMENT_DISPLAY_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByDocumentDisplayNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where documentDisplayNumber is not null
        defaultEventReportFiltering("documentDisplayNumber.specified=true", "documentDisplayNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsByDocumentDisplayNumberContainsSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where documentDisplayNumber contains
        defaultEventReportFiltering(
            "documentDisplayNumber.contains=" + DEFAULT_DOCUMENT_DISPLAY_NUMBER,
            "documentDisplayNumber.contains=" + UPDATED_DOCUMENT_DISPLAY_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByDocumentDisplayNumberNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where documentDisplayNumber does not contain
        defaultEventReportFiltering(
            "documentDisplayNumber.doesNotContain=" + UPDATED_DOCUMENT_DISPLAY_NUMBER,
            "documentDisplayNumber.doesNotContain=" + DEFAULT_DOCUMENT_DISPLAY_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByLegIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where leg equals to
        defaultEventReportFiltering("leg.equals=" + DEFAULT_LEG, "leg.equals=" + UPDATED_LEG);
    }

    @Test
    @Transactional
    void getAllEventReportsByLegIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where leg in
        defaultEventReportFiltering("leg.in=" + DEFAULT_LEG + "," + UPDATED_LEG, "leg.in=" + UPDATED_LEG);
    }

    @Test
    @Transactional
    void getAllEventReportsByLegIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where leg is not null
        defaultEventReportFiltering("leg.specified=true", "leg.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsByLegIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where leg is greater than or equal to
        defaultEventReportFiltering("leg.greaterThanOrEqual=" + DEFAULT_LEG, "leg.greaterThanOrEqual=" + UPDATED_LEG);
    }

    @Test
    @Transactional
    void getAllEventReportsByLegIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where leg is less than or equal to
        defaultEventReportFiltering("leg.lessThanOrEqual=" + DEFAULT_LEG, "leg.lessThanOrEqual=" + SMALLER_LEG);
    }

    @Test
    @Transactional
    void getAllEventReportsByLegIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where leg is less than
        defaultEventReportFiltering("leg.lessThan=" + UPDATED_LEG, "leg.lessThan=" + DEFAULT_LEG);
    }

    @Test
    @Transactional
    void getAllEventReportsByLegIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where leg is greater than
        defaultEventReportFiltering("leg.greaterThan=" + SMALLER_LEG, "leg.greaterThan=" + DEFAULT_LEG);
    }

    @Test
    @Transactional
    void getAllEventReportsByDistanceTravelledIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where distanceTravelled equals to
        defaultEventReportFiltering(
            "distanceTravelled.equals=" + DEFAULT_DISTANCE_TRAVELLED,
            "distanceTravelled.equals=" + UPDATED_DISTANCE_TRAVELLED
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByDistanceTravelledIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where distanceTravelled in
        defaultEventReportFiltering(
            "distanceTravelled.in=" + DEFAULT_DISTANCE_TRAVELLED + "," + UPDATED_DISTANCE_TRAVELLED,
            "distanceTravelled.in=" + UPDATED_DISTANCE_TRAVELLED
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByDistanceTravelledIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where distanceTravelled is not null
        defaultEventReportFiltering("distanceTravelled.specified=true", "distanceTravelled.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsByDistanceTravelledIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where distanceTravelled is greater than or equal to
        defaultEventReportFiltering(
            "distanceTravelled.greaterThanOrEqual=" + DEFAULT_DISTANCE_TRAVELLED,
            "distanceTravelled.greaterThanOrEqual=" + UPDATED_DISTANCE_TRAVELLED
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByDistanceTravelledIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where distanceTravelled is less than or equal to
        defaultEventReportFiltering(
            "distanceTravelled.lessThanOrEqual=" + DEFAULT_DISTANCE_TRAVELLED,
            "distanceTravelled.lessThanOrEqual=" + SMALLER_DISTANCE_TRAVELLED
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByDistanceTravelledIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where distanceTravelled is less than
        defaultEventReportFiltering(
            "distanceTravelled.lessThan=" + UPDATED_DISTANCE_TRAVELLED,
            "distanceTravelled.lessThan=" + DEFAULT_DISTANCE_TRAVELLED
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByDistanceTravelledIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where distanceTravelled is greater than
        defaultEventReportFiltering(
            "distanceTravelled.greaterThan=" + SMALLER_DISTANCE_TRAVELLED,
            "distanceTravelled.greaterThan=" + DEFAULT_DISTANCE_TRAVELLED
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByHoursUnderwayIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where hoursUnderway equals to
        defaultEventReportFiltering("hoursUnderway.equals=" + DEFAULT_HOURS_UNDERWAY, "hoursUnderway.equals=" + UPDATED_HOURS_UNDERWAY);
    }

    @Test
    @Transactional
    void getAllEventReportsByHoursUnderwayIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where hoursUnderway in
        defaultEventReportFiltering(
            "hoursUnderway.in=" + DEFAULT_HOURS_UNDERWAY + "," + UPDATED_HOURS_UNDERWAY,
            "hoursUnderway.in=" + UPDATED_HOURS_UNDERWAY
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByHoursUnderwayIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where hoursUnderway is not null
        defaultEventReportFiltering("hoursUnderway.specified=true", "hoursUnderway.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsByHoursUnderwayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where hoursUnderway is greater than or equal to
        defaultEventReportFiltering(
            "hoursUnderway.greaterThanOrEqual=" + DEFAULT_HOURS_UNDERWAY,
            "hoursUnderway.greaterThanOrEqual=" + UPDATED_HOURS_UNDERWAY
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByHoursUnderwayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where hoursUnderway is less than or equal to
        defaultEventReportFiltering(
            "hoursUnderway.lessThanOrEqual=" + DEFAULT_HOURS_UNDERWAY,
            "hoursUnderway.lessThanOrEqual=" + SMALLER_HOURS_UNDERWAY
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByHoursUnderwayIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where hoursUnderway is less than
        defaultEventReportFiltering("hoursUnderway.lessThan=" + UPDATED_HOURS_UNDERWAY, "hoursUnderway.lessThan=" + DEFAULT_HOURS_UNDERWAY);
    }

    @Test
    @Transactional
    void getAllEventReportsByHoursUnderwayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where hoursUnderway is greater than
        defaultEventReportFiltering(
            "hoursUnderway.greaterThan=" + SMALLER_HOURS_UNDERWAY,
            "hoursUnderway.greaterThan=" + DEFAULT_HOURS_UNDERWAY
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByEventStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where eventStatus equals to
        defaultEventReportFiltering("eventStatus.equals=" + DEFAULT_EVENT_STATUS, "eventStatus.equals=" + UPDATED_EVENT_STATUS);
    }

    @Test
    @Transactional
    void getAllEventReportsByEventStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where eventStatus in
        defaultEventReportFiltering(
            "eventStatus.in=" + DEFAULT_EVENT_STATUS + "," + UPDATED_EVENT_STATUS,
            "eventStatus.in=" + UPDATED_EVENT_STATUS
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByEventStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where eventStatus is not null
        defaultEventReportFiltering("eventStatus.specified=true", "eventStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsByLoadingConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where loadingCondition equals to
        defaultEventReportFiltering(
            "loadingCondition.equals=" + DEFAULT_LOADING_CONDITION,
            "loadingCondition.equals=" + UPDATED_LOADING_CONDITION
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByLoadingConditionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where loadingCondition in
        defaultEventReportFiltering(
            "loadingCondition.in=" + DEFAULT_LOADING_CONDITION + "," + UPDATED_LOADING_CONDITION,
            "loadingCondition.in=" + UPDATED_LOADING_CONDITION
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByLoadingConditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where loadingCondition is not null
        defaultEventReportFiltering("loadingCondition.specified=true", "loadingCondition.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsByCargoCarriedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where cargoCarried equals to
        defaultEventReportFiltering("cargoCarried.equals=" + DEFAULT_CARGO_CARRIED, "cargoCarried.equals=" + UPDATED_CARGO_CARRIED);
    }

    @Test
    @Transactional
    void getAllEventReportsByCargoCarriedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where cargoCarried in
        defaultEventReportFiltering(
            "cargoCarried.in=" + DEFAULT_CARGO_CARRIED + "," + UPDATED_CARGO_CARRIED,
            "cargoCarried.in=" + UPDATED_CARGO_CARRIED
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByCargoCarriedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where cargoCarried is not null
        defaultEventReportFiltering("cargoCarried.specified=true", "cargoCarried.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsByCargoCarriedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where cargoCarried is greater than or equal to
        defaultEventReportFiltering(
            "cargoCarried.greaterThanOrEqual=" + DEFAULT_CARGO_CARRIED,
            "cargoCarried.greaterThanOrEqual=" + UPDATED_CARGO_CARRIED
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByCargoCarriedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where cargoCarried is less than or equal to
        defaultEventReportFiltering(
            "cargoCarried.lessThanOrEqual=" + DEFAULT_CARGO_CARRIED,
            "cargoCarried.lessThanOrEqual=" + SMALLER_CARGO_CARRIED
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByCargoCarriedIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where cargoCarried is less than
        defaultEventReportFiltering("cargoCarried.lessThan=" + UPDATED_CARGO_CARRIED, "cargoCarried.lessThan=" + DEFAULT_CARGO_CARRIED);
    }

    @Test
    @Transactional
    void getAllEventReportsByCargoCarriedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where cargoCarried is greater than
        defaultEventReportFiltering(
            "cargoCarried.greaterThan=" + SMALLER_CARGO_CARRIED,
            "cargoCarried.greaterThan=" + DEFAULT_CARGO_CARRIED
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByCoordinatesLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where coordinatesLatitude equals to
        defaultEventReportFiltering(
            "coordinatesLatitude.equals=" + DEFAULT_COORDINATES_LATITUDE,
            "coordinatesLatitude.equals=" + UPDATED_COORDINATES_LATITUDE
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByCoordinatesLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where coordinatesLatitude in
        defaultEventReportFiltering(
            "coordinatesLatitude.in=" + DEFAULT_COORDINATES_LATITUDE + "," + UPDATED_COORDINATES_LATITUDE,
            "coordinatesLatitude.in=" + UPDATED_COORDINATES_LATITUDE
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByCoordinatesLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where coordinatesLatitude is not null
        defaultEventReportFiltering("coordinatesLatitude.specified=true", "coordinatesLatitude.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsByCoordinatesLatitudeContainsSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where coordinatesLatitude contains
        defaultEventReportFiltering(
            "coordinatesLatitude.contains=" + DEFAULT_COORDINATES_LATITUDE,
            "coordinatesLatitude.contains=" + UPDATED_COORDINATES_LATITUDE
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByCoordinatesLatitudeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where coordinatesLatitude does not contain
        defaultEventReportFiltering(
            "coordinatesLatitude.doesNotContain=" + UPDATED_COORDINATES_LATITUDE,
            "coordinatesLatitude.doesNotContain=" + DEFAULT_COORDINATES_LATITUDE
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByCoordinatesLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where coordinatesLongitude equals to
        defaultEventReportFiltering(
            "coordinatesLongitude.equals=" + DEFAULT_COORDINATES_LONGITUDE,
            "coordinatesLongitude.equals=" + UPDATED_COORDINATES_LONGITUDE
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByCoordinatesLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where coordinatesLongitude in
        defaultEventReportFiltering(
            "coordinatesLongitude.in=" + DEFAULT_COORDINATES_LONGITUDE + "," + UPDATED_COORDINATES_LONGITUDE,
            "coordinatesLongitude.in=" + UPDATED_COORDINATES_LONGITUDE
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByCoordinatesLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where coordinatesLongitude is not null
        defaultEventReportFiltering("coordinatesLongitude.specified=true", "coordinatesLongitude.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsByCoordinatesLongitudeContainsSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where coordinatesLongitude contains
        defaultEventReportFiltering(
            "coordinatesLongitude.contains=" + DEFAULT_COORDINATES_LONGITUDE,
            "coordinatesLongitude.contains=" + UPDATED_COORDINATES_LONGITUDE
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByCoordinatesLongitudeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where coordinatesLongitude does not contain
        defaultEventReportFiltering(
            "coordinatesLongitude.doesNotContain=" + UPDATED_COORDINATES_LONGITUDE,
            "coordinatesLongitude.doesNotContain=" + DEFAULT_COORDINATES_LONGITUDE
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByShipsHeadingIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where shipsHeading equals to
        defaultEventReportFiltering("shipsHeading.equals=" + DEFAULT_SHIPS_HEADING, "shipsHeading.equals=" + UPDATED_SHIPS_HEADING);
    }

    @Test
    @Transactional
    void getAllEventReportsByShipsHeadingIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where shipsHeading in
        defaultEventReportFiltering(
            "shipsHeading.in=" + DEFAULT_SHIPS_HEADING + "," + UPDATED_SHIPS_HEADING,
            "shipsHeading.in=" + UPDATED_SHIPS_HEADING
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByShipsHeadingIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where shipsHeading is not null
        defaultEventReportFiltering("shipsHeading.specified=true", "shipsHeading.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsByShipsHeadingContainsSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where shipsHeading contains
        defaultEventReportFiltering("shipsHeading.contains=" + DEFAULT_SHIPS_HEADING, "shipsHeading.contains=" + UPDATED_SHIPS_HEADING);
    }

    @Test
    @Transactional
    void getAllEventReportsByShipsHeadingNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where shipsHeading does not contain
        defaultEventReportFiltering(
            "shipsHeading.doesNotContain=" + UPDATED_SHIPS_HEADING,
            "shipsHeading.doesNotContain=" + DEFAULT_SHIPS_HEADING
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByBeaufortNoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where beaufortNo equals to
        defaultEventReportFiltering("beaufortNo.equals=" + DEFAULT_BEAUFORT_NO, "beaufortNo.equals=" + UPDATED_BEAUFORT_NO);
    }

    @Test
    @Transactional
    void getAllEventReportsByBeaufortNoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where beaufortNo in
        defaultEventReportFiltering(
            "beaufortNo.in=" + DEFAULT_BEAUFORT_NO + "," + UPDATED_BEAUFORT_NO,
            "beaufortNo.in=" + UPDATED_BEAUFORT_NO
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByBeaufortNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where beaufortNo is not null
        defaultEventReportFiltering("beaufortNo.specified=true", "beaufortNo.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsByBeaufortNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where beaufortNo is greater than or equal to
        defaultEventReportFiltering(
            "beaufortNo.greaterThanOrEqual=" + DEFAULT_BEAUFORT_NO,
            "beaufortNo.greaterThanOrEqual=" + UPDATED_BEAUFORT_NO
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByBeaufortNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where beaufortNo is less than or equal to
        defaultEventReportFiltering(
            "beaufortNo.lessThanOrEqual=" + DEFAULT_BEAUFORT_NO,
            "beaufortNo.lessThanOrEqual=" + SMALLER_BEAUFORT_NO
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByBeaufortNoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where beaufortNo is less than
        defaultEventReportFiltering("beaufortNo.lessThan=" + UPDATED_BEAUFORT_NO, "beaufortNo.lessThan=" + DEFAULT_BEAUFORT_NO);
    }

    @Test
    @Transactional
    void getAllEventReportsByBeaufortNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where beaufortNo is greater than
        defaultEventReportFiltering("beaufortNo.greaterThan=" + SMALLER_BEAUFORT_NO, "beaufortNo.greaterThan=" + DEFAULT_BEAUFORT_NO);
    }

    @Test
    @Transactional
    void getAllEventReportsByWeatherConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where weatherCondition equals to
        defaultEventReportFiltering(
            "weatherCondition.equals=" + DEFAULT_WEATHER_CONDITION,
            "weatherCondition.equals=" + UPDATED_WEATHER_CONDITION
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByWeatherConditionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where weatherCondition in
        defaultEventReportFiltering(
            "weatherCondition.in=" + DEFAULT_WEATHER_CONDITION + "," + UPDATED_WEATHER_CONDITION,
            "weatherCondition.in=" + UPDATED_WEATHER_CONDITION
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByWeatherConditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where weatherCondition is not null
        defaultEventReportFiltering("weatherCondition.specified=true", "weatherCondition.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsByWeatherConditionContainsSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where weatherCondition contains
        defaultEventReportFiltering(
            "weatherCondition.contains=" + DEFAULT_WEATHER_CONDITION,
            "weatherCondition.contains=" + UPDATED_WEATHER_CONDITION
        );
    }

    @Test
    @Transactional
    void getAllEventReportsByWeatherConditionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where weatherCondition does not contain
        defaultEventReportFiltering(
            "weatherCondition.doesNotContain=" + UPDATED_WEATHER_CONDITION,
            "weatherCondition.doesNotContain=" + DEFAULT_WEATHER_CONDITION
        );
    }

    @Test
    @Transactional
    void getAllEventReportsBySwellIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where swell equals to
        defaultEventReportFiltering("swell.equals=" + DEFAULT_SWELL, "swell.equals=" + UPDATED_SWELL);
    }

    @Test
    @Transactional
    void getAllEventReportsBySwellIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where swell in
        defaultEventReportFiltering("swell.in=" + DEFAULT_SWELL + "," + UPDATED_SWELL, "swell.in=" + UPDATED_SWELL);
    }

    @Test
    @Transactional
    void getAllEventReportsBySwellIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        // Get all the eventReportList where swell is not null
        defaultEventReportFiltering("swell.specified=true", "swell.specified=false");
    }

    @Test
    @Transactional
    void getAllEventReportsByVoyageIsEqualToSomething() throws Exception {
        Voyage voyage;
        if (TestUtil.findAll(em, Voyage.class).isEmpty()) {
            eventReportRepository.saveAndFlush(eventReport);
            voyage = VoyageResourceIT.createEntity(em);
        } else {
            voyage = TestUtil.findAll(em, Voyage.class).get(0);
        }
        em.persist(voyage);
        em.flush();
        eventReport.setVoyage(voyage);
        eventReportRepository.saveAndFlush(eventReport);
        Long voyageId = voyage.getId();
        // Get all the eventReportList where voyage equals to voyageId
        defaultEventReportShouldBeFound("voyageId.equals=" + voyageId);

        // Get all the eventReportList where voyage equals to (voyageId + 1)
        defaultEventReportShouldNotBeFound("voyageId.equals=" + (voyageId + 1));
    }

    private void defaultEventReportFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEventReportShouldBeFound(shouldBeFound);
        defaultEventReportShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEventReportShouldBeFound(String filter) throws Exception {
        restEventReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentDateAndTime").value(hasItem(sameInstant(DEFAULT_DOCUMENT_DATE_AND_TIME))))
            .andExpect(jsonPath("$.[*].speedGps").value(hasItem(sameNumber(DEFAULT_SPEED_GPS))))
            .andExpect(jsonPath("$.[*].documentDisplayNumber").value(hasItem(DEFAULT_DOCUMENT_DISPLAY_NUMBER)))
            .andExpect(jsonPath("$.[*].leg").value(hasItem(DEFAULT_LEG)))
            .andExpect(jsonPath("$.[*].distanceTravelled").value(hasItem(sameNumber(DEFAULT_DISTANCE_TRAVELLED))))
            .andExpect(jsonPath("$.[*].hoursUnderway").value(hasItem(sameNumber(DEFAULT_HOURS_UNDERWAY))))
            .andExpect(jsonPath("$.[*].eventStatus").value(hasItem(DEFAULT_EVENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].loadingCondition").value(hasItem(DEFAULT_LOADING_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].cargoCarried").value(hasItem(sameNumber(DEFAULT_CARGO_CARRIED))))
            .andExpect(jsonPath("$.[*].coordinatesLatitude").value(hasItem(DEFAULT_COORDINATES_LATITUDE)))
            .andExpect(jsonPath("$.[*].coordinatesLongitude").value(hasItem(DEFAULT_COORDINATES_LONGITUDE)))
            .andExpect(jsonPath("$.[*].shipsHeading").value(hasItem(DEFAULT_SHIPS_HEADING)))
            .andExpect(jsonPath("$.[*].beaufortNo").value(hasItem(DEFAULT_BEAUFORT_NO)))
            .andExpect(jsonPath("$.[*].weatherCondition").value(hasItem(DEFAULT_WEATHER_CONDITION)))
            .andExpect(jsonPath("$.[*].swell").value(hasItem(DEFAULT_SWELL)));

        // Check, that the count call also returns 1
        restEventReportMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEventReportShouldNotBeFound(String filter) throws Exception {
        restEventReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventReportMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEventReport() throws Exception {
        // Get the eventReport
        restEventReportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventReport() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventReport
        EventReport updatedEventReport = eventReportRepository.findById(eventReport.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEventReport are not directly saved in db
        em.detach(updatedEventReport);
        updatedEventReport
            .documentDateAndTime(UPDATED_DOCUMENT_DATE_AND_TIME)
            .speedGps(UPDATED_SPEED_GPS)
            .documentDisplayNumber(UPDATED_DOCUMENT_DISPLAY_NUMBER)
            .leg(UPDATED_LEG)
            .distanceTravelled(UPDATED_DISTANCE_TRAVELLED)
            .hoursUnderway(UPDATED_HOURS_UNDERWAY)
            .eventStatus(UPDATED_EVENT_STATUS)
            .loadingCondition(UPDATED_LOADING_CONDITION)
            .cargoCarried(UPDATED_CARGO_CARRIED)
            .coordinatesLatitude(UPDATED_COORDINATES_LATITUDE)
            .coordinatesLongitude(UPDATED_COORDINATES_LONGITUDE)
            .shipsHeading(UPDATED_SHIPS_HEADING)
            .beaufortNo(UPDATED_BEAUFORT_NO)
            .weatherCondition(UPDATED_WEATHER_CONDITION)
            .swell(UPDATED_SWELL);
        EventReportDTO eventReportDTO = eventReportMapper.toDto(updatedEventReport);

        restEventReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventReportDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventReportDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEventReportToMatchAllProperties(updatedEventReport);
    }

    @Test
    @Transactional
    void putNonExistingEventReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventReport.setId(longCount.incrementAndGet());

        // Create the EventReport
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventReportDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventReport.setId(longCount.incrementAndGet());

        // Create the EventReport
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventReport.setId(longCount.incrementAndGet());

        // Create the EventReport
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReportMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventReportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventReportWithPatch() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventReport using partial update
        EventReport partialUpdatedEventReport = new EventReport();
        partialUpdatedEventReport.setId(eventReport.getId());

        partialUpdatedEventReport
            .documentDateAndTime(UPDATED_DOCUMENT_DATE_AND_TIME)
            .distanceTravelled(UPDATED_DISTANCE_TRAVELLED)
            .loadingCondition(UPDATED_LOADING_CONDITION)
            .cargoCarried(UPDATED_CARGO_CARRIED)
            .coordinatesLatitude(UPDATED_COORDINATES_LATITUDE)
            .coordinatesLongitude(UPDATED_COORDINATES_LONGITUDE)
            .beaufortNo(UPDATED_BEAUFORT_NO)
            .weatherCondition(UPDATED_WEATHER_CONDITION);

        restEventReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventReport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEventReport))
            )
            .andExpect(status().isOk());

        // Validate the EventReport in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEventReportUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEventReport, eventReport),
            getPersistedEventReport(eventReport)
        );
    }

    @Test
    @Transactional
    void fullUpdateEventReportWithPatch() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventReport using partial update
        EventReport partialUpdatedEventReport = new EventReport();
        partialUpdatedEventReport.setId(eventReport.getId());

        partialUpdatedEventReport
            .documentDateAndTime(UPDATED_DOCUMENT_DATE_AND_TIME)
            .speedGps(UPDATED_SPEED_GPS)
            .documentDisplayNumber(UPDATED_DOCUMENT_DISPLAY_NUMBER)
            .leg(UPDATED_LEG)
            .distanceTravelled(UPDATED_DISTANCE_TRAVELLED)
            .hoursUnderway(UPDATED_HOURS_UNDERWAY)
            .eventStatus(UPDATED_EVENT_STATUS)
            .loadingCondition(UPDATED_LOADING_CONDITION)
            .cargoCarried(UPDATED_CARGO_CARRIED)
            .coordinatesLatitude(UPDATED_COORDINATES_LATITUDE)
            .coordinatesLongitude(UPDATED_COORDINATES_LONGITUDE)
            .shipsHeading(UPDATED_SHIPS_HEADING)
            .beaufortNo(UPDATED_BEAUFORT_NO)
            .weatherCondition(UPDATED_WEATHER_CONDITION)
            .swell(UPDATED_SWELL);

        restEventReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventReport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEventReport))
            )
            .andExpect(status().isOk());

        // Validate the EventReport in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEventReportUpdatableFieldsEquals(partialUpdatedEventReport, getPersistedEventReport(partialUpdatedEventReport));
    }

    @Test
    @Transactional
    void patchNonExistingEventReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventReport.setId(longCount.incrementAndGet());

        // Create the EventReport
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventReportDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eventReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventReport.setId(longCount.incrementAndGet());

        // Create the EventReport
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eventReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventReport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventReport.setId(longCount.incrementAndGet());

        // Create the EventReport
        EventReportDTO eventReportDTO = eventReportMapper.toDto(eventReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReportMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(eventReportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventReport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventReport() throws Exception {
        // Initialize the database
        insertedEventReport = eventReportRepository.saveAndFlush(eventReport);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eventReport
        restEventReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventReport.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eventReportRepository.count();
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

    protected EventReport getPersistedEventReport(EventReport eventReport) {
        return eventReportRepository.findById(eventReport.getId()).orElseThrow();
    }

    protected void assertPersistedEventReportToMatchAllProperties(EventReport expectedEventReport) {
        assertEventReportAllPropertiesEquals(expectedEventReport, getPersistedEventReport(expectedEventReport));
    }

    protected void assertPersistedEventReportToMatchUpdatableProperties(EventReport expectedEventReport) {
        assertEventReportAllUpdatablePropertiesEquals(expectedEventReport, getPersistedEventReport(expectedEventReport));
    }
}
