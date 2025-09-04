import { Routes } from '@angular/router';
import { LoginPage } from './components/user/login-page/login-page';
import { InstructionsPage } from './components/user/instructions-page/instructions-page';
export const routes: Routes = [
  { path: '', component: LoginPage },
  {
    path: 'test-instructions',
    component: InstructionsPage,
  },
];
