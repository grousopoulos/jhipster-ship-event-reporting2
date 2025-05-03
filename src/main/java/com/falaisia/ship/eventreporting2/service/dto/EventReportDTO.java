package com.falaisia.ship.eventreporting2.service.dto;

import com.falaisia.ship.eventreporting2.domain.enumeration.EventStatus;
import com.falaisia.ship.eventreporting2.domain.enumeration.LoadingCondition;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.falaisia.ship.eventreporting2.domain.EventReport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventReportDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime documentDateAndTime;

    private BigDecimal speedGps;

    private String documentDisplayNumber;

    private Integer leg;

    private BigDecimal distanceTravelled;

    private BigDecimal hoursUnderway;

    @NotNull
    private EventStatus eventStatus;

    @NotNull
    private LoadingCondition loadingCondition;

    private BigDecimal cargoCarried;

    @Pattern(regexp = "([0-8][0-9]|90)([0-5][0-9]|60)(N|S)")
    private String coordinatesLatitude;

    @Pattern(regexp = "(0[0-9][0-9]|1[0-7][0-9]|180)([0-5][0-9]|60)(W|E)")
    private String coordinatesLongitude;

    private String shipsHeading;

    private Integer beaufortNo;

    private String weatherCondition;

    private Boolean swell;

    @NotNull
    private VoyageDTO voyage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDocumentDateAndTime() {
        return documentDateAndTime;
    }

    public void setDocumentDateAndTime(ZonedDateTime documentDateAndTime) {
        this.documentDateAndTime = documentDateAndTime;
    }

    public BigDecimal getSpeedGps() {
        return speedGps;
    }

    public void setSpeedGps(BigDecimal speedGps) {
        this.speedGps = speedGps;
    }

    public String getDocumentDisplayNumber() {
        return documentDisplayNumber;
    }

    public void setDocumentDisplayNumber(String documentDisplayNumber) {
        this.documentDisplayNumber = documentDisplayNumber;
    }

    public Integer getLeg() {
        return leg;
    }

    public void setLeg(Integer leg) {
        this.leg = leg;
    }

    public BigDecimal getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(BigDecimal distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public BigDecimal getHoursUnderway() {
        return hoursUnderway;
    }

    public void setHoursUnderway(BigDecimal hoursUnderway) {
        this.hoursUnderway = hoursUnderway;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public LoadingCondition getLoadingCondition() {
        return loadingCondition;
    }

    public void setLoadingCondition(LoadingCondition loadingCondition) {
        this.loadingCondition = loadingCondition;
    }

    public BigDecimal getCargoCarried() {
        return cargoCarried;
    }

    public void setCargoCarried(BigDecimal cargoCarried) {
        this.cargoCarried = cargoCarried;
    }

    public String getCoordinatesLatitude() {
        return coordinatesLatitude;
    }

    public void setCoordinatesLatitude(String coordinatesLatitude) {
        this.coordinatesLatitude = coordinatesLatitude;
    }

    public String getCoordinatesLongitude() {
        return coordinatesLongitude;
    }

    public void setCoordinatesLongitude(String coordinatesLongitude) {
        this.coordinatesLongitude = coordinatesLongitude;
    }

    public String getShipsHeading() {
        return shipsHeading;
    }

    public void setShipsHeading(String shipsHeading) {
        this.shipsHeading = shipsHeading;
    }

    public Integer getBeaufortNo() {
        return beaufortNo;
    }

    public void setBeaufortNo(Integer beaufortNo) {
        this.beaufortNo = beaufortNo;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public Boolean getSwell() {
        return swell;
    }

    public void setSwell(Boolean swell) {
        this.swell = swell;
    }

    public VoyageDTO getVoyage() {
        return voyage;
    }

    public void setVoyage(VoyageDTO voyage) {
        this.voyage = voyage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventReportDTO)) {
            return false;
        }

        EventReportDTO eventReportDTO = (EventReportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventReportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventReportDTO{" +
            "id=" + getId() +
            ", documentDateAndTime='" + getDocumentDateAndTime() + "'" +
            ", speedGps=" + getSpeedGps() +
            ", documentDisplayNumber='" + getDocumentDisplayNumber() + "'" +
            ", leg=" + getLeg() +
            ", distanceTravelled=" + getDistanceTravelled() +
            ", hoursUnderway=" + getHoursUnderway() +
            ", eventStatus='" + getEventStatus() + "'" +
            ", loadingCondition='" + getLoadingCondition() + "'" +
            ", cargoCarried=" + getCargoCarried() +
            ", coordinatesLatitude='" + getCoordinatesLatitude() + "'" +
            ", coordinatesLongitude='" + getCoordinatesLongitude() + "'" +
            ", shipsHeading='" + getShipsHeading() + "'" +
            ", beaufortNo=" + getBeaufortNo() +
            ", weatherCondition='" + getWeatherCondition() + "'" +
            ", swell='" + getSwell() + "'" +
            ", voyage=" + getVoyage() +
            "}";
    }
}
