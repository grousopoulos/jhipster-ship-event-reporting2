import dayjs from 'dayjs';
import { IVoyage } from 'app/shared/model/voyage.model';
import { EventStatus } from 'app/shared/model/enumerations/event-status.model';
import { LoadingCondition } from 'app/shared/model/enumerations/loading-condition.model';

export interface IEventReport {
  id?: number;
  documentDateAndTime?: dayjs.Dayjs;
  speedGps?: number | null;
  documentDisplayNumber?: string | null;
  leg?: number | null;
  distanceTravelled?: number | null;
  hoursUnderway?: number | null;
  eventStatus?: keyof typeof EventStatus;
  loadingCondition?: keyof typeof LoadingCondition;
  cargoCarried?: number | null;
  coordinatesLatitude?: string | null;
  coordinatesLongitude?: string | null;
  shipsHeading?: string | null;
  beaufortNo?: number | null;
  weatherCondition?: string | null;
  swell?: boolean | null;
  voyage?: IVoyage;
}

export const defaultValue: Readonly<IEventReport> = {
  swell: false,
};
