package com.falaisia.ship.eventreporting2.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.falaisia.ship.eventreporting2.domain.MachineryOperationLine} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MachineryOperationLineDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal runningHours;

    private BigDecimal powerOutput;

    private BigDecimal averageRpm;

    @NotNull
    private EventReportDTO eventReport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRunningHours() {
        return runningHours;
    }

    public void setRunningHours(BigDecimal runningHours) {
        this.runningHours = runningHours;
    }

    public BigDecimal getPowerOutput() {
        return powerOutput;
    }

    public void setPowerOutput(BigDecimal powerOutput) {
        this.powerOutput = powerOutput;
    }

    public BigDecimal getAverageRpm() {
        return averageRpm;
    }

    public void setAverageRpm(BigDecimal averageRpm) {
        this.averageRpm = averageRpm;
    }

    public EventReportDTO getEventReport() {
        return eventReport;
    }

    public void setEventReport(EventReportDTO eventReport) {
        this.eventReport = eventReport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MachineryOperationLineDTO)) {
            return false;
        }

        MachineryOperationLineDTO machineryOperationLineDTO = (MachineryOperationLineDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, machineryOperationLineDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MachineryOperationLineDTO{" +
            "id=" + getId() +
            ", runningHours=" + getRunningHours() +
            ", powerOutput=" + getPowerOutput() +
            ", averageRpm=" + getAverageRpm() +
            ", eventReport=" + getEventReport() +
            "}";
    }
}
