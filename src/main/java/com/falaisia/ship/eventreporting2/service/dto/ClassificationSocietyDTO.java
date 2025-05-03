package com.falaisia.ship.eventreporting2.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.falaisia.ship.eventreporting2.domain.ClassificationSociety} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClassificationSocietyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String code;

    @NotNull
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassificationSocietyDTO)) {
            return false;
        }

        ClassificationSocietyDTO classificationSocietyDTO = (ClassificationSocietyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, classificationSocietyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassificationSocietyDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
