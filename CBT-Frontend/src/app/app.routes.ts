import { Routes } from '@angular/router';
import { LoginPage } from './pages/user/login-page/login-page';
import { InstructionsPage } from './pages/user/instructions-page/instructions-page';
import { TestPage } from './pages/user/test-page/test-page';
import { AdminLogin } from './pages/admin/admin-login/admin-login';
import { ParticipantForm } from './pages/user/participant-form/participant-form';
import { AdminMainSection } from './pages/admin/admin-main-section/admin-main-section';
import { ViewResult } from './pages/admin/view-result/view-result';
import { ContestListing } from './pages/admin/contest-listing/contest-listing';
import { ViewParticipantResponse } from './pages/admin/view-participant-response/view-participant-response';
import { CreateContest } from './pages/admin/create-contest/create-contest';
import { ViewContest } from './pages/admin/view-contest/view-contest';
import { EditContest } from './pages/admin/edit-contest/edit-contest';
import { MCQListing } from './pages/admin/mcq-listing/mcq-listing';
import { AddMcqQuestion } from './pages/admin/add-mcq-question/add-mcq-question';
import { EditMcq } from './pages/admin/edit-mcq/edit-mcq';
import { RequestDashboard } from './pages/admin/request-dashboard/request-dashboard';
import { DashboardEditmcq } from './pages/admin/dashboard-editmcq/dashboard-editmcq';
import { DashboardViewmcq } from './pages/admin/dashboard-viewmcq/dashboard-viewmcq';
import { DeveloperLogin } from './pages/developer/developer-login/developer-login';
import { DeveloperDashboard } from './pages/developer/developer-dashboard/developer-dashboard';
import { DeveloperMainSection } from './pages/developer/developer-main-section/developer-main-section';
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
    path: 'admin-login',
    component: AdminLogin,
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
        path: '',
        redirectTo: 'contest/manage-contestsList',
        pathMatch: 'full',
      },
      {
        path: 'contest',
        children: [
          {
            path: '',
            redirectTo: 'manage-contestsList',
            pathMatch: 'full',
          },
          {
            path: 'view-result/:id',
            component: ViewResult,
          },
          {
            path: 'view-contest/:id',
            component: ViewContest,
          },
          {
            path: 'manage-contestsList',
            component: ContestListing,
          },
          {
            path: 'view-response/:id',
            component: ViewParticipantResponse,
          },
          {
            path: 'create-contest',
            component: CreateContest,
          },
          { path: 'edit-contest', component: EditContest },
        ],
      },
      {
        path: 'requests',
        children: [
          {
            path: '',
            redirectTo: 'Request-Dashboard',
            pathMatch: 'full',
          },
          {
            path: 'Request-Dashboard',
            component: RequestDashboard,
          },
          {
            path: 'Request-McqEdit/:id',
            component: DashboardEditmcq,
          },
          {
            path: 'Request-McqView/:id',
            component: DashboardViewmcq,
          },
        ],
      },
      {
        path: 'mcqs',
        children: [
          {
            path: '',
            redirectTo: 'View-MCQListing',
            pathMatch: 'full',
          },
          {
            path: 'View-MCQListing',
            component: MCQListing,
          },
          {
            path: 'Add-McqQuestions',
            component: AddMcqQuestion,
          },
          {
            path: 'Edit-Mcq/:id',
            component: EditMcq,
          },
        ],
      },
    ],
  },
  {
    path: 'developer',
    component: DeveloperMainSection,
    children: [
      {
        path: 'developer-dashboard',
        component: DeveloperDashboard,
      },
    ],
  },
  {
    path:'developer-dashboard',component:DeveloperDashboard,
  }
];