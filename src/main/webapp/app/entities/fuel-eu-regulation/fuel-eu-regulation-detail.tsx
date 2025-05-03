import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './fuel-eu-regulation.reducer';

export const FuelEuRegulationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fuelEuRegulationEntity = useAppSelector(state => state.fuelEuRegulation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fuelEuRegulationDetailsHeading">
          <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.detail.title">FuelEuRegulation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fuelEuRegulationEntity.id}</dd>
          <dt>
            <span id="year">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.year">Year</Translate>
            </span>
          </dt>
          <dd>{fuelEuRegulationEntity.year}</dd>
          <dt>
            <span id="co2Gwp">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.co2Gwp">Co 2 Gwp</Translate>
            </span>
          </dt>
          <dd>{fuelEuRegulationEntity.co2Gwp}</dd>
          <dt>
            <span id="methaneGwp">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.methaneGwp">Methane Gwp</Translate>
            </span>
          </dt>
          <dd>{fuelEuRegulationEntity.methaneGwp}</dd>
          <dt>
            <span id="nitrousGwp">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.nitrousGwp">Nitrous Gwp</Translate>
            </span>
          </dt>
          <dd>{fuelEuRegulationEntity.nitrousGwp}</dd>
          <dt>
            <span id="targetIntensity">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.targetIntensity">Target Intensity</Translate>
            </span>
          </dt>
          <dd>{fuelEuRegulationEntity.targetIntensity}</dd>
          <dt>
            <span id="baselineIntensity">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.baselineIntensity">Baseline Intensity</Translate>
            </span>
          </dt>
          <dd>{fuelEuRegulationEntity.baselineIntensity}</dd>
          <dt>
            <span id="reductionFactorPercent">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.reductionFactorPercent">
                Reduction Factor Percent
              </Translate>
            </span>
          </dt>
          <dd>{fuelEuRegulationEntity.reductionFactorPercent}</dd>
          <dt>
            <span id="vlsfoEnergyContentPerTonMj">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.vlsfoEnergyContentPerTonMj">
                Vlsfo Energy Content Per Ton Mj
              </Translate>
            </span>
          </dt>
          <dd>{fuelEuRegulationEntity.vlsfoEnergyContentPerTonMj}</dd>
          <dt>
            <span id="vlsfoPenaltyEurPerTon">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.vlsfoPenaltyEurPerTon">
                Vlsfo Penalty Eur Per Ton
              </Translate>
            </span>
          </dt>
          <dd>{fuelEuRegulationEntity.vlsfoPenaltyEurPerTon}</dd>
          <dt>
            <span id="energyAllowanceMultiplier">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.energyAllowanceMultiplier">
                Energy Allowance Multiplier
              </Translate>
            </span>
          </dt>
          <dd>{fuelEuRegulationEntity.energyAllowanceMultiplier}</dd>
          <dt>
            <span id="nonBioFuelRewardFactor">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.nonBioFuelRewardFactor">
                Non Bio Fuel Reward Factor
              </Translate>
            </span>
          </dt>
          <dd>{fuelEuRegulationEntity.nonBioFuelRewardFactor}</dd>
        </dl>
        <Button tag={Link} to="/fuel-eu-regulation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fuel-eu-regulation/${fuelEuRegulationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FuelEuRegulationDetail;
