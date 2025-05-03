package com.falaisia.ship.eventreporting2.service.mapper;

import com.falaisia.ship.eventreporting2.domain.Country;
import com.falaisia.ship.eventreporting2.service.dto.CountryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Country} and its DTO {@link CountryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CountryMapper extends EntityMapper<CountryDTO, Country> {}
