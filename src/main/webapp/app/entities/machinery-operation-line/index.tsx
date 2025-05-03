import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MachineryOperationLine from './machinery-operation-line';
import MachineryOperationLineDetail from './machinery-operation-line-detail';
import MachineryOperationLineUpdate from './machinery-operation-line-update';
import MachineryOperationLineDeleteDialog from './machinery-operation-line-delete-dialog';

const MachineryOperationLineRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MachineryOperationLine />} />
    <Route path="new" element={<MachineryOperationLineUpdate />} />
    <Route path=":id">
      <Route index element={<MachineryOperationLineDetail />} />
      <Route path="edit" element={<MachineryOperationLineUpdate />} />
      <Route path="delete" element={<MachineryOperationLineDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MachineryOperationLineRoutes;
