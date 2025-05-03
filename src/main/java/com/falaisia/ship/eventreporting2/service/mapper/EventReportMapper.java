package com.falaisia.ship.eventreporting2.service.mapper;

import com.falaisia.ship.eventreporting2.domain.EventReport;
import com.falaisia.ship.eventreporting2.domain.Voyage;
import com.falaisia.ship.eventreporting2.service.dto.EventReportDTO;
import com.falaisia.ship.eventreporting2.service.dto.VoyageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventReport} and its DTO {@link EventReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventReportMapper extends EntityMapper<EventReportDTO, EventReport> {
    @Mapping(target = "voyage", source = "voyage", qualifiedByName = "voyageId")
    EventReportDTO toDto(EventReport s);

    @Named("voyageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VoyageDTO toDtoVoyageId(Voyage voyage);
}
