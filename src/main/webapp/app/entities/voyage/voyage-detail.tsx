import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './voyage.reducer';

export const VoyageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const voyageEntity = useAppSelector(state => state.voyage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="voyageDetailsHeading">
          <Translate contentKey="jhipsterShipEventReporting2App.voyage.detail.title">Voyage</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{voyageEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="jhipsterShipEventReporting2App.voyage.number">Number</Translate>
            </span>
          </dt>
          <dd>{voyageEntity.number}</dd>
          <dt>
            <Translate contentKey="jhipsterShipEventReporting2App.voyage.ship">Ship</Translate>
          </dt>
          <dd>{voyageEntity.ship ? voyageEntity.ship.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/voyage" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/voyage/${voyageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VoyageDetail;
