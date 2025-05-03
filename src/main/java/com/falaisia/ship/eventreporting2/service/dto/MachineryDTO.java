package com.falaisia.ship.eventreporting2.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.falaisia.ship.eventreporting2.domain.Machinery} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MachineryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private ShipDTO ship;

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
        if (!(o instanceof MachineryDTO)) {
            return false;
        }

        MachineryDTO machineryDTO = (MachineryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, machineryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MachineryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ship=" + getShip() +
            "}";
    }
}
