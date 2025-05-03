package com.falaisia.ship.eventreporting2.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BunkerReceivedNoteLineCriteriaTest {

    @Test
    void newBunkerReceivedNoteLineCriteriaHasAllFiltersNullTest() {
        var bunkerReceivedNoteLineCriteria = new BunkerReceivedNoteLineCriteria();
        assertThat(bunkerReceivedNoteLineCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void bunkerReceivedNoteLineCriteriaFluentMethodsCreatesFiltersTest() {
        var bunkerReceivedNoteLineCriteria = new BunkerReceivedNoteLineCriteria();

        setAllFilters(bunkerReceivedNoteLineCriteria);

        assertThat(bunkerReceivedNoteLineCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void bunkerReceivedNoteLineCriteriaCopyCreatesNullFilterTest() {
        var bunkerReceivedNoteLineCriteria = new BunkerReceivedNoteLineCriteria();
        var copy = bunkerReceivedNoteLineCriteria.copy();

        assertThat(bunkerReceivedNoteLineCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(bunkerReceivedNoteLineCriteria)
        );
    }

    @Test
    void bunkerReceivedNoteLineCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var bunkerReceivedNoteLineCriteria = new BunkerReceivedNoteLineCriteria();
        setAllFilters(bunkerReceivedNoteLineCriteria);

        var copy = bunkerReceivedNoteLineCriteria.copy();

        assertThat(bunkerReceivedNoteLineCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(bunkerReceivedNoteLineCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var bunkerReceivedNoteLineCriteria = new BunkerReceivedNoteLineCriteria();

        assertThat(bunkerReceivedNoteLineCriteria).hasToString("BunkerReceivedNoteLineCriteria{}");
    }

    private static void setAllFilters(BunkerReceivedNoteLineCriteria bunkerReceivedNoteLineCriteria) {
        bunkerReceivedNoteLineCriteria.id();
        bunkerReceivedNoteLineCriteria.quantity();
        bunkerReceivedNoteLineCriteria.unitOfMeasure();
        bunkerReceivedNoteLineCriteria.lowerCalorificValue();
        bunkerReceivedNoteLineCriteria.sulphurContent();
        bunkerReceivedNoteLineCriteria.density();
        bunkerReceivedNoteLineCriteria.viscosity();
        bunkerReceivedNoteLineCriteria.waterContent();
        bunkerReceivedNoteLineCriteria.bunkerReceivedNoteId();
        bunkerReceivedNoteLineCriteria.fuelTypeId();
        bunkerReceivedNoteLineCriteria.distinct();
    }

    private static Condition<BunkerReceivedNoteLineCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getQuantity()) &&
                condition.apply(criteria.getUnitOfMeasure()) &&
                condition.apply(criteria.getLowerCalorificValue()) &&
                condition.apply(criteria.getSulphurContent()) &&
                condition.apply(criteria.getDensity()) &&
                condition.apply(criteria.getViscosity()) &&
                condition.apply(criteria.getWaterContent()) &&
                condition.apply(criteria.getBunkerReceivedNoteId()) &&
                condition.apply(criteria.getFuelTypeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BunkerReceivedNoteLineCriteria> copyFiltersAre(
        BunkerReceivedNoteLineCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getQuantity(), copy.getQuantity()) &&
                condition.apply(criteria.getUnitOfMeasure(), copy.getUnitOfMeasure()) &&
                condition.apply(criteria.getLowerCalorificValue(), copy.getLowerCalorificValue()) &&
                condition.apply(criteria.getSulphurContent(), copy.getSulphurContent()) &&
                condition.apply(criteria.getDensity(), copy.getDensity()) &&
                condition.apply(criteria.getViscosity(), copy.getViscosity()) &&
                condition.apply(criteria.getWaterContent(), copy.getWaterContent()) &&
                condition.apply(criteria.getBunkerReceivedNoteId(), copy.getBunkerReceivedNoteId()) &&
                condition.apply(criteria.getFuelTypeId(), copy.getFuelTypeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
