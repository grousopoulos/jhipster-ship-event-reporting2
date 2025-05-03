import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getVoyages } from 'app/entities/voyage/voyage.reducer';
import { createEntity, getEntity, updateEntity } from './bunker-received-note.reducer';

export const BunkerReceivedNoteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const voyages = useAppSelector(state => state.voyage.entities);
  const bunkerReceivedNoteEntity = useAppSelector(state => state.bunkerReceivedNote.entity);
  const loading = useAppSelector(state => state.bunkerReceivedNote.loading);
  const updating = useAppSelector(state => state.bunkerReceivedNote.updating);
  const updateSuccess = useAppSelector(state => state.bunkerReceivedNote.updateSuccess);

  const handleClose = () => {
    navigate('/bunker-received-note');
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

    const entity = {
      ...bunkerReceivedNoteEntity,
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
          ...bunkerReceivedNoteEntity,
          documentDateAndTime: convertDateTimeFromServer(bunkerReceivedNoteEntity.documentDateAndTime),
          voyage: bunkerReceivedNoteEntity?.voyage?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jhipsterShipEventReporting2App.bunkerReceivedNote.home.createOrEditLabel" data-cy="BunkerReceivedNoteCreateUpdateHeading">
            <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNote.home.createOrEditLabel">
              Create or edit a BunkerReceivedNote
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
                  id="bunker-received-note-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.bunkerReceivedNote.documentDateAndTime')}
                id="bunker-received-note-documentDateAndTime"
                name="documentDateAndTime"
                data-cy="documentDateAndTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jhipsterShipEventReporting2App.bunkerReceivedNote.documentDisplayNumber')}
                id="bunker-received-note-documentDisplayNumber"
                name="documentDisplayNumber"
                data-cy="documentDisplayNumber"
                type="text"
              />
              <ValidatedField
                id="bunker-received-note-voyage"
                name="voyage"
                data-cy="voyage"
                label={translate('jhipsterShipEventReporting2App.bunkerReceivedNote.voyage')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bunker-received-note" replace color="info">
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

export default BunkerReceivedNoteUpdate;
