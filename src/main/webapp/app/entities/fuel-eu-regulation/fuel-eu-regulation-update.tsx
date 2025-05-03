import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './fuel-eu-regulation.reducer';

export const FuelEuRegulationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fuelEuRegulationEntity = useAppSelector(state => state.fuelEuRegulation.entity);
  const loading = useAppSelector(state => state.fuelEuRegulation.loading);
  const updating = useAppSelector(state => state.fuelEuRegulation.updating);
  const updateSuccess = useAppSelector(state => state.fuelEuRegulation.updateSuccess);

  const handleClose = () => {
    navigate('/fuel-eu-regulation');
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
    if (values.year !== undefined && typeof values.year !== 'number') {
      values.year = Number(values.year);
    }
    if (values.co2Gwp !== undefined && typeof values.co2Gwp !== 'number') {
      values.co2Gwp = Number(values.co2Gwp);
    }
    if (values.methaneGwp !== undefined && typeof values.methaneGwp !== 'number') {
      values.methaneGwp = Number(values.methaneGwp);
    }
    if (values.nitrousGwp !== undefined && typeof values.nitrousGwp !== 'number') {
      values.nitrousGwp = Number(values.nitrousGwp);
    }
    if (values.targetIntensity !== undefined && typeof values.targetIntensity !== 'number') {
      values.targetIntensity = Number(values.targetIntensity);
    }
    if (values.baselineIntensity !== undefined && typeof values.baselineIntensity !== 'number') {
      values.baselineIntensity = Number(values.baselineIntensity);
    }
    if (values.reductionFactorPercent !== undefined && typeof values.reductionFactorPercent !== 'number') {
      values.reductionFactorPercent = Number(values.reductionFactorPercent);
    }
    if (values.vlsfoEnergyContentPerTonMj !== undefined && typeof values.vlsfoEnergyContentPerTonMj !== 'number') {
      values.vlsfoEnergyContentPerTonMj = Number(values.vlsfoEnergyContentPerTonMj);
    }
    if (values.vlsfoPenaltyEurPerTon !== undefined && typeof values.vlsfoPenaltyEurPerTon !== 'number') {
      values.vlsfoPenaltyEurPerTon = Number(values.vlsfoPenaltyEurPerTon);
    }
    if (values.energyAllowanceMultiplier !== undefined && typeof values.energyAllowanceMultiplier !== 'number') {
      values.energyAllowanceMultiplier = Number(values.energyAllowanceMultiplier);
    }
    if (values.nonBioFuelRewardFactor !== undefined && typeof values.nonBioFuelRewardFactor !== 'number') {
      values.nonBioFuelRewardFactor = Number(values.nonBioFuelRewardFactor);
    }

    const entity = {
      ...fuelEuRegulationEntity,
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
          ...fuelEuRegulationEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterShipEventReporting2App.fuelEuRegulation.home.createOrEditLabel" data-cy="FuelEuRegulationCreateUpdateHeading">
            <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.home.createOrEditLabel">
              Create or edit a FuelEuRegulation
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
                  id="fuel-eu-regulation-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.fuelEuRegulation.year')}
                id="fuel-eu-regulation-year"
                name="year"
                data-cy="year"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.fuelEuRegulation.co2Gwp')}
                id="fuel-eu-regulation-co2Gwp"
                name="co2Gwp"
                data-cy="co2Gwp"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.fuelEuRegulation.methaneGwp')}
                id="fuel-eu-regulation-methaneGwp"
                name="methaneGwp"
                data-cy="methaneGwp"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.fuelEuRegulation.nitrousGwp')}
                id="fuel-eu-regulation-nitrousGwp"
                name="nitrousGwp"
                data-cy="nitrousGwp"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.fuelEuRegulation.targetIntensity')}
                id="fuel-eu-regulation-targetIntensity"
                name="targetIntensity"
                data-cy="targetIntensity"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.fuelEuRegulation.baselineIntensity')}
                id="fuel-eu-regulation-baselineIntensity"
                name="baselineIntensity"
                data-cy="baselineIntensity"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.fuelEuRegulation.reductionFactorPercent')}
                id="fuel-eu-regulation-reductionFactorPercent"
                name="reductionFactorPercent"
                data-cy="reductionFactorPercent"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.fuelEuRegulation.vlsfoEnergyContentPerTonMj')}
                id="fuel-eu-regulation-vlsfoEnergyContentPerTonMj"
                name="vlsfoEnergyContentPerTonMj"
                data-cy="vlsfoEnergyContentPerTonMj"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.fuelEuRegulation.vlsfoPenaltyEurPerTon')}
                id="fuel-eu-regulation-vlsfoPenaltyEurPerTon"
                name="vlsfoPenaltyEurPerTon"
                data-cy="vlsfoPenaltyEurPerTon"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.fuelEuRegulation.energyAllowanceMultiplier')}
                id="fuel-eu-regulation-energyAllowanceMultiplier"
                name="energyAllowanceMultiplier"
                data-cy="energyAllowanceMultiplier"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.fuelEuRegulation.nonBioFuelRewardFactor')}
                id="fuel-eu-regulation-nonBioFuelRewardFactor"
                name="nonBioFuelRewardFactor"
                data-cy="nonBioFuelRewardFactor"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/fuel-eu-regulation" replace color="info">
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

export default FuelEuRegulationUpdate;
