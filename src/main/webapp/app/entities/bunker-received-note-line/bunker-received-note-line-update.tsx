import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getBunkerReceivedNotes } from 'app/entities/bunker-received-note/bunker-received-note.reducer';
import { getEntities as getFuelTypes } from 'app/entities/fuel-type/fuel-type.reducer';
import { UnitOfMeasure } from 'app/shared/model/enumerations/unit-of-measure.model';
import { createEntity, getEntity, reset, updateEntity } from './bunker-received-note-line.reducer';

export const BunkerReceivedNoteLineUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bunkerReceivedNotes = useAppSelector(state => state.bunkerReceivedNote.entities);
  const fuelTypes = useAppSelector(state => state.fuelType.entities);
  const bunkerReceivedNoteLineEntity = useAppSelector(state => state.bunkerReceivedNoteLine.entity);
  const loading = useAppSelector(state => state.bunkerReceivedNoteLine.loading);
  const updating = useAppSelector(state => state.bunkerReceivedNoteLine.updating);
  const updateSuccess = useAppSelector(state => state.bunkerReceivedNoteLine.updateSuccess);
  const unitOfMeasureValues = Object.keys(UnitOfMeasure);

  const handleClose = () => {
    navigate('/bunker-received-note-line');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBunkerReceivedNotes({}));
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
      ...bunkerReceivedNoteLineEntity,
      ...values,
      bunkerReceivedNote: bunkerReceivedNotes.find(it => it.id.toString() === values.bunkerReceivedNote?.toString()),
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
          ...bunkerReceivedNoteLineEntity,
          bunkerReceivedNote: bunkerReceivedNoteLineEntity?.bunkerReceivedNote?.id,
          fuelType: bunkerReceivedNoteLineEntity?.fuelType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.home.createOrEditLabel"
            data-cy="BunkerReceivedNoteLineCreateUpdateHeading"
          >
            <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.home.createOrEditLabel">
              Create or edit a BunkerReceivedNoteLine
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
                  id="bunker-received-note-line-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.bunkerReceivedNoteLine.quantity')}
                id="bunker-received-note-line-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.bunkerReceivedNoteLine.unitOfMeasure')}
                id="bunker-received-note-line-unitOfMeasure"
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
                label={translate('jhipsterShipEventReporting2App.bunkerReceivedNoteLine.lowerCalorificValue')}
                id="bunker-received-note-line-lowerCalorificValue"
                name="lowerCalorificValue"
                data-cy="lowerCalorificValue"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.bunkerReceivedNoteLine.sulphurContent')}
                id="bunker-received-note-line-sulphurContent"
                name="sulphurContent"
                data-cy="sulphurContent"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.bunkerReceivedNoteLine.density')}
                id="bunker-received-note-line-density"
                name="density"
                data-cy="density"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.bunkerReceivedNoteLine.viscosity')}
                id="bunker-received-note-line-viscosity"
                name="viscosity"
                data-cy="viscosity"
                type="text"
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.bunkerReceivedNoteLine.waterContent')}
                id="bunker-received-note-line-waterContent"
                name="waterContent"
                data-cy="waterContent"
                type="text"
              />
              <ValidatedField
                id="bunker-received-note-line-bunkerReceivedNote"
                name="bunkerReceivedNote"
                data-cy="bunkerReceivedNote"
                label={translate('jhipsterShipEventReporting2App.bunkerReceivedNoteLine.bunkerReceivedNote')}
                type="select"
                required
              >
                <option value="" key="0" />
                {bunkerReceivedNotes
                  ? bunkerReceivedNotes.map(otherEntity => (
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
                id="bunker-received-note-line-fuelType"
                name="fuelType"
                data-cy="fuelType"
                label={translate('jhipsterShipEventReporting2App.bunkerReceivedNoteLine.fuelType')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bunker-received-note-line" replace color="info">
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

export default BunkerReceivedNoteLineUpdate;
