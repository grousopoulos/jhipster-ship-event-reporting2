import { ICountry } from 'app/shared/model/country.model';
import { IFlag } from 'app/shared/model/flag.model';
import { IClassificationSociety } from 'app/shared/model/classification-society.model';
import { IceClassPolarCode } from 'app/shared/model/enumerations/ice-class-polar-code.model';
import { TechnicalEfficiencyCode } from 'app/shared/model/enumerations/technical-efficiency-code.model';
import { ShipType } from 'app/shared/model/enumerations/ship-type.model';
import { MonitoringMethodCode } from 'app/shared/model/enumerations/monitoring-method-code.model';

export interface IShip {
  id?: number;
  name?: string;
  callSign?: string | null;
  iceClassPolarCode?: keyof typeof IceClassPolarCode | null;
  technicalEfficiencyCode?: keyof typeof TechnicalEfficiencyCode | null;
  shipType?: keyof typeof ShipType | null;
  monitoringMethodCode?: keyof typeof MonitoringMethodCode | null;
  ownerCountry?: ICountry | null;
  flag?: IFlag | null;
  classificationSociety?: IClassificationSociety | null;
}

export const defaultValue: Readonly<IShip> = {};
