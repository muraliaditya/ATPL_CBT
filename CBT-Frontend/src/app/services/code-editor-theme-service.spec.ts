import { TestBed } from '@angular/core/testing';

import { CodeEditorThemeService } from './code-editor-theme-service';

describe('CodeEditorThemeService', () => {
  let service: CodeEditorThemeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CodeEditorThemeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
