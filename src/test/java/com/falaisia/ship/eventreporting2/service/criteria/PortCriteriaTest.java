package com.falaisia.ship.eventreporting2.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PortCriteriaTest {

    @Test
    void newPortCriteriaHasAllFiltersNullTest() {
        var portCriteria = new PortCriteria();
        assertThat(portCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void portCriteriaFluentMethodsCreatesFiltersTest() {
        var portCriteria = new PortCriteria();

        setAllFilters(portCriteria);

        assertThat(portCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void portCriteriaCopyCreatesNullFilterTest() {
        var portCriteria = new PortCriteria();
        var copy = portCriteria.copy();

        assertThat(portCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(portCriteria)
        );
    }

    @Test
    void portCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var portCriteria = new PortCriteria();
        setAllFilters(portCriteria);

        var copy = portCriteria.copy();

        assertThat(portCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(portCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var portCriteria = new PortCriteria();

        assertThat(portCriteria).hasToString("PortCriteria{}");
    }

    private static void setAllFilters(PortCriteria portCriteria) {
        portCriteria.id();
        portCriteria.name();
        portCriteria.unlocode();
        portCriteria.distinct();
    }

    private static Condition<PortCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getUnlocode()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PortCriteria> copyFiltersAre(PortCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getUnlocode(), copy.getUnlocode()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
