import country from 'app/entities/country/country.reducer';
import flag from 'app/entities/flag/flag.reducer';
import port from 'app/entities/port/port.reducer';
import classificationSociety from 'app/entities/classification-society/classification-society.reducer';
import ship from 'app/entities/ship/ship.reducer';
import fuelEuRegulation from 'app/entities/fuel-eu-regulation/fuel-eu-regulation.reducer';
import voyage from 'app/entities/voyage/voyage.reducer';
import fuelType from 'app/entities/fuel-type/fuel-type.reducer';
import bunkerReceivedNote from 'app/entities/bunker-received-note/bunker-received-note.reducer';
import bunkerReceivedNoteLine from 'app/entities/bunker-received-note-line/bunker-received-note-line.reducer';
import eventReport from 'app/entities/event-report/event-report.reducer';
import consumptionLine from 'app/entities/consumption-line/consumption-line.reducer';
import machinery from 'app/entities/machinery/machinery.reducer';
import machineryOperationLine from 'app/entities/machinery-operation-line/machinery-operation-line.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  country,
  flag,
  port,
  classificationSociety,
  ship,
  fuelEuRegulation,
  voyage,
  fuelType,
  bunkerReceivedNote,
  bunkerReceivedNoteLine,
  eventReport,
  consumptionLine,
  machinery,
  machineryOperationLine,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
