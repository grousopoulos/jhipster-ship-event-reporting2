import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './machinery.reducer';

export const MachineryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const machineryEntity = useAppSelector(state => state.machinery.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="machineryDetailsHeading">
          <Translate contentKey="jhipsterShipEventReporting2App.machinery.detail.title">Machinery</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{machineryEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterShipEventReporting2App.machinery.name">Name</Translate>
            </span>
          </dt>
          <dd>{machineryEntity.name}</dd>
          <dt>
            <Translate contentKey="jhipsterShipEventReporting2App.machinery.ship">Ship</Translate>
          </dt>
          <dd>{machineryEntity.ship ? machineryEntity.ship.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/machinery" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/machinery/${machineryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MachineryDetail;
