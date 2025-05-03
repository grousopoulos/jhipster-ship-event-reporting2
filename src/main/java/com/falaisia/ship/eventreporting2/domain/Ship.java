package com.falaisia.ship.eventreporting2.domain;

import com.falaisia.ship.eventreporting2.domain.enumeration.IceClassPolarCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.MonitoringMethodCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.ShipType;
import com.falaisia.ship.eventreporting2.domain.enumeration.TechnicalEfficiencyCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ship.
 */
@Entity
@Table(name = "ship")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "call_sign")
    private String callSign;

    @Enumerated(EnumType.STRING)
    @Column(name = "ice_class_polar_code")
    private IceClassPolarCode iceClassPolarCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "technical_efficiency_code")
    private TechnicalEfficiencyCode technicalEfficiencyCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "ship_type")
    private ShipType shipType;

    @Enumerated(EnumType.STRING)
    @Column(name = "monitoring_method_code")
    private MonitoringMethodCode monitoringMethodCode;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country ownerCountry;

    @ManyToOne(fetch = FetchType.LAZY)
    private Flag flag;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClassificationSociety classificationSociety;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ship id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Ship name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCallSign() {
        return this.callSign;
    }

    public Ship callSign(String callSign) {
        this.setCallSign(callSign);
        return this;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public IceClassPolarCode getIceClassPolarCode() {
        return this.iceClassPolarCode;
    }

    public Ship iceClassPolarCode(IceClassPolarCode iceClassPolarCode) {
        this.setIceClassPolarCode(iceClassPolarCode);
        return this;
    }

    public void setIceClassPolarCode(IceClassPolarCode iceClassPolarCode) {
        this.iceClassPolarCode = iceClassPolarCode;
    }

    public TechnicalEfficiencyCode getTechnicalEfficiencyCode() {
        return this.technicalEfficiencyCode;
    }

    public Ship technicalEfficiencyCode(TechnicalEfficiencyCode technicalEfficiencyCode) {
        this.setTechnicalEfficiencyCode(technicalEfficiencyCode);
        return this;
    }

    public void setTechnicalEfficiencyCode(TechnicalEfficiencyCode technicalEfficiencyCode) {
        this.technicalEfficiencyCode = technicalEfficiencyCode;
    }

    public ShipType getShipType() {
        return this.shipType;
    }

    public Ship shipType(ShipType shipType) {
        this.setShipType(shipType);
        return this;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public MonitoringMethodCode getMonitoringMethodCode() {
        return this.monitoringMethodCode;
    }

    public Ship monitoringMethodCode(MonitoringMethodCode monitoringMethodCode) {
        this.setMonitoringMethodCode(monitoringMethodCode);
        return this;
    }

    public void setMonitoringMethodCode(MonitoringMethodCode monitoringMethodCode) {
        this.monitoringMethodCode = monitoringMethodCode;
    }

    public Country getOwnerCountry() {
        return this.ownerCountry;
    }

    public void setOwnerCountry(Country country) {
        this.ownerCountry = country;
    }

    public Ship ownerCountry(Country country) {
        this.setOwnerCountry(country);
        return this;
    }

    public Flag getFlag() {
        return this.flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public Ship flag(Flag flag) {
        this.setFlag(flag);
        return this;
    }

    public ClassificationSociety getClassificationSociety() {
        return this.classificationSociety;
    }

    public void setClassificationSociety(ClassificationSociety classificationSociety) {
        this.classificationSociety = classificationSociety;
    }

    public Ship classificationSociety(ClassificationSociety classificationSociety) {
        this.setClassificationSociety(classificationSociety);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ship)) {
            return false;
        }
        return getId() != null && getId().equals(((Ship) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ship{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", callSign='" + getCallSign() + "'" +
            ", iceClassPolarCode='" + getIceClassPolarCode() + "'" +
            ", technicalEfficiencyCode='" + getTechnicalEfficiencyCode() + "'" +
            ", shipType='" + getShipType() + "'" +
            ", monitoringMethodCode='" + getMonitoringMethodCode() + "'" +
            "}";
    }
}
