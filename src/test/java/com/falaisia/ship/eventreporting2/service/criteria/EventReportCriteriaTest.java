package com.falaisia.ship.eventreporting2.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EventReportCriteriaTest {

    @Test
    void newEventReportCriteriaHasAllFiltersNullTest() {
        var eventReportCriteria = new EventReportCriteria();
        assertThat(eventReportCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void eventReportCriteriaFluentMethodsCreatesFiltersTest() {
        var eventReportCriteria = new EventReportCriteria();

        setAllFilters(eventReportCriteria);

        assertThat(eventReportCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void eventReportCriteriaCopyCreatesNullFilterTest() {
        var eventReportCriteria = new EventReportCriteria();
        var copy = eventReportCriteria.copy();

        assertThat(eventReportCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(eventReportCriteria)
        );
    }

    @Test
    void eventReportCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var eventReportCriteria = new EventReportCriteria();
        setAllFilters(eventReportCriteria);

        var copy = eventReportCriteria.copy();

        assertThat(eventReportCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(eventReportCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var eventReportCriteria = new EventReportCriteria();

        assertThat(eventReportCriteria).hasToString("EventReportCriteria{}");
    }

    private static void setAllFilters(EventReportCriteria eventReportCriteria) {
        eventReportCriteria.id();
        eventReportCriteria.documentDateAndTime();
        eventReportCriteria.speedGps();
        eventReportCriteria.documentDisplayNumber();
        eventReportCriteria.leg();
        eventReportCriteria.distanceTravelled();
        eventReportCriteria.hoursUnderway();
        eventReportCriteria.eventStatus();
        eventReportCriteria.loadingCondition();
        eventReportCriteria.cargoCarried();
        eventReportCriteria.coordinatesLatitude();
        eventReportCriteria.coordinatesLongitude();
        eventReportCriteria.shipsHeading();
        eventReportCriteria.beaufortNo();
        eventReportCriteria.weatherCondition();
        eventReportCriteria.swell();
        eventReportCriteria.voyageId();
        eventReportCriteria.linesId();
        eventReportCriteria.operationLinesId();
        eventReportCriteria.distinct();
    }

    private static Condition<EventReportCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDocumentDateAndTime()) &&
                condition.apply(criteria.getSpeedGps()) &&
                condition.apply(criteria.getDocumentDisplayNumber()) &&
                condition.apply(criteria.getLeg()) &&
                condition.apply(criteria.getDistanceTravelled()) &&
                condition.apply(criteria.getHoursUnderway()) &&
                condition.apply(criteria.getEventStatus()) &&
                condition.apply(criteria.getLoadingCondition()) &&
                condition.apply(criteria.getCargoCarried()) &&
                condition.apply(criteria.getCoordinatesLatitude()) &&
                condition.apply(criteria.getCoordinatesLongitude()) &&
                condition.apply(criteria.getShipsHeading()) &&
                condition.apply(criteria.getBeaufortNo()) &&
                condition.apply(criteria.getWeatherCondition()) &&
                condition.apply(criteria.getSwell()) &&
                condition.apply(criteria.getVoyageId()) &&
                condition.apply(criteria.getLinesId()) &&
                condition.apply(criteria.getOperationLinesId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EventReportCriteria> copyFiltersAre(EventReportCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDocumentDateAndTime(), copy.getDocumentDateAndTime()) &&
                condition.apply(criteria.getSpeedGps(), copy.getSpeedGps()) &&
                condition.apply(criteria.getDocumentDisplayNumber(), copy.getDocumentDisplayNumber()) &&
                condition.apply(criteria.getLeg(), copy.getLeg()) &&
                condition.apply(criteria.getDistanceTravelled(), copy.getDistanceTravelled()) &&
                condition.apply(criteria.getHoursUnderway(), copy.getHoursUnderway()) &&
                condition.apply(criteria.getEventStatus(), copy.getEventStatus()) &&
                condition.apply(criteria.getLoadingCondition(), copy.getLoadingCondition()) &&
                condition.apply(criteria.getCargoCarried(), copy.getCargoCarried()) &&
                condition.apply(criteria.getCoordinatesLatitude(), copy.getCoordinatesLatitude()) &&
                condition.apply(criteria.getCoordinatesLongitude(), copy.getCoordinatesLongitude()) &&
                condition.apply(criteria.getShipsHeading(), copy.getShipsHeading()) &&
                condition.apply(criteria.getBeaufortNo(), copy.getBeaufortNo()) &&
                condition.apply(criteria.getWeatherCondition(), copy.getWeatherCondition()) &&
                condition.apply(criteria.getSwell(), copy.getSwell()) &&
                condition.apply(criteria.getVoyageId(), copy.getVoyageId()) &&
                condition.apply(criteria.getLinesId(), copy.getLinesId()) &&
                condition.apply(criteria.getOperationLinesId(), copy.getOperationLinesId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
