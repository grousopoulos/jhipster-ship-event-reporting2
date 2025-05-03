import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FuelEuRegulation from './fuel-eu-regulation';
import FuelEuRegulationDetail from './fuel-eu-regulation-detail';
import FuelEuRegulationUpdate from './fuel-eu-regulation-update';
import FuelEuRegulationDeleteDialog from './fuel-eu-regulation-delete-dialog';

const FuelEuRegulationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FuelEuRegulation />} />
    <Route path="new" element={<FuelEuRegulationUpdate />} />
    <Route path=":id">
      <Route index element={<FuelEuRegulationDetail />} />
      <Route path="edit" element={<FuelEuRegulationUpdate />} />
      <Route path="delete" element={<FuelEuRegulationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FuelEuRegulationRoutes;
