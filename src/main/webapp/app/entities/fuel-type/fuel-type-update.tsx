import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { FuelTypeCode } from 'app/shared/model/enumerations/fuel-type-code.model';
import { createEntity, getEntity, reset, updateEntity } from './fuel-type.reducer';

export const FuelTypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fuelTypeEntity = useAppSelector(state => state.fuelType.entity);
  const loading = useAppSelector(state => state.fuelType.loading);
  const updating = useAppSelector(state => state.fuelType.updating);
  const updateSuccess = useAppSelector(state => state.fuelType.updateSuccess);
  const fuelTypeCodeValues = Object.keys(FuelTypeCode);

  const handleClose = () => {
    navigate(`/fuel-type${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
    if (values.carbonFactory !== undefined && typeof values.carbonFactory !== 'number') {
      values.carbonFactory = Number(values.carbonFactory);
    }

    const entity = {
      ...fuelTypeEntity,
      ...values,
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
          fuelTypeCode: 'MDO',
          ...fuelTypeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterShipEventReporting2App.fuelType.home.createOrEditLabel" data-cy="FuelTypeCreateUpdateHeading">
            <Translate contentKey="jhipsterShipEventReporting2App.fuelType.home.createOrEditLabel">Create or edit a FuelType</Translate>
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
                  id="fuel-type-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.fuelType.name')}
                id="fuel-type-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.fuelType.fuelTypeCode')}
                id="fuel-type-fuelTypeCode"
                name="fuelTypeCode"
                data-cy="fuelTypeCode"
                type="select"
              >
                {fuelTypeCodeValues.map(fuelTypeCode => (
                  <option value={fuelTypeCode} key={fuelTypeCode}>
                    {translate(`jhipsterShipEventReporting2App.FuelTypeCode.${fuelTypeCode}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.fuelType.carbonFactory')}
                id="fuel-type-carbonFactory"
                name="carbonFactory"
                data-cy="carbonFactory"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/fuel-type" replace color="info">
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

export default FuelTypeUpdate;
