package com.falaisia.ship.eventreporting2.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FlagCriteriaTest {

    @Test
    void newFlagCriteriaHasAllFiltersNullTest() {
        var flagCriteria = new FlagCriteria();
        assertThat(flagCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void flagCriteriaFluentMethodsCreatesFiltersTest() {
        var flagCriteria = new FlagCriteria();

        setAllFilters(flagCriteria);

        assertThat(flagCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void flagCriteriaCopyCreatesNullFilterTest() {
        var flagCriteria = new FlagCriteria();
        var copy = flagCriteria.copy();

        assertThat(flagCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(flagCriteria)
        );
    }

    @Test
    void flagCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var flagCriteria = new FlagCriteria();
        setAllFilters(flagCriteria);

        var copy = flagCriteria.copy();

        assertThat(flagCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(flagCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var flagCriteria = new FlagCriteria();

        assertThat(flagCriteria).hasToString("FlagCriteria{}");
    }

    private static void setAllFilters(FlagCriteria flagCriteria) {
        flagCriteria.id();
        flagCriteria.code();
        flagCriteria.name();
        flagCriteria.distinct();
    }

    private static Condition<FlagCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FlagCriteria> copyFiltersAre(FlagCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
