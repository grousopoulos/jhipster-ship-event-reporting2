import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ship.reducer';

export const ShipDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const shipEntity = useAppSelector(state => state.ship.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="shipDetailsHeading">
          <Translate contentKey="jhipsterShipEventReporting2App.ship.detail.title">Ship</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{shipEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jhipsterShipEventReporting2App.ship.name">Name</Translate>
            </span>
          </dt>
          <dd>{shipEntity.name}</dd>
          <dt>
            <span id="callSign">
              <Translate contentKey="jhipsterShipEventReporting2App.ship.callSign">Call Sign</Translate>
            </span>
          </dt>
          <dd>{shipEntity.callSign}</dd>
          <dt>
            <span id="iceClassPolarCode">
              <Translate contentKey="jhipsterShipEventReporting2App.ship.iceClassPolarCode">Ice Class Polar Code</Translate>
            </span>
          </dt>
          <dd>{shipEntity.iceClassPolarCode}</dd>
          <dt>
            <span id="technicalEfficiencyCode">
              <Translate contentKey="jhipsterShipEventReporting2App.ship.technicalEfficiencyCode">Technical Efficiency Code</Translate>
            </span>
          </dt>
          <dd>{shipEntity.technicalEfficiencyCode}</dd>
          <dt>
            <span id="shipType">
              <Translate contentKey="jhipsterShipEventReporting2App.ship.shipType">Ship Type</Translate>
            </span>
          </dt>
          <dd>{shipEntity.shipType}</dd>
          <dt>
            <span id="monitoringMethodCode">
              <Translate contentKey="jhipsterShipEventReporting2App.ship.monitoringMethodCode">Monitoring Method Code</Translate>
            </span>
          </dt>
          <dd>{shipEntity.monitoringMethodCode}</dd>
          <dt>
            <Translate contentKey="jhipsterShipEventReporting2App.ship.ownerCountry">Owner Country</Translate>
          </dt>
          <dd>{shipEntity.ownerCountry ? shipEntity.ownerCountry.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterShipEventReporting2App.ship.flag">Flag</Translate>
          </dt>
          <dd>{shipEntity.flag ? shipEntity.flag.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterShipEventReporting2App.ship.classificationSociety">Classification Society</Translate>
          </dt>
          <dd>{shipEntity.classificationSociety ? shipEntity.classificationSociety.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/ship" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ship/${shipEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ShipDetail;
