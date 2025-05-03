import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './consumption-line.reducer';

export const ConsumptionLineDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const consumptionLineEntity = useAppSelector(state => state.consumptionLine.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="consumptionLineDetailsHeading">
          <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.detail.title">ConsumptionLine</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{consumptionLineEntity.id}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{consumptionLineEntity.quantity}</dd>
          <dt>
            <span id="unitOfMeasure">
              <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.unitOfMeasure">Unit Of Measure</Translate>
            </span>
          </dt>
          <dd>{consumptionLineEntity.unitOfMeasure}</dd>
          <dt>
            <span id="co2EmissionSourceTypeCode">
              <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.co2EmissionSourceTypeCode">
                Co 2 Emission Source Type Code
              </Translate>
            </span>
          </dt>
          <dd>{consumptionLineEntity.co2EmissionSourceTypeCode}</dd>
          <dt>
            <span id="lowerCalorificValue">
              <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.lowerCalorificValue">Lower Calorific Value</Translate>
            </span>
          </dt>
          <dd>{consumptionLineEntity.lowerCalorificValue}</dd>
          <dt>
            <span id="sulphurContent">
              <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.sulphurContent">Sulphur Content</Translate>
            </span>
          </dt>
          <dd>{consumptionLineEntity.sulphurContent}</dd>
          <dt>
            <span id="density">
              <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.density">Density</Translate>
            </span>
          </dt>
          <dd>{consumptionLineEntity.density}</dd>
          <dt>
            <span id="viscosity">
              <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.viscosity">Viscosity</Translate>
            </span>
          </dt>
          <dd>{consumptionLineEntity.viscosity}</dd>
          <dt>
            <span id="waterContent">
              <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.waterContent">Water Content</Translate>
            </span>
          </dt>
          <dd>{consumptionLineEntity.waterContent}</dd>
          <dt>
            <span id="portActivityCode">
              <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.portActivityCode">Port Activity Code</Translate>
            </span>
          </dt>
          <dd>{consumptionLineEntity.portActivityCode}</dd>
          <dt>
            <span id="diffCriterionCode">
              <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.diffCriterionCode">Diff Criterion Code</Translate>
            </span>
          </dt>
          <dd>{consumptionLineEntity.diffCriterionCode}</dd>
          <dt>
            <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.eventReport">Event Report</Translate>
          </dt>
          <dd>{consumptionLineEntity.eventReport ? consumptionLineEntity.eventReport.id : ''}</dd>
          <dt>
            <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.fuelType">Fuel Type</Translate>
          </dt>
          <dd>{consumptionLineEntity.fuelType ? consumptionLineEntity.fuelType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/consumption-line" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/consumption-line/${consumptionLineEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConsumptionLineDetail;
