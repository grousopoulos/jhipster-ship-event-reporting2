package com.falaisia.ship.eventreporting2.service.mapper;

import com.falaisia.ship.eventreporting2.domain.EventReport;
import com.falaisia.ship.eventreporting2.domain.MachineryOperationLine;
import com.falaisia.ship.eventreporting2.service.dto.EventReportDTO;
import com.falaisia.ship.eventreporting2.service.dto.MachineryOperationLineDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MachineryOperationLine} and its DTO {@link MachineryOperationLineDTO}.
 */
@Mapper(componentModel = "spring")
public interface MachineryOperationLineMapper extends EntityMapper<MachineryOperationLineDTO, MachineryOperationLine> {
    @Mapping(target = "eventReport", source = "eventReport", qualifiedByName = "eventReportId")
    MachineryOperationLineDTO toDto(MachineryOperationLine s);

    @Named("eventReportId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventReportDTO toDtoEventReportId(EventReport eventReport);
}
