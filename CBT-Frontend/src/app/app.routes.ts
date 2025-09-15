import { Routes } from '@angular/router';
import { LoginPage } from './pages/user/login-page/login-page';
import { InstructionsPage } from './pages/user/instructions-page/instructions-page';
import { TestPage } from './pages/user/test-page/test-page';
import { AdminLogin } from './pages/admin/admin-login/admin-login';
import { ParticipantForm } from './pages/user/participant-form/participant-form';
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
    path:'admin-login',component:AdminLogin
  }
];
