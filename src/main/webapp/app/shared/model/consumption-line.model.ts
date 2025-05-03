import { IEventReport } from 'app/shared/model/event-report.model';
import { IFuelType } from 'app/shared/model/fuel-type.model';
import { UnitOfMeasure } from 'app/shared/model/enumerations/unit-of-measure.model';
import { Co2EmissionSourceTypeCode } from 'app/shared/model/enumerations/co-2-emission-source-type-code.model';
import { PortActivityCode } from 'app/shared/model/enumerations/port-activity-code.model';
import { DiffCriterionCode } from 'app/shared/model/enumerations/diff-criterion-code.model';

export interface IConsumptionLine {
  id?: number;
  quantity?: number;
  unitOfMeasure?: keyof typeof UnitOfMeasure;
  co2EmissionSourceTypeCode?: keyof typeof Co2EmissionSourceTypeCode | null;
  lowerCalorificValue?: number | null;
  sulphurContent?: number | null;
  density?: number | null;
  viscosity?: number | null;
  waterContent?: number | null;
  portActivityCode?: keyof typeof PortActivityCode | null;
  diffCriterionCode?: keyof typeof DiffCriterionCode | null;
  eventReport?: IEventReport;
  fuelType?: IFuelType;
}

export const defaultValue: Readonly<IConsumptionLine> = {};
