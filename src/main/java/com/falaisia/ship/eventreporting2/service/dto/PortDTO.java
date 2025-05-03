package com.falaisia.ship.eventreporting2.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.falaisia.ship.eventreporting2.domain.Port} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PortDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Size(max = 10)
    private String unlocode;

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

    public String getUnlocode() {
        return unlocode;
    }

    public void setUnlocode(String unlocode) {
        this.unlocode = unlocode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortDTO)) {
            return false;
        }

        PortDTO portDTO = (PortDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, portDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PortDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", unlocode='" + getUnlocode() + "'" +
            "}";
    }
}
