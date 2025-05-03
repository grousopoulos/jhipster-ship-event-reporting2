import { IShip } from 'app/shared/model/ship.model';

export interface IVoyage {
  id?: number;
  number?: string;
  ship?: IShip;
}

export const defaultValue: Readonly<IVoyage> = {};
