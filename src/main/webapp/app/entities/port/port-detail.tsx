import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './port.reducer';

export const PortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const portEntity = useAppSelector(state => state.port.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="portDetailsHeading">
          <Translate contentKey="jhipsterShipEventReporting2App.port.detail.title">Port</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{portEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterShipEventReporting2App.port.name">Name</Translate>
            </span>
          </dt>
          <dd>{portEntity.name}</dd>
          <dt>
            <span id="unlocode">
              <Translate contentKey="jhipsterShipEventReporting2App.port.unlocode">Unlocode</Translate>
            </span>
          </dt>
          <dd>{portEntity.unlocode}</dd>
        </dl>
        <Button tag={Link} to="/port" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/port/${portEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PortDetail;
