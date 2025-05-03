package com.falaisia.ship.eventreporting2.service.mapper;

import com.falaisia.ship.eventreporting2.domain.BunkerReceivedNote;
import com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteLine;
import com.falaisia.ship.eventreporting2.domain.FuelType;
import com.falaisia.ship.eventreporting2.service.dto.BunkerReceivedNoteDTO;
import com.falaisia.ship.eventreporting2.service.dto.BunkerReceivedNoteLineDTO;
import com.falaisia.ship.eventreporting2.service.dto.FuelTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BunkerReceivedNoteLine} and its DTO {@link BunkerReceivedNoteLineDTO}.
 */
@Mapper(componentModel = "spring")
public interface BunkerReceivedNoteLineMapper extends EntityMapper<BunkerReceivedNoteLineDTO, BunkerReceivedNoteLine> {
    @Mapping(target = "bunkerReceivedNote", source = "bunkerReceivedNote", qualifiedByName = "bunkerReceivedNoteId")
    @Mapping(target = "fuelType", source = "fuelType", qualifiedByName = "fuelTypeId")
    BunkerReceivedNoteLineDTO toDto(BunkerReceivedNoteLine s);

    @Named("bunkerReceivedNoteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BunkerReceivedNoteDTO toDtoBunkerReceivedNoteId(BunkerReceivedNote bunkerReceivedNote);

    @Named("fuelTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FuelTypeDTO toDtoFuelTypeId(FuelType fuelType);
}
