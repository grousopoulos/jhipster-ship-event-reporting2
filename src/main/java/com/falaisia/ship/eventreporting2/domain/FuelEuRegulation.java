package com.falaisia.ship.eventreporting2.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FuelEuRegulation.
 */
@Entity
@Table(name = "fuel_eu_regulation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuelEuRegulation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "year")
    private Integer year;

    @Column(name = "co_2_gwp")
    private Integer co2Gwp;

    @Column(name = "methane_gwp")
    private Integer methaneGwp;

    @Column(name = "nitrous_gwp")
    private Integer nitrousGwp;

    @Column(name = "target_intensity", precision = 21, scale = 2)
    private BigDecimal targetIntensity;

    @Column(name = "baseline_intensity", precision = 21, scale = 2)
    private BigDecimal baselineIntensity;

    @Column(name = "reduction_factor_percent", precision = 21, scale = 2)
    private BigDecimal reductionFactorPercent;

    @Column(name = "vlsfo_energy_content_per_ton_mj", precision = 21, scale = 2)
    private BigDecimal vlsfoEnergyContentPerTonMj;

    @Column(name = "vlsfo_penalty_eur_per_ton", precision = 21, scale = 2)
    private BigDecimal vlsfoPenaltyEurPerTon;

    @Column(name = "energy_allowance_multiplier", precision = 21, scale = 2)
    private BigDecimal energyAllowanceMultiplier;

    @Column(name = "non_bio_fuel_reward_factor", precision = 21, scale = 2)
    private BigDecimal nonBioFuelRewardFactor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FuelEuRegulation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return this.year;
    }

    public FuelEuRegulation year(Integer year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCo2Gwp() {
        return this.co2Gwp;
    }

    public FuelEuRegulation co2Gwp(Integer co2Gwp) {
        this.setCo2Gwp(co2Gwp);
        return this;
    }

    public void setCo2Gwp(Integer co2Gwp) {
        this.co2Gwp = co2Gwp;
    }

    public Integer getMethaneGwp() {
        return this.methaneGwp;
    }

    public FuelEuRegulation methaneGwp(Integer methaneGwp) {
        this.setMethaneGwp(methaneGwp);
        return this;
    }

    public void setMethaneGwp(Integer methaneGwp) {
        this.methaneGwp = methaneGwp;
    }

    public Integer getNitrousGwp() {
        return this.nitrousGwp;
    }

    public FuelEuRegulation nitrousGwp(Integer nitrousGwp) {
        this.setNitrousGwp(nitrousGwp);
        return this;
    }

    public void setNitrousGwp(Integer nitrousGwp) {
        this.nitrousGwp = nitrousGwp;
    }

    public BigDecimal getTargetIntensity() {
        return this.targetIntensity;
    }

    public FuelEuRegulation targetIntensity(BigDecimal targetIntensity) {
        this.setTargetIntensity(targetIntensity);
        return this;
    }

    public void setTargetIntensity(BigDecimal targetIntensity) {
        this.targetIntensity = targetIntensity;
    }

    public BigDecimal getBaselineIntensity() {
        return this.baselineIntensity;
    }

    public FuelEuRegulation baselineIntensity(BigDecimal baselineIntensity) {
        this.setBaselineIntensity(baselineIntensity);
        return this;
    }

    public void setBaselineIntensity(BigDecimal baselineIntensity) {
        this.baselineIntensity = baselineIntensity;
    }

    public BigDecimal getReductionFactorPercent() {
        return this.reductionFactorPercent;
    }

    public FuelEuRegulation reductionFactorPercent(BigDecimal reductionFactorPercent) {
        this.setReductionFactorPercent(reductionFactorPercent);
        return this;
    }

    public void setReductionFactorPercent(BigDecimal reductionFactorPercent) {
        this.reductionFactorPercent = reductionFactorPercent;
    }

    public BigDecimal getVlsfoEnergyContentPerTonMj() {
        return this.vlsfoEnergyContentPerTonMj;
    }

    public FuelEuRegulation vlsfoEnergyContentPerTonMj(BigDecimal vlsfoEnergyContentPerTonMj) {
        this.setVlsfoEnergyContentPerTonMj(vlsfoEnergyContentPerTonMj);
        return this;
    }

    public void setVlsfoEnergyContentPerTonMj(BigDecimal vlsfoEnergyContentPerTonMj) {
        this.vlsfoEnergyContentPerTonMj = vlsfoEnergyContentPerTonMj;
    }

    public BigDecimal getVlsfoPenaltyEurPerTon() {
        return this.vlsfoPenaltyEurPerTon;
    }

    public FuelEuRegulation vlsfoPenaltyEurPerTon(BigDecimal vlsfoPenaltyEurPerTon) {
        this.setVlsfoPenaltyEurPerTon(vlsfoPenaltyEurPerTon);
        return this;
    }

    public void setVlsfoPenaltyEurPerTon(BigDecimal vlsfoPenaltyEurPerTon) {
        this.vlsfoPenaltyEurPerTon = vlsfoPenaltyEurPerTon;
    }

    public BigDecimal getEnergyAllowanceMultiplier() {
        return this.energyAllowanceMultiplier;
    }

    public FuelEuRegulation energyAllowanceMultiplier(BigDecimal energyAllowanceMultiplier) {
        this.setEnergyAllowanceMultiplier(energyAllowanceMultiplier);
        return this;
    }

    public void setEnergyAllowanceMultiplier(BigDecimal energyAllowanceMultiplier) {
        this.energyAllowanceMultiplier = energyAllowanceMultiplier;
    }

    public BigDecimal getNonBioFuelRewardFactor() {
        return this.nonBioFuelRewardFactor;
    }

    public FuelEuRegulation nonBioFuelRewardFactor(BigDecimal nonBioFuelRewardFactor) {
        this.setNonBioFuelRewardFactor(nonBioFuelRewardFactor);
        return this;
    }

    public void setNonBioFuelRewardFactor(BigDecimal nonBioFuelRewardFactor) {
        this.nonBioFuelRewardFactor = nonBioFuelRewardFactor;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuelEuRegulation)) {
            return false;
        }
        return getId() != null && getId().equals(((FuelEuRegulation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuelEuRegulation{" +
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
