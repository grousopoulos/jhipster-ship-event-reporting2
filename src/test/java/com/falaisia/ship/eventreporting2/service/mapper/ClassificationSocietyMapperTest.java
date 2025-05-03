package com.falaisia.ship.eventreporting2.service.mapper;

import static com.falaisia.ship.eventreporting2.domain.ClassificationSocietyAsserts.*;
import static com.falaisia.ship.eventreporting2.domain.ClassificationSocietyTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClassificationSocietyMapperTest {

    private ClassificationSocietyMapper classificationSocietyMapper;

    @BeforeEach
    void setUp() {
        classificationSocietyMapper = new ClassificationSocietyMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getClassificationSocietySample1();
        var actual = classificationSocietyMapper.toEntity(classificationSocietyMapper.toDto(expected));
        assertClassificationSocietyAllPropertiesEquals(expected, actual);
    }
}
