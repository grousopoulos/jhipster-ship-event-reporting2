import { IBunkerReceivedNote } from 'app/shared/model/bunker-received-note.model';
import { IFuelType } from 'app/shared/model/fuel-type.model';
import { UnitOfMeasure } from 'app/shared/model/enumerations/unit-of-measure.model';

export interface IBunkerReceivedNoteLine {
  id?: number;
  quantity?: number;
  unitOfMeasure?: keyof typeof UnitOfMeasure;
  lowerCalorificValue?: number | null;
  sulphurContent?: number | null;
  density?: number | null;
  viscosity?: number | null;
  waterContent?: number | null;
  bunkerReceivedNote?: IBunkerReceivedNote;
  fuelType?: IFuelType;
}

export const defaultValue: Readonly<IBunkerReceivedNoteLine> = {};
