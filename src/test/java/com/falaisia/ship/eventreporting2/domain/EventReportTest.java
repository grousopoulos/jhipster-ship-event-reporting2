package com.falaisia.ship.eventreporting2.domain;

import static com.falaisia.ship.eventreporting2.domain.ConsumptionLineTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.EventReportTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.MachineryOperationLineTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.VoyageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EventReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventReport.class);
        EventReport eventReport1 = getEventReportSample1();
        EventReport eventReport2 = new EventReport();
        assertThat(eventReport1).isNotEqualTo(eventReport2);

        eventReport2.setId(eventReport1.getId());
        assertThat(eventReport1).isEqualTo(eventReport2);

        eventReport2 = getEventReportSample2();
        assertThat(eventReport1).isNotEqualTo(eventReport2);
    }

    @Test
    void voyageTest() {
        EventReport eventReport = getEventReportRandomSampleGenerator();
        Voyage voyageBack = getVoyageRandomSampleGenerator();

        eventReport.setVoyage(voyageBack);
        assertThat(eventReport.getVoyage()).isEqualTo(voyageBack);

        eventReport.voyage(null);
        assertThat(eventReport.getVoyage()).isNull();
    }

    @Test
    void linesTest() {
        EventReport eventReport = getEventReportRandomSampleGenerator();
        ConsumptionLine consumptionLineBack = getConsumptionLineRandomSampleGenerator();

        eventReport.addLines(consumptionLineBack);
        assertThat(eventReport.getLines()).containsOnly(consumptionLineBack);
        assertThat(consumptionLineBack.getEventReport()).isEqualTo(eventReport);

        eventReport.removeLines(consumptionLineBack);
        assertThat(eventReport.getLines()).doesNotContain(consumptionLineBack);
        assertThat(consumptionLineBack.getEventReport()).isNull();

        eventReport.lines(new HashSet<>(Set.of(consumptionLineBack)));
        assertThat(eventReport.getLines()).containsOnly(consumptionLineBack);
        assertThat(consumptionLineBack.getEventReport()).isEqualTo(eventReport);

        eventReport.setLines(new HashSet<>());
        assertThat(eventReport.getLines()).doesNotContain(consumptionLineBack);
        assertThat(consumptionLineBack.getEventReport()).isNull();
    }

    @Test
    void operationLinesTest() {
        EventReport eventReport = getEventReportRandomSampleGenerator();
        MachineryOperationLine machineryOperationLineBack = getMachineryOperationLineRandomSampleGenerator();

        eventReport.addOperationLines(machineryOperationLineBack);
        assertThat(eventReport.getOperationLines()).containsOnly(machineryOperationLineBack);
        assertThat(machineryOperationLineBack.getEventReport()).isEqualTo(eventReport);

        eventReport.removeOperationLines(machineryOperationLineBack);
        assertThat(eventReport.getOperationLines()).doesNotContain(machineryOperationLineBack);
        assertThat(machineryOperationLineBack.getEventReport()).isNull();

        eventReport.operationLines(new HashSet<>(Set.of(machineryOperationLineBack)));
        assertThat(eventReport.getOperationLines()).containsOnly(machineryOperationLineBack);
        assertThat(machineryOperationLineBack.getEventReport()).isEqualTo(eventReport);

        eventReport.setOperationLines(new HashSet<>());
        assertThat(eventReport.getOperationLines()).doesNotContain(machineryOperationLineBack);
        assertThat(machineryOperationLineBack.getEventReport()).isNull();
    }
}
