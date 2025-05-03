import { IEventReport } from 'app/shared/model/event-report.model';

export interface IMachineryOperationLine {
  id?: number;
  runningHours?: number;
  powerOutput?: number | null;
  averageRpm?: number | null;
  eventReport?: IEventReport;
}

export const defaultValue: Readonly<IMachineryOperationLine> = {};
