package com.falaisia.ship.eventreporting2.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.falaisia.ship.eventreporting2.domain.Voyage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VoyageDTO implements Serializable {

    private Long id;

    @NotNull
    private String number;

    @NotNull
    private ShipDTO ship;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ShipDTO getShip() {
        return ship;
    }

    public void setShip(ShipDTO ship) {
        this.ship = ship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoyageDTO)) {
            return false;
        }

        VoyageDTO voyageDTO = (VoyageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, voyageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoyageDTO{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", ship=" + getShip() +
            "}";
    }
}
