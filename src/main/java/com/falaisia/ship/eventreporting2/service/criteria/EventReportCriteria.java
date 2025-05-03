package com.falaisia.ship.eventreporting2.service.criteria;

import com.falaisia.ship.eventreporting2.domain.enumeration.EventStatus;
import com.falaisia.ship.eventreporting2.domain.enumeration.LoadingCondition;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.falaisia.ship.eventreporting2.domain.EventReport} entity. This class is used
 * in {@link com.falaisia.ship.eventreporting2.web.rest.EventReportResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /event-reports?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventReportCriteria implements Serializable, Criteria {

    /**
     * Class for filtering EventStatus
     */
    public static class EventStatusFilter extends Filter<EventStatus> {

        public EventStatusFilter() {}

        public EventStatusFilter(EventStatusFilter filter) {
            super(filter);
        }

        @Override
        public EventStatusFilter copy() {
            return new EventStatusFilter(this);
        }
    }

    /**
     * Class for filtering LoadingCondition
     */
    public static class LoadingConditionFilter extends Filter<LoadingCondition> {

        public LoadingConditionFilter() {}

        public LoadingConditionFilter(LoadingConditionFilter filter) {
            super(filter);
        }

        @Override
        public LoadingConditionFilter copy() {
            return new LoadingConditionFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter documentDateAndTime;

    private BigDecimalFilter speedGps;

    private StringFilter documentDisplayNumber;

    private IntegerFilter leg;

    private BigDecimalFilter distanceTravelled;

    private BigDecimalFilter hoursUnderway;

    private EventStatusFilter eventStatus;

    private LoadingConditionFilter loadingCondition;

    private BigDecimalFilter cargoCarried;

    private StringFilter coordinatesLatitude;

    private StringFilter coordinatesLongitude;

    private StringFilter shipsHeading;

    private IntegerFilter beaufortNo;

    private StringFilter weatherCondition;

    private BooleanFilter swell;

    private LongFilter voyageId;

    private LongFilter linesId;

    private LongFilter operationLinesId;

    private Boolean distinct;

    public EventReportCriteria() {}

    public EventReportCriteria(EventReportCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.documentDateAndTime = other.optionalDocumentDateAndTime().map(ZonedDateTimeFilter::copy).orElse(null);
        this.speedGps = other.optionalSpeedGps().map(BigDecimalFilter::copy).orElse(null);
        this.documentDisplayNumber = other.optionalDocumentDisplayNumber().map(StringFilter::copy).orElse(null);
        this.leg = other.optionalLeg().map(IntegerFilter::copy).orElse(null);
        this.distanceTravelled = other.optionalDistanceTravelled().map(BigDecimalFilter::copy).orElse(null);
        this.hoursUnderway = other.optionalHoursUnderway().map(BigDecimalFilter::copy).orElse(null);
        this.eventStatus = other.optionalEventStatus().map(EventStatusFilter::copy).orElse(null);
        this.loadingCondition = other.optionalLoadingCondition().map(LoadingConditionFilter::copy).orElse(null);
        this.cargoCarried = other.optionalCargoCarried().map(BigDecimalFilter::copy).orElse(null);
        this.coordinatesLatitude = other.optionalCoordinatesLatitude().map(StringFilter::copy).orElse(null);
        this.coordinatesLongitude = other.optionalCoordinatesLongitude().map(StringFilter::copy).orElse(null);
        this.shipsHeading = other.optionalShipsHeading().map(StringFilter::copy).orElse(null);
        this.beaufortNo = other.optionalBeaufortNo().map(IntegerFilter::copy).orElse(null);
        this.weatherCondition = other.optionalWeatherCondition().map(StringFilter::copy).orElse(null);
        this.swell = other.optionalSwell().map(BooleanFilter::copy).orElse(null);
        this.voyageId = other.optionalVoyageId().map(LongFilter::copy).orElse(null);
        this.linesId = other.optionalLinesId().map(LongFilter::copy).orElse(null);
        this.operationLinesId = other.optionalOperationLinesId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EventReportCriteria copy() {
        return new EventReportCriteria(this);
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

    public ZonedDateTimeFilter getDocumentDateAndTime() {
        return documentDateAndTime;
    }

    public Optional<ZonedDateTimeFilter> optionalDocumentDateAndTime() {
        return Optional.ofNullable(documentDateAndTime);
    }

    public ZonedDateTimeFilter documentDateAndTime() {
        if (documentDateAndTime == null) {
            setDocumentDateAndTime(new ZonedDateTimeFilter());
        }
        return documentDateAndTime;
    }

    public void setDocumentDateAndTime(ZonedDateTimeFilter documentDateAndTime) {
        this.documentDateAndTime = documentDateAndTime;
    }

    public BigDecimalFilter getSpeedGps() {
        return speedGps;
    }

    public Optional<BigDecimalFilter> optionalSpeedGps() {
        return Optional.ofNullable(speedGps);
    }

    public BigDecimalFilter speedGps() {
        if (speedGps == null) {
            setSpeedGps(new BigDecimalFilter());
        }
        return speedGps;
    }

    public void setSpeedGps(BigDecimalFilter speedGps) {
        this.speedGps = speedGps;
    }

    public StringFilter getDocumentDisplayNumber() {
        return documentDisplayNumber;
    }

    public Optional<StringFilter> optionalDocumentDisplayNumber() {
        return Optional.ofNullable(documentDisplayNumber);
    }

    public StringFilter documentDisplayNumber() {
        if (documentDisplayNumber == null) {
            setDocumentDisplayNumber(new StringFilter());
        }
        return documentDisplayNumber;
    }

    public void setDocumentDisplayNumber(StringFilter documentDisplayNumber) {
        this.documentDisplayNumber = documentDisplayNumber;
    }

    public IntegerFilter getLeg() {
        return leg;
    }

    public Optional<IntegerFilter> optionalLeg() {
        return Optional.ofNullable(leg);
    }

    public IntegerFilter leg() {
        if (leg == null) {
            setLeg(new IntegerFilter());
        }
        return leg;
    }

    public void setLeg(IntegerFilter leg) {
        this.leg = leg;
    }

    public BigDecimalFilter getDistanceTravelled() {
        return distanceTravelled;
    }

    public Optional<BigDecimalFilter> optionalDistanceTravelled() {
        return Optional.ofNullable(distanceTravelled);
    }

    public BigDecimalFilter distanceTravelled() {
        if (distanceTravelled == null) {
            setDistanceTravelled(new BigDecimalFilter());
        }
        return distanceTravelled;
    }

    public void setDistanceTravelled(BigDecimalFilter distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public BigDecimalFilter getHoursUnderway() {
        return hoursUnderway;
    }

    public Optional<BigDecimalFilter> optionalHoursUnderway() {
        return Optional.ofNullable(hoursUnderway);
    }

    public BigDecimalFilter hoursUnderway() {
        if (hoursUnderway == null) {
            setHoursUnderway(new BigDecimalFilter());
        }
        return hoursUnderway;
    }

    public void setHoursUnderway(BigDecimalFilter hoursUnderway) {
        this.hoursUnderway = hoursUnderway;
    }

    public EventStatusFilter getEventStatus() {
        return eventStatus;
    }

    public Optional<EventStatusFilter> optionalEventStatus() {
        return Optional.ofNullable(eventStatus);
    }

    public EventStatusFilter eventStatus() {
        if (eventStatus == null) {
            setEventStatus(new EventStatusFilter());
        }
        return eventStatus;
    }

    public void setEventStatus(EventStatusFilter eventStatus) {
        this.eventStatus = eventStatus;
    }

    public LoadingConditionFilter getLoadingCondition() {
        return loadingCondition;
    }

    public Optional<LoadingConditionFilter> optionalLoadingCondition() {
        return Optional.ofNullable(loadingCondition);
    }

    public LoadingConditionFilter loadingCondition() {
        if (loadingCondition == null) {
            setLoadingCondition(new LoadingConditionFilter());
        }
        return loadingCondition;
    }

    public void setLoadingCondition(LoadingConditionFilter loadingCondition) {
        this.loadingCondition = loadingCondition;
    }

    public BigDecimalFilter getCargoCarried() {
        return cargoCarried;
    }

    public Optional<BigDecimalFilter> optionalCargoCarried() {
        return Optional.ofNullable(cargoCarried);
    }

    public BigDecimalFilter cargoCarried() {
        if (cargoCarried == null) {
            setCargoCarried(new BigDecimalFilter());
        }
        return cargoCarried;
    }

    public void setCargoCarried(BigDecimalFilter cargoCarried) {
        this.cargoCarried = cargoCarried;
    }

    public StringFilter getCoordinatesLatitude() {
        return coordinatesLatitude;
    }

    public Optional<StringFilter> optionalCoordinatesLatitude() {
        return Optional.ofNullable(coordinatesLatitude);
    }

    public StringFilter coordinatesLatitude() {
        if (coordinatesLatitude == null) {
            setCoordinatesLatitude(new StringFilter());
        }
        return coordinatesLatitude;
    }

    public void setCoordinatesLatitude(StringFilter coordinatesLatitude) {
        this.coordinatesLatitude = coordinatesLatitude;
    }

    public StringFilter getCoordinatesLongitude() {
        return coordinatesLongitude;
    }

    public Optional<StringFilter> optionalCoordinatesLongitude() {
        return Optional.ofNullable(coordinatesLongitude);
    }

    public StringFilter coordinatesLongitude() {
        if (coordinatesLongitude == null) {
            setCoordinatesLongitude(new StringFilter());
        }
        return coordinatesLongitude;
    }

    public void setCoordinatesLongitude(StringFilter coordinatesLongitude) {
        this.coordinatesLongitude = coordinatesLongitude;
    }

    public StringFilter getShipsHeading() {
        return shipsHeading;
    }

    public Optional<StringFilter> optionalShipsHeading() {
        return Optional.ofNullable(shipsHeading);
    }

    public StringFilter shipsHeading() {
        if (shipsHeading == null) {
            setShipsHeading(new StringFilter());
        }
        return shipsHeading;
    }

    public void setShipsHeading(StringFilter shipsHeading) {
        this.shipsHeading = shipsHeading;
    }

    public IntegerFilter getBeaufortNo() {
        return beaufortNo;
    }

    public Optional<IntegerFilter> optionalBeaufortNo() {
        return Optional.ofNullable(beaufortNo);
    }

    public IntegerFilter beaufortNo() {
        if (beaufortNo == null) {
            setBeaufortNo(new IntegerFilter());
        }
        return beaufortNo;
    }

    public void setBeaufortNo(IntegerFilter beaufortNo) {
        this.beaufortNo = beaufortNo;
    }

    public StringFilter getWeatherCondition() {
        return weatherCondition;
    }

    public Optional<StringFilter> optionalWeatherCondition() {
        return Optional.ofNullable(weatherCondition);
    }

    public StringFilter weatherCondition() {
        if (weatherCondition == null) {
            setWeatherCondition(new StringFilter());
        }
        return weatherCondition;
    }

    public void setWeatherCondition(StringFilter weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public BooleanFilter getSwell() {
        return swell;
    }

    public Optional<BooleanFilter> optionalSwell() {
        return Optional.ofNullable(swell);
    }

    public BooleanFilter swell() {
        if (swell == null) {
            setSwell(new BooleanFilter());
        }
        return swell;
    }

    public void setSwell(BooleanFilter swell) {
        this.swell = swell;
    }

    public LongFilter getVoyageId() {
        return voyageId;
    }

    public Optional<LongFilter> optionalVoyageId() {
        return Optional.ofNullable(voyageId);
    }

    public LongFilter voyageId() {
        if (voyageId == null) {
            setVoyageId(new LongFilter());
        }
        return voyageId;
    }

    public void setVoyageId(LongFilter voyageId) {
        this.voyageId = voyageId;
    }

    public LongFilter getLinesId() {
        return linesId;
    }

    public Optional<LongFilter> optionalLinesId() {
        return Optional.ofNullable(linesId);
    }

    public LongFilter linesId() {
        if (linesId == null) {
            setLinesId(new LongFilter());
        }
        return linesId;
    }

    public void setLinesId(LongFilter linesId) {
        this.linesId = linesId;
    }

    public LongFilter getOperationLinesId() {
        return operationLinesId;
    }

    public Optional<LongFilter> optionalOperationLinesId() {
        return Optional.ofNullable(operationLinesId);
    }

    public LongFilter operationLinesId() {
        if (operationLinesId == null) {
            setOperationLinesId(new LongFilter());
        }
        return operationLinesId;
    }

    public void setOperationLinesId(LongFilter operationLinesId) {
        this.operationLinesId = operationLinesId;
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
        final EventReportCriteria that = (EventReportCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(documentDateAndTime, that.documentDateAndTime) &&
            Objects.equals(speedGps, that.speedGps) &&
            Objects.equals(documentDisplayNumber, that.documentDisplayNumber) &&
            Objects.equals(leg, that.leg) &&
            Objects.equals(distanceTravelled, that.distanceTravelled) &&
            Objects.equals(hoursUnderway, that.hoursUnderway) &&
            Objects.equals(eventStatus, that.eventStatus) &&
            Objects.equals(loadingCondition, that.loadingCondition) &&
            Objects.equals(cargoCarried, that.cargoCarried) &&
            Objects.equals(coordinatesLatitude, that.coordinatesLatitude) &&
            Objects.equals(coordinatesLongitude, that.coordinatesLongitude) &&
            Objects.equals(shipsHeading, that.shipsHeading) &&
            Objects.equals(beaufortNo, that.beaufortNo) &&
            Objects.equals(weatherCondition, that.weatherCondition) &&
            Objects.equals(swell, that.swell) &&
            Objects.equals(voyageId, that.voyageId) &&
            Objects.equals(linesId, that.linesId) &&
            Objects.equals(operationLinesId, that.operationLinesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            documentDateAndTime,
            speedGps,
            documentDisplayNumber,
            leg,
            distanceTravelled,
            hoursUnderway,
            eventStatus,
            loadingCondition,
            cargoCarried,
            coordinatesLatitude,
            coordinatesLongitude,
            shipsHeading,
            beaufortNo,
            weatherCondition,
            swell,
            voyageId,
            linesId,
            operationLinesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventReportCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDocumentDateAndTime().map(f -> "documentDateAndTime=" + f + ", ").orElse("") +
            optionalSpeedGps().map(f -> "speedGps=" + f + ", ").orElse("") +
            optionalDocumentDisplayNumber().map(f -> "documentDisplayNumber=" + f + ", ").orElse("") +
            optionalLeg().map(f -> "leg=" + f + ", ").orElse("") +
            optionalDistanceTravelled().map(f -> "distanceTravelled=" + f + ", ").orElse("") +
            optionalHoursUnderway().map(f -> "hoursUnderway=" + f + ", ").orElse("") +
            optionalEventStatus().map(f -> "eventStatus=" + f + ", ").orElse("") +
            optionalLoadingCondition().map(f -> "loadingCondition=" + f + ", ").orElse("") +
            optionalCargoCarried().map(f -> "cargoCarried=" + f + ", ").orElse("") +
            optionalCoordinatesLatitude().map(f -> "coordinatesLatitude=" + f + ", ").orElse("") +
            optionalCoordinatesLongitude().map(f -> "coordinatesLongitude=" + f + ", ").orElse("") +
            optionalShipsHeading().map(f -> "shipsHeading=" + f + ", ").orElse("") +
            optionalBeaufortNo().map(f -> "beaufortNo=" + f + ", ").orElse("") +
            optionalWeatherCondition().map(f -> "weatherCondition=" + f + ", ").orElse("") +
            optionalSwell().map(f -> "swell=" + f + ", ").orElse("") +
            optionalVoyageId().map(f -> "voyageId=" + f + ", ").orElse("") +
            optionalLinesId().map(f -> "linesId=" + f + ", ").orElse("") +
            optionalOperationLinesId().map(f -> "operationLinesId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
