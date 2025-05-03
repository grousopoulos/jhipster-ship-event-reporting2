package com.falaisia.ship.eventreporting2.domain;

import static com.falaisia.ship.eventreporting2.domain.PortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Port.class);
        Port port1 = getPortSample1();
        Port port2 = new Port();
        assertThat(port1).isNotEqualTo(port2);

        port2.setId(port1.getId());
        assertThat(port1).isEqualTo(port2);

        port2 = getPortSample2();
        assertThat(port1).isNotEqualTo(port2);
    }
}
