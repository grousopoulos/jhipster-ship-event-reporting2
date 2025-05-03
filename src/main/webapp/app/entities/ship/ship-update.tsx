import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCountries } from 'app/entities/country/country.reducer';
import { getEntities as getFlags } from 'app/entities/flag/flag.reducer';
import { getEntities as getClassificationSocieties } from 'app/entities/classification-society/classification-society.reducer';
import { IceClassPolarCode } from 'app/shared/model/enumerations/ice-class-polar-code.model';
import { TechnicalEfficiencyCode } from 'app/shared/model/enumerations/technical-efficiency-code.model';
import { ShipType } from 'app/shared/model/enumerations/ship-type.model';
import { MonitoringMethodCode } from 'app/shared/model/enumerations/monitoring-method-code.model';
import { createEntity, getEntity, updateEntity } from './ship.reducer';

export const ShipUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const countries = useAppSelector(state => state.country.entities);
  const flags = useAppSelector(state => state.flag.entities);
  const classificationSocieties = useAppSelector(state => state.classificationSociety.entities);
  const shipEntity = useAppSelector(state => state.ship.entity);
  const loading = useAppSelector(state => state.ship.loading);
  const updating = useAppSelector(state => state.ship.updating);
  const updateSuccess = useAppSelector(state => state.ship.updateSuccess);
  const iceClassPolarCodeValues = Object.keys(IceClassPolarCode);
  const technicalEfficiencyCodeValues = Object.keys(TechnicalEfficiencyCode);
  const shipTypeValues = Object.keys(ShipType);
  const monitoringMethodCodeValues = Object.keys(MonitoringMethodCode);

  const handleClose = () => {
    navigate('/ship');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getCountries({}));
    dispatch(getFlags({}));
    dispatch(getClassificationSocieties({}));
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

    const entity = {
      ...shipEntity,
      ...values,
      ownerCountry: countries.find(it => it.id.toString() === values.ownerCountry?.toString()),
      flag: flags.find(it => it.id.toString() === values.flag?.toString()),
      classificationSociety: classificationSocieties.find(it => it.id.toString() === values.classificationSociety?.toString()),
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
          iceClassPolarCode: 'IA',
          technicalEfficiencyCode: 'EEDI',
          shipType: 'BULK',
          monitoringMethodCode: 'BDN',
          ...shipEntity,
          ownerCountry: shipEntity?.ownerCountry?.id,
          flag: shipEntity?.flag?.id,
          classificationSociety: shipEntity?.classificationSociety?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterShipEventReporting2App.ship.home.createOrEditLabel" data-cy="ShipCreateUpdateHeading">
            <Translate contentKey="jhipsterShipEventReporting2App.ship.home.createOrEditLabel">Create or edit a Ship</Translate>
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
                  id="ship-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.ship.name')}
                id="ship-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.ship.callSign')}
                id="ship-callSign"
                name="callSign"
                data-cy="callSign"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.ship.iceClassPolarCode')}
                id="ship-iceClassPolarCode"
                name="iceClassPolarCode"
                data-cy="iceClassPolarCode"
                type="select"
              >
                {iceClassPolarCodeValues.map(iceClassPolarCode => (
                  <option value={iceClassPolarCode} key={iceClassPolarCode}>
                    {translate(`jhipsterShipEventReporting2App.IceClassPolarCode.${iceClassPolarCode}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.ship.technicalEfficiencyCode')}
                id="ship-technicalEfficiencyCode"
                name="technicalEfficiencyCode"
                data-cy="technicalEfficiencyCode"
                type="select"
              >
                {technicalEfficiencyCodeValues.map(technicalEfficiencyCode => (
                  <option value={technicalEfficiencyCode} key={technicalEfficiencyCode}>
                    {translate(`jhipsterShipEventReporting2App.TechnicalEfficiencyCode.${technicalEfficiencyCode}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.ship.shipType')}
                id="ship-shipType"
                name="shipType"
                data-cy="shipType"
                type="select"
              >
                {shipTypeValues.map(shipType => (
                  <option value={shipType} key={shipType}>
                    {translate(`jhipsterShipEventReporting2App.ShipType.${shipType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.ship.monitoringMethodCode')}
                id="ship-monitoringMethodCode"
                name="monitoringMethodCode"
                data-cy="monitoringMethodCode"
                type="select"
              >
                {monitoringMethodCodeValues.map(monitoringMethodCode => (
                  <option value={monitoringMethodCode} key={monitoringMethodCode}>
                    {translate(`jhipsterShipEventReporting2App.MonitoringMethodCode.${monitoringMethodCode}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="ship-ownerCountry"
                name="ownerCountry"
                data-cy="ownerCountry"
                label={translate('jhipsterShipEventReporting2App.ship.ownerCountry')}
                type="select"
              >
                <option value="" key="0" />
                {countries
                  ? countries.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="ship-flag"
                name="flag"
                data-cy="flag"
                label={translate('jhipsterShipEventReporting2App.ship.flag')}
                type="select"
              >
                <option value="" key="0" />
                {flags
                  ? flags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="ship-classificationSociety"
                name="classificationSociety"
                data-cy="classificationSociety"
                label={translate('jhipsterShipEventReporting2App.ship.classificationSociety')}
                type="select"
              >
                <option value="" key="0" />
                {classificationSocieties
                  ? classificationSocieties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ship" replace color="info">
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

export default ShipUpdate;
