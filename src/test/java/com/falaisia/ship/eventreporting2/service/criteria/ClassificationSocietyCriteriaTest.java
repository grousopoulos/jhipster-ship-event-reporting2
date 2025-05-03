package com.falaisia.ship.eventreporting2.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ClassificationSocietyCriteriaTest {

    @Test
    void newClassificationSocietyCriteriaHasAllFiltersNullTest() {
        var classificationSocietyCriteria = new ClassificationSocietyCriteria();
        assertThat(classificationSocietyCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void classificationSocietyCriteriaFluentMethodsCreatesFiltersTest() {
        var classificationSocietyCriteria = new ClassificationSocietyCriteria();

        setAllFilters(classificationSocietyCriteria);

        assertThat(classificationSocietyCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void classificationSocietyCriteriaCopyCreatesNullFilterTest() {
        var classificationSocietyCriteria = new ClassificationSocietyCriteria();
        var copy = classificationSocietyCriteria.copy();

        assertThat(classificationSocietyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(classificationSocietyCriteria)
        );
    }

    @Test
    void classificationSocietyCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var classificationSocietyCriteria = new ClassificationSocietyCriteria();
        setAllFilters(classificationSocietyCriteria);

        var copy = classificationSocietyCriteria.copy();

        assertThat(classificationSocietyCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(classificationSocietyCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var classificationSocietyCriteria = new ClassificationSocietyCriteria();

        assertThat(classificationSocietyCriteria).hasToString("ClassificationSocietyCriteria{}");
    }

    private static void setAllFilters(ClassificationSocietyCriteria classificationSocietyCriteria) {
        classificationSocietyCriteria.id();
        classificationSocietyCriteria.code();
        classificationSocietyCriteria.name();
        classificationSocietyCriteria.distinct();
    }

    private static Condition<ClassificationSocietyCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ClassificationSocietyCriteria> copyFiltersAre(
        ClassificationSocietyCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
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
