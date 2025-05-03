package com.falaisia.ship.eventreporting2;

import com.falaisia.ship.eventreporting2.config.AsyncSyncConfiguration;
import com.falaisia.ship.eventreporting2.config.EmbeddedSQL;
import com.falaisia.ship.eventreporting2.config.JacksonConfiguration;
import com.falaisia.ship.eventreporting2.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = {
        JhipsterShipEventReporting2App.class, JacksonConfiguration.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class,
    }
)
@EmbeddedSQL
public @interface IntegrationTest {
}
