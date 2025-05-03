package com.falaisia.ship.eventreporting2.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FuelTypeCriteriaTest {

    @Test
    void newFuelTypeCriteriaHasAllFiltersNullTest() {
        var fuelTypeCriteria = new FuelTypeCriteria();
        assertThat(fuelTypeCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void fuelTypeCriteriaFluentMethodsCreatesFiltersTest() {
        var fuelTypeCriteria = new FuelTypeCriteria();

        setAllFilters(fuelTypeCriteria);

        assertThat(fuelTypeCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void fuelTypeCriteriaCopyCreatesNullFilterTest() {
        var fuelTypeCriteria = new FuelTypeCriteria();
        var copy = fuelTypeCriteria.copy();

        assertThat(fuelTypeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(fuelTypeCriteria)
        );
    }

    @Test
    void fuelTypeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var fuelTypeCriteria = new FuelTypeCriteria();
        setAllFilters(fuelTypeCriteria);

        var copy = fuelTypeCriteria.copy();

        assertThat(fuelTypeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(fuelTypeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var fuelTypeCriteria = new FuelTypeCriteria();

        assertThat(fuelTypeCriteria).hasToString("FuelTypeCriteria{}");
    }

    private static void setAllFilters(FuelTypeCriteria fuelTypeCriteria) {
        fuelTypeCriteria.id();
        fuelTypeCriteria.name();
        fuelTypeCriteria.fuelTypeCode();
        fuelTypeCriteria.carbonFactory();
        fuelTypeCriteria.distinct();
    }

    private static Condition<FuelTypeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getFuelTypeCode()) &&
                condition.apply(criteria.getCarbonFactory()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FuelTypeCriteria> copyFiltersAre(FuelTypeCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getFuelTypeCode(), copy.getFuelTypeCode()) &&
                condition.apply(criteria.getCarbonFactory(), copy.getCarbonFactory()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
