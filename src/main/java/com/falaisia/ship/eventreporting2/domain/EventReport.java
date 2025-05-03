package com.falaisia.ship.eventreporting2.domain;

import com.falaisia.ship.eventreporting2.domain.enumeration.EventStatus;
import com.falaisia.ship.eventreporting2.domain.enumeration.LoadingCondition;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EventReport.
 */
@Entity
@Table(name = "event_report")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "document_date_and_time", nullable = false)
    private ZonedDateTime documentDateAndTime;

    @Column(name = "speed_gps", precision = 21, scale = 2)
    private BigDecimal speedGps;

    @Column(name = "document_display_number")
    private String documentDisplayNumber;

    @Column(name = "leg")
    private Integer leg;

    @Column(name = "distance_travelled", precision = 21, scale = 2)
    private BigDecimal distanceTravelled;

    @Column(name = "hours_underway", precision = 21, scale = 2)
    private BigDecimal hoursUnderway;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "event_status", nullable = false)
    private EventStatus eventStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "loading_condition", nullable = false)
    private LoadingCondition loadingCondition;

    @Column(name = "cargo_carried", precision = 21, scale = 2)
    private BigDecimal cargoCarried;

    @Pattern(regexp = "([0-8][0-9]|90)([0-5][0-9]|60)(N|S)")
    @Column(name = "coordinates_latitude")
    private String coordinatesLatitude;

    @Pattern(regexp = "(0[0-9][0-9]|1[0-7][0-9]|180)([0-5][0-9]|60)(W|E)")
    @Column(name = "coordinates_longitude")
    private String coordinatesLongitude;

    @Column(name = "ships_heading")
    private String shipsHeading;

    @Column(name = "beaufort_no")
    private Integer beaufortNo;

    @Column(name = "weather_condition")
    private String weatherCondition;

    @Column(name = "swell")
    private Boolean swell;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "ship" }, allowSetters = true)
    private Voyage voyage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventReport")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eventReport", "fuelType" }, allowSetters = true)
    private Set<ConsumptionLine> lines = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventReport")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eventReport" }, allowSetters = true)
    private Set<MachineryOperationLine> operationLines = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EventReport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDocumentDateAndTime() {
        return this.documentDateAndTime;
    }

    public EventReport documentDateAndTime(ZonedDateTime documentDateAndTime) {
        this.setDocumentDateAndTime(documentDateAndTime);
        return this;
    }

    public void setDocumentDateAndTime(ZonedDateTime documentDateAndTime) {
        this.documentDateAndTime = documentDateAndTime;
    }

    public BigDecimal getSpeedGps() {
        return this.speedGps;
    }

    public EventReport speedGps(BigDecimal speedGps) {
        this.setSpeedGps(speedGps);
        return this;
    }

    public void setSpeedGps(BigDecimal speedGps) {
        this.speedGps = speedGps;
    }

    public String getDocumentDisplayNumber() {
        return this.documentDisplayNumber;
    }

    public EventReport documentDisplayNumber(String documentDisplayNumber) {
        this.setDocumentDisplayNumber(documentDisplayNumber);
        return this;
    }

    public void setDocumentDisplayNumber(String documentDisplayNumber) {
        this.documentDisplayNumber = documentDisplayNumber;
    }

    public Integer getLeg() {
        return this.leg;
    }

    public EventReport leg(Integer leg) {
        this.setLeg(leg);
        return this;
    }

    public void setLeg(Integer leg) {
        this.leg = leg;
    }

    public BigDecimal getDistanceTravelled() {
        return this.distanceTravelled;
    }

    public EventReport distanceTravelled(BigDecimal distanceTravelled) {
        this.setDistanceTravelled(distanceTravelled);
        return this;
    }

    public void setDistanceTravelled(BigDecimal distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public BigDecimal getHoursUnderway() {
        return this.hoursUnderway;
    }

    public EventReport hoursUnderway(BigDecimal hoursUnderway) {
        this.setHoursUnderway(hoursUnderway);
        return this;
    }

    public void setHoursUnderway(BigDecimal hoursUnderway) {
        this.hoursUnderway = hoursUnderway;
    }

    public EventStatus getEventStatus() {
        return this.eventStatus;
    }

    public EventReport eventStatus(EventStatus eventStatus) {
        this.setEventStatus(eventStatus);
        return this;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public LoadingCondition getLoadingCondition() {
        return this.loadingCondition;
    }

    public EventReport loadingCondition(LoadingCondition loadingCondition) {
        this.setLoadingCondition(loadingCondition);
        return this;
    }

    public void setLoadingCondition(LoadingCondition loadingCondition) {
        this.loadingCondition = loadingCondition;
    }

    public BigDecimal getCargoCarried() {
        return this.cargoCarried;
    }

    public EventReport cargoCarried(BigDecimal cargoCarried) {
        this.setCargoCarried(cargoCarried);
        return this;
    }

    public void setCargoCarried(BigDecimal cargoCarried) {
        this.cargoCarried = cargoCarried;
    }

    public String getCoordinatesLatitude() {
        return this.coordinatesLatitude;
    }

    public EventReport coordinatesLatitude(String coordinatesLatitude) {
        this.setCoordinatesLatitude(coordinatesLatitude);
        return this;
    }

    public void setCoordinatesLatitude(String coordinatesLatitude) {
        this.coordinatesLatitude = coordinatesLatitude;
    }

    public String getCoordinatesLongitude() {
        return this.coordinatesLongitude;
    }

    public EventReport coordinatesLongitude(String coordinatesLongitude) {
        this.setCoordinatesLongitude(coordinatesLongitude);
        return this;
    }

    public void setCoordinatesLongitude(String coordinatesLongitude) {
        this.coordinatesLongitude = coordinatesLongitude;
    }

    public String getShipsHeading() {
        return this.shipsHeading;
    }

    public EventReport shipsHeading(String shipsHeading) {
        this.setShipsHeading(shipsHeading);
        return this;
    }

    public void setShipsHeading(String shipsHeading) {
        this.shipsHeading = shipsHeading;
    }

    public Integer getBeaufortNo() {
        return this.beaufortNo;
    }

    public EventReport beaufortNo(Integer beaufortNo) {
        this.setBeaufortNo(beaufortNo);
        return this;
    }

    public void setBeaufortNo(Integer beaufortNo) {
        this.beaufortNo = beaufortNo;
    }

    public String getWeatherCondition() {
        return this.weatherCondition;
    }

    public EventReport weatherCondition(String weatherCondition) {
        this.setWeatherCondition(weatherCondition);
        return this;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public Boolean getSwell() {
        return this.swell;
    }

    public EventReport swell(Boolean swell) {
        this.setSwell(swell);
        return this;
    }

    public void setSwell(Boolean swell) {
        this.swell = swell;
    }

    public Voyage getVoyage() {
        return this.voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public EventReport voyage(Voyage voyage) {
        this.setVoyage(voyage);
        return this;
    }

    public Set<ConsumptionLine> getLines() {
        return this.lines;
    }

    public void setLines(Set<ConsumptionLine> consumptionLines) {
        if (this.lines != null) {
            this.lines.forEach(i -> i.setEventReport(null));
        }
        if (consumptionLines != null) {
            consumptionLines.forEach(i -> i.setEventReport(this));
        }
        this.lines = consumptionLines;
    }

    public EventReport lines(Set<ConsumptionLine> consumptionLines) {
        this.setLines(consumptionLines);
        return this;
    }

    public EventReport addLines(ConsumptionLine consumptionLine) {
        this.lines.add(consumptionLine);
        consumptionLine.setEventReport(this);
        return this;
    }

    public EventReport removeLines(ConsumptionLine consumptionLine) {
        this.lines.remove(consumptionLine);
        consumptionLine.setEventReport(null);
        return this;
    }

    public Set<MachineryOperationLine> getOperationLines() {
        return this.operationLines;
    }

    public void setOperationLines(Set<MachineryOperationLine> machineryOperationLines) {
        if (this.operationLines != null) {
            this.operationLines.forEach(i -> i.setEventReport(null));
        }
        if (machineryOperationLines != null) {
            machineryOperationLines.forEach(i -> i.setEventReport(this));
        }
        this.operationLines = machineryOperationLines;
    }

    public EventReport operationLines(Set<MachineryOperationLine> machineryOperationLines) {
        this.setOperationLines(machineryOperationLines);
        return this;
    }

    public EventReport addOperationLines(MachineryOperationLine machineryOperationLine) {
        this.operationLines.add(machineryOperationLine);
        machineryOperationLine.setEventReport(this);
        return this;
    }

    public EventReport removeOperationLines(MachineryOperationLine machineryOperationLine) {
        this.operationLines.remove(machineryOperationLine);
        machineryOperationLine.setEventReport(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventReport)) {
            return false;
        }
        return getId() != null && getId().equals(((EventReport) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventReport{" +
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
            "}";
    }
}
