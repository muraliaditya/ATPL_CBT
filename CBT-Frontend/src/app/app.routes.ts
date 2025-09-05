import { Routes } from '@angular/router';
import { LoginPage } from './pages/user/login-page/login-page';
import { InstructionsPage } from './pages/user/instructions-page/instructions-page';
import { TestPage } from './pages/user/test-page/test-page';

export const routes: Routes = [
  { path: '', component: LoginPage },
  {
    path: 'test-instructions',
    component: InstructionsPage,
  },
  {
    path: 'test',
    component: TestPage,
  },
];
