package com.falaisia.ship.eventreporting2.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.falaisia.ship.eventreporting2.domain.BunkerReceivedNote} entity. This class is used
 * in {@link com.falaisia.ship.eventreporting2.web.rest.BunkerReceivedNoteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bunker-received-notes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BunkerReceivedNoteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter documentDateAndTime;

    private StringFilter documentDisplayNumber;

    private LongFilter voyageId;

    private LongFilter linesId;

    private Boolean distinct;

    public BunkerReceivedNoteCriteria() {}

    public BunkerReceivedNoteCriteria(BunkerReceivedNoteCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.documentDateAndTime = other.optionalDocumentDateAndTime().map(ZonedDateTimeFilter::copy).orElse(null);
        this.documentDisplayNumber = other.optionalDocumentDisplayNumber().map(StringFilter::copy).orElse(null);
        this.voyageId = other.optionalVoyageId().map(LongFilter::copy).orElse(null);
        this.linesId = other.optionalLinesId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public BunkerReceivedNoteCriteria copy() {
        return new BunkerReceivedNoteCriteria(this);
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

    public ZonedDateTimeFilter getDocumentDateAndTime() {
        return documentDateAndTime;
    }

    public Optional<ZonedDateTimeFilter> optionalDocumentDateAndTime() {
        return Optional.ofNullable(documentDateAndTime);
    }

    public ZonedDateTimeFilter documentDateAndTime() {
        if (documentDateAndTime == null) {
            setDocumentDateAndTime(new ZonedDateTimeFilter());
        }
        return documentDateAndTime;
    }

    public void setDocumentDateAndTime(ZonedDateTimeFilter documentDateAndTime) {
        this.documentDateAndTime = documentDateAndTime;
    }

    public StringFilter getDocumentDisplayNumber() {
        return documentDisplayNumber;
    }

    public Optional<StringFilter> optionalDocumentDisplayNumber() {
        return Optional.ofNullable(documentDisplayNumber);
    }

    public StringFilter documentDisplayNumber() {
        if (documentDisplayNumber == null) {
            setDocumentDisplayNumber(new StringFilter());
        }
        return documentDisplayNumber;
    }

    public void setDocumentDisplayNumber(StringFilter documentDisplayNumber) {
        this.documentDisplayNumber = documentDisplayNumber;
    }

    public LongFilter getVoyageId() {
        return voyageId;
    }

    public Optional<LongFilter> optionalVoyageId() {
        return Optional.ofNullable(voyageId);
    }

    public LongFilter voyageId() {
        if (voyageId == null) {
            setVoyageId(new LongFilter());
        }
        return voyageId;
    }

    public void setVoyageId(LongFilter voyageId) {
        this.voyageId = voyageId;
    }

    public LongFilter getLinesId() {
        return linesId;
    }

    public Optional<LongFilter> optionalLinesId() {
        return Optional.ofNullable(linesId);
    }

    public LongFilter linesId() {
        if (linesId == null) {
            setLinesId(new LongFilter());
        }
        return linesId;
    }

    public void setLinesId(LongFilter linesId) {
        this.linesId = linesId;
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
        final BunkerReceivedNoteCriteria that = (BunkerReceivedNoteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(documentDateAndTime, that.documentDateAndTime) &&
            Objects.equals(documentDisplayNumber, that.documentDisplayNumber) &&
            Objects.equals(voyageId, that.voyageId) &&
            Objects.equals(linesId, that.linesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentDateAndTime, documentDisplayNumber, voyageId, linesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BunkerReceivedNoteCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDocumentDateAndTime().map(f -> "documentDateAndTime=" + f + ", ").orElse("") +
            optionalDocumentDisplayNumber().map(f -> "documentDisplayNumber=" + f + ", ").orElse("") +
            optionalVoyageId().map(f -> "voyageId=" + f + ", ").orElse("") +
            optionalLinesId().map(f -> "linesId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
