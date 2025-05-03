package com.falaisia.ship.eventreporting2.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CountryCriteriaTest {

    @Test
    void newCountryCriteriaHasAllFiltersNullTest() {
        var countryCriteria = new CountryCriteria();
        assertThat(countryCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void countryCriteriaFluentMethodsCreatesFiltersTest() {
        var countryCriteria = new CountryCriteria();

        setAllFilters(countryCriteria);

        assertThat(countryCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void countryCriteriaCopyCreatesNullFilterTest() {
        var countryCriteria = new CountryCriteria();
        var copy = countryCriteria.copy();

        assertThat(countryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(countryCriteria)
        );
    }

    @Test
    void countryCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var countryCriteria = new CountryCriteria();
        setAllFilters(countryCriteria);

        var copy = countryCriteria.copy();

        assertThat(countryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(countryCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var countryCriteria = new CountryCriteria();

        assertThat(countryCriteria).hasToString("CountryCriteria{}");
    }

    private static void setAllFilters(CountryCriteria countryCriteria) {
        countryCriteria.id();
        countryCriteria.code();
        countryCriteria.name();
        countryCriteria.distinct();
    }

    private static Condition<CountryCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CountryCriteria> copyFiltersAre(CountryCriteria copy, BiFunction<Object, Object, Boolean> condition) {
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
