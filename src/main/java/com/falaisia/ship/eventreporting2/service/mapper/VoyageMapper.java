package com.falaisia.ship.eventreporting2.service.mapper;

import com.falaisia.ship.eventreporting2.domain.Ship;
import com.falaisia.ship.eventreporting2.domain.Voyage;
import com.falaisia.ship.eventreporting2.service.dto.ShipDTO;
import com.falaisia.ship.eventreporting2.service.dto.VoyageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Voyage} and its DTO {@link VoyageDTO}.
 */
@Mapper(componentModel = "spring")
public interface VoyageMapper extends EntityMapper<VoyageDTO, Voyage> {
    @Mapping(target = "ship", source = "ship", qualifiedByName = "shipId")
    VoyageDTO toDto(Voyage s);

    @Named("shipId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShipDTO toDtoShipId(Ship ship);
}
