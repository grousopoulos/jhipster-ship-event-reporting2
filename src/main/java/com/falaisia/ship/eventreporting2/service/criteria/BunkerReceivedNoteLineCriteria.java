package com.falaisia.ship.eventreporting2.service.criteria;

import com.falaisia.ship.eventreporting2.domain.enumeration.UnitOfMeasure;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.falaisia.ship.eventreporting2.domain.BunkerReceivedNoteLine} entity. This class is used
 * in {@link com.falaisia.ship.eventreporting2.web.rest.BunkerReceivedNoteLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bunker-received-note-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BunkerReceivedNoteLineCriteria implements Serializable, Criteria {

    /**
     * Class for filtering UnitOfMeasure
     */
    public static class UnitOfMeasureFilter extends Filter<UnitOfMeasure> {

        public UnitOfMeasureFilter() {}

        public UnitOfMeasureFilter(UnitOfMeasureFilter filter) {
            super(filter);
        }

        @Override
        public UnitOfMeasureFilter copy() {
            return new UnitOfMeasureFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter quantity;

    private UnitOfMeasureFilter unitOfMeasure;

    private BigDecimalFilter lowerCalorificValue;

    private BigDecimalFilter sulphurContent;

    private BigDecimalFilter density;

    private BigDecimalFilter viscosity;

    private BigDecimalFilter waterContent;

    private LongFilter bunkerReceivedNoteId;

    private LongFilter fuelTypeId;

    private Boolean distinct;

    public BunkerReceivedNoteLineCriteria() {}

    public BunkerReceivedNoteLineCriteria(BunkerReceivedNoteLineCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.quantity = other.optionalQuantity().map(BigDecimalFilter::copy).orElse(null);
        this.unitOfMeasure = other.optionalUnitOfMeasure().map(UnitOfMeasureFilter::copy).orElse(null);
        this.lowerCalorificValue = other.optionalLowerCalorificValue().map(BigDecimalFilter::copy).orElse(null);
        this.sulphurContent = other.optionalSulphurContent().map(BigDecimalFilter::copy).orElse(null);
        this.density = other.optionalDensity().map(BigDecimalFilter::copy).orElse(null);
        this.viscosity = other.optionalViscosity().map(BigDecimalFilter::copy).orElse(null);
        this.waterContent = other.optionalWaterContent().map(BigDecimalFilter::copy).orElse(null);
        this.bunkerReceivedNoteId = other.optionalBunkerReceivedNoteId().map(LongFilter::copy).orElse(null);
        this.fuelTypeId = other.optionalFuelTypeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public BunkerReceivedNoteLineCriteria copy() {
        return new BunkerReceivedNoteLineCriteria(this);
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

    public BigDecimalFilter getQuantity() {
        return quantity;
    }

    public Optional<BigDecimalFilter> optionalQuantity() {
        return Optional.ofNullable(quantity);
    }

    public BigDecimalFilter quantity() {
        if (quantity == null) {
            setQuantity(new BigDecimalFilter());
        }
        return quantity;
    }

    public void setQuantity(BigDecimalFilter quantity) {
        this.quantity = quantity;
    }

    public UnitOfMeasureFilter getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Optional<UnitOfMeasureFilter> optionalUnitOfMeasure() {
        return Optional.ofNullable(unitOfMeasure);
    }

    public UnitOfMeasureFilter unitOfMeasure() {
        if (unitOfMeasure == null) {
            setUnitOfMeasure(new UnitOfMeasureFilter());
        }
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasureFilter unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public BigDecimalFilter getLowerCalorificValue() {
        return lowerCalorificValue;
    }

    public Optional<BigDecimalFilter> optionalLowerCalorificValue() {
        return Optional.ofNullable(lowerCalorificValue);
    }

    public BigDecimalFilter lowerCalorificValue() {
        if (lowerCalorificValue == null) {
            setLowerCalorificValue(new BigDecimalFilter());
        }
        return lowerCalorificValue;
    }

    public void setLowerCalorificValue(BigDecimalFilter lowerCalorificValue) {
        this.lowerCalorificValue = lowerCalorificValue;
    }

    public BigDecimalFilter getSulphurContent() {
        return sulphurContent;
    }

    public Optional<BigDecimalFilter> optionalSulphurContent() {
        return Optional.ofNullable(sulphurContent);
    }

    public BigDecimalFilter sulphurContent() {
        if (sulphurContent == null) {
            setSulphurContent(new BigDecimalFilter());
        }
        return sulphurContent;
    }

    public void setSulphurContent(BigDecimalFilter sulphurContent) {
        this.sulphurContent = sulphurContent;
    }

    public BigDecimalFilter getDensity() {
        return density;
    }

    public Optional<BigDecimalFilter> optionalDensity() {
        return Optional.ofNullable(density);
    }

    public BigDecimalFilter density() {
        if (density == null) {
            setDensity(new BigDecimalFilter());
        }
        return density;
    }

    public void setDensity(BigDecimalFilter density) {
        this.density = density;
    }

    public BigDecimalFilter getViscosity() {
        return viscosity;
    }

    public Optional<BigDecimalFilter> optionalViscosity() {
        return Optional.ofNullable(viscosity);
    }

    public BigDecimalFilter viscosity() {
        if (viscosity == null) {
            setViscosity(new BigDecimalFilter());
        }
        return viscosity;
    }

    public void setViscosity(BigDecimalFilter viscosity) {
        this.viscosity = viscosity;
    }

    public BigDecimalFilter getWaterContent() {
        return waterContent;
    }

    public Optional<BigDecimalFilter> optionalWaterContent() {
        return Optional.ofNullable(waterContent);
    }

    public BigDecimalFilter waterContent() {
        if (waterContent == null) {
            setWaterContent(new BigDecimalFilter());
        }
        return waterContent;
    }

    public void setWaterContent(BigDecimalFilter waterContent) {
        this.waterContent = waterContent;
    }

    public LongFilter getBunkerReceivedNoteId() {
        return bunkerReceivedNoteId;
    }

    public Optional<LongFilter> optionalBunkerReceivedNoteId() {
        return Optional.ofNullable(bunkerReceivedNoteId);
    }

    public LongFilter bunkerReceivedNoteId() {
        if (bunkerReceivedNoteId == null) {
            setBunkerReceivedNoteId(new LongFilter());
        }
        return bunkerReceivedNoteId;
    }

    public void setBunkerReceivedNoteId(LongFilter bunkerReceivedNoteId) {
        this.bunkerReceivedNoteId = bunkerReceivedNoteId;
    }

    public LongFilter getFuelTypeId() {
        return fuelTypeId;
    }

    public Optional<LongFilter> optionalFuelTypeId() {
        return Optional.ofNullable(fuelTypeId);
    }

    public LongFilter fuelTypeId() {
        if (fuelTypeId == null) {
            setFuelTypeId(new LongFilter());
        }
        return fuelTypeId;
    }

    public void setFuelTypeId(LongFilter fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
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
        final BunkerReceivedNoteLineCriteria that = (BunkerReceivedNoteLineCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(unitOfMeasure, that.unitOfMeasure) &&
            Objects.equals(lowerCalorificValue, that.lowerCalorificValue) &&
            Objects.equals(sulphurContent, that.sulphurContent) &&
            Objects.equals(density, that.density) &&
            Objects.equals(viscosity, that.viscosity) &&
            Objects.equals(waterContent, that.waterContent) &&
            Objects.equals(bunkerReceivedNoteId, that.bunkerReceivedNoteId) &&
            Objects.equals(fuelTypeId, that.fuelTypeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            quantity,
            unitOfMeasure,
            lowerCalorificValue,
            sulphurContent,
            density,
            viscosity,
            waterContent,
            bunkerReceivedNoteId,
            fuelTypeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BunkerReceivedNoteLineCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalQuantity().map(f -> "quantity=" + f + ", ").orElse("") +
            optionalUnitOfMeasure().map(f -> "unitOfMeasure=" + f + ", ").orElse("") +
            optionalLowerCalorificValue().map(f -> "lowerCalorificValue=" + f + ", ").orElse("") +
            optionalSulphurContent().map(f -> "sulphurContent=" + f + ", ").orElse("") +
            optionalDensity().map(f -> "density=" + f + ", ").orElse("") +
            optionalViscosity().map(f -> "viscosity=" + f + ", ").orElse("") +
            optionalWaterContent().map(f -> "waterContent=" + f + ", ").orElse("") +
            optionalBunkerReceivedNoteId().map(f -> "bunkerReceivedNoteId=" + f + ", ").orElse("") +
            optionalFuelTypeId().map(f -> "fuelTypeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
