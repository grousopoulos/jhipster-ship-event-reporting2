package com.falaisia.ship.eventreporting2.service;

import com.falaisia.ship.eventreporting2.domain.Port;
import com.falaisia.ship.eventreporting2.repository.PortRepository;
import com.falaisia.ship.eventreporting2.service.dto.PortDTO;
import com.falaisia.ship.eventreporting2.service.mapper.PortMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.falaisia.ship.eventreporting2.domain.Port}.
 */
@Service
@Transactional
public class PortService {

    private static final Logger LOG = LoggerFactory.getLogger(PortService.class);

    private final PortRepository portRepository;

    private final PortMapper portMapper;

    public PortService(PortRepository portRepository, PortMapper portMapper) {
        this.portRepository = portRepository;
        this.portMapper = portMapper;
    }

    /**
     * Save a port.
     *
     * @param portDTO the entity to save.
     * @return the persisted entity.
     */
    public PortDTO save(PortDTO portDTO) {
        LOG.debug("Request to save Port : {}", portDTO);
        Port port = portMapper.toEntity(portDTO);
        port = portRepository.save(port);
        return portMapper.toDto(port);
    }

    /**
     * Update a port.
     *
     * @param portDTO the entity to save.
     * @return the persisted entity.
     */
    public PortDTO update(PortDTO portDTO) {
        LOG.debug("Request to update Port : {}", portDTO);
        Port port = portMapper.toEntity(portDTO);
        port = portRepository.save(port);
        return portMapper.toDto(port);
    }

    /**
     * Partially update a port.
     *
     * @param portDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PortDTO> partialUpdate(PortDTO portDTO) {
        LOG.debug("Request to partially update Port : {}", portDTO);

        return portRepository
            .findById(portDTO.getId())
            .map(existingPort -> {
                portMapper.partialUpdate(existingPort, portDTO);

                return existingPort;
            })
            .map(portRepository::save)
            .map(portMapper::toDto);
    }

    /**
     * Get one port by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PortDTO> findOne(Long id) {
        LOG.debug("Request to get Port : {}", id);
        return portRepository.findById(id).map(portMapper::toDto);
    }

    /**
     * Delete the port by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Port : {}", id);
        portRepository.deleteById(id);
    }
}
