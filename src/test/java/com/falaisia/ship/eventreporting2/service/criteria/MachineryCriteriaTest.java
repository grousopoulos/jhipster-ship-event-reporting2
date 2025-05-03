package com.falaisia.ship.eventreporting2.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class MachineryCriteriaTest {

    @Test
    void newMachineryCriteriaHasAllFiltersNullTest() {
        var machineryCriteria = new MachineryCriteria();
        assertThat(machineryCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void machineryCriteriaFluentMethodsCreatesFiltersTest() {
        var machineryCriteria = new MachineryCriteria();

        setAllFilters(machineryCriteria);

        assertThat(machineryCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void machineryCriteriaCopyCreatesNullFilterTest() {
        var machineryCriteria = new MachineryCriteria();
        var copy = machineryCriteria.copy();

        assertThat(machineryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(machineryCriteria)
        );
    }

    @Test
    void machineryCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var machineryCriteria = new MachineryCriteria();
        setAllFilters(machineryCriteria);

        var copy = machineryCriteria.copy();

        assertThat(machineryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(machineryCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var machineryCriteria = new MachineryCriteria();

        assertThat(machineryCriteria).hasToString("MachineryCriteria{}");
    }

    private static void setAllFilters(MachineryCriteria machineryCriteria) {
        machineryCriteria.id();
        machineryCriteria.name();
        machineryCriteria.shipId();
        machineryCriteria.distinct();
    }

    private static Condition<MachineryCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getShipId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<MachineryCriteria> copyFiltersAre(MachineryCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getShipId(), copy.getShipId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
