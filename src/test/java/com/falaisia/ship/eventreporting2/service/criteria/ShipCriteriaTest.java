package com.falaisia.ship.eventreporting2.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ShipCriteriaTest {

    @Test
    void newShipCriteriaHasAllFiltersNullTest() {
        var shipCriteria = new ShipCriteria();
        assertThat(shipCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void shipCriteriaFluentMethodsCreatesFiltersTest() {
        var shipCriteria = new ShipCriteria();

        setAllFilters(shipCriteria);

        assertThat(shipCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void shipCriteriaCopyCreatesNullFilterTest() {
        var shipCriteria = new ShipCriteria();
        var copy = shipCriteria.copy();

        assertThat(shipCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(shipCriteria)
        );
    }

    @Test
    void shipCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var shipCriteria = new ShipCriteria();
        setAllFilters(shipCriteria);

        var copy = shipCriteria.copy();

        assertThat(shipCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(shipCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var shipCriteria = new ShipCriteria();

        assertThat(shipCriteria).hasToString("ShipCriteria{}");
    }

    private static void setAllFilters(ShipCriteria shipCriteria) {
        shipCriteria.id();
        shipCriteria.name();
        shipCriteria.callSign();
        shipCriteria.iceClassPolarCode();
        shipCriteria.technicalEfficiencyCode();
        shipCriteria.shipType();
        shipCriteria.monitoringMethodCode();
        shipCriteria.ownerCountryId();
        shipCriteria.flagId();
        shipCriteria.classificationSocietyId();
        shipCriteria.distinct();
    }

    private static Condition<ShipCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getCallSign()) &&
                condition.apply(criteria.getIceClassPolarCode()) &&
                condition.apply(criteria.getTechnicalEfficiencyCode()) &&
                condition.apply(criteria.getShipType()) &&
                condition.apply(criteria.getMonitoringMethodCode()) &&
                condition.apply(criteria.getOwnerCountryId()) &&
                condition.apply(criteria.getFlagId()) &&
                condition.apply(criteria.getClassificationSocietyId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ShipCriteria> copyFiltersAre(ShipCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getCallSign(), copy.getCallSign()) &&
                condition.apply(criteria.getIceClassPolarCode(), copy.getIceClassPolarCode()) &&
                condition.apply(criteria.getTechnicalEfficiencyCode(), copy.getTechnicalEfficiencyCode()) &&
                condition.apply(criteria.getShipType(), copy.getShipType()) &&
                condition.apply(criteria.getMonitoringMethodCode(), copy.getMonitoringMethodCode()) &&
                condition.apply(criteria.getOwnerCountryId(), copy.getOwnerCountryId()) &&
                condition.apply(criteria.getFlagId(), copy.getFlagId()) &&
                condition.apply(criteria.getClassificationSocietyId(), copy.getClassificationSocietyId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
