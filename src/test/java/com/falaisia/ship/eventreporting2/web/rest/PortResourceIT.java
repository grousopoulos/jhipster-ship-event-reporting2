package com.falaisia.ship.eventreporting2.web.rest;

import static com.falaisia.ship.eventreporting2.domain.PortAsserts.*;
import static com.falaisia.ship.eventreporting2.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.falaisia.ship.eventreporting2.IntegrationTest;
import com.falaisia.ship.eventreporting2.domain.Port;
import com.falaisia.ship.eventreporting2.repository.PortRepository;
import com.falaisia.ship.eventreporting2.service.dto.PortDTO;
import com.falaisia.ship.eventreporting2.service.mapper.PortMapper;
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
 * Integration tests for the {@link PortResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PortResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UNLOCODE = "AAAAAAAAAA";
    private static final String UPDATED_UNLOCODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PortRepository portRepository;

    @Autowired
    private PortMapper portMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPortMockMvc;

    private Port port;

    private Port insertedPort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Port createEntity() {
        return new Port().name(DEFAULT_NAME).unlocode(DEFAULT_UNLOCODE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Port createUpdatedEntity() {
        return new Port().name(UPDATED_NAME).unlocode(UPDATED_UNLOCODE);
    }

    @BeforeEach
    void initTest() {
        port = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPort != null) {
            portRepository.delete(insertedPort);
            insertedPort = null;
        }
    }

    @Test
    @Transactional
    void createPort() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Port
        PortDTO portDTO = portMapper.toDto(port);
        var returnedPortDTO = om.readValue(
            restPortMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(portDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PortDTO.class
        );

        // Validate the Port in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPort = portMapper.toEntity(returnedPortDTO);
        assertPortUpdatableFieldsEquals(returnedPort, getPersistedPort(returnedPort));

        insertedPort = returnedPort;
    }

    @Test
    @Transactional
    void createPortWithExistingId() throws Exception {
        // Create the Port with an existing ID
        port.setId(1L);
        PortDTO portDTO = portMapper.toDto(port);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(portDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Port in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        port.setName(null);

        // Create the Port, which fails.
        PortDTO portDTO = portMapper.toDto(port);

        restPortMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(portDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnlocodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        port.setUnlocode(null);

        // Create the Port, which fails.
        PortDTO portDTO = portMapper.toDto(port);

        restPortMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(portDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPorts() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        // Get all the portList
        restPortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(port.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].unlocode").value(hasItem(DEFAULT_UNLOCODE)));
    }

    @Test
    @Transactional
    void getPort() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        // Get the port
        restPortMockMvc
            .perform(get(ENTITY_API_URL_ID, port.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(port.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.unlocode").value(DEFAULT_UNLOCODE));
    }

    @Test
    @Transactional
    void getPortsByIdFiltering() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        Long id = port.getId();

        defaultPortFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPortFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPortFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPortsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        // Get all the portList where name equals to
        defaultPortFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPortsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        // Get all the portList where name in
        defaultPortFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPortsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        // Get all the portList where name is not null
        defaultPortFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllPortsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        // Get all the portList where name contains
        defaultPortFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPortsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        // Get all the portList where name does not contain
        defaultPortFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllPortsByUnlocodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        // Get all the portList where unlocode equals to
        defaultPortFiltering("unlocode.equals=" + DEFAULT_UNLOCODE, "unlocode.equals=" + UPDATED_UNLOCODE);
    }

    @Test
    @Transactional
    void getAllPortsByUnlocodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        // Get all the portList where unlocode in
        defaultPortFiltering("unlocode.in=" + DEFAULT_UNLOCODE + "," + UPDATED_UNLOCODE, "unlocode.in=" + UPDATED_UNLOCODE);
    }

    @Test
    @Transactional
    void getAllPortsByUnlocodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        // Get all the portList where unlocode is not null
        defaultPortFiltering("unlocode.specified=true", "unlocode.specified=false");
    }

    @Test
    @Transactional
    void getAllPortsByUnlocodeContainsSomething() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        // Get all the portList where unlocode contains
        defaultPortFiltering("unlocode.contains=" + DEFAULT_UNLOCODE, "unlocode.contains=" + UPDATED_UNLOCODE);
    }

    @Test
    @Transactional
    void getAllPortsByUnlocodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        // Get all the portList where unlocode does not contain
        defaultPortFiltering("unlocode.doesNotContain=" + UPDATED_UNLOCODE, "unlocode.doesNotContain=" + DEFAULT_UNLOCODE);
    }

    private void defaultPortFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPortShouldBeFound(shouldBeFound);
        defaultPortShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPortShouldBeFound(String filter) throws Exception {
        restPortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(port.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].unlocode").value(hasItem(DEFAULT_UNLOCODE)));

        // Check, that the count call also returns 1
        restPortMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPortShouldNotBeFound(String filter) throws Exception {
        restPortMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPortMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPort() throws Exception {
        // Get the port
        restPortMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPort() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the port
        Port updatedPort = portRepository.findById(port.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPort are not directly saved in db
        em.detach(updatedPort);
        updatedPort.name(UPDATED_NAME).unlocode(UPDATED_UNLOCODE);
        PortDTO portDTO = portMapper.toDto(updatedPort);

        restPortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, portDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(portDTO))
            )
            .andExpect(status().isOk());

        // Validate the Port in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPortToMatchAllProperties(updatedPort);
    }

    @Test
    @Transactional
    void putNonExistingPort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        port.setId(longCount.incrementAndGet());

        // Create the Port
        PortDTO portDTO = portMapper.toDto(port);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, portDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(portDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Port in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        port.setId(longCount.incrementAndGet());

        // Create the Port
        PortDTO portDTO = portMapper.toDto(port);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(portDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Port in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        port.setId(longCount.incrementAndGet());

        // Create the Port
        PortDTO portDTO = portMapper.toDto(port);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(portDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Port in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePortWithPatch() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the port using partial update
        Port partialUpdatedPort = new Port();
        partialUpdatedPort.setId(port.getId());

        partialUpdatedPort.unlocode(UPDATED_UNLOCODE);

        restPortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPort.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPort))
            )
            .andExpect(status().isOk());

        // Validate the Port in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPortUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPort, port), getPersistedPort(port));
    }

    @Test
    @Transactional
    void fullUpdatePortWithPatch() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the port using partial update
        Port partialUpdatedPort = new Port();
        partialUpdatedPort.setId(port.getId());

        partialUpdatedPort.name(UPDATED_NAME).unlocode(UPDATED_UNLOCODE);

        restPortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPort.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPort))
            )
            .andExpect(status().isOk());

        // Validate the Port in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPortUpdatableFieldsEquals(partialUpdatedPort, getPersistedPort(partialUpdatedPort));
    }

    @Test
    @Transactional
    void patchNonExistingPort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        port.setId(longCount.incrementAndGet());

        // Create the Port
        PortDTO portDTO = portMapper.toDto(port);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, portDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(portDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Port in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        port.setId(longCount.incrementAndGet());

        // Create the Port
        PortDTO portDTO = portMapper.toDto(port);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(portDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Port in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPort() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        port.setId(longCount.incrementAndGet());

        // Create the Port
        PortDTO portDTO = portMapper.toDto(port);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(portDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Port in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePort() throws Exception {
        // Initialize the database
        insertedPort = portRepository.saveAndFlush(port);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the port
        restPortMockMvc
            .perform(delete(ENTITY_API_URL_ID, port.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return portRepository.count();
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

    protected Port getPersistedPort(Port port) {
        return portRepository.findById(port.getId()).orElseThrow();
    }

    protected void assertPersistedPortToMatchAllProperties(Port expectedPort) {
        assertPortAllPropertiesEquals(expectedPort, getPersistedPort(expectedPort));
    }

    protected void assertPersistedPortToMatchUpdatableProperties(Port expectedPort) {
        assertPortAllUpdatablePropertiesEquals(expectedPort, getPersistedPort(expectedPort));
    }
}
