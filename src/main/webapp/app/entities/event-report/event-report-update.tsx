import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getVoyages } from 'app/entities/voyage/voyage.reducer';
import { EventStatus } from 'app/shared/model/enumerations/event-status.model';
import { LoadingCondition } from 'app/shared/model/enumerations/loading-condition.model';
import { createEntity, getEntity, updateEntity } from './event-report.reducer';

export const EventReportUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const voyages = useAppSelector(state => state.voyage.entities);
  const eventReportEntity = useAppSelector(state => state.eventReport.entity);
  const loading = useAppSelector(state => state.eventReport.loading);
  const updating = useAppSelector(state => state.eventReport.updating);
  const updateSuccess = useAppSelector(state => state.eventReport.updateSuccess);
  const eventStatusValues = Object.keys(EventStatus);
  const loadingConditionValues = Object.keys(LoadingCondition);

  const handleClose = () => {
    navigate('/event-report');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getVoyages({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.documentDateAndTime = convertDateTimeToServer(values.documentDateAndTime);
    if (values.speedGps !== undefined && typeof values.speedGps !== 'number') {
      values.speedGps = Number(values.speedGps);
    }
    if (values.leg !== undefined && typeof values.leg !== 'number') {
      values.leg = Number(values.leg);
    }
    if (values.distanceTravelled !== undefined && typeof values.distanceTravelled !== 'number') {
      values.distanceTravelled = Number(values.distanceTravelled);
    }
    if (values.hoursUnderway !== undefined && typeof values.hoursUnderway !== 'number') {
      values.hoursUnderway = Number(values.hoursUnderway);
    }
    if (values.cargoCarried !== undefined && typeof values.cargoCarried !== 'number') {
      values.cargoCarried = Number(values.cargoCarried);
    }
    if (values.beaufortNo !== undefined && typeof values.beaufortNo !== 'number') {
      values.beaufortNo = Number(values.beaufortNo);
    }

    const entity = {
      ...eventReportEntity,
      ...values,
      voyage: voyages.find(it => it.id.toString() === values.voyage?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          documentDateAndTime: displayDefaultDateTime(),
        }
      : {
          eventStatus: 'DEPARTURE',
          loadingCondition: 'LADEN',
          ...eventReportEntity,
          documentDateAndTime: convertDateTimeFromServer(eventReportEntity.documentDateAndTime),
          voyage: eventReportEntity?.voyage?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterShipEventReporting2App.eventReport.home.createOrEditLabel" data-cy="EventReportCreateUpdateHeading">
            <Translate contentKey="jhipsterShipEventReporting2App.eventReport.home.createOrEditLabel">
              Create or edit a EventReport
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="event-report-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.documentDateAndTime')}
                id="event-report-documentDateAndTime"
                name="documentDateAndTime"
                data-cy="documentDateAndTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.speedGps')}
                id="event-report-speedGps"
                name="speedGps"
                data-cy="speedGps"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.documentDisplayNumber')}
                id="event-report-documentDisplayNumber"
                name="documentDisplayNumber"
                data-cy="documentDisplayNumber"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.leg')}
                id="event-report-leg"
                name="leg"
                data-cy="leg"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.distanceTravelled')}
                id="event-report-distanceTravelled"
                name="distanceTravelled"
                data-cy="distanceTravelled"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.hoursUnderway')}
                id="event-report-hoursUnderway"
                name="hoursUnderway"
                data-cy="hoursUnderway"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.eventStatus')}
                id="event-report-eventStatus"
                name="eventStatus"
                data-cy="eventStatus"
                type="select"
              >
                {eventStatusValues.map(eventStatus => (
                  <option value={eventStatus} key={eventStatus}>
                    {translate(`jhipsterShipEventReporting2App.EventStatus.${eventStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.loadingCondition')}
                id="event-report-loadingCondition"
                name="loadingCondition"
                data-cy="loadingCondition"
                type="select"
              >
                {loadingConditionValues.map(loadingCondition => (
                  <option value={loadingCondition} key={loadingCondition}>
                    {translate(`jhipsterShipEventReporting2App.LoadingCondition.${loadingCondition}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.cargoCarried')}
                id="event-report-cargoCarried"
                name="cargoCarried"
                data-cy="cargoCarried"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.coordinatesLatitude')}
                id="event-report-coordinatesLatitude"
                name="coordinatesLatitude"
                data-cy="coordinatesLatitude"
                type="text"
                validate={{
                  pattern: {
                    value: /([0-8][0-9]|90)([0-5][0-9]|60)(N|S)/,
                    message: translate('entity.validation.pattern', { pattern: '([0-8][0-9]|90)([0-5][0-9]|60)(N|S)' }),
                  },
                }}
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.coordinatesLongitude')}
                id="event-report-coordinatesLongitude"
                name="coordinatesLongitude"
                data-cy="coordinatesLongitude"
                type="text"
                validate={{
                  pattern: {
                    value: /(0[0-9][0-9]|1[0-7][0-9]|180)([0-5][0-9]|60)(W|E)/,
                    message: translate('entity.validation.pattern', { pattern: '(0[0-9][0-9]|1[0-7][0-9]|180)([0-5][0-9]|60)(W|E)' }),
                  },
                }}
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.shipsHeading')}
                id="event-report-shipsHeading"
                name="shipsHeading"
                data-cy="shipsHeading"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.beaufortNo')}
                id="event-report-beaufortNo"
                name="beaufortNo"
                data-cy="beaufortNo"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.weatherCondition')}
                id="event-report-weatherCondition"
                name="weatherCondition"
                data-cy="weatherCondition"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.eventReport.swell')}
                id="event-report-swell"
                name="swell"
                data-cy="swell"
                check
                type="checkbox"
              />
              <ValidatedField
                id="event-report-voyage"
                name="voyage"
                data-cy="voyage"
                label={translate('jhipsterShipEventReporting2App.eventReport.voyage')}
                type="select"
                required
              >
                <option value="" key="0" />
                {voyages
                  ? voyages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/event-report" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EventReportUpdate;
