package com.falaisia.ship.eventreporting2.service.criteria;

import com.falaisia.ship.eventreporting2.domain.enumeration.IceClassPolarCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.MonitoringMethodCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.ShipType;
import com.falaisia.ship.eventreporting2.domain.enumeration.TechnicalEfficiencyCode;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.falaisia.ship.eventreporting2.domain.Ship} entity. This class is used
 * in {@link com.falaisia.ship.eventreporting2.web.rest.ShipResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ships?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipCriteria implements Serializable, Criteria {

    /**
     * Class for filtering IceClassPolarCode
     */
    public static class IceClassPolarCodeFilter extends Filter<IceClassPolarCode> {

        public IceClassPolarCodeFilter() {}

        public IceClassPolarCodeFilter(IceClassPolarCodeFilter filter) {
            super(filter);
        }

        @Override
        public IceClassPolarCodeFilter copy() {
            return new IceClassPolarCodeFilter(this);
        }
    }

    /**
     * Class for filtering TechnicalEfficiencyCode
     */
    public static class TechnicalEfficiencyCodeFilter extends Filter<TechnicalEfficiencyCode> {

        public TechnicalEfficiencyCodeFilter() {}

        public TechnicalEfficiencyCodeFilter(TechnicalEfficiencyCodeFilter filter) {
            super(filter);
        }

        @Override
        public TechnicalEfficiencyCodeFilter copy() {
            return new TechnicalEfficiencyCodeFilter(this);
        }
    }

    /**
     * Class for filtering ShipType
     */
    public static class ShipTypeFilter extends Filter<ShipType> {

        public ShipTypeFilter() {}

        public ShipTypeFilter(ShipTypeFilter filter) {
            super(filter);
        }

        @Override
        public ShipTypeFilter copy() {
            return new ShipTypeFilter(this);
        }
    }

    /**
     * Class for filtering MonitoringMethodCode
     */
    public static class MonitoringMethodCodeFilter extends Filter<MonitoringMethodCode> {

        public MonitoringMethodCodeFilter() {}

        public MonitoringMethodCodeFilter(MonitoringMethodCodeFilter filter) {
            super(filter);
        }

        @Override
        public MonitoringMethodCodeFilter copy() {
            return new MonitoringMethodCodeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter callSign;

    private IceClassPolarCodeFilter iceClassPolarCode;

    private TechnicalEfficiencyCodeFilter technicalEfficiencyCode;

    private ShipTypeFilter shipType;

    private MonitoringMethodCodeFilter monitoringMethodCode;

    private LongFilter ownerCountryId;

    private LongFilter flagId;

    private LongFilter classificationSocietyId;

    private Boolean distinct;

    public ShipCriteria() {}

    public ShipCriteria(ShipCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.callSign = other.optionalCallSign().map(StringFilter::copy).orElse(null);
        this.iceClassPolarCode = other.optionalIceClassPolarCode().map(IceClassPolarCodeFilter::copy).orElse(null);
        this.technicalEfficiencyCode = other.optionalTechnicalEfficiencyCode().map(TechnicalEfficiencyCodeFilter::copy).orElse(null);
        this.shipType = other.optionalShipType().map(ShipTypeFilter::copy).orElse(null);
        this.monitoringMethodCode = other.optionalMonitoringMethodCode().map(MonitoringMethodCodeFilter::copy).orElse(null);
        this.ownerCountryId = other.optionalOwnerCountryId().map(LongFilter::copy).orElse(null);
        this.flagId = other.optionalFlagId().map(LongFilter::copy).orElse(null);
        this.classificationSocietyId = other.optionalClassificationSocietyId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ShipCriteria copy() {
        return new ShipCriteria(this);
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

    public StringFilter getCallSign() {
        return callSign;
    }

    public Optional<StringFilter> optionalCallSign() {
        return Optional.ofNullable(callSign);
    }

    public StringFilter callSign() {
        if (callSign == null) {
            setCallSign(new StringFilter());
        }
        return callSign;
    }

    public void setCallSign(StringFilter callSign) {
        this.callSign = callSign;
    }

    public IceClassPolarCodeFilter getIceClassPolarCode() {
        return iceClassPolarCode;
    }

    public Optional<IceClassPolarCodeFilter> optionalIceClassPolarCode() {
        return Optional.ofNullable(iceClassPolarCode);
    }

    public IceClassPolarCodeFilter iceClassPolarCode() {
        if (iceClassPolarCode == null) {
            setIceClassPolarCode(new IceClassPolarCodeFilter());
        }
        return iceClassPolarCode;
    }

    public void setIceClassPolarCode(IceClassPolarCodeFilter iceClassPolarCode) {
        this.iceClassPolarCode = iceClassPolarCode;
    }

    public TechnicalEfficiencyCodeFilter getTechnicalEfficiencyCode() {
        return technicalEfficiencyCode;
    }

    public Optional<TechnicalEfficiencyCodeFilter> optionalTechnicalEfficiencyCode() {
        return Optional.ofNullable(technicalEfficiencyCode);
    }

    public TechnicalEfficiencyCodeFilter technicalEfficiencyCode() {
        if (technicalEfficiencyCode == null) {
            setTechnicalEfficiencyCode(new TechnicalEfficiencyCodeFilter());
        }
        return technicalEfficiencyCode;
    }

    public void setTechnicalEfficiencyCode(TechnicalEfficiencyCodeFilter technicalEfficiencyCode) {
        this.technicalEfficiencyCode = technicalEfficiencyCode;
    }

    public ShipTypeFilter getShipType() {
        return shipType;
    }

    public Optional<ShipTypeFilter> optionalShipType() {
        return Optional.ofNullable(shipType);
    }

    public ShipTypeFilter shipType() {
        if (shipType == null) {
            setShipType(new ShipTypeFilter());
        }
        return shipType;
    }

    public void setShipType(ShipTypeFilter shipType) {
        this.shipType = shipType;
    }

    public MonitoringMethodCodeFilter getMonitoringMethodCode() {
        return monitoringMethodCode;
    }

    public Optional<MonitoringMethodCodeFilter> optionalMonitoringMethodCode() {
        return Optional.ofNullable(monitoringMethodCode);
    }

    public MonitoringMethodCodeFilter monitoringMethodCode() {
        if (monitoringMethodCode == null) {
            setMonitoringMethodCode(new MonitoringMethodCodeFilter());
        }
        return monitoringMethodCode;
    }

    public void setMonitoringMethodCode(MonitoringMethodCodeFilter monitoringMethodCode) {
        this.monitoringMethodCode = monitoringMethodCode;
    }

    public LongFilter getOwnerCountryId() {
        return ownerCountryId;
    }

    public Optional<LongFilter> optionalOwnerCountryId() {
        return Optional.ofNullable(ownerCountryId);
    }

    public LongFilter ownerCountryId() {
        if (ownerCountryId == null) {
            setOwnerCountryId(new LongFilter());
        }
        return ownerCountryId;
    }

    public void setOwnerCountryId(LongFilter ownerCountryId) {
        this.ownerCountryId = ownerCountryId;
    }

    public LongFilter getFlagId() {
        return flagId;
    }

    public Optional<LongFilter> optionalFlagId() {
        return Optional.ofNullable(flagId);
    }

    public LongFilter flagId() {
        if (flagId == null) {
            setFlagId(new LongFilter());
        }
        return flagId;
    }

    public void setFlagId(LongFilter flagId) {
        this.flagId = flagId;
    }

    public LongFilter getClassificationSocietyId() {
        return classificationSocietyId;
    }

    public Optional<LongFilter> optionalClassificationSocietyId() {
        return Optional.ofNullable(classificationSocietyId);
    }

    public LongFilter classificationSocietyId() {
        if (classificationSocietyId == null) {
            setClassificationSocietyId(new LongFilter());
        }
        return classificationSocietyId;
    }

    public void setClassificationSocietyId(LongFilter classificationSocietyId) {
        this.classificationSocietyId = classificationSocietyId;
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
        final ShipCriteria that = (ShipCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(callSign, that.callSign) &&
            Objects.equals(iceClassPolarCode, that.iceClassPolarCode) &&
            Objects.equals(technicalEfficiencyCode, that.technicalEfficiencyCode) &&
            Objects.equals(shipType, that.shipType) &&
            Objects.equals(monitoringMethodCode, that.monitoringMethodCode) &&
            Objects.equals(ownerCountryId, that.ownerCountryId) &&
            Objects.equals(flagId, that.flagId) &&
            Objects.equals(classificationSocietyId, that.classificationSocietyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            callSign,
            iceClassPolarCode,
            technicalEfficiencyCode,
            shipType,
            monitoringMethodCode,
            ownerCountryId,
            flagId,
            classificationSocietyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalCallSign().map(f -> "callSign=" + f + ", ").orElse("") +
            optionalIceClassPolarCode().map(f -> "iceClassPolarCode=" + f + ", ").orElse("") +
            optionalTechnicalEfficiencyCode().map(f -> "technicalEfficiencyCode=" + f + ", ").orElse("") +
            optionalShipType().map(f -> "shipType=" + f + ", ").orElse("") +
            optionalMonitoringMethodCode().map(f -> "monitoringMethodCode=" + f + ", ").orElse("") +
            optionalOwnerCountryId().map(f -> "ownerCountryId=" + f + ", ").orElse("") +
            optionalFlagId().map(f -> "flagId=" + f + ", ").orElse("") +
            optionalClassificationSocietyId().map(f -> "classificationSocietyId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
