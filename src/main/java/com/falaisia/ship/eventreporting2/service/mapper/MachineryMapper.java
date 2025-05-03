package com.falaisia.ship.eventreporting2.service.mapper;

import com.falaisia.ship.eventreporting2.domain.Machinery;
import com.falaisia.ship.eventreporting2.domain.Ship;
import com.falaisia.ship.eventreporting2.service.dto.MachineryDTO;
import com.falaisia.ship.eventreporting2.service.dto.ShipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Machinery} and its DTO {@link MachineryDTO}.
 */
@Mapper(componentModel = "spring")
public interface MachineryMapper extends EntityMapper<MachineryDTO, Machinery> {
    @Mapping(target = "ship", source = "ship", qualifiedByName = "shipId")
    MachineryDTO toDto(Machinery s);

    @Named("shipId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShipDTO toDtoShipId(Ship ship);
}
