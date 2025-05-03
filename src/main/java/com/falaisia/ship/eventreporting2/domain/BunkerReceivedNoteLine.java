package com.falaisia.ship.eventreporting2.domain;

import com.falaisia.ship.eventreporting2.domain.enumeration.UnitOfMeasure;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BunkerReceivedNoteLine.
 */
@Entity
@Table(name = "bunker_received_note_line")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BunkerReceivedNoteLine implements Serializable {

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "voyage", "lines" }, allowSetters = true)
    private BunkerReceivedNote bunkerReceivedNote;

    @ManyToOne(optional = false)
    @NotNull
    private FuelType fuelType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BunkerReceivedNoteLine id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public BunkerReceivedNoteLine quantity(BigDecimal quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    public BunkerReceivedNoteLine unitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.setUnitOfMeasure(unitOfMeasure);
        return this;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public BigDecimal getLowerCalorificValue() {
        return this.lowerCalorificValue;
    }

    public BunkerReceivedNoteLine lowerCalorificValue(BigDecimal lowerCalorificValue) {
        this.setLowerCalorificValue(lowerCalorificValue);
        return this;
    }

    public void setLowerCalorificValue(BigDecimal lowerCalorificValue) {
        this.lowerCalorificValue = lowerCalorificValue;
    }

    public BigDecimal getSulphurContent() {
        return this.sulphurContent;
    }

    public BunkerReceivedNoteLine sulphurContent(BigDecimal sulphurContent) {
        this.setSulphurContent(sulphurContent);
        return this;
    }

    public void setSulphurContent(BigDecimal sulphurContent) {
        this.sulphurContent = sulphurContent;
    }

    public BigDecimal getDensity() {
        return this.density;
    }

    public BunkerReceivedNoteLine density(BigDecimal density) {
        this.setDensity(density);
        return this;
    }

    public void setDensity(BigDecimal density) {
        this.density = density;
    }

    public BigDecimal getViscosity() {
        return this.viscosity;
    }

    public BunkerReceivedNoteLine viscosity(BigDecimal viscosity) {
        this.setViscosity(viscosity);
        return this;
    }

    public void setViscosity(BigDecimal viscosity) {
        this.viscosity = viscosity;
    }

    public BigDecimal getWaterContent() {
        return this.waterContent;
    }

    public BunkerReceivedNoteLine waterContent(BigDecimal waterContent) {
        this.setWaterContent(waterContent);
        return this;
    }

    public void setWaterContent(BigDecimal waterContent) {
        this.waterContent = waterContent;
    }

    public BunkerReceivedNote getBunkerReceivedNote() {
        return this.bunkerReceivedNote;
    }

    public void setBunkerReceivedNote(BunkerReceivedNote bunkerReceivedNote) {
        this.bunkerReceivedNote = bunkerReceivedNote;
    }

    public BunkerReceivedNoteLine bunkerReceivedNote(BunkerReceivedNote bunkerReceivedNote) {
        this.setBunkerReceivedNote(bunkerReceivedNote);
        return this;
    }

    public FuelType getFuelType() {
        return this.fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public BunkerReceivedNoteLine fuelType(FuelType fuelType) {
        this.setFuelType(fuelType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BunkerReceivedNoteLine)) {
            return false;
        }
        return getId() != null && getId().equals(((BunkerReceivedNoteLine) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BunkerReceivedNoteLine{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unitOfMeasure='" + getUnitOfMeasure() + "'" +
            ", lowerCalorificValue=" + getLowerCalorificValue() +
            ", sulphurContent=" + getSulphurContent() +
            ", density=" + getDensity() +
            ", viscosity=" + getViscosity() +
            ", waterContent=" + getWaterContent() +
            "}";
    }
}
