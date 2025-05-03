package com.falaisia.ship.eventreporting2.service.mapper;

import com.falaisia.ship.eventreporting2.domain.Flag;
import com.falaisia.ship.eventreporting2.service.dto.FlagDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Flag} and its DTO {@link FlagDTO}.
 */
@Mapper(componentModel = "spring")
public interface FlagMapper extends EntityMapper<FlagDTO, Flag> {}
