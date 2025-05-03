package com.falaisia.ship.eventreporting2.domain;

import com.falaisia.ship.eventreporting2.domain.enumeration.Co2EmissionSourceTypeCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.DiffCriterionCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.PortActivityCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.UnitOfMeasure;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ConsumptionLine.
 */
@Entity
@Table(name = "consumption_line")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConsumptionLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "quantity", precision = 21, scale = 2, nullable = false)
    private BigDecimal quantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit_of_measure", nullable = false)
    private UnitOfMeasure unitOfMeasure;

    @Enumerated(EnumType.STRING)
    @Column(name = "co_2_emission_source_type_code")
    private Co2EmissionSourceTypeCode co2EmissionSourceTypeCode;

    @Column(name = "lower_calorific_value", precision = 21, scale = 2)
    private BigDecimal lowerCalorificValue;

    @Column(name = "sulphur_content", precision = 21, scale = 2)
    private BigDecimal sulphurContent;

    @Column(name = "density", precision = 21, scale = 2)
    private BigDecimal density;

    @Column(name = "viscosity", precision = 21, scale = 2)
    private BigDecimal viscosity;

    @Column(name = "water_content", precision = 21, scale = 2)
    private BigDecimal waterContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "port_activity_code")
    private PortActivityCode portActivityCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "diff_criterion_code")
    private DiffCriterionCode diffCriterionCode;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "voyage", "lines", "operationLines" }, allowSetters = true)
    private EventReport eventReport;

    @ManyToOne(optional = false)
    @NotNull
    private FuelType fuelType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ConsumptionLine id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public ConsumptionLine quantity(BigDecimal quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    public ConsumptionLine unitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.setUnitOfMeasure(unitOfMeasure);
        return this;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Co2EmissionSourceTypeCode getCo2EmissionSourceTypeCode() {
        return this.co2EmissionSourceTypeCode;
    }

    public ConsumptionLine co2EmissionSourceTypeCode(Co2EmissionSourceTypeCode co2EmissionSourceTypeCode) {
        this.setCo2EmissionSourceTypeCode(co2EmissionSourceTypeCode);
        return this;
    }

    public void setCo2EmissionSourceTypeCode(Co2EmissionSourceTypeCode co2EmissionSourceTypeCode) {
        this.co2EmissionSourceTypeCode = co2EmissionSourceTypeCode;
    }

    public BigDecimal getLowerCalorificValue() {
        return this.lowerCalorificValue;
    }

    public ConsumptionLine lowerCalorificValue(BigDecimal lowerCalorificValue) {
        this.setLowerCalorificValue(lowerCalorificValue);
        return this;
    }

    public void setLowerCalorificValue(BigDecimal lowerCalorificValue) {
        this.lowerCalorificValue = lowerCalorificValue;
    }

    public BigDecimal getSulphurContent() {
        return this.sulphurContent;
    }

    public ConsumptionLine sulphurContent(BigDecimal sulphurContent) {
        this.setSulphurContent(sulphurContent);
        return this;
    }

    public void setSulphurContent(BigDecimal sulphurContent) {
        this.sulphurContent = sulphurContent;
    }

    public BigDecimal getDensity() {
        return this.density;
    }

    public ConsumptionLine density(BigDecimal density) {
        this.setDensity(density);
        return this;
    }

    public void setDensity(BigDecimal density) {
        this.density = density;
    }

    public BigDecimal getViscosity() {
        return this.viscosity;
    }

    public ConsumptionLine viscosity(BigDecimal viscosity) {
        this.setViscosity(viscosity);
        return this;
    }

    public void setViscosity(BigDecimal viscosity) {
        this.viscosity = viscosity;
    }

    public BigDecimal getWaterContent() {
        return this.waterContent;
    }

    public ConsumptionLine waterContent(BigDecimal waterContent) {
        this.setWaterContent(waterContent);
        return this;
    }

    public void setWaterContent(BigDecimal waterContent) {
        this.waterContent = waterContent;
    }

    public PortActivityCode getPortActivityCode() {
        return this.portActivityCode;
    }

    public ConsumptionLine portActivityCode(PortActivityCode portActivityCode) {
        this.setPortActivityCode(portActivityCode);
        return this;
    }

    public void setPortActivityCode(PortActivityCode portActivityCode) {
        this.portActivityCode = portActivityCode;
    }

    public DiffCriterionCode getDiffCriterionCode() {
        return this.diffCriterionCode;
    }

    public ConsumptionLine diffCriterionCode(DiffCriterionCode diffCriterionCode) {
        this.setDiffCriterionCode(diffCriterionCode);
        return this;
    }

    public void setDiffCriterionCode(DiffCriterionCode diffCriterionCode) {
        this.diffCriterionCode = diffCriterionCode;
    }

    public EventReport getEventReport() {
        return this.eventReport;
    }

    public void setEventReport(EventReport eventReport) {
        this.eventReport = eventReport;
    }

    public ConsumptionLine eventReport(EventReport eventReport) {
        this.setEventReport(eventReport);
        return this;
    }

    public FuelType getFuelType() {
        return this.fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public ConsumptionLine fuelType(FuelType fuelType) {
        this.setFuelType(fuelType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsumptionLine)) {
            return false;
        }
        return getId() != null && getId().equals(((ConsumptionLine) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsumptionLine{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unitOfMeasure='" + getUnitOfMeasure() + "'" +
            ", co2EmissionSourceTypeCode='" + getCo2EmissionSourceTypeCode() + "'" +
            ", lowerCalorificValue=" + getLowerCalorificValue() +
            ", sulphurContent=" + getSulphurContent() +
            ", density=" + getDensity() +
            ", viscosity=" + getViscosity() +
            ", waterContent=" + getWaterContent() +
            ", portActivityCode='" + getPortActivityCode() + "'" +
            ", diffCriterionCode='" + getDiffCriterionCode() + "'" +
            "}";
    }
}
