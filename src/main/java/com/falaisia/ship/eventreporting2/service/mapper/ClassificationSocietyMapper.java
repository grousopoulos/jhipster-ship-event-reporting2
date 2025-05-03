package com.falaisia.ship.eventreporting2.service.mapper;

import com.falaisia.ship.eventreporting2.domain.ClassificationSociety;
import com.falaisia.ship.eventreporting2.service.dto.ClassificationSocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClassificationSociety} and its DTO {@link ClassificationSocietyDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClassificationSocietyMapper extends EntityMapper<ClassificationSocietyDTO, ClassificationSociety> {}
