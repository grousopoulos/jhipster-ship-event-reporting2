import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Machinery from './machinery';
import MachineryDetail from './machinery-detail';
import MachineryUpdate from './machinery-update';
import MachineryDeleteDialog from './machinery-delete-dialog';

const MachineryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Machinery />} />
    <Route path="new" element={<MachineryUpdate />} />
    <Route path=":id">
      <Route index element={<MachineryDetail />} />
      <Route path="edit" element={<MachineryUpdate />} />
      <Route path="delete" element={<MachineryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MachineryRoutes;
