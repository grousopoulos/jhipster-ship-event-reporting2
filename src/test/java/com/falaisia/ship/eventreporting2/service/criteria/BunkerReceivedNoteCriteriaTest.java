package com.falaisia.ship.eventreporting2.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BunkerReceivedNoteCriteriaTest {

    @Test
    void newBunkerReceivedNoteCriteriaHasAllFiltersNullTest() {
        var bunkerReceivedNoteCriteria = new BunkerReceivedNoteCriteria();
        assertThat(bunkerReceivedNoteCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void bunkerReceivedNoteCriteriaFluentMethodsCreatesFiltersTest() {
        var bunkerReceivedNoteCriteria = new BunkerReceivedNoteCriteria();

        setAllFilters(bunkerReceivedNoteCriteria);

        assertThat(bunkerReceivedNoteCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void bunkerReceivedNoteCriteriaCopyCreatesNullFilterTest() {
        var bunkerReceivedNoteCriteria = new BunkerReceivedNoteCriteria();
        var copy = bunkerReceivedNoteCriteria.copy();

        assertThat(bunkerReceivedNoteCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(bunkerReceivedNoteCriteria)
        );
    }

    @Test
    void bunkerReceivedNoteCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var bunkerReceivedNoteCriteria = new BunkerReceivedNoteCriteria();
        setAllFilters(bunkerReceivedNoteCriteria);

        var copy = bunkerReceivedNoteCriteria.copy();

        assertThat(bunkerReceivedNoteCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(bunkerReceivedNoteCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var bunkerReceivedNoteCriteria = new BunkerReceivedNoteCriteria();

        assertThat(bunkerReceivedNoteCriteria).hasToString("BunkerReceivedNoteCriteria{}");
    }

    private static void setAllFilters(BunkerReceivedNoteCriteria bunkerReceivedNoteCriteria) {
        bunkerReceivedNoteCriteria.id();
        bunkerReceivedNoteCriteria.documentDateAndTime();
        bunkerReceivedNoteCriteria.documentDisplayNumber();
        bunkerReceivedNoteCriteria.voyageId();
        bunkerReceivedNoteCriteria.linesId();
        bunkerReceivedNoteCriteria.distinct();
    }

    private static Condition<BunkerReceivedNoteCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDocumentDateAndTime()) &&
                condition.apply(criteria.getDocumentDisplayNumber()) &&
                condition.apply(criteria.getVoyageId()) &&
                condition.apply(criteria.getLinesId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BunkerReceivedNoteCriteria> copyFiltersAre(
        BunkerReceivedNoteCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDocumentDateAndTime(), copy.getDocumentDateAndTime()) &&
                condition.apply(criteria.getDocumentDisplayNumber(), copy.getDocumentDisplayNumber()) &&
                condition.apply(criteria.getVoyageId(), copy.getVoyageId()) &&
                condition.apply(criteria.getLinesId(), copy.getLinesId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
