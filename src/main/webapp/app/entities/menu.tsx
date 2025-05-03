import React from 'react';
import { Translate } from 'react-jhipster'; // eslint-disable-line

import MenuItem from 'app/shared/layout/menus/menu-item'; // eslint-disable-line

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/country">
        <Translate contentKey="global.menu.entities.country" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/flag">
        <Translate contentKey="global.menu.entities.flag" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/port">
        <Translate contentKey="global.menu.entities.port" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/classification-society">
        <Translate contentKey="global.menu.entities.classificationSociety" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/ship">
        <Translate contentKey="global.menu.entities.ship" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/fuel-eu-regulation">
        <Translate contentKey="global.menu.entities.fuelEuRegulation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/voyage">
        <Translate contentKey="global.menu.entities.voyage" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/fuel-type">
        <Translate contentKey="global.menu.entities.fuelType" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/bunker-received-note">
        <Translate contentKey="global.menu.entities.bunkerReceivedNote" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/bunker-received-note-line">
        <Translate contentKey="global.menu.entities.bunkerReceivedNoteLine" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/event-report">
        <Translate contentKey="global.menu.entities.eventReport" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/consumption-line">
        <Translate contentKey="global.menu.entities.consumptionLine" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/machinery">
        <Translate contentKey="global.menu.entities.machinery" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/machinery-operation-line">
        <Translate contentKey="global.menu.entities.machineryOperationLine" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
