import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './consumption-line.reducer';

export const ConsumptionLine = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const consumptionLineList = useAppSelector(state => state.consumptionLine.entities);
  const loading = useAppSelector(state => state.consumptionLine.loading);

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
      <h2 id="consumption-line-heading" data-cy="ConsumptionLineHeading">
        <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.home.title">Consumption Lines</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/consumption-line/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.home.createLabel">Create new Consumption Line</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {consumptionLineList && consumptionLineList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('quantity')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.quantity">Quantity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('quantity')} />
                </th>
                <th className="hand" onClick={sort('unitOfMeasure')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.unitOfMeasure">Unit Of Measure</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('unitOfMeasure')} />
                </th>
                <th className="hand" onClick={sort('co2EmissionSourceTypeCode')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.co2EmissionSourceTypeCode">
                    Co 2 Emission Source Type Code
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('co2EmissionSourceTypeCode')} />
                </th>
                <th className="hand" onClick={sort('lowerCalorificValue')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.lowerCalorificValue">
                    Lower Calorific Value
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lowerCalorificValue')} />
                </th>
                <th className="hand" onClick={sort('sulphurContent')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.sulphurContent">Sulphur Content</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('sulphurContent')} />
                </th>
                <th className="hand" onClick={sort('density')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.density">Density</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('density')} />
                </th>
                <th className="hand" onClick={sort('viscosity')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.viscosity">Viscosity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('viscosity')} />
                </th>
                <th className="hand" onClick={sort('waterContent')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.waterContent">Water Content</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('waterContent')} />
                </th>
                <th className="hand" onClick={sort('portActivityCode')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.portActivityCode">Port Activity Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('portActivityCode')} />
                </th>
                <th className="hand" onClick={sort('diffCriterionCode')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.diffCriterionCode">Diff Criterion Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('diffCriterionCode')} />
                </th>
                <th>
                  <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.eventReport">Event Report</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.fuelType">Fuel Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {consumptionLineList.map((consumptionLine, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/consumption-line/${consumptionLine.id}`} color="link" size="sm">
                      {consumptionLine.id}
                    </Button>
                  </td>
                  <td>{consumptionLine.quantity}</td>
                  <td>
                    <Translate contentKey={`jhipsterShipEventReporting2App.UnitOfMeasure.${consumptionLine.unitOfMeasure}`} />
                  </td>
                  <td>
                    <Translate
                      contentKey={`jhipsterShipEventReporting2App.Co2EmissionSourceTypeCode.${consumptionLine.co2EmissionSourceTypeCode}`}
                    />
                  </td>
                  <td>{consumptionLine.lowerCalorificValue}</td>
                  <td>{consumptionLine.sulphurContent}</td>
                  <td>{consumptionLine.density}</td>
                  <td>{consumptionLine.viscosity}</td>
                  <td>{consumptionLine.waterContent}</td>
                  <td>
                    <Translate contentKey={`jhipsterShipEventReporting2App.PortActivityCode.${consumptionLine.portActivityCode}`} />
                  </td>
                  <td>
                    <Translate contentKey={`jhipsterShipEventReporting2App.DiffCriterionCode.${consumptionLine.diffCriterionCode}`} />
                  </td>
                  <td>
                    {consumptionLine.eventReport ? (
                      <Link to={`/event-report/${consumptionLine.eventReport.id}`}>{consumptionLine.eventReport.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {consumptionLine.fuelType ? (
                      <Link to={`/fuel-type/${consumptionLine.fuelType.id}`}>{consumptionLine.fuelType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/consumption-line/${consumptionLine.id}`}
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
                        to={`/consumption-line/${consumptionLine.id}/edit`}
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
                        onClick={() => (window.location.href = `/consumption-line/${consumptionLine.id}/delete`)}
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
              <Translate contentKey="jhipsterShipEventReporting2App.consumptionLine.home.notFound">No Consumption Lines found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ConsumptionLine;
