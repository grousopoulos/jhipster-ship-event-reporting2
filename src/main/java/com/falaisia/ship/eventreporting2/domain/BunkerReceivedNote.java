package com.falaisia.ship.eventreporting2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BunkerReceivedNote.
 */
@Entity
@Table(name = "bunker_received_note")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BunkerReceivedNote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "document_date_and_time", nullable = false)
    private ZonedDateTime documentDateAndTime;

    @Column(name = "document_display_number")
    private String documentDisplayNumber;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "ship" }, allowSetters = true)
    private Voyage voyage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bunkerReceivedNote")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "bunkerReceivedNote", "fuelType" }, allowSetters = true)
    private Set<BunkerReceivedNoteLine> lines = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BunkerReceivedNote id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDocumentDateAndTime() {
        return this.documentDateAndTime;
    }

    public BunkerReceivedNote documentDateAndTime(ZonedDateTime documentDateAndTime) {
        this.setDocumentDateAndTime(documentDateAndTime);
        return this;
    }

    public void setDocumentDateAndTime(ZonedDateTime documentDateAndTime) {
        this.documentDateAndTime = documentDateAndTime;
    }

    public String getDocumentDisplayNumber() {
        return this.documentDisplayNumber;
    }

    public BunkerReceivedNote documentDisplayNumber(String documentDisplayNumber) {
        this.setDocumentDisplayNumber(documentDisplayNumber);
        return this;
    }

    public void setDocumentDisplayNumber(String documentDisplayNumber) {
        this.documentDisplayNumber = documentDisplayNumber;
    }

    public Voyage getVoyage() {
        return this.voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public BunkerReceivedNote voyage(Voyage voyage) {
        this.setVoyage(voyage);
        return this;
    }

    public Set<BunkerReceivedNoteLine> getLines() {
        return this.lines;
    }

    public void setLines(Set<BunkerReceivedNoteLine> bunkerReceivedNoteLines) {
        if (this.lines != null) {
            this.lines.forEach(i -> i.setBunkerReceivedNote(null));
        }
        if (bunkerReceivedNoteLines != null) {
            bunkerReceivedNoteLines.forEach(i -> i.setBunkerReceivedNote(this));
        }
        this.lines = bunkerReceivedNoteLines;
    }

    public BunkerReceivedNote lines(Set<BunkerReceivedNoteLine> bunkerReceivedNoteLines) {
        this.setLines(bunkerReceivedNoteLines);
        return this;
    }

    public BunkerReceivedNote addLines(BunkerReceivedNoteLine bunkerReceivedNoteLine) {
        this.lines.add(bunkerReceivedNoteLine);
        bunkerReceivedNoteLine.setBunkerReceivedNote(this);
        return this;
    }

    public BunkerReceivedNote removeLines(BunkerReceivedNoteLine bunkerReceivedNoteLine) {
        this.lines.remove(bunkerReceivedNoteLine);
        bunkerReceivedNoteLine.setBunkerReceivedNote(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BunkerReceivedNote)) {
            return false;
        }
        return getId() != null && getId().equals(((BunkerReceivedNote) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BunkerReceivedNote{" +
            "id=" + getId() +
            ", documentDateAndTime='" + getDocumentDateAndTime() + "'" +
            ", documentDisplayNumber='" + getDocumentDisplayNumber() + "'" +
            "}";
    }
}
