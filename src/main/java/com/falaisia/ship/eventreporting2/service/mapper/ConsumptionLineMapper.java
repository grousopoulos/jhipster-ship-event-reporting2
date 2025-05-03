package com.falaisia.ship.eventreporting2.service.mapper;

import com.falaisia.ship.eventreporting2.domain.ConsumptionLine;
import com.falaisia.ship.eventreporting2.domain.EventReport;
import com.falaisia.ship.eventreporting2.domain.FuelType;
import com.falaisia.ship.eventreporting2.service.dto.ConsumptionLineDTO;
import com.falaisia.ship.eventreporting2.service.dto.EventReportDTO;
import com.falaisia.ship.eventreporting2.service.dto.FuelTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConsumptionLine} and its DTO {@link ConsumptionLineDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConsumptionLineMapper extends EntityMapper<ConsumptionLineDTO, ConsumptionLine> {
    @Mapping(target = "eventReport", source = "eventReport", qualifiedByName = "eventReportId")
    @Mapping(target = "fuelType", source = "fuelType", qualifiedByName = "fuelTypeId")
    ConsumptionLineDTO toDto(ConsumptionLine s);

    @Named("eventReportId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventReportDTO toDtoEventReportId(EventReport eventReport);

    @Named("fuelTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FuelTypeDTO toDtoFuelTypeId(FuelType fuelType);
}
