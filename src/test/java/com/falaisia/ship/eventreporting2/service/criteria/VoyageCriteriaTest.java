package com.falaisia.ship.eventreporting2.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class VoyageCriteriaTest {

    @Test
    void newVoyageCriteriaHasAllFiltersNullTest() {
        var voyageCriteria = new VoyageCriteria();
        assertThat(voyageCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void voyageCriteriaFluentMethodsCreatesFiltersTest() {
        var voyageCriteria = new VoyageCriteria();

        setAllFilters(voyageCriteria);

        assertThat(voyageCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void voyageCriteriaCopyCreatesNullFilterTest() {
        var voyageCriteria = new VoyageCriteria();
        var copy = voyageCriteria.copy();

        assertThat(voyageCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(voyageCriteria)
        );
    }

    @Test
    void voyageCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var voyageCriteria = new VoyageCriteria();
        setAllFilters(voyageCriteria);

        var copy = voyageCriteria.copy();

        assertThat(voyageCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(voyageCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var voyageCriteria = new VoyageCriteria();

        assertThat(voyageCriteria).hasToString("VoyageCriteria{}");
    }

    private static void setAllFilters(VoyageCriteria voyageCriteria) {
        voyageCriteria.id();
        voyageCriteria.number();
        voyageCriteria.shipId();
        voyageCriteria.distinct();
    }

    private static Condition<VoyageCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNumber()) &&
                condition.apply(criteria.getShipId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<VoyageCriteria> copyFiltersAre(VoyageCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNumber(), copy.getNumber()) &&
                condition.apply(criteria.getShipId(), copy.getShipId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
