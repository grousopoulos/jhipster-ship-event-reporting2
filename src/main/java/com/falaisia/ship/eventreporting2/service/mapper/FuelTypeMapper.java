package com.falaisia.ship.eventreporting2.service.mapper;

import com.falaisia.ship.eventreporting2.domain.FuelType;
import com.falaisia.ship.eventreporting2.service.dto.FuelTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FuelType} and its DTO {@link FuelTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface FuelTypeMapper extends EntityMapper<FuelTypeDTO, FuelType> {}
