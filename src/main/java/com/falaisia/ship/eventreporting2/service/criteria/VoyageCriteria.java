package com.falaisia.ship.eventreporting2.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.falaisia.ship.eventreporting2.domain.Voyage} entity. This class is used
 * in {@link com.falaisia.ship.eventreporting2.web.rest.VoyageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /voyages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VoyageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter number;

    private LongFilter shipId;

    private Boolean distinct;

    public VoyageCriteria() {}

    public VoyageCriteria(VoyageCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.number = other.optionalNumber().map(StringFilter::copy).orElse(null);
        this.shipId = other.optionalShipId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public VoyageCriteria copy() {
        return new VoyageCriteria(this);
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

    public StringFilter getNumber() {
        return number;
    }

    public Optional<StringFilter> optionalNumber() {
        return Optional.ofNullable(number);
    }

    public StringFilter number() {
        if (number == null) {
            setNumber(new StringFilter());
        }
        return number;
    }

    public void setNumber(StringFilter number) {
        this.number = number;
    }

    public LongFilter getShipId() {
        return shipId;
    }

    public Optional<LongFilter> optionalShipId() {
        return Optional.ofNullable(shipId);
    }

    public LongFilter shipId() {
        if (shipId == null) {
            setShipId(new LongFilter());
        }
        return shipId;
    }

    public void setShipId(LongFilter shipId) {
        this.shipId = shipId;
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
        final VoyageCriteria that = (VoyageCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(number, that.number) &&
            Objects.equals(shipId, that.shipId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, shipId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoyageCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNumber().map(f -> "number=" + f + ", ").orElse("") +
            optionalShipId().map(f -> "shipId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
