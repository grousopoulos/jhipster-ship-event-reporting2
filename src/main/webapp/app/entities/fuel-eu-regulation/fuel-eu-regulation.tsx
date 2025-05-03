import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './fuel-eu-regulation.reducer';

export const FuelEuRegulation = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const fuelEuRegulationList = useAppSelector(state => state.fuelEuRegulation.entities);
  const loading = useAppSelector(state => state.fuelEuRegulation.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="fuel-eu-regulation-heading" data-cy="FuelEuRegulationHeading">
        <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.home.title">Fuel Eu Regulations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/fuel-eu-regulation/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.home.createLabel">
              Create new Fuel Eu Regulation
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {fuelEuRegulationList && fuelEuRegulationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('year')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.year">Year</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('year')} />
                </th>
                <th className="hand" onClick={sort('co2Gwp')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.co2Gwp">Co 2 Gwp</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('co2Gwp')} />
                </th>
                <th className="hand" onClick={sort('methaneGwp')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.methaneGwp">Methane Gwp</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('methaneGwp')} />
                </th>
                <th className="hand" onClick={sort('nitrousGwp')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.nitrousGwp">Nitrous Gwp</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('nitrousGwp')} />
                </th>
                <th className="hand" onClick={sort('targetIntensity')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.targetIntensity">Target Intensity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('targetIntensity')} />
                </th>
                <th className="hand" onClick={sort('baselineIntensity')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.baselineIntensity">Baseline Intensity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('baselineIntensity')} />
                </th>
                <th className="hand" onClick={sort('reductionFactorPercent')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.reductionFactorPercent">
                    Reduction Factor Percent
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('reductionFactorPercent')} />
                </th>
                <th className="hand" onClick={sort('vlsfoEnergyContentPerTonMj')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.vlsfoEnergyContentPerTonMj">
                    Vlsfo Energy Content Per Ton Mj
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('vlsfoEnergyContentPerTonMj')} />
                </th>
                <th className="hand" onClick={sort('vlsfoPenaltyEurPerTon')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.vlsfoPenaltyEurPerTon">
                    Vlsfo Penalty Eur Per Ton
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('vlsfoPenaltyEurPerTon')} />
                </th>
                <th className="hand" onClick={sort('energyAllowanceMultiplier')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.energyAllowanceMultiplier">
                    Energy Allowance Multiplier
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('energyAllowanceMultiplier')} />
                </th>
                <th className="hand" onClick={sort('nonBioFuelRewardFactor')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.nonBioFuelRewardFactor">
                    Non Bio Fuel Reward Factor
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('nonBioFuelRewardFactor')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fuelEuRegulationList.map((fuelEuRegulation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/fuel-eu-regulation/${fuelEuRegulation.id}`} color="link" size="sm">
                      {fuelEuRegulation.id}
                    </Button>
                  </td>
                  <td>{fuelEuRegulation.year}</td>
                  <td>{fuelEuRegulation.co2Gwp}</td>
                  <td>{fuelEuRegulation.methaneGwp}</td>
                  <td>{fuelEuRegulation.nitrousGwp}</td>
                  <td>{fuelEuRegulation.targetIntensity}</td>
                  <td>{fuelEuRegulation.baselineIntensity}</td>
                  <td>{fuelEuRegulation.reductionFactorPercent}</td>
                  <td>{fuelEuRegulation.vlsfoEnergyContentPerTonMj}</td>
                  <td>{fuelEuRegulation.vlsfoPenaltyEurPerTon}</td>
                  <td>{fuelEuRegulation.energyAllowanceMultiplier}</td>
                  <td>{fuelEuRegulation.nonBioFuelRewardFactor}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/fuel-eu-regulation/${fuelEuRegulation.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/fuel-eu-regulation/${fuelEuRegulation.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/fuel-eu-regulation/${fuelEuRegulation.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jhipsterShipEventReporting2App.fuelEuRegulation.home.notFound">No Fuel Eu Regulations found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FuelEuRegulation;
