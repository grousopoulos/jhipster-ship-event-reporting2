package com.falaisia.ship.eventreporting2.service.mapper;

import com.falaisia.ship.eventreporting2.domain.FuelEuRegulation;
import com.falaisia.ship.eventreporting2.service.dto.FuelEuRegulationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FuelEuRegulation} and its DTO {@link FuelEuRegulationDTO}.
 */
@Mapper(componentModel = "spring")
public interface FuelEuRegulationMapper extends EntityMapper<FuelEuRegulationDTO, FuelEuRegulation> {}
