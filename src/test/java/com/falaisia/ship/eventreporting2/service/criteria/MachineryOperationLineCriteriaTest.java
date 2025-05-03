package com.falaisia.ship.eventreporting2.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class MachineryOperationLineCriteriaTest {

    @Test
    void newMachineryOperationLineCriteriaHasAllFiltersNullTest() {
        var machineryOperationLineCriteria = new MachineryOperationLineCriteria();
        assertThat(machineryOperationLineCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void machineryOperationLineCriteriaFluentMethodsCreatesFiltersTest() {
        var machineryOperationLineCriteria = new MachineryOperationLineCriteria();

        setAllFilters(machineryOperationLineCriteria);

        assertThat(machineryOperationLineCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void machineryOperationLineCriteriaCopyCreatesNullFilterTest() {
        var machineryOperationLineCriteria = new MachineryOperationLineCriteria();
        var copy = machineryOperationLineCriteria.copy();

        assertThat(machineryOperationLineCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(machineryOperationLineCriteria)
        );
    }

    @Test
    void machineryOperationLineCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var machineryOperationLineCriteria = new MachineryOperationLineCriteria();
        setAllFilters(machineryOperationLineCriteria);

        var copy = machineryOperationLineCriteria.copy();

        assertThat(machineryOperationLineCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(machineryOperationLineCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var machineryOperationLineCriteria = new MachineryOperationLineCriteria();

        assertThat(machineryOperationLineCriteria).hasToString("MachineryOperationLineCriteria{}");
    }

    private static void setAllFilters(MachineryOperationLineCriteria machineryOperationLineCriteria) {
        machineryOperationLineCriteria.id();
        machineryOperationLineCriteria.runningHours();
        machineryOperationLineCriteria.powerOutput();
        machineryOperationLineCriteria.averageRpm();
        machineryOperationLineCriteria.eventReportId();
        machineryOperationLineCriteria.distinct();
    }

    private static Condition<MachineryOperationLineCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRunningHours()) &&
                condition.apply(criteria.getPowerOutput()) &&
                condition.apply(criteria.getAverageRpm()) &&
                condition.apply(criteria.getEventReportId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<MachineryOperationLineCriteria> copyFiltersAre(
        MachineryOperationLineCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRunningHours(), copy.getRunningHours()) &&
                condition.apply(criteria.getPowerOutput(), copy.getPowerOutput()) &&
                condition.apply(criteria.getAverageRpm(), copy.getAverageRpm()) &&
                condition.apply(criteria.getEventReportId(), copy.getEventReportId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
