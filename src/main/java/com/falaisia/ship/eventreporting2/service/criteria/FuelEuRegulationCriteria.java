package com.falaisia.ship.eventreporting2.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.falaisia.ship.eventreporting2.domain.FuelEuRegulation} entity. This class is used
 * in {@link com.falaisia.ship.eventreporting2.web.rest.FuelEuRegulationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fuel-eu-regulations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuelEuRegulationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter year;

    private IntegerFilter co2Gwp;

    private IntegerFilter methaneGwp;

    private IntegerFilter nitrousGwp;

    private BigDecimalFilter targetIntensity;

    private BigDecimalFilter baselineIntensity;

    private BigDecimalFilter reductionFactorPercent;

    private BigDecimalFilter vlsfoEnergyContentPerTonMj;

    private BigDecimalFilter vlsfoPenaltyEurPerTon;

    private BigDecimalFilter energyAllowanceMultiplier;

    private BigDecimalFilter nonBioFuelRewardFactor;

    private Boolean distinct;

    public FuelEuRegulationCriteria() {}

    public FuelEuRegulationCriteria(FuelEuRegulationCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.year = other.optionalYear().map(IntegerFilter::copy).orElse(null);
        this.co2Gwp = other.optionalCo2Gwp().map(IntegerFilter::copy).orElse(null);
        this.methaneGwp = other.optionalMethaneGwp().map(IntegerFilter::copy).orElse(null);
        this.nitrousGwp = other.optionalNitrousGwp().map(IntegerFilter::copy).orElse(null);
        this.targetIntensity = other.optionalTargetIntensity().map(BigDecimalFilter::copy).orElse(null);
        this.baselineIntensity = other.optionalBaselineIntensity().map(BigDecimalFilter::copy).orElse(null);
        this.reductionFactorPercent = other.optionalReductionFactorPercent().map(BigDecimalFilter::copy).orElse(null);
        this.vlsfoEnergyContentPerTonMj = other.optionalVlsfoEnergyContentPerTonMj().map(BigDecimalFilter::copy).orElse(null);
        this.vlsfoPenaltyEurPerTon = other.optionalVlsfoPenaltyEurPerTon().map(BigDecimalFilter::copy).orElse(null);
        this.energyAllowanceMultiplier = other.optionalEnergyAllowanceMultiplier().map(BigDecimalFilter::copy).orElse(null);
        this.nonBioFuelRewardFactor = other.optionalNonBioFuelRewardFactor().map(BigDecimalFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FuelEuRegulationCriteria copy() {
        return new FuelEuRegulationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getYear() {
        return year;
    }

    public Optional<IntegerFilter> optionalYear() {
        return Optional.ofNullable(year);
    }

    public IntegerFilter year() {
        if (year == null) {
            setYear(new IntegerFilter());
        }
        return year;
    }

    public void setYear(IntegerFilter year) {
        this.year = year;
    }

    public IntegerFilter getCo2Gwp() {
        return co2Gwp;
    }

    public Optional<IntegerFilter> optionalCo2Gwp() {
        return Optional.ofNullable(co2Gwp);
    }

    public IntegerFilter co2Gwp() {
        if (co2Gwp == null) {
            setCo2Gwp(new IntegerFilter());
        }
        return co2Gwp;
    }

    public void setCo2Gwp(IntegerFilter co2Gwp) {
        this.co2Gwp = co2Gwp;
    }

    public IntegerFilter getMethaneGwp() {
        return methaneGwp;
    }

    public Optional<IntegerFilter> optionalMethaneGwp() {
        return Optional.ofNullable(methaneGwp);
    }

    public IntegerFilter methaneGwp() {
        if (methaneGwp == null) {
            setMethaneGwp(new IntegerFilter());
        }
        return methaneGwp;
    }

    public void setMethaneGwp(IntegerFilter methaneGwp) {
        this.methaneGwp = methaneGwp;
    }

    public IntegerFilter getNitrousGwp() {
        return nitrousGwp;
    }

    public Optional<IntegerFilter> optionalNitrousGwp() {
        return Optional.ofNullable(nitrousGwp);
    }

    public IntegerFilter nitrousGwp() {
        if (nitrousGwp == null) {
            setNitrousGwp(new IntegerFilter());
        }
        return nitrousGwp;
    }

    public void setNitrousGwp(IntegerFilter nitrousGwp) {
        this.nitrousGwp = nitrousGwp;
    }

    public BigDecimalFilter getTargetIntensity() {
        return targetIntensity;
    }

    public Optional<BigDecimalFilter> optionalTargetIntensity() {
        return Optional.ofNullable(targetIntensity);
    }

    public BigDecimalFilter targetIntensity() {
        if (targetIntensity == null) {
            setTargetIntensity(new BigDecimalFilter());
        }
        return targetIntensity;
    }

    public void setTargetIntensity(BigDecimalFilter targetIntensity) {
        this.targetIntensity = targetIntensity;
    }

    public BigDecimalFilter getBaselineIntensity() {
        return baselineIntensity;
    }

    public Optional<BigDecimalFilter> optionalBaselineIntensity() {
        return Optional.ofNullable(baselineIntensity);
    }

    public BigDecimalFilter baselineIntensity() {
        if (baselineIntensity == null) {
            setBaselineIntensity(new BigDecimalFilter());
        }
        return baselineIntensity;
    }

    public void setBaselineIntensity(BigDecimalFilter baselineIntensity) {
        this.baselineIntensity = baselineIntensity;
    }

    public BigDecimalFilter getReductionFactorPercent() {
        return reductionFactorPercent;
    }

    public Optional<BigDecimalFilter> optionalReductionFactorPercent() {
        return Optional.ofNullable(reductionFactorPercent);
    }

    public BigDecimalFilter reductionFactorPercent() {
        if (reductionFactorPercent == null) {
            setReductionFactorPercent(new BigDecimalFilter());
        }
        return reductionFactorPercent;
    }

    public void setReductionFactorPercent(BigDecimalFilter reductionFactorPercent) {
        this.reductionFactorPercent = reductionFactorPercent;
    }

    public BigDecimalFilter getVlsfoEnergyContentPerTonMj() {
        return vlsfoEnergyContentPerTonMj;
    }

    public Optional<BigDecimalFilter> optionalVlsfoEnergyContentPerTonMj() {
        return Optional.ofNullable(vlsfoEnergyContentPerTonMj);
    }

    public BigDecimalFilter vlsfoEnergyContentPerTonMj() {
        if (vlsfoEnergyContentPerTonMj == null) {
            setVlsfoEnergyContentPerTonMj(new BigDecimalFilter());
        }
        return vlsfoEnergyContentPerTonMj;
    }

    public void setVlsfoEnergyContentPerTonMj(BigDecimalFilter vlsfoEnergyContentPerTonMj) {
        this.vlsfoEnergyContentPerTonMj = vlsfoEnergyContentPerTonMj;
    }

    public BigDecimalFilter getVlsfoPenaltyEurPerTon() {
        return vlsfoPenaltyEurPerTon;
    }

    public Optional<BigDecimalFilter> optionalVlsfoPenaltyEurPerTon() {
        return Optional.ofNullable(vlsfoPenaltyEurPerTon);
    }

    public BigDecimalFilter vlsfoPenaltyEurPerTon() {
        if (vlsfoPenaltyEurPerTon == null) {
            setVlsfoPenaltyEurPerTon(new BigDecimalFilter());
        }
        return vlsfoPenaltyEurPerTon;
    }

    public void setVlsfoPenaltyEurPerTon(BigDecimalFilter vlsfoPenaltyEurPerTon) {
        this.vlsfoPenaltyEurPerTon = vlsfoPenaltyEurPerTon;
    }

    public BigDecimalFilter getEnergyAllowanceMultiplier() {
        return energyAllowanceMultiplier;
    }

    public Optional<BigDecimalFilter> optionalEnergyAllowanceMultiplier() {
        return Optional.ofNullable(energyAllowanceMultiplier);
    }

    public BigDecimalFilter energyAllowanceMultiplier() {
        if (energyAllowanceMultiplier == null) {
            setEnergyAllowanceMultiplier(new BigDecimalFilter());
        }
        return energyAllowanceMultiplier;
    }

    public void setEnergyAllowanceMultiplier(BigDecimalFilter energyAllowanceMultiplier) {
        this.energyAllowanceMultiplier = energyAllowanceMultiplier;
    }

    public BigDecimalFilter getNonBioFuelRewardFactor() {
        return nonBioFuelRewardFactor;
    }

    public Optional<BigDecimalFilter> optionalNonBioFuelRewardFactor() {
        return Optional.ofNullable(nonBioFuelRewardFactor);
    }

    public BigDecimalFilter nonBioFuelRewardFactor() {
        if (nonBioFuelRewardFactor == null) {
            setNonBioFuelRewardFactor(new BigDecimalFilter());
        }
        return nonBioFuelRewardFactor;
    }

    public void setNonBioFuelRewardFactor(BigDecimalFilter nonBioFuelRewardFactor) {
        this.nonBioFuelRewardFactor = nonBioFuelRewardFactor;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FuelEuRegulationCriteria that = (FuelEuRegulationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(year, that.year) &&
            Objects.equals(co2Gwp, that.co2Gwp) &&
            Objects.equals(methaneGwp, that.methaneGwp) &&
            Objects.equals(nitrousGwp, that.nitrousGwp) &&
            Objects.equals(targetIntensity, that.targetIntensity) &&
            Objects.equals(baselineIntensity, that.baselineIntensity) &&
            Objects.equals(reductionFactorPercent, that.reductionFactorPercent) &&
            Objects.equals(vlsfoEnergyContentPerTonMj, that.vlsfoEnergyContentPerTonMj) &&
            Objects.equals(vlsfoPenaltyEurPerTon, that.vlsfoPenaltyEurPerTon) &&
            Objects.equals(energyAllowanceMultiplier, that.energyAllowanceMultiplier) &&
            Objects.equals(nonBioFuelRewardFactor, that.nonBioFuelRewardFactor) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            year,
            co2Gwp,
            methaneGwp,
            nitrousGwp,
            targetIntensity,
            baselineIntensity,
            reductionFactorPercent,
            vlsfoEnergyContentPerTonMj,
            vlsfoPenaltyEurPerTon,
            energyAllowanceMultiplier,
            nonBioFuelRewardFactor,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuelEuRegulationCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalYear().map(f -> "year=" + f + ", ").orElse("") +
            optionalCo2Gwp().map(f -> "co2Gwp=" + f + ", ").orElse("") +
            optionalMethaneGwp().map(f -> "methaneGwp=" + f + ", ").orElse("") +
            optionalNitrousGwp().map(f -> "nitrousGwp=" + f + ", ").orElse("") +
            optionalTargetIntensity().map(f -> "targetIntensity=" + f + ", ").orElse("") +
            optionalBaselineIntensity().map(f -> "baselineIntensity=" + f + ", ").orElse("") +
            optionalReductionFactorPercent().map(f -> "reductionFactorPercent=" + f + ", ").orElse("") +
            optionalVlsfoEnergyContentPerTonMj().map(f -> "vlsfoEnergyContentPerTonMj=" + f + ", ").orElse("") +
            optionalVlsfoPenaltyEurPerTon().map(f -> "vlsfoPenaltyEurPerTon=" + f + ", ").orElse("") +
            optionalEnergyAllowanceMultiplier().map(f -> "energyAllowanceMultiplier=" + f + ", ").orElse("") +
            optionalNonBioFuelRewardFactor().map(f -> "nonBioFuelRewardFactor=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
