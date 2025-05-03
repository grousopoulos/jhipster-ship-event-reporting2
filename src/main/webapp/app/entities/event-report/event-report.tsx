import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './event-report.reducer';

export const EventReport = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const eventReportList = useAppSelector(state => state.eventReport.entities);
  const loading = useAppSelector(state => state.eventReport.loading);
  const links = useAppSelector(state => state.eventReport.links);
  const updateSuccess = useAppSelector(state => state.eventReport.updateSuccess);

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
      <h2 id="event-report-heading" data-cy="EventReportHeading">
        <Translate contentKey="jhipsterShipEventReporting2App.eventReport.home.title">Event Reports</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterShipEventReporting2App.eventReport.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/event-report/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterShipEventReporting2App.eventReport.home.createLabel">Create new Event Report</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={eventReportList ? eventReportList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {eventReportList && eventReportList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('documentDateAndTime')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.documentDateAndTime">
                      Document Date And Time
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentDateAndTime')} />
                  </th>
                  <th className="hand" onClick={sort('speedGps')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.speedGps">Speed Gps</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('speedGps')} />
                  </th>
                  <th className="hand" onClick={sort('documentDisplayNumber')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.documentDisplayNumber">
                      Document Display Number
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentDisplayNumber')} />
                  </th>
                  <th className="hand" onClick={sort('leg')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.leg">Leg</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('leg')} />
                  </th>
                  <th className="hand" onClick={sort('distanceTravelled')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.distanceTravelled">Distance Travelled</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('distanceTravelled')} />
                  </th>
                  <th className="hand" onClick={sort('hoursUnderway')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.hoursUnderway">Hours Underway</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('hoursUnderway')} />
                  </th>
                  <th className="hand" onClick={sort('eventStatus')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.eventStatus">Event Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('eventStatus')} />
                  </th>
                  <th className="hand" onClick={sort('loadingCondition')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.loadingCondition">Loading Condition</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('loadingCondition')} />
                  </th>
                  <th className="hand" onClick={sort('cargoCarried')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.cargoCarried">Cargo Carried</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('cargoCarried')} />
                  </th>
                  <th className="hand" onClick={sort('coordinatesLatitude')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.coordinatesLatitude">Coordinates Latitude</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('coordinatesLatitude')} />
                  </th>
                  <th className="hand" onClick={sort('coordinatesLongitude')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.coordinatesLongitude">
                      Coordinates Longitude
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('coordinatesLongitude')} />
                  </th>
                  <th className="hand" onClick={sort('shipsHeading')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.shipsHeading">Ships Heading</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('shipsHeading')} />
                  </th>
                  <th className="hand" onClick={sort('beaufortNo')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.beaufortNo">Beaufort No</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('beaufortNo')} />
                  </th>
                  <th className="hand" onClick={sort('weatherCondition')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.weatherCondition">Weather Condition</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('weatherCondition')} />
                  </th>
                  <th className="hand" onClick={sort('swell')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.swell">Swell</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('swell')} />
                  </th>
                  <th>
                    <Translate contentKey="jhipsterShipEventReporting2App.eventReport.voyage">Voyage</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {eventReportList.map((eventReport, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/event-report/${eventReport.id}`} color="link" size="sm">
                        {eventReport.id}
                      </Button>
                    </td>
                    <td>
                      {eventReport.documentDateAndTime ? (
                        <TextFormat type="date" value={eventReport.documentDateAndTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{eventReport.speedGps}</td>
                    <td>{eventReport.documentDisplayNumber}</td>
                    <td>{eventReport.leg}</td>
                    <td>{eventReport.distanceTravelled}</td>
                    <td>{eventReport.hoursUnderway}</td>
                    <td>
                      <Translate contentKey={`jhipsterShipEventReporting2App.EventStatus.${eventReport.eventStatus}`} />
                    </td>
                    <td>
                      <Translate contentKey={`jhipsterShipEventReporting2App.LoadingCondition.${eventReport.loadingCondition}`} />
                    </td>
                    <td>{eventReport.cargoCarried}</td>
                    <td>{eventReport.coordinatesLatitude}</td>
                    <td>{eventReport.coordinatesLongitude}</td>
                    <td>{eventReport.shipsHeading}</td>
                    <td>{eventReport.beaufortNo}</td>
                    <td>{eventReport.weatherCondition}</td>
                    <td>{eventReport.swell ? 'true' : 'false'}</td>
                    <td>{eventReport.voyage ? <Link to={`/voyage/${eventReport.voyage.id}`}>{eventReport.voyage.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/event-report/${eventReport.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/event-report/${eventReport.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/event-report/${eventReport.id}/delete`)}
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
                <Translate contentKey="jhipsterShipEventReporting2App.eventReport.home.notFound">No Event Reports found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default EventReport;
