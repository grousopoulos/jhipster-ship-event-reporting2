export interface IFuelEuRegulation {
  id?: number;
  year?: number | null;
  co2Gwp?: number | null;
  methaneGwp?: number | null;
  nitrousGwp?: number | null;
  targetIntensity?: number | null;
  baselineIntensity?: number | null;
  reductionFactorPercent?: number | null;
  vlsfoEnergyContentPerTonMj?: number | null;
  vlsfoPenaltyEurPerTon?: number | null;
  energyAllowanceMultiplier?: number | null;
  nonBioFuelRewardFactor?: number | null;
}

export const defaultValue: Readonly<IFuelEuRegulation> = {};
