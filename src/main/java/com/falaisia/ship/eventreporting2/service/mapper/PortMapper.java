package com.falaisia.ship.eventreporting2.service.mapper;

import com.falaisia.ship.eventreporting2.domain.Port;
import com.falaisia.ship.eventreporting2.service.dto.PortDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Port} and its DTO {@link PortDTO}.
 */
@Mapper(componentModel = "spring")
public interface PortMapper extends EntityMapper<PortDTO, Port> {}
