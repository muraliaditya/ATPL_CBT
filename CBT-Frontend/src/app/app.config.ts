import {
  ApplicationConfig,
  provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection,
} from '@angular/core';
import { appReducers } from './store/main-store/app.reducer';

import { provideRouter } from '@angular/router';
import { MyPreset } from './themes/my-preference';

import { providePrimeNG } from 'primeng/config';
import { routes } from './app.routes';

import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { provideHttpClient } from '@angular/common/http';
import { leetcodeTheme } from './themes/code-area-themes/leetcodeTheme';
import { lightTheme } from './themes/code-area-themes/lightTheme';
import {
  NgxMonacoEditorConfig,
  provideMonacoEditor,
} from 'ngx-monaco-editor-v2';
import { NgxCsvParserModule } from 'ngx-csv-parser';
import { provideStore } from '@ngrx/store';

const monacoConfig: NgxMonacoEditorConfig = {
  onMonacoLoad: () => {
    (window as any).monaco.editor.defineTheme('leetcodeTheme', leetcodeTheme);
    (window as any).monaco.editor.defineTheme('lightTheme', lightTheme);
  },
};

export const appConfig: ApplicationConfig = {
  providers: [
    NgxCsvParserModule,
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideNoopAnimations(),
    provideHttpClient(),
    providePrimeNG({
      theme: {
        preset: MyPreset,
        options: {
          darkModeSelector: false,
          cssLayer: {
            name: 'primeng',
            order: 'theme, base, primeng',
          },
        },
      },
    }),
    provideMonacoEditor(monacoConfig),
    provideStore(appReducers),
  ],
};
