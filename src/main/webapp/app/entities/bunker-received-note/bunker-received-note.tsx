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

import { getEntities, reset } from './bunker-received-note.reducer';

export const BunkerReceivedNote = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const bunkerReceivedNoteList = useAppSelector(state => state.bunkerReceivedNote.entities);
  const loading = useAppSelector(state => state.bunkerReceivedNote.loading);
  const links = useAppSelector(state => state.bunkerReceivedNote.links);
  const updateSuccess = useAppSelector(state => state.bunkerReceivedNote.updateSuccess);

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
      <h2 id="bunker-received-note-heading" data-cy="BunkerReceivedNoteHeading">
        <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNote.home.title">Bunker Received Notes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNote.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/bunker-received-note/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNote.home.createLabel">
              Create new Bunker Received Note
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={bunkerReceivedNoteList ? bunkerReceivedNoteList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {bunkerReceivedNoteList && bunkerReceivedNoteList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNote.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('documentDateAndTime')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNote.documentDateAndTime">
                      Document Date And Time
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentDateAndTime')} />
                  </th>
                  <th className="hand" onClick={sort('documentDisplayNumber')}>
                    <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNote.documentDisplayNumber">
                      Document Display Number
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('documentDisplayNumber')} />
                  </th>
                  <th>
                    <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNote.voyage">Voyage</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {bunkerReceivedNoteList.map((bunkerReceivedNote, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/bunker-received-note/${bunkerReceivedNote.id}`} color="link" size="sm">
                        {bunkerReceivedNote.id}
                      </Button>
                    </td>
                    <td>
                      {bunkerReceivedNote.documentDateAndTime ? (
                        <TextFormat type="date" value={bunkerReceivedNote.documentDateAndTime} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{bunkerReceivedNote.documentDisplayNumber}</td>
                    <td>
                      {bunkerReceivedNote.voyage ? (
                        <Link to={`/voyage/${bunkerReceivedNote.voyage.id}`}>{bunkerReceivedNote.voyage.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/bunker-received-note/${bunkerReceivedNote.id}`}
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
                          to={`/bunker-received-note/${bunkerReceivedNote.id}/edit`}
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
                          onClick={() => (window.location.href = `/bunker-received-note/${bunkerReceivedNote.id}/delete`)}
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
                <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNote.home.notFound">
                  No Bunker Received Notes found
                </Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default BunkerReceivedNote;
