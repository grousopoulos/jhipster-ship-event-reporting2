import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getEventReports } from 'app/entities/event-report/event-report.reducer';
import { getEntities as getFuelTypes } from 'app/entities/fuel-type/fuel-type.reducer';
import { UnitOfMeasure } from 'app/shared/model/enumerations/unit-of-measure.model';
import { Co2EmissionSourceTypeCode } from 'app/shared/model/enumerations/co-2-emission-source-type-code.model';
import { PortActivityCode } from 'app/shared/model/enumerations/port-activity-code.model';
import { DiffCriterionCode } from 'app/shared/model/enumerations/diff-criterion-code.model';
import { createEntity, getEntity, reset, updateEntity } from './consumption-line.reducer';

export const ConsumptionLineUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const eventReports = useAppSelector(state => state.eventReport.entities);
  const fuelTypes = useAppSelector(state => state.fuelType.entities);
  const consumptionLineEntity = useAppSelector(state => state.consumptionLine.entity);
  const loading = useAppSelector(state => state.consumptionLine.loading);
  const updating = useAppSelector(state => state.consumptionLine.updating);
  const updateSuccess = useAppSelector(state => state.consumptionLine.updateSuccess);
  const unitOfMeasureValues = Object.keys(UnitOfMeasure);
  const co2EmissionSourceTypeCodeValues = Object.keys(Co2EmissionSourceTypeCode);
  const portActivityCodeValues = Object.keys(PortActivityCode);
  const diffCriterionCodeValues = Object.keys(DiffCriterionCode);

  const handleClose = () => {
    navigate('/consumption-line');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getEventReports({}));
    dispatch(getFuelTypes({}));
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
    if (values.quantity !== undefined && typeof values.quantity !== 'number') {
      values.quantity = Number(values.quantity);
    }
    if (values.lowerCalorificValue !== undefined && typeof values.lowerCalorificValue !== 'number') {
      values.lowerCalorificValue = Number(values.lowerCalorificValue);
    }
    if (values.sulphurContent !== undefined && typeof values.sulphurContent !== 'number') {
      values.sulphurContent = Number(values.sulphurContent);
    }
    if (values.density !== undefined && typeof values.density !== 'number') {
      values.density = Number(values.density);
    }
    if (values.viscosity !== undefined && typeof values.viscosity !== 'number') {
      values.viscosity = Number(values.viscosity);
    }
    if (values.waterContent !== undefined && typeof values.waterContent !== 'number') {
      values.waterContent = Number(values.waterContent);
    }

    const entity = {
      ...consumptionLineEntity,
      ...values,
      eventReport: eventReports.find(it => it.id.toString() === values.eventReport?.toString()),
      fuelType: fuelTypes.find(it => it.id.toString() === values.fuelType?.toString()),
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
          unitOfMeasure: 'M_TONNES',
          co2EmissionSourceTypeCode: 'AUX_ENGINE',
          portActivityCode: 'AT_BERTH',
          diffCriterionCode: 'ON_BALLAST',
          ...consumptionLineEntity,
          eventReport: consumptionLineEntity?.eventReport?.id,
          fuelType: consumptionLineEntity?.fuelType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterShipEventReporting2App.consumptionLine.home.createOrEditLabel" data-cy="ConsumptionLineCreateUpdateHeading">
            <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.home.createOrEditLabel">
              Create or edit a ConsumptionLine
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
                  id="consumption-line-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.consumptionLine.quantity')}
                id="consumption-line-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.consumptionLine.unitOfMeasure')}
                id="consumption-line-unitOfMeasure"
                name="unitOfMeasure"
                data-cy="unitOfMeasure"
                type="select"
              >
                {unitOfMeasureValues.map(unitOfMeasure => (
                  <option value={unitOfMeasure} key={unitOfMeasure}>
                    {translate(`jhipsterShipEventReporting2App.UnitOfMeasure.${unitOfMeasure}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.consumptionLine.co2EmissionSourceTypeCode')}
                id="consumption-line-co2EmissionSourceTypeCode"
                name="co2EmissionSourceTypeCode"
                data-cy="co2EmissionSourceTypeCode"
                type="select"
              >
                {co2EmissionSourceTypeCodeValues.map(co2EmissionSourceTypeCode => (
                  <option value={co2EmissionSourceTypeCode} key={co2EmissionSourceTypeCode}>
                    {translate(`jhipsterShipEventReporting2App.Co2EmissionSourceTypeCode.${co2EmissionSourceTypeCode}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.consumptionLine.lowerCalorificValue')}
                id="consumption-line-lowerCalorificValue"
                name="lowerCalorificValue"
                data-cy="lowerCalorificValue"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.consumptionLine.sulphurContent')}
                id="consumption-line-sulphurContent"
                name="sulphurContent"
                data-cy="sulphurContent"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.consumptionLine.density')}
                id="consumption-line-density"
                name="density"
                data-cy="density"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.consumptionLine.viscosity')}
                id="consumption-line-viscosity"
                name="viscosity"
                data-cy="viscosity"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.consumptionLine.waterContent')}
                id="consumption-line-waterContent"
                name="waterContent"
                data-cy="waterContent"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.consumptionLine.portActivityCode')}
                id="consumption-line-portActivityCode"
                name="portActivityCode"
                data-cy="portActivityCode"
                type="select"
              >
                {portActivityCodeValues.map(portActivityCode => (
                  <option value={portActivityCode} key={portActivityCode}>
                    {translate(`jhipsterShipEventReporting2App.PortActivityCode.${portActivityCode}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.consumptionLine.diffCriterionCode')}
                id="consumption-line-diffCriterionCode"
                name="diffCriterionCode"
                data-cy="diffCriterionCode"
                type="select"
              >
                {diffCriterionCodeValues.map(diffCriterionCode => (
                  <option value={diffCriterionCode} key={diffCriterionCode}>
                    {translate(`jhipsterShipEventReporting2App.DiffCriterionCode.${diffCriterionCode}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="consumption-line-eventReport"
                name="eventReport"
                data-cy="eventReport"
                label={translate('jhipsterShipEventReporting2App.consumptionLine.eventReport')}
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
              <ValidatedField
                id="consumption-line-fuelType"
                name="fuelType"
                data-cy="fuelType"
                label={translate('jhipsterShipEventReporting2App.consumptionLine.fuelType')}
                type="select"
                required
              >
                <option value="" key="0" />
                {fuelTypes
                  ? fuelTypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/consumption-line" replace color="info">
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

export default ConsumptionLineUpdate;
