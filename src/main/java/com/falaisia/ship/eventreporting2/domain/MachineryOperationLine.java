package com.falaisia.ship.eventreporting2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MachineryOperationLine.
 */
@Entity
@Table(name = "machinery_operation_line")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MachineryOperationLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "running_hours", precision = 21, scale = 2, nullable = false)
    private BigDecimal runningHours;

    @Column(name = "power_output", precision = 21, scale = 2)
    private BigDecimal powerOutput;

    @Column(name = "average_rpm", precision = 21, scale = 2)
    private BigDecimal averageRpm;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "voyage", "lines", "operationLines" }, allowSetters = true)
    private EventReport eventReport;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MachineryOperationLine id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRunningHours() {
        return this.runningHours;
    }

    public MachineryOperationLine runningHours(BigDecimal runningHours) {
        this.setRunningHours(runningHours);
        return this;
    }

    public void setRunningHours(BigDecimal runningHours) {
        this.runningHours = runningHours;
    }

    public BigDecimal getPowerOutput() {
        return this.powerOutput;
    }

    public MachineryOperationLine powerOutput(BigDecimal powerOutput) {
        this.setPowerOutput(powerOutput);
        return this;
    }

    public void setPowerOutput(BigDecimal powerOutput) {
        this.powerOutput = powerOutput;
    }

    public BigDecimal getAverageRpm() {
        return this.averageRpm;
    }

    public MachineryOperationLine averageRpm(BigDecimal averageRpm) {
        this.setAverageRpm(averageRpm);
        return this;
    }

    public void setAverageRpm(BigDecimal averageRpm) {
        this.averageRpm = averageRpm;
    }

    public EventReport getEventReport() {
        return this.eventReport;
    }

    public void setEventReport(EventReport eventReport) {
        this.eventReport = eventReport;
    }

    public MachineryOperationLine eventReport(EventReport eventReport) {
        this.setEventReport(eventReport);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MachineryOperationLine)) {
            return false;
        }
        return getId() != null && getId().equals(((MachineryOperationLine) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MachineryOperationLine{" +
            "id=" + getId() +
            ", runningHours=" + getRunningHours() +
            ", powerOutput=" + getPowerOutput() +
            ", averageRpm=" + getAverageRpm() +
            "}";
    }
}
