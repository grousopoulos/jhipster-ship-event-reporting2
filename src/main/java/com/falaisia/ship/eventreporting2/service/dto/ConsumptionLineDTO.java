package com.falaisia.ship.eventreporting2.service.dto;

import com.falaisia.ship.eventreporting2.domain.enumeration.Co2EmissionSourceTypeCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.DiffCriterionCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.PortActivityCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.UnitOfMeasure;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.falaisia.ship.eventreporting2.domain.ConsumptionLine} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConsumptionLineDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private UnitOfMeasure unitOfMeasure;

    private Co2EmissionSourceTypeCode co2EmissionSourceTypeCode;

    private BigDecimal lowerCalorificValue;

    private BigDecimal sulphurContent;

    private BigDecimal density;

    private BigDecimal viscosity;

    private BigDecimal waterContent;

    private PortActivityCode portActivityCode;

    private DiffCriterionCode diffCriterionCode;

    @NotNull
    private EventReportDTO eventReport;

    @NotNull
    private FuelTypeDTO fuelType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Co2EmissionSourceTypeCode getCo2EmissionSourceTypeCode() {
        return co2EmissionSourceTypeCode;
    }

    public void setCo2EmissionSourceTypeCode(Co2EmissionSourceTypeCode co2EmissionSourceTypeCode) {
        this.co2EmissionSourceTypeCode = co2EmissionSourceTypeCode;
    }

    public BigDecimal getLowerCalorificValue() {
        return lowerCalorificValue;
    }

    public void setLowerCalorificValue(BigDecimal lowerCalorificValue) {
        this.lowerCalorificValue = lowerCalorificValue;
    }

    public BigDecimal getSulphurContent() {
        return sulphurContent;
    }

    public void setSulphurContent(BigDecimal sulphurContent) {
        this.sulphurContent = sulphurContent;
    }

    public BigDecimal getDensity() {
        return density;
    }

    public void setDensity(BigDecimal density) {
        this.density = density;
    }

    public BigDecimal getViscosity() {
        return viscosity;
    }

    public void setViscosity(BigDecimal viscosity) {
        this.viscosity = viscosity;
    }

    public BigDecimal getWaterContent() {
        return waterContent;
    }

    public void setWaterContent(BigDecimal waterContent) {
        this.waterContent = waterContent;
    }

    public PortActivityCode getPortActivityCode() {
        return portActivityCode;
    }

    public void setPortActivityCode(PortActivityCode portActivityCode) {
        this.portActivityCode = portActivityCode;
    }

    public DiffCriterionCode getDiffCriterionCode() {
        return diffCriterionCode;
    }

    public void setDiffCriterionCode(DiffCriterionCode diffCriterionCode) {
        this.diffCriterionCode = diffCriterionCode;
    }

    public EventReportDTO getEventReport() {
        return eventReport;
    }

    public void setEventReport(EventReportDTO eventReport) {
        this.eventReport = eventReport;
    }

    public FuelTypeDTO getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelTypeDTO fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsumptionLineDTO)) {
            return false;
        }

        ConsumptionLineDTO consumptionLineDTO = (ConsumptionLineDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, consumptionLineDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsumptionLineDTO{" +
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
            ", eventReport=" + getEventReport() +
            ", fuelType=" + getFuelType() +
            "}";
    }
}
