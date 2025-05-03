package com.falaisia.ship.eventreporting2.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.falaisia.ship.eventreporting2.domain.FuelEuRegulation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuelEuRegulationDTO implements Serializable {

    private Long id;

    private Integer year;

    private Integer co2Gwp;

    private Integer methaneGwp;

    private Integer nitrousGwp;

    private BigDecimal targetIntensity;

    private BigDecimal baselineIntensity;

    private BigDecimal reductionFactorPercent;

    private BigDecimal vlsfoEnergyContentPerTonMj;

    private BigDecimal vlsfoPenaltyEurPerTon;

    private BigDecimal energyAllowanceMultiplier;

    private BigDecimal nonBioFuelRewardFactor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCo2Gwp() {
        return co2Gwp;
    }

    public void setCo2Gwp(Integer co2Gwp) {
        this.co2Gwp = co2Gwp;
    }

    public Integer getMethaneGwp() {
        return methaneGwp;
    }

    public void setMethaneGwp(Integer methaneGwp) {
        this.methaneGwp = methaneGwp;
    }

    public Integer getNitrousGwp() {
        return nitrousGwp;
    }

    public void setNitrousGwp(Integer nitrousGwp) {
        this.nitrousGwp = nitrousGwp;
    }

    public BigDecimal getTargetIntensity() {
        return targetIntensity;
    }

    public void setTargetIntensity(BigDecimal targetIntensity) {
        this.targetIntensity = targetIntensity;
    }

    public BigDecimal getBaselineIntensity() {
        return baselineIntensity;
    }

    public void setBaselineIntensity(BigDecimal baselineIntensity) {
        this.baselineIntensity = baselineIntensity;
    }

    public BigDecimal getReductionFactorPercent() {
        return reductionFactorPercent;
    }

    public void setReductionFactorPercent(BigDecimal reductionFactorPercent) {
        this.reductionFactorPercent = reductionFactorPercent;
    }

    public BigDecimal getVlsfoEnergyContentPerTonMj() {
        return vlsfoEnergyContentPerTonMj;
    }

    public void setVlsfoEnergyContentPerTonMj(BigDecimal vlsfoEnergyContentPerTonMj) {
        this.vlsfoEnergyContentPerTonMj = vlsfoEnergyContentPerTonMj;
    }

    public BigDecimal getVlsfoPenaltyEurPerTon() {
        return vlsfoPenaltyEurPerTon;
    }

    public void setVlsfoPenaltyEurPerTon(BigDecimal vlsfoPenaltyEurPerTon) {
        this.vlsfoPenaltyEurPerTon = vlsfoPenaltyEurPerTon;
    }

    public BigDecimal getEnergyAllowanceMultiplier() {
        return energyAllowanceMultiplier;
    }

    public void setEnergyAllowanceMultiplier(BigDecimal energyAllowanceMultiplier) {
        this.energyAllowanceMultiplier = energyAllowanceMultiplier;
    }

    public BigDecimal getNonBioFuelRewardFactor() {
        return nonBioFuelRewardFactor;
    }

    public void setNonBioFuelRewardFactor(BigDecimal nonBioFuelRewardFactor) {
        this.nonBioFuelRewardFactor = nonBioFuelRewardFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuelEuRegulationDTO)) {
            return false;
        }

        FuelEuRegulationDTO fuelEuRegulationDTO = (FuelEuRegulationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fuelEuRegulationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuelEuRegulationDTO{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", co2Gwp=" + getCo2Gwp() +
            ", methaneGwp=" + getMethaneGwp() +
            ", nitrousGwp=" + getNitrousGwp() +
            ", targetIntensity=" + getTargetIntensity() +
            ", baselineIntensity=" + getBaselineIntensity() +
            ", reductionFactorPercent=" + getReductionFactorPercent() +
            ", vlsfoEnergyContentPerTonMj=" + getVlsfoEnergyContentPerTonMj() +
            ", vlsfoPenaltyEurPerTon=" + getVlsfoPenaltyEurPerTon() +
            ", energyAllowanceMultiplier=" + getEnergyAllowanceMultiplier() +
            ", nonBioFuelRewardFactor=" + getNonBioFuelRewardFactor() +
            "}";
    }
}
