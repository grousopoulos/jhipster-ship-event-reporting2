package com.falaisia.ship.eventreporting2.domain;

import static com.falaisia.ship.eventreporting2.domain.EventReportTestSamples.*;
import static com.falaisia.ship.eventreporting2.domain.MachineryOperationLineTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MachineryOperationLineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MachineryOperationLine.class);
        MachineryOperationLine machineryOperationLine1 = getMachineryOperationLineSample1();
        MachineryOperationLine machineryOperationLine2 = new MachineryOperationLine();
        assertThat(machineryOperationLine1).isNotEqualTo(machineryOperationLine2);

        machineryOperationLine2.setId(machineryOperationLine1.getId());
        assertThat(machineryOperationLine1).isEqualTo(machineryOperationLine2);

        machineryOperationLine2 = getMachineryOperationLineSample2();
        assertThat(machineryOperationLine1).isNotEqualTo(machineryOperationLine2);
    }

    @Test
    void eventReportTest() {
        MachineryOperationLine machineryOperationLine = getMachineryOperationLineRandomSampleGenerator();
        EventReport eventReportBack = getEventReportRandomSampleGenerator();

        machineryOperationLine.setEventReport(eventReportBack);
        assertThat(machineryOperationLine.getEventReport()).isEqualTo(eventReportBack);

        machineryOperationLine.eventReport(null);
        assertThat(machineryOperationLine.getEventReport()).isNull();
    }
}
