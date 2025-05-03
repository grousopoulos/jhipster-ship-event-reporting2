import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './ship.reducer';

export const Ship = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const shipList = useAppSelector(state => state.ship.entities);
  const loading = useAppSelector(state => state.ship.loading);
  const links = useAppSelector(state => state.ship.links);
  const updateSuccess = useAppSelector(state => state.ship.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="ship-heading" data-cy="ShipHeading">
        <Translate contentKey="jhipsterShipEventReporting2App.ship.home.title">Ships</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterShipEventReporting2App.ship.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/ship/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterShipEventReporting2App.ship.home.createLabel">Create new Ship</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={shipList ? shipList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {shipList && shipList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.ship.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.ship.name">Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                  </th>
                  <th className="hand" onClick={sort('callSign')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.ship.callSign">Call Sign</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('callSign')} />
                  </th>
                  <th className="hand" onClick={sort('iceClassPolarCode')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.ship.iceClassPolarCode">Ice Class Polar Code</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('iceClassPolarCode')} />
                  </th>
                  <th className="hand" onClick={sort('technicalEfficiencyCode')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.ship.technicalEfficiencyCode">
                      Technical Efficiency Code
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('technicalEfficiencyCode')} />
                  </th>
                  <th className="hand" onClick={sort('shipType')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.ship.shipType">Ship Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('shipType')} />
                  </th>
                  <th className="hand" onClick={sort('monitoringMethodCode')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.ship.monitoringMethodCode">Monitoring Method Code</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('monitoringMethodCode')} />
                  </th>
                  <th>
                    <Translate contentKey="jhipsterShipEventReporting2App.ship.ownerCountry">Owner Country</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="jhipsterShipEventReporting2App.ship.flag">Flag</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="jhipsterShipEventReporting2App.ship.classificationSociety">Classification Society</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {shipList.map((ship, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/ship/${ship.id}`} color="link" size="sm">
                        {ship.id}
                      </Button>
                    </td>
                    <td>{ship.name}</td>
                    <td>{ship.callSign}</td>
                    <td>
                      <Translate contentKey={`jhipsterShipEventReporting2App.IceClassPolarCode.${ship.iceClassPolarCode}`} />
                    </td>
                    <td>
                      <Translate contentKey={`jhipsterShipEventReporting2App.TechnicalEfficiencyCode.${ship.technicalEfficiencyCode}`} />
                    </td>
                    <td>
                      <Translate contentKey={`jhipsterShipEventReporting2App.ShipType.${ship.shipType}`} />
                    </td>
                    <td>
                      <Translate contentKey={`jhipsterShipEventReporting2App.MonitoringMethodCode.${ship.monitoringMethodCode}`} />
                    </td>
                    <td>{ship.ownerCountry ? <Link to={`/country/${ship.ownerCountry.id}`}>{ship.ownerCountry.id}</Link> : ''}</td>
                    <td>{ship.flag ? <Link to={`/flag/${ship.flag.id}`}>{ship.flag.id}</Link> : ''}</td>
                    <td>
                      {ship.classificationSociety ? (
                        <Link to={`/classification-society/${ship.classificationSociety.id}`}>{ship.classificationSociety.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/ship/${ship.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/ship/${ship.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/ship/${ship.id}/delete`)}
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
                <Translate contentKey="jhipsterShipEventReporting2App.ship.home.notFound">No Ships found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Ship;
