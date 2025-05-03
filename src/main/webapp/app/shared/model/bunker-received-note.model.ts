import dayjs from 'dayjs';
import { IVoyage } from 'app/shared/model/voyage.model';

export interface IBunkerReceivedNote {
  id?: number;
  documentDateAndTime?: dayjs.Dayjs;
  documentDisplayNumber?: string | null;
  voyage?: IVoyage;
}

export const defaultValue: Readonly<IBunkerReceivedNote> = {};
