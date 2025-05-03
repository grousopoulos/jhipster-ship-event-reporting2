import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './machinery-operation-line.reducer';

export const MachineryOperationLine = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const machineryOperationLineList = useAppSelector(state => state.machineryOperationLine.entities);
  const loading = useAppSelector(state => state.machineryOperationLine.loading);

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
      <h2 id="machinery-operation-line-heading" data-cy="MachineryOperationLineHeading">
        <Translate contentKey="jhipsterShipEventReporting2App.machineryOperationLine.home.title">Machinery Operation Lines</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterShipEventReporting2App.machineryOperationLine.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/machinery-operation-line/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterShipEventReporting2App.machineryOperationLine.home.createLabel">
              Create new Machinery Operation Line
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {machineryOperationLineList && machineryOperationLineList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.machineryOperationLine.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('runningHours')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.machineryOperationLine.runningHours">Running Hours</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('runningHours')} />
                </th>
                <th className="hand" onClick={sort('powerOutput')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.machineryOperationLine.powerOutput">Power Output</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('powerOutput')} />
                </th>
                <th className="hand" onClick={sort('averageRpm')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.machineryOperationLine.averageRpm">Average Rpm</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('averageRpm')} />
                </th>
                <th>
                  <Translate contentKey="jhipsterShipEventReporting2App.machineryOperationLine.eventReport">Event Report</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {machineryOperationLineList.map((machineryOperationLine, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/machinery-operation-line/${machineryOperationLine.id}`} color="link" size="sm">
                      {machineryOperationLine.id}
                    </Button>
                  </td>
                  <td>{machineryOperationLine.runningHours}</td>
                  <td>{machineryOperationLine.powerOutput}</td>
                  <td>{machineryOperationLine.averageRpm}</td>
                  <td>
                    {machineryOperationLine.eventReport ? (
                      <Link to={`/event-report/${machineryOperationLine.eventReport.id}`}>{machineryOperationLine.eventReport.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/machinery-operation-line/${machineryOperationLine.id}`}
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
                        to={`/machinery-operation-line/${machineryOperationLine.id}/edit`}
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
                        onClick={() => (window.location.href = `/machinery-operation-line/${machineryOperationLine.id}/delete`)}
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
              <Translate contentKey="jhipsterShipEventReporting2App.machineryOperationLine.home.notFound">
                No Machinery Operation Lines found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default MachineryOperationLine;
