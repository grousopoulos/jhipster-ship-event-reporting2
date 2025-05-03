package com.falaisia.ship.eventreporting2.service.dto;

import com.falaisia.ship.eventreporting2.domain.enumeration.UnitOfMeasure;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteLine} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BunkerReceivedNoteLineDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private UnitOfMeasure unitOfMeasure;

    private BigDecimal lowerCalorificValue;

    private BigDecimal sulphurContent;

    private BigDecimal density;

    private BigDecimal viscosity;

    private BigDecimal waterContent;

    @NotNull
    private BunkerReceivedNoteDTO bunkerReceivedNote;

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

    public BunkerReceivedNoteDTO getBunkerReceivedNote() {
        return bunkerReceivedNote;
    }

    public void setBunkerReceivedNote(BunkerReceivedNoteDTO bunkerReceivedNote) {
        this.bunkerReceivedNote = bunkerReceivedNote;
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
        if (!(o instanceof BunkerReceivedNoteLineDTO)) {
            return false;
        }

        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO = (BunkerReceivedNoteLineDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bunkerReceivedNoteLineDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BunkerReceivedNoteLineDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unitOfMeasure='" + getUnitOfMeasure() + "'" +
            ", lowerCalorificValue=" + getLowerCalorificValue() +
            ", sulphurContent=" + getSulphurContent() +
            ", density=" + getDensity() +
            ", viscosity=" + getViscosity() +
            ", waterContent=" + getWaterContent() +
            ", bunkerReceivedNote=" + getBunkerReceivedNote() +
            ", fuelType=" + getFuelType() +
            "}";
    }
}
