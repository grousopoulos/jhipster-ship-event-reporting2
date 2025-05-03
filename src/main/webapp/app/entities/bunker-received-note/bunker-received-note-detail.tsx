import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bunker-received-note.reducer';

export const BunkerReceivedNoteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bunkerReceivedNoteEntity = useAppSelector(state => state.bunkerReceivedNote.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bunkerReceivedNoteDetailsHeading">
          <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNote.detail.title">BunkerReceivedNote</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bunkerReceivedNoteEntity.id}</dd>
          <dt>
            <span id="documentDateAndTime">
              <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNote.documentDateAndTime">
                Document Date And Time
              </Translate>
            </span>
          </dt>
          <dd>
            {bunkerReceivedNoteEntity.documentDateAndTime ? (
              <TextFormat value={bunkerReceivedNoteEntity.documentDateAndTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="documentDisplayNumber">
              <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNote.documentDisplayNumber">
                Document Display Number
              </Translate>
            </span>
          </dt>
          <dd>{bunkerReceivedNoteEntity.documentDisplayNumber}</dd>
          <dt>
            <Translate contentKey="jhipsterShipEventReporting2App.bunkerReceivedNote.voyage">Voyage</Translate>
          </dt>
          <dd>{bunkerReceivedNoteEntity.voyage ? bunkerReceivedNoteEntity.voyage.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bunker-received-note" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bunker-received-note/${bunkerReceivedNoteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BunkerReceivedNoteDetail;
