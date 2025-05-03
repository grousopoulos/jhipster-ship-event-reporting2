package com.falaisia.ship.eventreporting2.service.dto;

import com.falaisia.ship.eventreporting2.domain.enumeration.IceClassPolarCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.MonitoringMethodCode;
import com.falaisia.ship.eventreporting2.domain.enumeration.ShipType;
import com.falaisia.ship.eventreporting2.domain.enumeration.TechnicalEfficiencyCode;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.falaisia.ship.eventreporting2.domain.Ship} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String callSign;

    private IceClassPolarCode iceClassPolarCode;

    private TechnicalEfficiencyCode technicalEfficiencyCode;

    private ShipType shipType;

    private MonitoringMethodCode monitoringMethodCode;

    private CountryDTO ownerCountry;

    private FlagDTO flag;

    private ClassificationSocietyDTO classificationSociety;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public IceClassPolarCode getIceClassPolarCode() {
        return iceClassPolarCode;
    }

    public void setIceClassPolarCode(IceClassPolarCode iceClassPolarCode) {
        this.iceClassPolarCode = iceClassPolarCode;
    }

    public TechnicalEfficiencyCode getTechnicalEfficiencyCode() {
        return technicalEfficiencyCode;
    }

    public void setTechnicalEfficiencyCode(TechnicalEfficiencyCode technicalEfficiencyCode) {
        this.technicalEfficiencyCode = technicalEfficiencyCode;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public MonitoringMethodCode getMonitoringMethodCode() {
        return monitoringMethodCode;
    }

    public void setMonitoringMethodCode(MonitoringMethodCode monitoringMethodCode) {
        this.monitoringMethodCode = monitoringMethodCode;
    }

    public CountryDTO getOwnerCountry() {
        return ownerCountry;
    }

    public void setOwnerCountry(CountryDTO ownerCountry) {
        this.ownerCountry = ownerCountry;
    }

    public FlagDTO getFlag() {
        return flag;
    }

    public void setFlag(FlagDTO flag) {
        this.flag = flag;
    }

    public ClassificationSocietyDTO getClassificationSociety() {
        return classificationSociety;
    }

    public void setClassificationSociety(ClassificationSocietyDTO classificationSociety) {
        this.classificationSociety = classificationSociety;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipDTO)) {
            return false;
        }

        ShipDTO shipDTO = (ShipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", callSign='" + getCallSign() + "'" +
            ", iceClassPolarCode='" + getIceClassPolarCode() + "'" +
            ", technicalEfficiencyCode='" + getTechnicalEfficiencyCode() + "'" +
            ", shipType='" + getShipType() + "'" +
            ", monitoringMethodCode='" + getMonitoringMethodCode() + "'" +
            ", ownerCountry=" + getOwnerCountry() +
            ", flag=" + getFlag() +
            ", classificationSociety=" + getClassificationSociety() +
            "}";
    }
}
