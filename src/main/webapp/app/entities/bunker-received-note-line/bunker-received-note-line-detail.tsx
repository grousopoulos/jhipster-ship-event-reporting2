import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bunker-received-note-line.reducer';

export const BunkerReceivedNoteLineDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bunkerReceivedNoteLineEntity = useAppSelector(state => state.bunkerReceivedNoteLine.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bunkerReceivedNoteLineDetailsHeading">
          <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.detail.title">BunkerReceivedNoteLine</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bunkerReceivedNoteLineEntity.id}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{bunkerReceivedNoteLineEntity.quantity}</dd>
          <dt>
            <span id="unitOfMeasure">
              <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.unitOfMeasure">Unit Of Measure</Translate>
            </span>
          </dt>
          <dd>{bunkerReceivedNoteLineEntity.unitOfMeasure}</dd>
          <dt>
            <span id="lowerCalorificValue">
              <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.lowerCalorificValue">
                Lower Calorific Value
              </Translate>
            </span>
          </dt>
          <dd>{bunkerReceivedNoteLineEntity.lowerCalorificValue}</dd>
          <dt>
            <span id="sulphurContent">
              <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.sulphurContent">Sulphur Content</Translate>
            </span>
          </dt>
          <dd>{bunkerReceivedNoteLineEntity.sulphurContent}</dd>
          <dt>
            <span id="density">
              <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.density">Density</Translate>
            </span>
          </dt>
          <dd>{bunkerReceivedNoteLineEntity.density}</dd>
          <dt>
            <span id="viscosity">
              <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.viscosity">Viscosity</Translate>
            </span>
          </dt>
          <dd>{bunkerReceivedNoteLineEntity.viscosity}</dd>
          <dt>
            <span id="waterContent">
              <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.waterContent">Water Content</Translate>
            </span>
          </dt>
          <dd>{bunkerReceivedNoteLineEntity.waterContent}</dd>
          <dt>
            <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.bunkerReceivedNote">
              Bunker Received Note
            </Translate>
          </dt>
          <dd>{bunkerReceivedNoteLineEntity.bunkerReceivedNote ? bunkerReceivedNoteLineEntity.bunkerReceivedNote.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.fuelType">Fuel Type</Translate>
          </dt>
          <dd>{bunkerReceivedNoteLineEntity.fuelType ? bunkerReceivedNoteLineEntity.fuelType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bunker-received-note-line" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bunker-received-note-line/${bunkerReceivedNoteLineEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BunkerReceivedNoteLineDetail;
