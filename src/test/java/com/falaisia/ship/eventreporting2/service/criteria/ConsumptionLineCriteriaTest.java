package com.falaisia.ship.eventreporting2.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ConsumptionLineCriteriaTest {

    @Test
    void newConsumptionLineCriteriaHasAllFiltersNullTest() {
        var consumptionLineCriteria = new ConsumptionLineCriteria();
        assertThat(consumptionLineCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void consumptionLineCriteriaFluentMethodsCreatesFiltersTest() {
        var consumptionLineCriteria = new ConsumptionLineCriteria();

        setAllFilters(consumptionLineCriteria);

        assertThat(consumptionLineCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void consumptionLineCriteriaCopyCreatesNullFilterTest() {
        var consumptionLineCriteria = new ConsumptionLineCriteria();
        var copy = consumptionLineCriteria.copy();

        assertThat(consumptionLineCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(consumptionLineCriteria)
        );
    }

    @Test
    void consumptionLineCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var consumptionLineCriteria = new ConsumptionLineCriteria();
        setAllFilters(consumptionLineCriteria);

        var copy = consumptionLineCriteria.copy();

        assertThat(consumptionLineCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(consumptionLineCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var consumptionLineCriteria = new ConsumptionLineCriteria();

        assertThat(consumptionLineCriteria).hasToString("ConsumptionLineCriteria{}");
    }

    private static void setAllFilters(ConsumptionLineCriteria consumptionLineCriteria) {
        consumptionLineCriteria.id();
        consumptionLineCriteria.quantity();
        consumptionLineCriteria.unitOfMeasure();
        consumptionLineCriteria.co2EmissionSourceTypeCode();
        consumptionLineCriteria.lowerCalorificValue();
        consumptionLineCriteria.sulphurContent();
        consumptionLineCriteria.density();
        consumptionLineCriteria.viscosity();
        consumptionLineCriteria.waterContent();
        consumptionLineCriteria.portActivityCode();
        consumptionLineCriteria.diffCriterionCode();
        consumptionLineCriteria.eventReportId();
        consumptionLineCriteria.fuelTypeId();
        consumptionLineCriteria.distinct();
    }

    private static Condition<ConsumptionLineCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getQuantity()) &&
                condition.apply(criteria.getUnitOfMeasure()) &&
                condition.apply(criteria.getCo2EmissionSourceTypeCode()) &&
                condition.apply(criteria.getLowerCalorificValue()) &&
                condition.apply(criteria.getSulphurContent()) &&
                condition.apply(criteria.getDensity()) &&
                condition.apply(criteria.getViscosity()) &&
                condition.apply(criteria.getWaterContent()) &&
                condition.apply(criteria.getPortActivityCode()) &&
                condition.apply(criteria.getDiffCriterionCode()) &&
                condition.apply(criteria.getEventReportId()) &&
                condition.apply(criteria.getFuelTypeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ConsumptionLineCriteria> copyFiltersAre(
        ConsumptionLineCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getQuantity(), copy.getQuantity()) &&
                condition.apply(criteria.getUnitOfMeasure(), copy.getUnitOfMeasure()) &&
                condition.apply(criteria.getCo2EmissionSourceTypeCode(), copy.getCo2EmissionSourceTypeCode()) &&
                condition.apply(criteria.getLowerCalorificValue(), copy.getLowerCalorificValue()) &&
                condition.apply(criteria.getSulphurContent(), copy.getSulphurContent()) &&
                condition.apply(criteria.getDensity(), copy.getDensity()) &&
                condition.apply(criteria.getViscosity(), copy.getViscosity()) &&
                condition.apply(criteria.getWaterContent(), copy.getWaterContent()) &&
                condition.apply(criteria.getPortActivityCode(), copy.getPortActivityCode()) &&
                condition.apply(criteria.getDiffCriterionCode(), copy.getDiffCriterionCode()) &&
                condition.apply(criteria.getEventReportId(), copy.getEventReportId()) &&
                condition.apply(criteria.getFuelTypeId(), copy.getFuelTypeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
