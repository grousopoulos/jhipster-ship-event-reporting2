import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ConsumptionLine from './consumption-line';
import ConsumptionLineDetail from './consumption-line-detail';
import ConsumptionLineUpdate from './consumption-line-update';
import ConsumptionLineDeleteDialog from './consumption-line-delete-dialog';

const ConsumptionLineRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ConsumptionLine />} />
    <Route path="new" element={<ConsumptionLineUpdate />} />
    <Route path=":id">
      <Route index element={<ConsumptionLineDetail />} />
      <Route path="edit" element={<ConsumptionLineUpdate />} />
      <Route path="delete" element={<ConsumptionLineDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConsumptionLineRoutes;
