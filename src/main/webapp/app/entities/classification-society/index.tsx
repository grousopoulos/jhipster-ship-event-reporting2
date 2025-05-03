import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ClassificationSociety from './classification-society';
import ClassificationSocietyDetail from './classification-society-detail';
import ClassificationSocietyUpdate from './classification-society-update';
import ClassificationSocietyDeleteDialog from './classification-society-delete-dialog';

const ClassificationSocietyRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ClassificationSociety />} />
    <Route path="new" element={<ClassificationSocietyUpdate />} />
    <Route path=":id">
      <Route index element={<ClassificationSocietyDetail />} />
      <Route path="edit" element={<ClassificationSocietyUpdate />} />
      <Route path="delete" element={<ClassificationSocietyDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ClassificationSocietyRoutes;
