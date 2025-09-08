import { Routes } from '@angular/router';
import { LoginPage } from './pages/user/login-page/login-page';
import { InstructionsPage } from './pages/user/instructions-page/instructions-page';
import { TestPage } from './pages/user/test-page/test-page';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./pages/user/login-page/login-page').then((c) => c.LoginPage),
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
];
