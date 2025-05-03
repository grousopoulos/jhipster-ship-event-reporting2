import { IShip } from 'app/shared/model/ship.model';

export interface IMachinery {
  id?: number;
  name?: string;
  ship?: IShip | null;
}

export const defaultValue: Readonly<IMachinery> = {};
