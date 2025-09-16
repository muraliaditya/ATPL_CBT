import { Routes } from '@angular/router';
import { LoginPage } from './pages/user/login-page/login-page';
import { InstructionsPage } from './pages/user/instructions-page/instructions-page';
import { TestPage } from './pages/user/test-page/test-page';
import { AdminLogin } from './pages/admin/admin-login/admin-login';
import { ParticipantForm } from './pages/user/participant-form/participant-form';
import { AdminMainSection } from './pages/admin/admin-main-section/admin-main-section';
import { ViewResult } from './pages/admin/view-result/view-result';
import { CreateContest } from './pages/admin/create-contest/create-contest';
import { Practice } from './compoennts/practice/practice';
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
        path: 'view-result/:id',
        component: ViewResult,
      },
      {
        path: 'create-contest',
        component: CreateContest,
      },
    ],
  },
  {
    path: 'admin-login',
    component: AdminLogin,
  },
  {
    path: 'path',
    component: Practice,
  },
];
