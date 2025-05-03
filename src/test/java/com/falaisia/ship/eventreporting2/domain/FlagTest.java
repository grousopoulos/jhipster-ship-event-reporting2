package com.falaisia.ship.eventreporting2.domain;

import static com.falaisia.ship.eventreporting2.domain.FlagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.falaisia.ship.eventreporting2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FlagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Flag.class);
        Flag flag1 = getFlagSample1();
        Flag flag2 = new Flag();
        assertThat(flag1).isNotEqualTo(flag2);

        flag2.setId(flag1.getId());
        assertThat(flag1).isEqualTo(flag2);

        flag2 = getFlagSample2();
        assertThat(flag1).isNotEqualTo(flag2);
    }
}
