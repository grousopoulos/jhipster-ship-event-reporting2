import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './classification-society.reducer';

export const ClassificationSocietyDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const classificationSocietyEntity = useAppSelector(state => state.classificationSociety.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="classificationSocietyDetailsHeading">
          <Translate contentKey="jhipsterShipEventReporting2App.classificationSociety.detail.title">ClassificationSociety</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{classificationSocietyEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jhipsterShipEventReporting2App.classificationSociety.code">Code</Translate>
            </span>
          </dt>
          <dd>{classificationSocietyEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterShipEventReporting2App.classificationSociety.name">Name</Translate>
            </span>
          </dt>
          <dd>{classificationSocietyEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/classification-society" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/classification-society/${classificationSocietyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClassificationSocietyDetail;
