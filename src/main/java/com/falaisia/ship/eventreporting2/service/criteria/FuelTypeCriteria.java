package com.falaisia.ship.eventreporting2.service.criteria;

import com.falaisia.ship.eventreporting2.domain.enumeration.FuelTypeCode;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.falaisia.ship.eventreporting2.domain.FuelType} entity. This class is used
 * in {@link com.falaisia.ship.eventreporting2.web.rest.FuelTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fuel-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuelTypeCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FuelTypeCode
     */
    public static class FuelTypeCodeFilter extends Filter<FuelTypeCode> {

        public FuelTypeCodeFilter() {}

        public FuelTypeCodeFilter(FuelTypeCodeFilter filter) {
            super(filter);
        }

        @Override
        public FuelTypeCodeFilter copy() {
            return new FuelTypeCodeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private FuelTypeCodeFilter fuelTypeCode;

    private BigDecimalFilter carbonFactory;

    private Boolean distinct;

    public FuelTypeCriteria() {}

    public FuelTypeCriteria(FuelTypeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.fuelTypeCode = other.optionalFuelTypeCode().map(FuelTypeCodeFilter::copy).orElse(null);
        this.carbonFactory = other.optionalCarbonFactory().map(BigDecimalFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FuelTypeCriteria copy() {
        return new FuelTypeCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public FuelTypeCodeFilter getFuelTypeCode() {
        return fuelTypeCode;
    }

    public Optional<FuelTypeCodeFilter> optionalFuelTypeCode() {
        return Optional.ofNullable(fuelTypeCode);
    }

    public FuelTypeCodeFilter fuelTypeCode() {
        if (fuelTypeCode == null) {
            setFuelTypeCode(new FuelTypeCodeFilter());
        }
        return fuelTypeCode;
    }

    public void setFuelTypeCode(FuelTypeCodeFilter fuelTypeCode) {
        this.fuelTypeCode = fuelTypeCode;
    }

    public BigDecimalFilter getCarbonFactory() {
        return carbonFactory;
    }

    public Optional<BigDecimalFilter> optionalCarbonFactory() {
        return Optional.ofNullable(carbonFactory);
    }

    public BigDecimalFilter carbonFactory() {
        if (carbonFactory == null) {
            setCarbonFactory(new BigDecimalFilter());
        }
        return carbonFactory;
    }

    public void setCarbonFactory(BigDecimalFilter carbonFactory) {
        this.carbonFactory = carbonFactory;
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
        final FuelTypeCriteria that = (FuelTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(fuelTypeCode, that.fuelTypeCode) &&
            Objects.equals(carbonFactory, that.carbonFactory) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, fuelTypeCode, carbonFactory, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuelTypeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalFuelTypeCode().map(f -> "fuelTypeCode=" + f + ", ").orElse("") +
            optionalCarbonFactory().map(f -> "carbonFactory=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
