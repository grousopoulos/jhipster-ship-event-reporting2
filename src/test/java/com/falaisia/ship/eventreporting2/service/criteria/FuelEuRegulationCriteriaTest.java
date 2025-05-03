package com.falaisia.ship.eventreporting2.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FuelEuRegulationCriteriaTest {

    @Test
    void newFuelEuRegulationCriteriaHasAllFiltersNullTest() {
        var fuelEuRegulationCriteria = new FuelEuRegulationCriteria();
        assertThat(fuelEuRegulationCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void fuelEuRegulationCriteriaFluentMethodsCreatesFiltersTest() {
        var fuelEuRegulationCriteria = new FuelEuRegulationCriteria();

        setAllFilters(fuelEuRegulationCriteria);

        assertThat(fuelEuRegulationCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void fuelEuRegulationCriteriaCopyCreatesNullFilterTest() {
        var fuelEuRegulationCriteria = new FuelEuRegulationCriteria();
        var copy = fuelEuRegulationCriteria.copy();

        assertThat(fuelEuRegulationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(fuelEuRegulationCriteria)
        );
    }

    @Test
    void fuelEuRegulationCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var fuelEuRegulationCriteria = new FuelEuRegulationCriteria();
        setAllFilters(fuelEuRegulationCriteria);

        var copy = fuelEuRegulationCriteria.copy();

        assertThat(fuelEuRegulationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(fuelEuRegulationCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var fuelEuRegulationCriteria = new FuelEuRegulationCriteria();

        assertThat(fuelEuRegulationCriteria).hasToString("FuelEuRegulationCriteria{}");
    }

    private static void setAllFilters(FuelEuRegulationCriteria fuelEuRegulationCriteria) {
        fuelEuRegulationCriteria.id();
        fuelEuRegulationCriteria.year();
        fuelEuRegulationCriteria.co2Gwp();
        fuelEuRegulationCriteria.methaneGwp();
        fuelEuRegulationCriteria.nitrousGwp();
        fuelEuRegulationCriteria.targetIntensity();
        fuelEuRegulationCriteria.baselineIntensity();
        fuelEuRegulationCriteria.reductionFactorPercent();
        fuelEuRegulationCriteria.vlsfoEnergyContentPerTonMj();
        fuelEuRegulationCriteria.vlsfoPenaltyEurPerTon();
        fuelEuRegulationCriteria.energyAllowanceMultiplier();
        fuelEuRegulationCriteria.nonBioFuelRewardFactor();
        fuelEuRegulationCriteria.distinct();
    }

    private static Condition<FuelEuRegulationCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getYear()) &&
                condition.apply(criteria.getCo2Gwp()) &&
                condition.apply(criteria.getMethaneGwp()) &&
                condition.apply(criteria.getNitrousGwp()) &&
                condition.apply(criteria.getTargetIntensity()) &&
                condition.apply(criteria.getBaselineIntensity()) &&
                condition.apply(criteria.getReductionFactorPercent()) &&
                condition.apply(criteria.getVlsfoEnergyContentPerTonMj()) &&
                condition.apply(criteria.getVlsfoPenaltyEurPerTon()) &&
                condition.apply(criteria.getEnergyAllowanceMultiplier()) &&
                condition.apply(criteria.getNonBioFuelRewardFactor()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FuelEuRegulationCriteria> copyFiltersAre(
        FuelEuRegulationCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getYear(), copy.getYear()) &&
                condition.apply(criteria.getCo2Gwp(), copy.getCo2Gwp()) &&
                condition.apply(criteria.getMethaneGwp(), copy.getMethaneGwp()) &&
                condition.apply(criteria.getNitrousGwp(), copy.getNitrousGwp()) &&
                condition.apply(criteria.getTargetIntensity(), copy.getTargetIntensity()) &&
                condition.apply(criteria.getBaselineIntensity(), copy.getBaselineIntensity()) &&
                condition.apply(criteria.getReductionFactorPercent(), copy.getReductionFactorPercent()) &&
                condition.apply(criteria.getVlsfoEnergyContentPerTonMj(), copy.getVlsfoEnergyContentPerTonMj()) &&
                condition.apply(criteria.getVlsfoPenaltyEurPerTon(), copy.getVlsfoPenaltyEurPerTon()) &&
                condition.apply(criteria.getEnergyAllowanceMultiplier(), copy.getEnergyAllowanceMultiplier()) &&
                condition.apply(criteria.getNonBioFuelRewardFactor(), copy.getNonBioFuelRewardFactor()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
