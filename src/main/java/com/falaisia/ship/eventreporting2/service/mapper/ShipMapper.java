package com.falaisia.ship.eventreporting2.service.mapper;

import com.falaisia.ship.eventreporting2.domain.ClassificationSociety;
import com.falaisia.ship.eventreporting2.domain.Country;
import com.falaisia.ship.eventreporting2.domain.Flag;
import com.falaisia.ship.eventreporting2.domain.Ship;
import com.falaisia.ship.eventreporting2.service.dto.ClassificationSocietyDTO;
import com.falaisia.ship.eventreporting2.service.dto.CountryDTO;
import com.falaisia.ship.eventreporting2.service.dto.FlagDTO;
import com.falaisia.ship.eventreporting2.service.dto.ShipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ship} and its DTO {@link ShipDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShipMapper extends EntityMapper<ShipDTO, Ship> {
    @Mapping(target = "ownerCountry", source = "ownerCountry", qualifiedByName = "countryId")
    @Mapping(target = "flag", source = "flag", qualifiedByName = "flagId")
    @Mapping(target = "classificationSociety", source = "classificationSociety", qualifiedByName = "classificationSocietyId")
    ShipDTO toDto(Ship s);

    @Named("countryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountryDTO toDtoCountryId(Country country);

    @Named("flagId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FlagDTO toDtoFlagId(Flag flag);

    @Named("classificationSocietyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClassificationSocietyDTO toDtoClassificationSocietyId(ClassificationSociety classificationSociety);
}
