package com.falaisia.ship.eventreporting2.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.falaisia.ship.eventreporting2.domain.MachineryOperationLine} entity. This class is used
 * in {@link com.falaisia.ship.eventreporting2.web.rest.MachineryOperationLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /machinery-operation-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MachineryOperationLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter runningHours;

    private BigDecimalFilter powerOutput;

    private BigDecimalFilter averageRpm;

    private LongFilter eventReportId;

    private Boolean distinct;

    public MachineryOperationLineCriteria() {}

    public MachineryOperationLineCriteria(MachineryOperationLineCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.runningHours = other.optionalRunningHours().map(BigDecimalFilter::copy).orElse(null);
        this.powerOutput = other.optionalPowerOutput().map(BigDecimalFilter::copy).orElse(null);
        this.averageRpm = other.optionalAverageRpm().map(BigDecimalFilter::copy).orElse(null);
        this.eventReportId = other.optionalEventReportId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public MachineryOperationLineCriteria copy() {
        return new MachineryOperationLineCriteria(this);
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

    public BigDecimalFilter getRunningHours() {
        return runningHours;
    }

    public Optional<BigDecimalFilter> optionalRunningHours() {
        return Optional.ofNullable(runningHours);
    }

    public BigDecimalFilter runningHours() {
        if (runningHours == null) {
            setRunningHours(new BigDecimalFilter());
        }
        return runningHours;
    }

    public void setRunningHours(BigDecimalFilter runningHours) {
        this.runningHours = runningHours;
    }

    public BigDecimalFilter getPowerOutput() {
        return powerOutput;
    }

    public Optional<BigDecimalFilter> optionalPowerOutput() {
        return Optional.ofNullable(powerOutput);
    }

    public BigDecimalFilter powerOutput() {
        if (powerOutput == null) {
            setPowerOutput(new BigDecimalFilter());
        }
        return powerOutput;
    }

    public void setPowerOutput(BigDecimalFilter powerOutput) {
        this.powerOutput = powerOutput;
    }

    public BigDecimalFilter getAverageRpm() {
        return averageRpm;
    }

    public Optional<BigDecimalFilter> optionalAverageRpm() {
        return Optional.ofNullable(averageRpm);
    }

    public BigDecimalFilter averageRpm() {
        if (averageRpm == null) {
            setAverageRpm(new BigDecimalFilter());
        }
        return averageRpm;
    }

    public void setAverageRpm(BigDecimalFilter averageRpm) {
        this.averageRpm = averageRpm;
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
        final MachineryOperationLineCriteria that = (MachineryOperationLineCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(runningHours, that.runningHours) &&
            Objects.equals(powerOutput, that.powerOutput) &&
            Objects.equals(averageRpm, that.averageRpm) &&
            Objects.equals(eventReportId, that.eventReportId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, runningHours, powerOutput, averageRpm, eventReportId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MachineryOperationLineCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalRunningHours().map(f -> "runningHours=" + f + ", ").orElse("") +
            optionalPowerOutput().map(f -> "powerOutput=" + f + ", ").orElse("") +
            optionalAverageRpm().map(f -> "averageRpm=" + f + ", ").orElse("") +
            optionalEventReportId().map(f -> "eventReportId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
