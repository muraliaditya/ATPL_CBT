import { Routes } from '@angular/router';
import { LoginPage } from './pages/user/login-page/login-page';
import { InstructionsPage } from './pages/user/instructions-page/instructions-page';
import { TestPage } from './pages/user/test-page/test-page';
import { AdminLogin } from './pages/admin/admin-login/admin-login';
import { ParticipantForm } from './pages/user/participant-form/participant-form';
import { AdminMainSection } from './pages/admin/admin-main-section/admin-main-section';
import { ViewResult } from './pages/admin/view-result/view-result';
<<<<<<< HEAD
=======
import { ContestListing } from './pages/admin/contest-listing/contest-listing';
>>>>>>> origin/cbt-frontend2
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
<<<<<<< HEAD
=======
    path:'admin-login',component:AdminLogin
  },
  {
>>>>>>> origin/cbt-frontend2
    path: 'admin',
    component: AdminMainSection,
    children: [
      {
<<<<<<< HEAD
        path: 'view-result/:id',
=======
        path: 'manage-contests',
>>>>>>> origin/cbt-frontend2
        component: ViewResult,
      },
    ],
  },
  {
<<<<<<< HEAD
    path: 'admin-login',
    component: AdminLogin,
=======
    path: 'admin',
    component: AdminMainSection,
    children: [
      {
        path: 'manage-contestsList',
        component: ContestListing,
      },
    ],
>>>>>>> origin/cbt-frontend2
  },
];
