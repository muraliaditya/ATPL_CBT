import {
  ApplicationConfig,
  provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { leetcodeTheme } from './leetcodeTheme';
import { lightTheme } from './lightTheme';

import {
  NgxMonacoEditorConfig,
  provideMonacoEditor,
} from 'ngx-monaco-editor-v2';

const monacoConfig: NgxMonacoEditorConfig = {
  onMonacoLoad: () => {
    (window as any).monaco.editor.defineTheme('leetcodeTheme', leetcodeTheme);
    (window as any).monaco.editor.defineTheme('lightTheme', lightTheme);
  },
};

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideMonacoEditor(monacoConfig),
  ],
};
