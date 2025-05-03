import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './event-report.reducer';

export const EventReportDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const eventReportEntity = useAppSelector(state => state.eventReport.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="eventReportDetailsHeading">
          <Translate contentKey="jhipsterShipEventReporting2App.eventReport.detail.title">EventReport</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.id}</dd>
          <dt>
            <span id="documentDateAndTime">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.documentDateAndTime">Document Date And Time</Translate>
            </span>
          </dt>
          <dd>
            {eventReportEntity.documentDateAndTime ? (
              <TextFormat value={eventReportEntity.documentDateAndTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="speedGps">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.speedGps">Speed Gps</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.speedGps}</dd>
          <dt>
            <span id="documentDisplayNumber">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.documentDisplayNumber">Document Display Number</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.documentDisplayNumber}</dd>
          <dt>
            <span id="leg">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.leg">Leg</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.leg}</dd>
          <dt>
            <span id="distanceTravelled">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.distanceTravelled">Distance Travelled</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.distanceTravelled}</dd>
          <dt>
            <span id="hoursUnderway">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.hoursUnderway">Hours Underway</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.hoursUnderway}</dd>
          <dt>
            <span id="eventStatus">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.eventStatus">Event Status</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.eventStatus}</dd>
          <dt>
            <span id="loadingCondition">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.loadingCondition">Loading Condition</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.loadingCondition}</dd>
          <dt>
            <span id="cargoCarried">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.cargoCarried">Cargo Carried</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.cargoCarried}</dd>
          <dt>
            <span id="coordinatesLatitude">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.coordinatesLatitude">Coordinates Latitude</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.coordinatesLatitude}</dd>
          <dt>
            <span id="coordinatesLongitude">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.coordinatesLongitude">Coordinates Longitude</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.coordinatesLongitude}</dd>
          <dt>
            <span id="shipsHeading">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.shipsHeading">Ships Heading</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.shipsHeading}</dd>
          <dt>
            <span id="beaufortNo">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.beaufortNo">Beaufort No</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.beaufortNo}</dd>
          <dt>
            <span id="weatherCondition">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.weatherCondition">Weather Condition</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.weatherCondition}</dd>
          <dt>
            <span id="swell">
              <Translate contentKey="jhipsterShipEventReporting2App.eventReport.swell">Swell</Translate>
            </span>
          </dt>
          <dd>{eventReportEntity.swell ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="jhipsterShipEventReporting2App.eventReport.voyage">Voyage</Translate>
          </dt>
          <dd>{eventReportEntity.voyage ? eventReportEntity.voyage.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/event-report" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/event-report/${eventReportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EventReportDetail;
