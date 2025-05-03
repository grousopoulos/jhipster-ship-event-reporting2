package com.falaisia.ship.eventreporting2.domain;

import static com.falaisia.ship.eventreporting2.domain.ConsumptionLineTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.EventReportTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.FuelTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConsumptionLineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsumptionLine.class);
        ConsumptionLine consumptionLine1 = getConsumptionLineSample1();
        ConsumptionLine consumptionLine2 = new ConsumptionLine();
        assertThat(consumptionLine1).isNotEqualTo(consumptionLine2);

        consumptionLine2.setId(consumptionLine1.getId());
        assertThat(consumptionLine1).isEqualTo(consumptionLine2);

        consumptionLine2 = getConsumptionLineSample2();
        assertThat(consumptionLine1).isNotEqualTo(consumptionLine2);
    }

    @Test
    void eventReportTest() {
        ConsumptionLine consumptionLine = getConsumptionLineRandomSampleGenerator();
        EventReport eventReportBack = getEventReportRandomSampleGenerator();

        consumptionLine.setEventReport(eventReportBack);
        assertThat(consumptionLine.getEventReport()).isEqualTo(eventReportBack);

        consumptionLine.eventReport(null);
        assertThat(consumptionLine.getEventReport()).isNull();
    }

    @Test
    void fuelTypeTest() {
        ConsumptionLine consumptionLine = getConsumptionLineRandomSampleGenerator();
        FuelType fuelTypeBack = getFuelTypeRandomSampleGenerator();

        consumptionLine.setFuelType(fuelTypeBack);
        assertThat(consumptionLine.getFuelType()).isEqualTo(fuelTypeBack);

        consumptionLine.fuelType(null);
        assertThat(consumptionLine.getFuelType()).isNull();
    }
}
