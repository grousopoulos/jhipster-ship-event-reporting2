import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getEventReports } from 'app/entities/event-report/event-report.reducer';
import { createEntity, getEntity, reset, updateEntity } from './machinery-operation-line.reducer';

export const MachineryOperationLineUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const eventReports = useAppSelector(state => state.eventReport.entities);
  const machineryOperationLineEntity = useAppSelector(state => state.machineryOperationLine.entity);
  const loading = useAppSelector(state => state.machineryOperationLine.loading);
  const updating = useAppSelector(state => state.machineryOperationLine.updating);
  const updateSuccess = useAppSelector(state => state.machineryOperationLine.updateSuccess);

  const handleClose = () => {
    navigate('/machinery-operation-line');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEventReports({}));
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
    if (values.runningHours !== undefined && typeof values.runningHours !== 'number') {
      values.runningHours = Number(values.runningHours);
    }
    if (values.powerOutput !== undefined && typeof values.powerOutput !== 'number') {
      values.powerOutput = Number(values.powerOutput);
    }
    if (values.averageRpm !== undefined && typeof values.averageRpm !== 'number') {
      values.averageRpm = Number(values.averageRpm);
    }

    const entity = {
      ...machineryOperationLineEntity,
      ...values,
      eventReport: eventReports.find(it => it.id.toString() === values.eventReport?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...machineryOperationLineEntity,
          eventReport: machineryOperationLineEntity?.eventReport?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="jhipsterShipEventReporting2App.machineryOperationLine.home.createOrEditLabel"
            data-cy="MachineryOperationLineCreateUpdateHeading"
          >
            <Translate contentKey="jhipsterShipEventReporting2App.machineryOperationLine.home.createOrEditLabel">
              Create or edit a MachineryOperationLine
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
                  id="machinery-operation-line-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.machineryOperationLine.runningHours')}
                id="machinery-operation-line-runningHours"
                name="runningHours"
                data-cy="runningHours"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.machineryOperationLine.powerOutput')}
                id="machinery-operation-line-powerOutput"
                name="powerOutput"
                data-cy="powerOutput"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.machineryOperationLine.averageRpm')}
                id="machinery-operation-line-averageRpm"
                name="averageRpm"
                data-cy="averageRpm"
                type="text"
              />
              <ValidatedField
                id="machinery-operation-line-eventReport"
                name="eventReport"
                data-cy="eventReport"
                label={translate('jhipsterShipEventReporting2App.machineryOperationLine.eventReport')}
                type="select"
                required
              >
                <option value="" key="0" />
                {eventReports
                  ? eventReports.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/machinery-operation-line" replace color="info">
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

export default MachineryOperationLineUpdate;
