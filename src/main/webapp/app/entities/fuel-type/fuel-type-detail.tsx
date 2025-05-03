import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './fuel-type.reducer';

export const FuelTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fuelTypeEntity = useAppSelector(state => state.fuelType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fuelTypeDetailsHeading">
          <Translate contentKey="jhipsterShipEventReporting2App.fuelType.detail.title">FuelType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fuelTypeEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelType.name">Name</Translate>
            </span>
          </dt>
          <dd>{fuelTypeEntity.name}</dd>
          <dt>
            <span id="fuelTypeCode">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelType.fuelTypeCode">Fuel Type Code</Translate>
            </span>
          </dt>
          <dd>{fuelTypeEntity.fuelTypeCode}</dd>
          <dt>
            <span id="carbonFactory">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelType.carbonFactory">Carbon Factory</Translate>
            </span>
          </dt>
          <dd>{fuelTypeEntity.carbonFactory}</dd>
        </dl>
        <Button tag={Link} to="/fuel-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fuel-type/${fuelTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FuelTypeDetail;
