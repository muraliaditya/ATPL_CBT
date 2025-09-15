import { Routes } from '@angular/router';
import { LoginPage } from './pages/user/login-page/login-page';
import { InstructionsPage } from './pages/user/instructions-page/instructions-page';
import { TestPage } from './pages/user/test-page/test-page';
import { ParticipantForm } from './pages/user/participant-form/participant-form';
import { AdminMainSection } from './pages/admin/admin-main-section/admin-main-section';
import { ViewResult } from './pages/admin/view-result/view-result';
export const routes: Routes = [
  {
    path: '',
    component: ParticipantForm,
  },
  {
    path: 'test-instructions',
    loadComponent: () =>
      import('./pages/user/instructions-page/instructions-page').then(
        (c) => c.InstructionsPage
      ),
  },
  {
    path: 'test',
    loadComponent: () =>
      import('./pages/user/test-page/test-page').then((c) => c.TestPage),
  },
  {
    path: 'admin',
    component: AdminMainSection,
    children: [
      {
        path: 'manage-contests',
        component: ViewResult,
      },
    ],
  },
];
