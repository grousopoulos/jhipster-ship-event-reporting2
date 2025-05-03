import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './bunker-received-note-line.reducer';

export const BunkerReceivedNoteLine = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const bunkerReceivedNoteLineList = useAppSelector(state => state.bunkerReceivedNoteLine.entities);
  const loading = useAppSelector(state => state.bunkerReceivedNoteLine.loading);

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
      <h2 id="bunker-received-note-line-heading" data-cy="BunkerReceivedNoteLineHeading">
        <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.home.title">Bunker Received Note Lines</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/bunker-received-note-line/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.home.createLabel">
              Create new Bunker Received Note Line
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bunkerReceivedNoteLineList && bunkerReceivedNoteLineList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('quantity')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.quantity">Quantity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('quantity')} />
                </th>
                <th className="hand" onClick={sort('unitOfMeasure')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.unitOfMeasure">Unit Of Measure</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('unitOfMeasure')} />
                </th>
                <th className="hand" onClick={sort('lowerCalorificValue')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.lowerCalorificValue">
                    Lower Calorific Value
                  </Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('lowerCalorificValue')} />
                </th>
                <th className="hand" onClick={sort('sulphurContent')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.sulphurContent">Sulphur Content</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('sulphurContent')} />
                </th>
                <th className="hand" onClick={sort('density')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.density">Density</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('density')} />
                </th>
                <th className="hand" onClick={sort('viscosity')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.viscosity">Viscosity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('viscosity')} />
                </th>
                <th className="hand" onClick={sort('waterContent')}>
                  <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.waterContent">Water Content</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('waterContent')} />
                </th>
                <th>
                  <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.bunkerReceivedNote">
                    Bunker Received Note
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.fuelType">Fuel Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bunkerReceivedNoteLineList.map((bunkerReceivedNoteLine, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/bunker-received-note-line/${bunkerReceivedNoteLine.id}`} color="link" size="sm">
                      {bunkerReceivedNoteLine.id}
                    </Button>
                  </td>
                  <td>{bunkerReceivedNoteLine.quantity}</td>
                  <td>
                    <Translate contentKey={`jhipsterShipEventReporting2App.UnitOfMeasure.${bunkerReceivedNoteLine.unitOfMeasure}`} />
                  </td>
                  <td>{bunkerReceivedNoteLine.lowerCalorificValue}</td>
                  <td>{bunkerReceivedNoteLine.sulphurContent}</td>
                  <td>{bunkerReceivedNoteLine.density}</td>
                  <td>{bunkerReceivedNoteLine.viscosity}</td>
                  <td>{bunkerReceivedNoteLine.waterContent}</td>
                  <td>
                    {bunkerReceivedNoteLine.bunkerReceivedNote ? (
                      <Link to={`/bunker-received-note/${bunkerReceivedNoteLine.bunkerReceivedNote.id}`}>
                        {bunkerReceivedNoteLine.bunkerReceivedNote.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {bunkerReceivedNoteLine.fuelType ? (
                      <Link to={`/fuel-type/${bunkerReceivedNoteLine.fuelType.id}`}>{bunkerReceivedNoteLine.fuelType.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/bunker-received-note-line/${bunkerReceivedNoteLine.id}`}
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
                        to={`/bunker-received-note-line/${bunkerReceivedNoteLine.id}/edit`}
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
                        onClick={() => (window.location.href = `/bunker-received-note-line/${bunkerReceivedNoteLine.id}/delete`)}
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
              <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNoteLine.home.notFound">
                No Bunker Received Note Lines found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BunkerReceivedNoteLine;
