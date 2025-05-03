package com.falaisia.ship.eventreporting2.service.mapper;

import com.falaisia.ship.eventreporting2.domain.BunkerReceivedNote;
import com.falaisia.ship.eventreporting2.domain.Voyage;
import com.falaisia.ship.eventreporting2.service.dto.BunkerReceivedNoteDTO;
import com.falaisia.ship.eventreporting2.service.dto.VoyageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BunkerReceivedNote} and its DTO {@link BunkerReceivedNoteDTO}.
 */
@Mapper(componentModel = "spring")
public interface BunkerReceivedNoteMapper extends EntityMapper<BunkerReceivedNoteDTO, BunkerReceivedNote> {
    @Mapping(target = "voyage", source = "voyage", qualifiedByName = "voyageId")
    BunkerReceivedNoteDTO toDto(BunkerReceivedNote s);

    @Named("voyageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VoyageDTO toDtoVoyageId(Voyage voyage);
}
