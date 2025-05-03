package com.falaisia.ship.eventreporting2.service.criteria;

import com.falaisia.ship.eventreporting2.domain.enumeration.Co2EmissionSourceTypeCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.DiffCriterionCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.PortActivityCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.UnitOfMeasure;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.falaisia.ship.eventreporting2.domain.ConsumptionLine} entity. This class is used
 * in {@link com.falaisia.ship.eventreporting2.web.rest.ConsumptionLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /consumption-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConsumptionLineCriteria implements Serializable, Criteria {

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

    /**
     * Class for filtering Co2EmissionSourceTypeCode
     */
    public static class Co2EmissionSourceTypeCodeFilter extends Filter<Co2EmissionSourceTypeCode> {

        public Co2EmissionSourceTypeCodeFilter() {}

        public Co2EmissionSourceTypeCodeFilter(Co2EmissionSourceTypeCodeFilter filter) {
            super(filter);
        }

        @Override
        public Co2EmissionSourceTypeCodeFilter copy() {
            return new Co2EmissionSourceTypeCodeFilter(this);
        }
    }

    /**
     * Class for filtering PortActivityCode
     */
    public static class PortActivityCodeFilter extends Filter<PortActivityCode> {

        public PortActivityCodeFilter() {}

        public PortActivityCodeFilter(PortActivityCodeFilter filter) {
            super(filter);
        }

        @Override
        public PortActivityCodeFilter copy() {
            return new PortActivityCodeFilter(this);
        }
    }

    /**
     * Class for filtering DiffCriterionCode
     */
    public static class DiffCriterionCodeFilter extends Filter<DiffCriterionCode> {

        public DiffCriterionCodeFilter() {}

        public DiffCriterionCodeFilter(DiffCriterionCodeFilter filter) {
            super(filter);
        }

        @Override
        public DiffCriterionCodeFilter copy() {
            return new DiffCriterionCodeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter quantity;

    private UnitOfMeasureFilter unitOfMeasure;

    private Co2EmissionSourceTypeCodeFilter co2EmissionSourceTypeCode;

    private BigDecimalFilter lowerCalorificValue;

    private BigDecimalFilter sulphurContent;

    private BigDecimalFilter density;

    private BigDecimalFilter viscosity;

    private BigDecimalFilter waterContent;

    private PortActivityCodeFilter portActivityCode;

    private DiffCriterionCodeFilter diffCriterionCode;

    private LongFilter eventReportId;

    private LongFilter fuelTypeId;

    private Boolean distinct;

    public ConsumptionLineCriteria() {}

    public ConsumptionLineCriteria(ConsumptionLineCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.quantity = other.optionalQuantity().map(BigDecimalFilter::copy).orElse(null);
        this.unitOfMeasure = other.optionalUnitOfMeasure().map(UnitOfMeasureFilter::copy).orElse(null);
        this.co2EmissionSourceTypeCode = other.optionalCo2EmissionSourceTypeCode().map(Co2EmissionSourceTypeCodeFilter::copy).orElse(null);
        this.lowerCalorificValue = other.optionalLowerCalorificValue().map(BigDecimalFilter::copy).orElse(null);
        this.sulphurContent = other.optionalSulphurContent().map(BigDecimalFilter::copy).orElse(null);
        this.density = other.optionalDensity().map(BigDecimalFilter::copy).orElse(null);
        this.viscosity = other.optionalViscosity().map(BigDecimalFilter::copy).orElse(null);
        this.waterContent = other.optionalWaterContent().map(BigDecimalFilter::copy).orElse(null);
        this.portActivityCode = other.optionalPortActivityCode().map(PortActivityCodeFilter::copy).orElse(null);
        this.diffCriterionCode = other.optionalDiffCriterionCode().map(DiffCriterionCodeFilter::copy).orElse(null);
        this.eventReportId = other.optionalEventReportId().map(LongFilter::copy).orElse(null);
        this.fuelTypeId = other.optionalFuelTypeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ConsumptionLineCriteria copy() {
        return new ConsumptionLineCriteria(this);
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

    public Co2EmissionSourceTypeCodeFilter getCo2EmissionSourceTypeCode() {
        return co2EmissionSourceTypeCode;
    }

    public Optional<Co2EmissionSourceTypeCodeFilter> optionalCo2EmissionSourceTypeCode() {
        return Optional.ofNullable(co2EmissionSourceTypeCode);
    }

    public Co2EmissionSourceTypeCodeFilter co2EmissionSourceTypeCode() {
        if (co2EmissionSourceTypeCode == null) {
            setCo2EmissionSourceTypeCode(new Co2EmissionSourceTypeCodeFilter());
        }
        return co2EmissionSourceTypeCode;
    }

    public void setCo2EmissionSourceTypeCode(Co2EmissionSourceTypeCodeFilter co2EmissionSourceTypeCode) {
        this.co2EmissionSourceTypeCode = co2EmissionSourceTypeCode;
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

    public PortActivityCodeFilter getPortActivityCode() {
        return portActivityCode;
    }

    public Optional<PortActivityCodeFilter> optionalPortActivityCode() {
        return Optional.ofNullable(portActivityCode);
    }

    public PortActivityCodeFilter portActivityCode() {
        if (portActivityCode == null) {
            setPortActivityCode(new PortActivityCodeFilter());
        }
        return portActivityCode;
    }

    public void setPortActivityCode(PortActivityCodeFilter portActivityCode) {
        this.portActivityCode = portActivityCode;
    }

    public DiffCriterionCodeFilter getDiffCriterionCode() {
        return diffCriterionCode;
    }

    public Optional<DiffCriterionCodeFilter> optionalDiffCriterionCode() {
        return Optional.ofNullable(diffCriterionCode);
    }

    public DiffCriterionCodeFilter diffCriterionCode() {
        if (diffCriterionCode == null) {
            setDiffCriterionCode(new DiffCriterionCodeFilter());
        }
        return diffCriterionCode;
    }

    public void setDiffCriterionCode(DiffCriterionCodeFilter diffCriterionCode) {
        this.diffCriterionCode = diffCriterionCode;
    }

    public LongFilter getEventReportId() {
        return eventReportId;
    }

    public Optional<LongFilter> optionalEventReportId() {
        return Optional.ofNullable(eventReportId);
    }

    public LongFilter eventReportId() {
        if (eventReportId == null) {
            setEventReportId(new LongFilter());
        }
        return eventReportId;
    }

    public void setEventReportId(LongFilter eventReportId) {
        this.eventReportId = eventReportId;
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
        final ConsumptionLineCriteria that = (ConsumptionLineCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(unitOfMeasure, that.unitOfMeasure) &&
            Objects.equals(co2EmissionSourceTypeCode, that.co2EmissionSourceTypeCode) &&
            Objects.equals(lowerCalorificValue, that.lowerCalorificValue) &&
            Objects.equals(sulphurContent, that.sulphurContent) &&
            Objects.equals(density, that.density) &&
            Objects.equals(viscosity, that.viscosity) &&
            Objects.equals(waterContent, that.waterContent) &&
            Objects.equals(portActivityCode, that.portActivityCode) &&
            Objects.equals(diffCriterionCode, that.diffCriterionCode) &&
            Objects.equals(eventReportId, that.eventReportId) &&
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
            co2EmissionSourceTypeCode,
            lowerCalorificValue,
            sulphurContent,
            density,
            viscosity,
            waterContent,
            portActivityCode,
            diffCriterionCode,
            eventReportId,
            fuelTypeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsumptionLineCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalQuantity().map(f -> "quantity=" + f + ", ").orElse("") +
            optionalUnitOfMeasure().map(f -> "unitOfMeasure=" + f + ", ").orElse("") +
            optionalCo2EmissionSourceTypeCode().map(f -> "co2EmissionSourceTypeCode=" + f + ", ").orElse("") +
            optionalLowerCalorificValue().map(f -> "lowerCalorificValue=" + f + ", ").orElse("") +
            optionalSulphurContent().map(f -> "sulphurContent=" + f + ", ").orElse("") +
            optionalDensity().map(f -> "density=" + f + ", ").orElse("") +
            optionalViscosity().map(f -> "viscosity=" + f + ", ").orElse("") +
            optionalWaterContent().map(f -> "waterContent=" + f + ", ").orElse("") +
            optionalPortActivityCode().map(f -> "portActivityCode=" + f + ", ").orElse("") +
            optionalDiffCriterionCode().map(f -> "diffCriterionCode=" + f + ", ").orElse("") +
            optionalEventReportId().map(f -> "eventReportId=" + f + ", ").orElse("") +
            optionalFuelTypeId().map(f -> "fuelTypeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
