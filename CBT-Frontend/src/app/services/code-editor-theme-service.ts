import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CodeEditorThemeService {
  private currentTheme = new BehaviorSubject<string>('lightTheme');

  getcurrentTheme() {
    return this.currentTheme.asObservable();
  }

  setTheme() {
    if (this.currentTheme.value === 'lightTheme') {
      (window as any).monaco.editor.setTheme('leetcodeTheme');
      this.currentTheme.next('leetcodeTheme');
    } else {
      (window as any).monaco.editor.setTheme('lightTheme');
      this.currentTheme.next('lightTheme');
    }
  }
  getDefaultTheme(): string {
    return 'lightTheme';
  }
}
